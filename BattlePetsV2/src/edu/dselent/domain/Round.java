package edu.dselent.domain;

import java.util.ArrayList;
import java.util.List;

public class Round
{
	private Fight fight;
	// TODO CHANGE TO INDEX?
	private int roundNumber;
	private List<PlayerRoundData> playerRoundDataList;
	
	public Round(Fight fight, int roundNumber)
	{
		this.fight = fight;
		this.roundNumber = roundNumber;
		playerRoundDataList = new ArrayList<>();
	}

	public Fight getFight()
	{
		return fight;
	}
	
	public int getRoundNumber()
	{
		return roundNumber;
	}

	public List<PlayerRoundData> getPlayerRoundDataList()
	{
		return playerRoundDataList;
	}
	
	
}
