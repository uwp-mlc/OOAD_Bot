/**
 * 
 */
package edu.furbiesfighters.events;

import java.util.ArrayList;
import java.util.List;

import edu.furbiesfighters.skills.Skills;

/**
 * @author Furbies Fighters
 *	Class: FightStartEvent
 *	Extends: BaseEvent
 *		Event Class that handles the fight creation.
 */
public final class FightStartEvent extends BaseEvent {
	private final static int INT_DEFAULT = 0;
	
	private final int fightNumber;
	private final List<PlayerEventInfo> playerEventInfo;
	
	//Private constructor for FightStartEvent
	//@params: FightStartEventBuilder 
	private FightStartEvent(FightStartEventBuilder builder) 
	{
		super(EventTypes.FIGHT_START);
		
		if(builder.fightNumber == null)
		{
			fightNumber = INT_DEFAULT;
		}
		else
		{
			fightNumber = builder.fightNumber;
		}
		
		if(builder.playerEventInfo == null)
		{
			playerEventInfo = new ArrayList<PlayerEventInfo>();
		}
		else
		{
			playerEventInfo = builder.playerEventInfo;
		}
	}
	
	/**
	 * Getter method that returns fight number
	 * @return fightNumber
	 */
	public int getFightNumber()
	{
		return fightNumber;
	}
	
	/**
	 * Getter Method for List of Player Event Info
	 * @return List<PlayerEventInfo>
	 */
	public List<PlayerEventInfo> getPlayerEventinfo()
	{
		return playerEventInfo;
	}
	
	/**
	 * Overridden hashCode method that returns the hashcode representation 
	 * for the class.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + fightNumber;
		result = prime * result + ((playerEventInfo == null) ? 0 : playerEventInfo.hashCode());
		return result;
	}
	
	/**
	 * Method for returning whether or not two PlayerEventInfo are equal.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FightStartEvent other = (FightStartEvent) obj;
		if (fightNumber != other.fightNumber)
			return false;
		if (playerEventInfo == null) {
			if (other.playerEventInfo != null)
				return false;
		} else if (!playerEventInfo.equals(other.playerEventInfo))
			return false;
		return true;
	}
	
	/**
	 * Overridden method to return the string representation of the class.
	 */
	@Override
	public String toString() {
		return "FightStartEvent [fightNumber=" + fightNumber + ", playerEventInfo=" + playerEventInfo + "]";
	}
	
	/**
	 * A builder class that builds a new Fight Start Event given a certain
	 * amount of constructor input.
	 */
	public static class FightStartEventBuilder 
	{
		private Integer fightNumber;
		private List<PlayerEventInfo> playerEventInfo;
		
		/**
		 * An empty constructor. All variables instantiated later on.
		 */
		public FightStartEventBuilder() {
			// TODO Auto-generated constructor stub
		}
		
		/**
		 * Method for adding the fightNumber parameter to the builder.
		 * It returns the builder to be further added to.
		 */
		public FightStartEventBuilder withFightNumber(int fightNumber) 
		{
			this.fightNumber = fightNumber;
			return this;
		}

		/**
		 * Method for adding a passed list of player event info to the builder.
		 * It returns the builder to be further added to.
		 */
		public FightStartEventBuilder withPlayerEventInfo(List<PlayerEventInfo> playerEventInfo) 
		{
			this.playerEventInfo = playerEventInfo;
			return this;
		}
		
		/**
		 * Method to actually instantiate the new FightStartEvent using the
		 * parameters passed to the builder.
		 */
		public FightStartEvent build()
		{
			return new FightStartEvent(this);
		}
	}
}
