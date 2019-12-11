package edu.dselent.damage;

import edu.dselent.control.TextRoundControl;
import edu.dselent.player.PetTypes;
import edu.dselent.skill.Skills;

// If more than one damage calculator is created, can consider abstracting attributes/methods
public class DamageCalculator implements Calculatable
{
	// TODO CHANGE TO INTERFACE to support both TEXT AND UI versions
	private TextRoundControl roundControl;
	
	// TODO
	// Hard-coded for now, otherwise would need to come through game settings
	private static final double MIN_RANDOM = 0.0;
	private static final double MAX_RANDOM = 5.0;

	
	private Calculatable powerDamageCalculator;
	private Calculatable speedDamageCalculator;
	private Calculatable intelligenceDamageCalculator;
	
	public DamageCalculator(TextRoundControl roundControl)
	{
		this.roundControl = roundControl;
		
		powerDamageCalculator = new PowerDamageCalculator(this);
		speedDamageCalculator = new SpeedDamageCalculator(this);
		intelligenceDamageCalculator = new IntelligenceDamageCalculator(this);
	}

	protected Skills getSkillEnum(int playerIndex)
	{
		return roundControl.getPlayerSkill(playerIndex);
	}
	
	protected Skills getPredictedSkillEnum(int playerIndex)
	{
		return roundControl.getPredictedSkillEnum(playerIndex);
	}
	
	protected double getCumulativeRandomDamageDifference(int playerIndex)
	{
		return roundControl.getCumulativeRandomDamageDifference(playerIndex);
	}
	
	protected static double getMinRandom()
	{
		return MIN_RANDOM;
	}

	protected static double getMaxRandom()
	{
		return MAX_RANDOM;
	}
	
	protected double calculateRandomDamage()
	{
		return roundControl.getRngHolder().nextDouble(MIN_RANDOM, MAX_RANDOM);
	}

	@Override
	public Damage calculateDamage(int attackingPlayerIndex, int victimPlayerIndex)
	{
		Damage damage = null;
		
		PetTypes attackingPetType = roundControl.getPetType(attackingPlayerIndex);
		
		if(attackingPetType == PetTypes.POWER)
		{
			damage = powerDamageCalculator.calculateDamage(attackingPlayerIndex, victimPlayerIndex);
		}
		else if(attackingPetType == PetTypes.SPEED)
		{
			damage = speedDamageCalculator.calculateDamage(attackingPlayerIndex, victimPlayerIndex);
		}
		else if(attackingPetType == PetTypes.INTELLIGENCE)
		{
			damage = intelligenceDamageCalculator.calculateDamage(attackingPlayerIndex, victimPlayerIndex);
		}
		else
		{
			// TODO throw custom exception
			throw new RuntimeException("Invalid pet type: " + attackingPetType);
		}

		return damage;		
	}

	public double getCurrentHp(int victimPlayerIndex)
	{
		return roundControl.getPlayableHp(victimPlayerIndex);
	}
	
	public double getStartingHp(int victimPlayerIndex)
	{
		return roundControl.getPlayableStartingHp(victimPlayerIndex);
	}
	
	public boolean isSkillRecharing(int playerIndex, Skills skill)
	{
		return roundControl.isSkillRecharging(playerIndex, skill);
	}


}
