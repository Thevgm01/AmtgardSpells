package roles;
import java.util.ArrayList;

public class SpellList {

	public ArrayList<Spell> spells = new ArrayList<Spell>();
	
	public SpellList() {

	}
	
	public Spell find(String name) {
		for(Spell s: spells) if(s.name.equals(name)) return s;
		return null;
	}
	
	public void addSpell(String line) {
		String[] t = line.split("#");
		if(t[2].equals("-")) t[2] = "-1";
		if(t.length != 7) System.out.println("WRONG LENGTH: " + line);
		spells.add(new Spell(t[0], Integer.parseInt(t[1]), Integer.parseInt(t[2]), t[3], t[4], t[5], t[6]));
	}
	
	public void addSpell(Spell other) {
		spells.add(other);
	}
	
	public void printTable(boolean numbered) { printTable(null, numbered); }
	public void printTable(SpellList other, boolean numbered) {
		if(other == null && spells.size() == 0) return;
		
		String[] header = new String[]{"Name", "Cost", "Level", "Max", "Frequency", "Type", "School", "Range"};
		int[] longest = new int[7];
		for(int i = -1; i < spells.size(); i++){
			String[] sArray;
			if(i == -1) sArray = header;
			else sArray = spells.get(i).toArray();
			for(int j = 0; j < longest.length; j++){
				if(sArray[j].length() > longest[j]) longest[j] = sArray[j].length();
			}
		}
		for(int i = -1; i < spells.size(); i++){
			String[] sArray;
			if(i == -1){
				sArray = header;
				if(numbered) System.out.print("    ");
			}
			else{
				sArray = spells.get(i).toArray();
				if(other != null) for(Spell s: other.spells) if(s.name.equals(sArray[0])) sArray[2] = "" + s.getLevel();
				if(sArray[2].equals("0")) sArray[2] = "";
				if(sArray[3].equals("-1")) sArray[3] = "-";
				
				if(numbered) System.out.printf("%2d: ", i+1);
			}
			for(int j = 0; j < longest.length; j++){
				String form = "%-" + (longest[j]+2) + "s";
				System.out.printf(form, sArray[j]);
			}
			System.out.println();
			//if(i == -1) System.out.println();
		}
	}
	
}
