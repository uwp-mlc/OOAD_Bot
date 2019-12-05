package edu.dselent.player;

import edu.dselent.utils.Utils;

// curious - pet types as data type stored in pet or sub classes of pet
// type can be switched between battles -> should be attribute not sub class
public enum PetTypes
{
	POWER,
	SPEED,
	INTELLIGENCE;
	
	@Override
	public String toString()
	{		
		return Utils.convertEnumString(this.name());
	}
}
