package edu.dselent.skill;

import edu.dselent.utils.Utils;

public enum Skills
{
	ROCK_THROW,
	SCISSORS_POKE,
	PAPER_CUT,
	SHOOT_THE_MOON,
	REVERSAL_OF_FORTUNE;
	
	@Override
	public String toString()
	{		
		return Utils.convertEnumString(this.name());
	}
}
