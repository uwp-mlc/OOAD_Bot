package edu.dselent.domain;

import java.util.ArrayList;
import java.util.List;
import edu.dselent.player.Playable;

public class Battle
{
	private int numberOfFights;
	private List<Fight> fightList;
	private List<Playable> playableList;
	
	public Battle(int numberOfFights, List<Playable> playableList)
	{
		this.numberOfFights = numberOfFights;
		fightList = new ArrayList<>();
		this.playableList = playableList;
	}

	public int getNumberOfFights()
	{
		return numberOfFights;
	}
		
	public List<Fight> getFightList()
	{
		return fightList;
	}

	public List<Playable> getPlayableList()
	{
		return playableList;
	}

	
}
