package edu.dselent.damage;

import edu.dselent.skill.Skills;

public class SpeedDamageCalculator extends TypeDamageCalculator
{	
	public SpeedDamageCalculator(DamageCalculator parentCalculator)
	{
		super(parentCalculator);
	}

	
	// Copied and pasted for all three types...
	// Method signatures for delegated methods vary
	@Override
	public Damage calculateDamage(int attackingPlayerIndex, int victimPlayerIndex)
	{
		double randomDamage = getParentCalculator().calculateRandomDamage();
		double conditionalDamage = -1;
		
		Skills attackingSkillEnum = getParentCalculator().getSkillEnum(attackingPlayerIndex);
		
		if(attackingSkillEnum == Skills.ROCK_THROW)
		{
			conditionalDamage = calculateRockThrowDamage(victimPlayerIndex);
		}
		else if(attackingSkillEnum == Skills.SCISSORS_POKE)
		{
			conditionalDamage = calculateScissorPokeDamage(victimPlayerIndex);
		}
		else if(attackingSkillEnum == Skills.PAPER_CUT)
		{
			conditionalDamage = calculatePaperCutDamage(victimPlayerIndex);
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

	protected double calculateRockThrowDamage(int victimPlayerIndex)
	{
		double conditionalDamage = 0.0;
		
		Skills victimSkillEnum = getParentCalculator().getSkillEnum(victimPlayerIndex);
		
		double currentHp = getParentCalculator().getCurrentHp(victimPlayerIndex);
		double startingHp = getParentCalculator().getStartingHp(victimPlayerIndex);
		double victimHpPercent = currentHp / startingHp * 100.0;
		
		if((victimSkillEnum == Skills.SCISSORS_POKE || victimSkillEnum == Skills.PAPER_CUT) && victimHpPercent >= ConditionalDamageConstants.SPEED_CONDITIONAL_CRITERIA_1)
		{
			conditionalDamage = ConditionalDamageConstants.SPEED_CONDITIONAL_DAMAGE;
		}
		
		return conditionalDamage;
	}

	protected double calculateScissorPokeDamage(int victimPlayerIndex)
	{
		double conditionalDamage = 0.0;
		
		Skills victimSkillEnum = getParentCalculator().getSkillEnum(victimPlayerIndex);
		
		double currentHp = getParentCalculator().getCurrentHp(victimPlayerIndex);
		double startingHp = getParentCalculator().getStartingHp(victimPlayerIndex);
		double victimHpPercent = currentHp / startingHp * 100.0;
		
		if((victimSkillEnum == Skills.ROCK_THROW || victimSkillEnum == Skills.PAPER_CUT) &&
				victimHpPercent < ConditionalDamageConstants.SPEED_CONDITIONAL_CRITERIA_1 &&
				victimHpPercent >= ConditionalDamageConstants.SPEED_CONDITIONAL_CRITERIA_2 )
		{
			conditionalDamage = ConditionalDamageConstants.SPEED_CONDITIONAL_DAMAGE;
		}
		
		return conditionalDamage;
	}

	protected double calculatePaperCutDamage(int victimPlayerIndex)
	{
		double conditionalDamage = 0.0;
		
		Skills victimSkillEnum = getParentCalculator().getSkillEnum(victimPlayerIndex);
		
		double currentHp = getParentCalculator().getCurrentHp(victimPlayerIndex);
		double startingHp = getParentCalculator().getStartingHp(victimPlayerIndex);
		double victimHpPercent = currentHp / startingHp * 100.0;
		
		if((victimSkillEnum == Skills.ROCK_THROW || victimSkillEnum == Skills.SCISSORS_POKE) && victimHpPercent < ConditionalDamageConstants.SPEED_CONDITIONAL_CRITERIA_2)
		{
			conditionalDamage = ConditionalDamageConstants.SPEED_CONDITIONAL_DAMAGE;
		}
		
		return conditionalDamage;
	}
}
