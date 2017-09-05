package character;
import init.CONSTANTS;
import init.Input;
import roles.Role;
import roles.Spell;
import roles.SpellList;

public class Character {

	private String name;
	private Role role;
	private int level;
		
	public SpellList[] spells = new SpellList[CONSTANTS.MAX_LEVEL];
	
	public String getName() { return name; }
	public Role getRole() { return role; }
	public int getLevel() { return level; }

	public Character(String str) {
		load(str);
		
		for(int i = 0; i < CONSTANTS.MAX_LEVEL; i++)
			spells[i] = new SpellList();
	}
	
	public Character(String n, Role r, int l) {
		name = n;
		role = r;
		level = l;
		
		for(int i = 0; i < CONSTANTS.MAX_LEVEL; i++)
			spells[i] = new SpellList();
	}
	
	public String export() {
		String result = name + "#" + role.getName() + "#" + level;
		for(int i = 0; i < CONSTANTS.MAX_LEVEL; i++){
			if(spells[i] != null){
				for(Spell s: spells[i].spells){
					result += "\n" + (i+1) + "#" + s.name + "#" + s.getLevel();
				}
			}
		}
		return result;
	}
	
	public void load(String str) {
		String[] lines = str.split("\n");
		
		for(int i = 1; i < lines.length; i++){			
			String[] tokens = lines[i].split("#");
			int r = Integer.parseInt(tokens[0]) - 1;
			String name = tokens[1];
			int l = Integer.parseInt(tokens[2]);
						
			for(Spell s: role.levels[r].spells){
				if(s.name.equals(name)){
					Spell newSpell = new Spell(s);
					newSpell.setLevel(l);
					if(spells[r] == null) spells[r] = new SpellList();
					spells[r].addSpell(newSpell);
				}
			}
		}
	}
	
	public void printSpells() {
		for(int i = 0; i < level; i++){
			System.out.printf("Level %d %s spells\n", i+1, role.getName());
			spells[i].printTable(false);
			System.out.println();
		}
	}
	
	public void levelUp(Input in) {
		if(level >= CONSTANTS.MAX_LEVEL) return;
		level++;
		System.out.printf("Congrats! You are now level %d.\n", level);
		chooseSpells(level, in);
	}
	
	public void chooseSpells(int level, Input in) {
		int points = 5;

		System.out.println("Pick your spells from level " + level + " and below.");
		System.out.println();
		
		for(Spell s: role.levels[level-1].spells) if(s.getLevel() > 0){
			System.out.printf("You have learned %s!\n", s.name);
			spells[level-1].addSpell(new Spell(s));
		}
		
		while(points > 0){
			System.out.printf("You have %d points remaining.\n", points);
			System.out.println("What spell list would you like to view?");
			for(int i = 0; i < level; i++)
				System.out.printf("%d: Level %d %s spells.\n", i+1, i+1, role.getName());
			int a = in.intRange(1, level) - 1;
			role.levels[a].printTable(spells[a], true);
			System.out.println();
			System.out.printf("Which level %d %s spell would you like to put a point into? (you can also type \"back\")\n", (a+1), role.getName());
			int b;
			do{
				b = in.intRange(1, role.levels[a].spells.size(), "back") - 1;
				if(b > -1){
					boolean known = true;
					Spell s = spells[a].find(role.levels[a].spells.get(b).name);
					if(s == null){
						s = new Spell(role.levels[a].spells.get(b));
						known = false;
					}
					switch(s.canLevel(points)){
					case 1:	s.addLevel();
							points -= s.cost;
							if(!known) spells[a].addSpell(s);
							System.out.printf("You are now level %d in %s.\n", s.getLevel(), s.name);
							System.out.printf("You have %d point(s) remaining.\n", points);
							break;
					case -1:System.out.printf("You are at the max level for that spell.\n", points);
							break;
					case -2:System.out.printf("You don't have enough points for that spell.\n", points);
							break;
					}
				}
			} while(b > -1 && points > 0);
		}
		System.out.println();
	}
	
	public int compareTo(Character other) {
		if(level > other.level) return 1;
		else if(level < other.level) return -1;
		else return -name.compareTo(other.name);
	}
	
}
