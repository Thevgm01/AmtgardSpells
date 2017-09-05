package roles;

import init.CONSTANTS;

public class Role {

	private String name;
	
	public SpellList[] levels = new SpellList[CONSTANTS.MAX_LEVEL];
	
	public Role(String n) {
		name = n;
	}
	
	public String getName() { return name; }
	
}
