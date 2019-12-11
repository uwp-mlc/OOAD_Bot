package edu.dselent.damage;

public abstract class TypeDamageCalculator implements Calculatable
{
	private DamageCalculator parentCalculator;
	
	public TypeDamageCalculator(DamageCalculator parentCalculator)
	{
		this.parentCalculator = parentCalculator;
	}

	protected DamageCalculator getParentCalculator()
	{
		return parentCalculator;
	}
	

	protected void setParentCalculator(DamageCalculator parentCalculator)
	{
		this.parentCalculator = parentCalculator;
	}


	// Chose not to implement the other skills here
	// Method signatures vary for each type's implementation
	
	protected double calculateShootTheMoonDamage(int attackingPlayerIndex, int victimPlayerIndex)
	{
		double conditionalDamage = 0;
		
		if(parentCalculator.getPredictedSkillEnum(attackingPlayerIndex) == parentCalculator.getSkillEnum(victimPlayerIndex))
		{
			conditionalDamage = ConditionalDamageConstants.SHOOT_THE_MOON_CONDITIONAL_DAMAGE;
		}
		
		return conditionalDamage;
	}
	
	protected double calculateReversalOfFortuneDamage(int attackingPlayerIndex)
	{
		return parentCalculator.getCumulativeRandomDamageDifference(attackingPlayerIndex) * -1.0;
	}

}
