package edu.furbiesfighters.players;

public enum PlayerTypes {
	HUMAN,
	AI,
	JARVIS;
	
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
