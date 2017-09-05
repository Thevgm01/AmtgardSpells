package roles;

public class Spell {

	public String name;
	public int cost;
	public int max;
	public String frequency;
	public String type;
	public String school;
	public String range;
	
	private int level = 0;
	
	public Spell(String n, int c, int m, String f, String t, String s, String r) {
		name = n;
		cost = c;
		max = m;
		frequency = f;
		type = t;
		school = s;
		range = r;
		
		if(cost == 0) level = max;
	}
	
	public Spell(Spell other) {
		name = other.name;
		cost = other.cost;
		max = other.max;
		frequency = other.frequency;
		type = other.type;
		school = other.school;
		range = other.range;
		
		level = other.level;
	}
	
	public int getLevel() { return level; }
	public void addLevel() { level++; }
	public void setLevel(int i) { level = i; }
	public int canLevel(int points) {
		if(points >= cost){
			if(max == -1) return 1;
			if(level < max) return 1;
			return -1;
		}
		return -2;
	}
	
	String[] toArray() { return new String[]{name, "" + cost, "" + level, "" + max, frequency, type, school, range}; }
	
}
