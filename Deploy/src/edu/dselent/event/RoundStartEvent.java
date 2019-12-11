package edu.dselent.event;


public class RoundStartEvent extends BaseEvent
{
	private final int roundNumber;
	
	public RoundStartEvent(int roundNumber)
	{
		super(EventTypes.ROUND_START);
		this.roundNumber = roundNumber;
	}

	public RoundStartEvent(RoundStartEvent otherEvent)
	{
		super(EventTypes.ROUND_START);
		this.roundNumber = otherEvent.roundNumber;
	}
	
	public int getRoundNumber()
	{
		return roundNumber;
	}


	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + roundNumber;
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof RoundStartEvent))
			return false;
		RoundStartEvent other = (RoundStartEvent) obj;
		if (roundNumber != other.roundNumber)
			return false;
		return true;
	}

	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder("RoundStartEvent{");
		sb.append("roundNumber=").append(roundNumber);
		sb.append(", eventType=").append(getEventType());
		sb.append('}');
		return sb.toString();
	}
}
