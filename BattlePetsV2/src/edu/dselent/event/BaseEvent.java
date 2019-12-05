package edu.dselent.event;


public abstract class BaseEvent
{

	private final EventTypes eventType;
	

	BaseEvent(EventTypes eventType)
	{
		this.eventType = eventType;
	}


	public EventTypes getEventType()
	{
		return eventType;
	}


	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((eventType == null) ? 0 : eventType.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		
		if (obj == null)
		{
			return false;
		}
		
		if (!(obj instanceof BaseEvent))
		{
			return false;
		}
		
		BaseEvent other = (BaseEvent) obj;
		
		if (eventType != other.eventType)
		{
			return false;
		}
		
		return true;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("BaseEvent [eventType=");
		builder.append(eventType);
		builder.append("]");
		return builder.toString();
	}
}
