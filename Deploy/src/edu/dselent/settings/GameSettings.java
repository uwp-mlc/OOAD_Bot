package edu.dselent.settings;

public class GameSettings
{
	private long randomSeed;
	private int numberOfPlayers;
	private int numberOfFights;

	
	public GameSettings(long randomSeed, int numberOfPlayers, int numberOfFights)
	{
		this.randomSeed = randomSeed;
		this.numberOfPlayers = numberOfPlayers;
		this.numberOfFights = numberOfFights;		

	}
		
	public long getRandomSeed()
	{
		return randomSeed;
	}

	public int getNumberOfPlayers()
	{
		return numberOfPlayers;
	}

	public int getNumberOfFights()
	{
		return numberOfFights;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + numberOfFights;
		result = prime * result + numberOfPlayers;
		result = prime * result + (int) (randomSeed ^ (randomSeed >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof GameSettings))
			return false;
		
		GameSettings other = (GameSettings) obj;
		
		if (numberOfFights != other.numberOfFights)
			return false;
		if (numberOfPlayers != other.numberOfPlayers)
			return false;
		if (randomSeed != other.randomSeed)
			return false;
		return true;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append("GameSettings [randomSeed=");
		builder.append(randomSeed);
		builder.append(", numberOfPlayers=");
		builder.append(numberOfPlayers);
		builder.append(", numberOfFights=");
		builder.append(numberOfFights);
		builder.append("]");
		
		return builder.toString();
	}
	

	

}
