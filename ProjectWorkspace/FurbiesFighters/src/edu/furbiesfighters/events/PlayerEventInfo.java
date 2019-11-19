package edu.furbiesfighters.events;

import java.util.HashSet;
import java.util.Set;

import edu.furbiesfighters.skills.Skills;
import edu.furbiesfighters.players.PetTypes;
import edu.furbiesfighters.players.PlayerTypes;

/**
 * @author Furbies Fighters
 * Class: PlayerEventInfo 
 *  Builds player utilizing the builder pattern
 */
public final class PlayerEventInfo 
{
	private final static double DOUBLE_DEFAULT = 0.0;
	private final PetTypes petType;
	private final PlayerTypes playerType;
	private final Set<Skills> skillSet; 
	private final double startingHp;
	private final String petName;
	
	/**
	 * Private constructor for PlayerEventInfo builder
	 * @param PlayerEventInfoBuilder
	 */
	private PlayerEventInfo(PlayerEventInfoBuilder builder) 
	{
		if(builder.petType == null)
		{
			petType = PetTypes.INTELLIGENCE;
		}
		else
		{
			petType = builder.petType;
		}
		
		if(builder.playerType == null)
		{
			playerType = PlayerTypes.AI;
		}
		else
		{
			playerType = builder.playerType;
		}
		
		if(builder.skillSet == null)
		{
			skillSet = new HashSet<Skills>();
		}
		else
		{
			skillSet = builder.skillSet;
		}
		
		if(builder.startingHp == null)
		{
			startingHp = DOUBLE_DEFAULT;
		}
		else
		{
			startingHp = builder.startingHp;
		}
		
		if(builder.petName == null)
		{
			petName = "";
		}
		else
		{
			petName = builder.petName;
		}
	}
	
	/**
	 * Getter method for Pet Type
	 * @return petType
	 */
	public PetTypes getPetType()
	{
		return petType;
	}
	
	/**
	 * Getter method for Player Type
	 * @return playerType
	 */
	public PlayerTypes getPlayerType()
	{
		return playerType;
	}
	
	/**
	 * Getter method for Set Skill
	 * @return Set<Skills>
	 */
	public Set<Skills> getSkillSet()
	{
		return skillSet;
	}
	
	/**
	 * Getter method for Starting HP
	 * @return startingHp
	 */
	public double getStartingHp()
	{
		return startingHp;
	}
	
	/**
	 * Getter method for pet name
	 * @return startingHp
	 */
	public String getPetName()
	{
		return petName;
	}
	
	/**
	 * Method for returning the hashcode representation of the class.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(startingHp);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}
	
	/**
	 * Method for returning the string representation of a class.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PlayerEventInfo other = (PlayerEventInfo) obj;
		if (Double.doubleToLongBits(startingHp) != Double.doubleToLongBits(other.startingHp))
			return false;
		return true;
	}
	
	/**
	 * Method for returning whether or not two PlayerEventInfo are equal.
	 */
	@Override
	public String toString() {
		return "PlayerEventInfo [startingHp=" + startingHp + "]";
	}
	
	/**
	 * @author Furbies Fighters
	 *	Public Builder Class PlayerEventInfoBuilder
	 */
	public static class PlayerEventInfoBuilder {
		private PetTypes petType;
		private PlayerTypes playerType;
		private Set<Skills> skillSet;
		private Double startingHp;
		private String petName;
		
		//Class default Constructor
		public PlayerEventInfoBuilder()
		{
			
		}
		
		/**
		 * Method for adding the petType parameter to the builder.
		 * It returns the builder to be further added to.
		 */
		public PlayerEventInfoBuilder withPetType(PetTypes petType)
		{
			this.petType = petType;
			return this;
		}
		

		/**
		 * Method for adding the playerType parameter to the builder.
		 * It returns the builder to be further added to.
		 */
		public PlayerEventInfoBuilder withPlayerType(PlayerTypes playerType) 
		{
			this.playerType = playerType;
			return this;
		}
		
		/**
		 * Method for adding a passed skillSet to the builder.
		 * It returns the builder to be further added to.
		 */
		public PlayerEventInfoBuilder withSkillSet(Set<Skills> skillSet)
		{
			this.skillSet = skillSet;
			return this;
		}
		
		/**
		 * Method for adding the startingHp parameter to the builder.
		 * It returns the builder to be further added to.
		 */
		public PlayerEventInfoBuilder withStartingHp(double startingHp) {
			this.startingHp = startingHp;
			return this;
		}
		
		/**
		 * Method for adding the petName parameter to the builder.
		 * It returns the builder to be further added to.
		 */
		public PlayerEventInfoBuilder withPetName(String petName) {
			this.petName = petName;
			return this;
		}
		
		/**
		 * Method to actually instantiate the new PlayerEventInfo using the
		 * parameters passed to the builder.
		 */
		public PlayerEventInfo build()
		{
			return new PlayerEventInfo(this);
		}
	}
}
