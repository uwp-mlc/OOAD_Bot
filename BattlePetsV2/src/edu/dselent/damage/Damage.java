package edu.dselent.damage;

public class Damage
{
	private double randomDamage;
	private double conditionalDamage;
	
	public Damage(double randomDamage, double conditionalDamage)
	{
		this.randomDamage = randomDamage;
		this.conditionalDamage = conditionalDamage;
	}

	public Damage(Damage otherDamage)
	{
		this.randomDamage = otherDamage.randomDamage;
		this.conditionalDamage = otherDamage.conditionalDamage;
	}

	public double getRandomDamage()
	{
		return randomDamage;
	}

	public void setRandomDamage(double randomDamage)
	{
		this.randomDamage = randomDamage;
	}
	
	public double getConditionalDamage()
	{
		return conditionalDamage;
	}
	
	public void setConditionalDamage(double conditionalDamage)
	{
		this.conditionalDamage = conditionalDamage;
	}

	public double calculateTotalDamage()
	{
		return randomDamage + conditionalDamage;
	}

}
