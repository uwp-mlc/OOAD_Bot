/**
 * 
 */
package edu.furbiesfighters.events;

/**
 * An enumeration class that holds an enumeration for each event type.
 * @author Furbies Fighters
 *
 */
public enum EventTypes {
	ATTACK,
	FIGHT_START,
	ROUND_START;
	
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
