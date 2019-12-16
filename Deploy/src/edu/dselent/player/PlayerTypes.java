package edu.dselent.player;

import edu.dselent.utils.Utils;

public enum PlayerTypes
{
	HUMAN,
	COMPUTER,
	JARVIS,
	MLC;
	// Add more here
	
	@Override
	public String toString()
	{		
		return Utils.convertEnumString(this.name());
	}
}
