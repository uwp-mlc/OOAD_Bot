package edu.dselent.domain;

import java.util.Random;

/**
 * Okay to have this public since the game control creates its own instance
 * @author Doug
 *
 */
public class RngHolder
{
	private Long randomSeed;
	private Random thePrecious;
	
	public RngHolder()
	{
		thePrecious = new Random();
	}
	
	public RngHolder(long randomSeed)
	{
		this.randomSeed = randomSeed;
		thePrecious = new Random(randomSeed);
	}

	public Long getRandomSeed()
	{
		return randomSeed;
	}
	
	public double nextDouble()
	{
		return thePrecious.nextDouble();
	}
	
	// TODO input validation checks
	public double nextDouble(double min, double max)
	{
		return thePrecious.nextDouble()*(max - min) + min;
	}
	
}
