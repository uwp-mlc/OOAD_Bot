package edu.dselent.player;

public class Pet
{
	private Player player;
	private PetTypes petType;
	private String name;
	
	public Pet(Player owner, PetTypes petType, String name)
	{
		this.player = owner;
		this.petType = petType;
		this.name = name;
	}

	public Player getPlayer()
	{
		return player;
	}

	public void setPlayer(Player player)
	{
		if(player == null)
		{
			throw new IllegalArgumentException("A Pet must belong to a player");
		}
		
		// object reference comparison
		if(this != player.getPet())
		{
			throw new IllegalArgumentException("A pet can only belong to a player who has that pet");
		}
		
		this.player = player;
	}
	
	public PetTypes getPetType()
	{
		return petType;
	}

	public void setPetType(PetTypes petType)
	{
		this.petType = petType;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((petType == null) ? 0 : petType.hashCode());
		result = prime * result + ((player == null) ? 0 : player.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Pet))
			return false;
		Pet other = (Pet) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (petType != other.petType)
			return false;
		if (player == null) {
			if (other.player != null)
				return false;
		} else if (!player.equals(other.player))
			return false;
		return true;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("Pet [petType=");
		builder.append(petType);
		builder.append(", name=");
		builder.append(name);
		builder.append("]");
		return builder.toString();
	}

	
}
