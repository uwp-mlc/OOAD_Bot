package edu.dselent.damage;

import edu.dselent.skill.Skills;

public class PowerDamageCalculator extends TypeDamageCalculator
{
	public PowerDamageCalculator(DamageCalculator parentCalculator)
	{
		super(parentCalculator);
	}

	@Override
	public Damage calculateDamage(int attackingPlayerIndex, int victimPlayerIndex)
	{
		double randomDamage = getParentCalculator().calculateRandomDamage();
		double conditionalDamage = -1;
		
		Skills attackingSkillEnum = getParentCalculator().getSkillEnum(attackingPlayerIndex);
		
		if(attackingSkillEnum == Skills.ROCK_THROW)
		{
			conditionalDamage = calculateRockThrowDamage(victimPlayerIndex, randomDamage);
		}
		else if(attackingSkillEnum == Skills.SCISSORS_POKE)
		{
			conditionalDamage = calculateScissorPokeDamage(victimPlayerIndex, randomDamage);
		}
		else if(attackingSkillEnum == Skills.PAPER_CUT)
		{
			conditionalDamage = calculatePaperCutDamage(victimPlayerIndex, randomDamage);
		}
		else if(attackingSkillEnum == Skills.SHOOT_THE_MOON)
		{
			conditionalDamage = super.calculateShootTheMoonDamage(attackingPlayerIndex, victimPlayerIndex);
		}
		else if(attackingSkillEnum == Skills.REVERSAL_OF_FORTUNE)
		{
			conditionalDamage = super.calculateReversalOfFortuneDamage(attackingPlayerIndex);
			randomDamage = randomDamage + conditionalDamage;
		}
		else
		{
			// TODO throw custom exception
			throw new RuntimeException("Unknown skill choice:" + attackingSkillEnum);
		}
		
		return new Damage(randomDamage, conditionalDamage);
	}

	protected double calculateRockThrowDamage(int victimPlayerIndex, double randomDamage)
	{
		double conditionalDamage = 0.0;
		
		Skills victimSkillEnum = getParentCalculator().getSkillEnum(victimPlayerIndex);
		
		if(victimSkillEnum == Skills.SCISSORS_POKE)
		{
			conditionalDamage = ConditionalDamageConstants.POWER_CONDITIONAL_DAMAGE * randomDamage;
		}
		
		return conditionalDamage;
	}

	protected double calculateScissorPokeDamage(int victimPlayerIndex, double randomDamage)
	{
		double conditionalDamage = 0.0;
		
		Skills victimSkillEnum = getParentCalculator().getSkillEnum(victimPlayerIndex);
		
		if(victimSkillEnum == Skills.PAPER_CUT)
		{
			conditionalDamage = ConditionalDamageConstants.POWER_CONDITIONAL_DAMAGE * randomDamage;
		}
		
		return conditionalDamage;
	}

	protected double calculatePaperCutDamage(int victimPlayerIndex, double randomDamage)
	{
		double conditionalDamage = 0.0;
		
		Skills victimSkillEnum = getParentCalculator().getSkillEnum(victimPlayerIndex);
		
		if(victimSkillEnum == Skills.ROCK_THROW)
		{
			conditionalDamage = ConditionalDamageConstants.POWER_CONDITIONAL_DAMAGE * randomDamage;
		}
		
		return conditionalDamage;
	}

}
