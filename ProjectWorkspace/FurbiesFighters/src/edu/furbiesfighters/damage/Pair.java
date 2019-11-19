package edu.furbiesfighters.damage;
/**
 * A class for pairing random and condition damage together.
 */
public class Pair 
{
	private double random;
	private double conditional;
	
	/**
	 * Constructor for the class. It assigns the two variables
	 * @param random, The random damage
	 * @param conditional, The conditional damage.
	 */
	public Pair(double random, double conditional)
	{
		this.random = random;
		this.conditional = conditional;
	}

	/**
	 * Method for getting the random variable.
	 * @return
	 */
	public double getRandom() {
		return random;
	}

	/**
	 * Method for setting the random variable
	 * @param random, the random damage.
	 */
	public void setRandom(double random) {
		this.random = random;
	}

	/**
	 * Method for getting the conditional variable.
	 * @return conditional, the variable.
	 */
	public double getConditional() {
		return conditional;
	}

	/**
	 * Method for setting the conditional variable.
	 * @param conditional, the variable you want to set it to.
	 */
	public void setConditional(double conditional) {
		this.conditional = conditional;
	}
}
