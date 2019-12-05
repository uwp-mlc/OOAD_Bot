package edu.dselent.damage;

import edu.dselent.skill.Skills;


public class IntelligenceDamageCalculator  extends TypeDamageCalculator
{	
	public IntelligenceDamageCalculator(DamageCalculator parentCalculator)
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
		
		boolean isSTMRecharing = getParentCalculator().isSkillRecharing(victimPlayerIndex, Skills.SHOOT_THE_MOON);
		boolean isScissorsRecharging = getParentCalculator().isSkillRecharing(victimPlayerIndex, Skills.SCISSORS_POKE);
		boolean isRockRecharging = getParentCalculator().isSkillRecharing(victimPlayerIndex, Skills.ROCK_THROW);
		
		if(isSTMRecharing)
		{
			conditionalDamage = conditionalDamage + ConditionalDamageConstants.INTELLIGENCE_CONDITIONAL_DAMAGE_1;
		}
		
		if(isScissorsRecharging)
		{
			conditionalDamage = conditionalDamage + ConditionalDamageConstants.INTELLIGENCE_CONDITIONAL_DAMAGE_2;
		}
		
		if(isRockRecharging)
		{
			conditionalDamage = conditionalDamage + ConditionalDamageConstants.INTELLIGENCE_CONDITIONAL_DAMAGE_1;
		}
		
		return conditionalDamage;
	}

	protected double calculateScissorPokeDamage(int victimPlayerIndex, double randomDamage)
	{
		double conditionalDamage = 0.0;
		
		boolean isSTMRecharing = getParentCalculator().isSkillRecharing(victimPlayerIndex, Skills.SHOOT_THE_MOON);
		boolean isPaperRecharging = getParentCalculator().isSkillRecharing(victimPlayerIndex, Skills.PAPER_CUT);
		boolean isScissorsRecharging = getParentCalculator().isSkillRecharing(victimPlayerIndex, Skills.SCISSORS_POKE);
		
		if(isSTMRecharing)
		{
			conditionalDamage = conditionalDamage + ConditionalDamageConstants.INTELLIGENCE_CONDITIONAL_DAMAGE_1;
		}
		
		if(isScissorsRecharging)
		{
			conditionalDamage = conditionalDamage + ConditionalDamageConstants.INTELLIGENCE_CONDITIONAL_DAMAGE_1;
		}
		
		if(isPaperRecharging)
		{
			conditionalDamage = conditionalDamage + ConditionalDamageConstants.INTELLIGENCE_CONDITIONAL_DAMAGE_2;
		}
		
		return conditionalDamage;
	}

	protected double calculatePaperCutDamage(int victimPlayerIndex, double randomDamage)
	{
		double conditionalDamage = 0.0;
		
		boolean isSTMRecharing = getParentCalculator().isSkillRecharing(victimPlayerIndex, Skills.SHOOT_THE_MOON);
		boolean isRockRecharging = getParentCalculator().isSkillRecharing(victimPlayerIndex, Skills.ROCK_THROW);
		boolean isPaperRecharging = getParentCalculator().isSkillRecharing(victimPlayerIndex, Skills.PAPER_CUT);
		
		
		if(isSTMRecharing)
		{
			conditionalDamage = conditionalDamage + ConditionalDamageConstants.INTELLIGENCE_CONDITIONAL_DAMAGE_1;
		}
		
		if(isRockRecharging)
		{
			conditionalDamage = conditionalDamage + ConditionalDamageConstants.INTELLIGENCE_CONDITIONAL_DAMAGE_2;
		}
		
		if(isPaperRecharging)
		{
			conditionalDamage = conditionalDamage + ConditionalDamageConstants.INTELLIGENCE_CONDITIONAL_DAMAGE_1;
		}
		
		return conditionalDamage;
	}
}
