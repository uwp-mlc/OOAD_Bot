package edu.dselent.domain;

import java.util.ArrayList;
import java.util.List;

import edu.dselent.player.Playable;

public class Fight
{
	private Battle battle;
	private int fightIndex;
	private List<Round> roundList;
	
	// Can calculate from the list of round data, but could be very slow
	// Keeping this as an extra variable for performance reasons
	private List<Double> randomDamageDifferenceList;
	
	private List<Playable> winnerList;
	
	// Making claim fight cannot exist without a battle
	public Fight(Battle battle, int fightIndex)
	{
		this.battle = battle;
		this.fightIndex = fightIndex;
		roundList = new ArrayList<>();
		randomDamageDifferenceList = new ArrayList<>();
		
		int numberOfPlayers = battle.getPlayableList().size();
		
		for(int i=0; i<numberOfPlayers; i++)
		{
			randomDamageDifferenceList.add(0.0);
		}
		
		winnerList = new ArrayList<>();
	}
	
	public Battle getBattle()
	{
		return battle;
	}
	
	public int getFightIndex()
	{
		return fightIndex;
	}
	
	public List<Round> getRoundList()
	{
		return roundList;
	}
	
	public List<Playable> getWinnerList()
	{
		return winnerList;
	}
	
	public void setWinnerList(List<Playable> winnerList)
	{
		this.winnerList = winnerList;
	}
	
	// When using reversal of fortune on same turn, it swaps
	// Not same turn = zero balance
	// Issue happens when it is used on same turn, need to think of a solution here
	public double getRandomDamageDifference(int playerIndex)
	{
		return randomDamageDifferenceList.get(playerIndex);
	}
	
	private void setRandomDamageDifference(int playerIndex, double randomDamageDifference)
	{
		randomDamageDifferenceList.set(playerIndex, randomDamageDifference);
	}
	
	public void updateRandomDamageDifference(int playerIndex, double randomDamage)
	{
		double oldRandomDamage = getRandomDamageDifference(playerIndex);
		double newRandomDamage = oldRandomDamage + randomDamage;
		setRandomDamageDifference(playerIndex, newRandomDamage);
	}
}
