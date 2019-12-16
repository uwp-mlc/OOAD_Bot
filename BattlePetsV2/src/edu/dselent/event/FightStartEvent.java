package edu.dselent.event;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class FightStartEvent extends BaseEvent
{
	private final int fightIndex;
	private final List<PlayerEventInfo> playerEventInfoList;
	
	public FightStartEvent(int fightIndex, List<PlayerEventInfo> playerEventInfoList)
	{
		super(EventTypes.FIGHT_START);
		
		this.fightIndex = fightIndex;
		this.playerEventInfoList = playerEventInfoList;
	}

	public FightStartEvent(FightStartEvent otherEvent)
	{
		super(EventTypes.FIGHT_START);

		this.fightIndex = otherEvent.fightIndex;
		this.playerEventInfoList = otherEvent.getPlayerEventInfoList();
	}

	public int getFightIndex()
	{
		return fightIndex;
	}

	// Returns a deep copy so pets cannot modify information and affect each others information
	public List<PlayerEventInfo> getPlayerEventInfoList()
	{
		List<PlayerEventInfo> playerEventInfoListCopy = new ArrayList<>();
		playerEventInfoList.forEach(playerEventInfo -> playerEventInfoListCopy.add(new PlayerEventInfo(playerEventInfo)));
		return playerEventInfoListCopy;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (o == null || getClass() != o.getClass())
		{
			return false;
		}
		if (!super.equals(o))
		{
			return false;
		}
		FightStartEvent that = (FightStartEvent) o;
		return fightIndex == that.fightIndex && Objects.equals(playerEventInfoList, that.playerEventInfoList);
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(super.hashCode(), fightIndex, playerEventInfoList);
	}

	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder("FightStartEvent{");
		sb.append("fightIndex=").append(fightIndex);
		sb.append(", playerEventInfoList=").append(playerEventInfoList);
		sb.append(", eventType=").append(getEventType());
		sb.append('}');
		return sb.toString();
	}
}
