package edu.dselent.player;


public class Player
{
	private String name;
	private PlayerTypes playerType;
	private Pet pet;
	
	public Player(String name, PlayerTypes playerType)
	{
		this.name = name;
		this.playerType = playerType;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public PlayerTypes getPlayerType()
	{
		return playerType;
	}

	public Pet getPet()
	{
		return pet;
	}

	public void setPet(Pet pet)
	{
		// prevent an orphaned pet
		if(this.pet != null)
		{
			throw new IllegalArgumentException("Cannot set pet, because this player already has a pet");
		}
		
		this.pet = pet;
		pet.setPlayer(this);
	}

	

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((pet == null) ? 0 : pet.hashCode());
		result = prime * result + ((playerType == null) ? 0 : playerType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Player))
			return false;
		Player other = (Player) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (pet == null) {
			if (other.pet != null)
				return false;
		} else if (!pet.equals(other.pet))
			return false;
		if (playerType != other.playerType)
			return false;
		return true;
	}

	@Override
	public String toString()
	{
		return name;
	}
}
