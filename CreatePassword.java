import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {

		// length of the password
		int length = 20;
		// Intermediate passwords
		char [][] fake = new char [5][length];
		// final password
		char [] pass = new char [length];
		
		// possible symbols
		ArrayList<Character> poss = new ArrayList<Character>();
		poss.add('%');
		poss.add('&');
		poss.add('*');
		poss.add('#');
		poss.add('@');
		poss.add('^');
		poss.add('¨');
		poss.add('Ç');
		poss.add('>');
		poss.add('<');
		poss.add('ç');
		
		// numbers
		for (int i = 48; i <= 57; i++) {
			poss.add((char)i);
		}
		
		// letters
		for (int i = 65; i <= 90; i++) {
			poss.add((char)i);
		}
		
		// letters mayus
		for (int i = 97; i <= 122; i++) {
			poss.add((char)i);
		}
		
		// 5 secondary passwords
		for (int i = 0; i < fake.length; i++) {
			for (int j = 0; j < fake[i].length; j++) {
				fake[i][j] = poss.get((int)(Math.random()*(poss.size())));
				System.out.print(fake[i][j]);
			}
			System.out.println();
		}
		
		// Final password is a combination of the 5 fake passwords
		System.out.println("Contraseña: ");
		for (int i = 0; i < pass.length; i++) {
			pass[i] = poss.get(((fake[0][i]+fake[1][i]+fake[2][i]+fake[3][i]+fake[4][i])%(poss.size()-1)));
			System.out.print(pass[i]);
		}
		
	}

}
