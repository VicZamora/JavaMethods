package default;

import java.util.ArrayList;

public class Coordinates {

	public static ArrayList<Double> leerRuta(BufferedReader lector) throws IOException {
		ArrayList<Double> cGeo = new ArrayList<Double>();
		// Para leer el archivo
		String contenido = "", palabra = "", numero = "";
		// Se lee el fichero completo (solo tiene una línea)
		contenido = lector.readLine();
		// Se recorre parando en los espacios en blanco
		for (int i = 0; i < contenido.length(); i++) {
			if (contenido.charAt(i) == ' ' || i == contenido.length() - 1) {
				// Si es latitud
				if (palabra.charAt(0) == 'l' && palabra.charAt(1) == 'a' && palabra.charAt(2) == 't') {
					// Obtener el numero
					for (int j = 5; j < palabra.length(); j++) {
						if (palabra.charAt(j) != '"') {
							numero += palabra.charAt(j);
						} else {
							break;
						}
					}
					// Meter a lista
					c.add(Double.parseDouble(numero));
					numero = "";
				}

				// Si es longitud
				if (palabra.charAt(0) == 'l' && palabra.charAt(1) == 'o' && palabra.charAt(2) == 'n') {
					// Obtener el numero
					for (int j = 5; j < palabra.length(); j++) {
						if (palabra.charAt(j) != '"') {
							numero += palabra.charAt(j);
						} else {
							break;
						}
					}
					// Meter a lista
					cGeo.add(Double.parseDouble(numero));
					numero = "";
				}
				// Se reinicia la palabra
				palabra = "";
			} else {
				palabra += contenido.charAt(i);
			}

		}
		return cGeo;
	}


	public static ArrayList<double[]> pasarGeo_UTM(ArrayList<Double> cGeo) {
		ArrayList<double[]> cUTM = new ArrayList<double[]>();
		double[] aux;
		for (int i = 0; i < cGeo.size(); i += 2) {
			aux = new double[2];
			double latDeci = cGeo.get(i);
			double lonDeci = cGeo.get(i + 1);
			
			// Pasar a radianes
			double latRad = latDeci * Math.PI / 180;
			double lonRad = lonDeci * Math.PI / 180;
			
			// Geometria del elipsoide de Hayford
			double semiejeMayor = 6378388.0; // a
			double semiejeMenor = 6356911.946130; // b
			double excentricidad = Math.sqrt(Math.pow(semiejeMayor, 2) - Math.pow(semiejeMenor, 2)) / semiejeMayor; // e
			double segundaExcentricidad = Math.sqrt(Math.pow(semiejeMayor, 2) - Math.pow(semiejeMenor, 2))/ semiejeMenor; // e'
			double radioPolarCurvatura = Math.pow(semiejeMayor, 2) / semiejeMenor;
			double aplanamiento = (semiejeMayor - semiejeMenor) / semiejeMayor; // alpha
			
			// Factor de escala aplicado para proyeccion UTM
			double factorEscala = 0.9996;
			
			// Obtener huso
			int huso = (int) Math.floor((lonDeci / 6) + 31);
			
			// Obtener meridiano central del huso en el que caen las coordenadas
			// geodesicas sobre las que operamos
			int meridianoCentralHusoDeci = huso * 6 - 183;
			double meridianoCentralHusoRad = meridianoCentralHusoDeci * Math.PI / 180;
			
			// Calcular distancia angular entre longitud del punto con el que
			// operamos y el meridiano central del huso
			double distanciaAngular = lonRad - meridianoCentralHusoRad;
			
			// Calculo de parametros encadenados unos a otros, nucleo de las
			// ecuaciones de Coticchia-Surace
			double A = Math.cos(latRad) * Math.sin(distanciaAngular);
			double xi = 0.5 * Math.log((1 + A) / (1 - A));
			double eta = Math.atan((Math.tan(latRad)) / (Math.cos(distanciaAngular))) - latRad;
			double ni = factorEscala * radioPolarCurvatura/ Math.pow((1 + Math.pow(segundaExcentricidad, 2) * Math.pow(Math.cos(latRad), 2)), 0.5);
			double dseta = Math.pow(segundaExcentricidad, 2) / 2 * Math.pow(xi, 2) * Math.pow(Math.cos(latRad), 2);
			double A1 = Math.sin(2 * latRad);
			double A2 = A1 * Math.pow(Math.cos(latRad), 2);
			double J2 = latRad + A1 / 2;
			double J4 = (3 * J2 + A2) / 4;
			double J6 = (5 * J4 + A2 * Math.pow(Math.cos(latRad), 2)) / 3;
			double alpha = 0.75 * Math.pow(segundaExcentricidad, 2);
			double beta = (double) 5 / 3 * Math.pow(alpha, 2);
			double gamma = (double) 35 / 27 * Math.pow(alpha, 3);
			double B0 = factorEscala * radioPolarCurvatura * (latRad - alpha * J2 + beta * J4 - gamma * J6);

			// Calculo final de coordenadas
			double x = xi * ni * (1 + dseta / 3) + 500000;
			double y = eta * ni * (1 + dseta) + B0;
			aux[0] = x;
			aux[1] = y;
			cUTM.add(aux);
		}
		return cUTM;
	}
	
}
