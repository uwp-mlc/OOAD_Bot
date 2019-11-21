package edu.furbiesfighters.skills;
/**
 * 
 */

/**
 * Enumeration for skills. Has a different enumeration for each skill.
 */
public enum Skills {
	ROCK_THROW,
	SCISSOR_POKE,
	PAPER_CUT,
	SHOOT_THE_MOON,
	REVERSAL_OF_FORTUNE;
	
	/**
	 * Returns the string representation of the skill.
	 */
	@Override
	public String toString()
	{
		return name().toLowerCase().replaceAll("_", " ");
	}
}
