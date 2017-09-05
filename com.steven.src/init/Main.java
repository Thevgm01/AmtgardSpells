package init;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import character.Character;
import roles.Role;
import roles.SpellList;

public class Main {

	// This is simply a test
	
	public static void main(String[] args) throws IOException {
		Role[] roles = loadRoles("/roles/roles.txt");
		Input in = new Input();
		menu(in, roles);
	}
	
	private static void menu(Input in, Role[] roles) throws IOException {
		System.out.println("Hello!");
		
		File f = new File("Characters");
		if(!f.exists());
			f.mkdir();

		File[] files = f.listFiles(); // Character loading
		ArrayList<Character> characters = new ArrayList<Character>();
		for(int i = 0; i < files.length; i++){
			FileReader fis = new FileReader(files[i].getPath());
			BufferedReader br = new BufferedReader(fis);
			
			String[] top = br.readLine().split("#");
			Role r = null; for(Role role: roles) if(role.getName().equals(top[1])) r = role;
			
			characters.add(new Character(top[0], r, Integer.parseInt(top[2])));
			
			String spells = "", line;
			while((line = br.readLine()) != null) { spells += "\n" + line; }
			characters.get(i).load(spells);
			
			br.close();
		}
		
		if(files.length == 0){
			System.out.println("There are no existing characters. Would you like to create one? (y/n)");
			switch(in.yn()){
			case 'y': Character c = createCharacter(in, roles);
					  characters.add(c);
					  characterMenu(c, in);
					  break;
			case 'n': System.out.println("Well... okay then."); System.exit(1);
			}
		}
		boolean exit = false;
		while(!exit){
			sortCharacters(characters);
			System.out.println("Here are all of your previous characters.");
			for(int i = 0; i < characters.size(); i++)
				System.out.printf("%d: %s (level %d %s)\n", i+1, characters.get(i).getName(), characters.get(i).getLevel(), characters.get(i).getRole().getName());
			System.out.printf("%d: Create a new character\n%d: Quit\n", characters.size() + 1, characters.size() + 2);
			int choice = in.intRange(1, characters.size() + 2);
			if(choice < characters.size() + 1){ // Selected a character
				characterMenu(characters.get(choice-1), in);
			}
			else if(choice == characters.size() + 1) { // Made a new character
				Character c = createCharacter(in, roles);
				characters.add(c);
				characterMenu(c, in);
			}
			else if(choice == characters.size() + 2) { // Exited
				System.out.println("See you later.");
				exit = true;
			}
		}
	}

	private static void sortCharacters(ArrayList<Character> list) {
		if(list.size() <= 1) return;
		boolean swapped = true;
		while(swapped){
			swapped = false;
			for(int i = 0; i < list.size()-1; i++){
				if(list.get(i).compareTo(list.get(i+1)) < 0){
					//System.out.println("Swapped");
					Character temp = list.get(i);
					list.set(i, list.get(i+1));
					list.set(i+1, temp);
					swapped = true;
				}
			}
		}
	}
	
	private static void characterMenu(Character c, Input in) throws IOException {
		System.out.printf("%s, the level %d %s.\n", c.getName(), c.getLevel(), c.getRole().getName());
		boolean exit = false;
		while(!exit){
			System.out.println("What would you like to do?\n1: Level up\n2: View Spells\n3: Back");
			switch(in.intRange(1, 3)){
			case 1: c.levelUp(in); break;
			case 2: c.printSpells(); break;
			case 3: exit = true; break;
			}
		}
		System.out.println("Goodbye.");
		
		BufferedWriter bw = new BufferedWriter(new FileWriter("Characters/" + c.getName() + ".txt"));
		bw.write(c.export());
		bw.close();
	}
	
	private static Character createCharacter(Input in, Role[] classes) {
		System.out.println("What will your name be?");
		String n = in.next();
		System.out.println("Okay, " + n + ". Here are the classes you can pick from:");
		for(int i = 0; i < classes.length; i++)
			System.out.println((i+1) + ": " + classes[i].getName());
		System.out.println("Which one would you like to play as?");
		int c = in.intRange(1, classes.length) - 1;
		System.out.println("Welcome, " + classes[c].getName() + " " + n + ".");
		System.out.println("What level are you? (1-" + CONSTANTS.MAX_LEVEL + ")");
		int l = in.intRange(1, 6);
		
		Character character = new Character(n, classes[c], l);
		
		for(int i = l; i > 0; i--) character.chooseSpells(i, in);
		
		return character;
	}
	
	private static Role[] loadRoles(String path) throws IOException {
		InputStream is = Class.class.getResourceAsStream(path);
		BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		String[] names = br.readLine().split(" ");
		is.close(); br.close();
		
		Role[] roles = new Role[names.length];
		for(int i = 0; i < names.length; i++)
			roles[i] = loadRole(names[i], "/roles/spells/" + names[i] + ".txt");
		return roles;
	}
	
	private static Role loadRole(String title, String path) throws IOException {
		Role c = new Role(title);
		InputStream is = Class.class.getResourceAsStream(path);
		BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		
		String line = null;
		int index = 0;
		while((line = br.readLine()) != null){
			if(line.length() == 1){
				index = Integer.parseInt(line);
				c.levels[index-1] = new SpellList();
			}
			else if(line.length() != 0){
				c.levels[index-1].addSpell(line);
			}
		}
		
		is.close();	br.close();
		
		return c;
	}
	
}
