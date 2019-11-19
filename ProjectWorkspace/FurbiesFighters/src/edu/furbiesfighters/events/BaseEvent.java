package edu.furbiesfighters.events;

/**
 * Base event class that all other events will inherit from.
 */
public abstract class BaseEvent {
	
	private EventTypes eventType;
	
	/**
	 * Constructor that instantiates eventType.
	 */
	public BaseEvent(EventTypes eventType)
	{
		this.eventType = eventType;
	}

	/**
	 * Method for returning the string representation of a class.
	 */
	@Override
	public String toString() {
		return "BaseEvent [getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}
	
	/**
	 * Method for returning the hashcode representation of the class.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((eventType == null) ? 0 : eventType.hashCode());
		return result;
	}

	/**
	 * Method for returning whether or not two BaseEvents are equal.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BaseEvent other = (BaseEvent) obj;
		if (eventType != other.eventType)
			return false;
		return true;
	}
	
	/**
	 * Getter method for returning the eventType.
	 */
	public EventTypes getEventType()
	{
		return this.eventType;
	}
}
