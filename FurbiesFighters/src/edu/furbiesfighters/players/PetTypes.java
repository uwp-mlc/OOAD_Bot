package edu.furbiesfighters.players;
/**
 * Enumeration to encapsulate all pet types. 
 */

/**
 * Enumeration of the types. Has a different enumeration for each type.
 */
public enum PetTypes {
	INTELLIGENCE,
	SPEED,
	POWER;
	
	/**
	 * Returns the string representation of the type.
	 */
	@Override
	public String toString()
	{
		String name;
		
		name = Character.toUpperCase(name().charAt(0)) + name().substring(1);
		
		return name;
	}
}
