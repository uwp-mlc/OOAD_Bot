package edu.dselent.player;

import java.util.ArrayList;
//import java.util.HashMap;
import java.util.List;
//import java.util.Map;

import edu.dselent.settings.PlayerInfo;
import jneat.Organism;

public class PlayableInstantiator
{
//	public static List<Playable> instantiatePlayables(List<PlayerInfo> playerInfoList)
//	{
//		List<Playable> playableList = new ArrayList<>();
//
//		for(int i=0; i<playerInfoList.size(); i++)
//		{
//			PlayerInfo playerInfo = playerInfoList.get(i);
//			Playable playable = instantiatePlayable(i, playerInfo);
//			playableList.add(playable);
//		}
//		
//		return playableList;
//	}
//	
//	public static Playable instantiatePlayable(int playableUid, PlayerInfo playerInfo)
//	{
//		Playable thePlayable = null;
//		
//		PlayerTypes playerType = playerInfo.getPlayerType();
//		
//		
//		if(playerType == PlayerTypes.HUMAN)
//		{
//			// TODO
//			// Not yet implemented
//			thePlayable = new HumanPetInstance(playableUid, playerInfo);
//		}
//		else if(playerType == PlayerTypes.COMPUTER)
//		{
//			// Doug's default AI
//			thePlayable = new ComputerPetInstance(playableUid, playerInfo);
//		}
//		else if (playerType == PlayerTypes.JARVIS)
//		{
//			thePlayable = new JarvisPlayer(playableUid, playerInfo);
//		}
//		else if (playerType == PlayerTypes.AVERAGE_JOE)
//		{
//			thePlayable = new JarvisPlayer(playableUid, playerInfo);
//		}
//		/*
//		Add more here
//		 */
//		else
//		{
//			// TODO make custom exception
//			throw new RuntimeException("Invalid playerType: " + playerType);
//		}
//		
//		
//		return thePlayable;
//	}
	
	public static List<Playable> instantiatePlayables(List<PlayerInfo> playerInfoList, Organism org)
	{
		List<Playable> playableList = new ArrayList<>();

		for(int i=0; i<playerInfoList.size(); i++)
		{
			PlayerInfo playerInfo = playerInfoList.get(i);
			Playable playable = instantiatePlayable(i, playerInfo, org);
			playableList.add(playable);
		}
		
		return playableList;
	}
	
	public static Playable instantiatePlayable(int playableUid, PlayerInfo playerInfo, Organism org)
	{
		Playable thePlayable = null;
		
		PlayerTypes playerType = playerInfo.getPlayerType();
		
		
		if(playerType == PlayerTypes.HUMAN)
		{
			// TODO
			// Not yet implemented
			thePlayable = new HumanPetInstance(playableUid, playerInfo);
		}
		else if(playerType == PlayerTypes.COMPUTER)
		{
			// Doug's default AI
			thePlayable = new ComputerPetInstance(playableUid, playerInfo);
		}
		else if (playerType == PlayerTypes.JARVIS)
		{
			thePlayable = new JarvisPlayer(playableUid, playerInfo);
		}
		else if (playerType == PlayerTypes.AVERAGE_JOE)
		{
			thePlayable = new AverageJoeInstance(playableUid, playerInfo, org);
		}
		/*
		Add more here
		 */
		else
		{
			// TODO make custom exception
			throw new RuntimeException("Invalid playerType: " + playerType);
		}
		
		
		return thePlayable;
	}
}
