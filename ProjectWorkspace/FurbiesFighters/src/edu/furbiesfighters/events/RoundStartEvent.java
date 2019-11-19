package edu.furbiesfighters.events;

/**
 * @author Furbies Fighters
 * Class RoundStartEvent
 * 	Responsible for creating the round, utilizes the builder pattern 
 */
public class RoundStartEvent extends BaseEvent {
	private static final int INT_DEFAULT = 0;
	
	private int roundNumber;
	/**  
	 * Private constructor for RoundStartEvent builder
	 * @param RoundStartEventBuilder
	 */
	private RoundStartEvent(RoundStartEventBuilder builder)
	{
		super(EventTypes.ROUND_START);
		
		if(builder.roundNumber == null)
		{
			roundNumber = INT_DEFAULT;
		}
		else
		{
			roundNumber = builder.roundNumber;
		}
	}

	/**
	 * Getter method returns round number
	 * @return roundNumber
	 */
	public int getRoundNumber() {
		return roundNumber;
	}

	/**
	 * Overridden hashCode method that returns the hashcode representation 
	 * for the class.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + roundNumber;
		return result;
	}

	/**
	 * Method for returning whether or not two RoundStartEvent are equal.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RoundStartEvent other = (RoundStartEvent) obj;
		if (roundNumber != other.roundNumber)
			return false;
		return true;
	}
	
	/**
	 * Overridden method to return the string representation of the class.
	 */
	@Override
	public String toString() {
		return "RoundStartEvent [roundNumber=" + roundNumber + "]";
	}
	
	/**
	 * A builder class that builds a new Round Start Event given a certain
	 * amount of constructor input.
	 */
	public static class RoundStartEventBuilder {
		private Integer roundNumber;
		
		/**
		 * An empty constructor. All variables instantiated later on.
		 */
		public RoundStartEventBuilder()
		{
			
		}
		
		/**
		 * Method for adding the roundNumber parameter to the builder.
		 * It returns the builder to be further added to.
		 */
		public RoundStartEventBuilder withRoundNumber(int roundNumber)
		{
			this.roundNumber = roundNumber;
			return this;
		}
		
		/**
		 * Method to actually instantiate the new RoundStartEvent using the
		 * parameters passed to the builder.
		 */
		public RoundStartEvent build()
		{
			return new RoundStartEvent(this);
		}
	}
}
