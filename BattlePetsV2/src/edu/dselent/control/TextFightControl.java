package edu.dselent.control;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import edu.dselent.config.ApplicationConfigurations;
import edu.dselent.domain.Fight;
import edu.dselent.domain.PlayerRoundData;
import edu.dselent.domain.RngHolder;
import edu.dselent.domain.Round;
import edu.dselent.event.FightStartEvent;
import edu.dselent.event.PlayerEventInfo;
import edu.dselent.event.PlayerEventInfo.PlayerEventInfoBuilder;
import edu.dselent.io.IoManager;
import edu.dselent.player.Playable;

class TextFightControl
{
	private TextBattleControl textBattleControl;
	private TextRoundControl textRoundControl;
	
	TextFightControl(TextBattleControl textBattleControl)
	{
		this.textBattleControl = textBattleControl;
		textRoundControl = new TextRoundControl(this);
	}

	public TextBattleControl getTextBattleControl()
	{
		return textBattleControl;
	}

	void runFight(Fight fight)
	{
		List<Playable> playerList = fight.getBattle().getPlayableList();
		
		for(Playable currentPlayable : playerList)
		{
			currentPlayable.reset();
		}
		
		IoManager ioManager = ApplicationConfigurations.INSTANCE.getIoManager();
		ioManager.getOutputSender().outputString("Fight " + (fight.getFightIndex()+1) + " Started");
		ioManager.getOutputSender().outputString("\n");
		ioManager.getOutputSender().outputString("Players");
		ioManager.getOutputSender().outputString("\n");
				
		for(int i=0; i<playerList.size(); i++)
		{
			Playable currentPlayer = playerList.get(i);
			ioManager.getOutputSender().outputString("Player " + (i+1));
			ioManager.getOutputSender().outputString("Player Name: " + currentPlayer.getPlayerName());
			ioManager.getOutputSender().outputString("Pet Name: " + currentPlayer.getPetName());
			ioManager.getOutputSender().outputString("Pet Type: " + currentPlayer.getPetType());
			ioManager.getOutputSender().outputString("Starting HP: " + currentPlayer.getStartingHp());
			ioManager.getOutputSender().outputString("\n");
			
			// TODO output skill set
		}
		
		EventBus eventBus = textBattleControl.getEventBus();
		
		List<PlayerEventInfo> playerEventInfoList = new ArrayList<>();
		
		for(Playable currentPlayable : playerList)
		{
			PlayerEventInfoBuilder peib = new PlayerEventInfoBuilder();
			peib.withPlayableUid(currentPlayable.getPlayableUid());
			
			peib.withPetName(currentPlayable.getPetName());
			peib.withPetType(currentPlayable.getPetType());
			peib.withPlayerType(currentPlayable.getPlayerType());
			peib.withSkillSet(currentPlayable.getSkillSet());
			peib.withStartingHp(currentPlayable.getCurrentHp());
			
			playerEventInfoList.add(peib.build());
		}

		FightStartEvent fightStartEvent = new FightStartEvent(fight.getFightIndex(), playerEventInfoList);
		eventBus.fireEvent(fightStartEvent);
		
		int roundIndex = 0;
		while(!isFightOver(fight))
		{
			Round round = new Round(fight, roundIndex);
			fight.getRoundList().add(round);
			textRoundControl.runRound(round);
		
			roundIndex++;
		}
		
		ioManager.getOutputSender().outputString("Pets");
		ioManager.getOutputSender().outputString("\n");
		
		for(int i=0; i<playerList.size(); i++)
		{
			Playable currentPlayer = playerList.get(i);
			ioManager.getOutputSender().outputString("Pet " + (i+1));
			ioManager.getOutputSender().outputString("Pet Name: " + currentPlayer.getPetName());
			ioManager.getOutputSender().outputString("Pet Type: " + currentPlayer.getPetType());
			ioManager.getOutputSender().outputString("Current HP: " + currentPlayer.getCurrentHp());
			ioManager.getOutputSender().outputString("\n");
			
			// TODO output skill set
		}
		
		
		ioManager.getOutputSender().outputString("Sorted by HP");
		ioManager.getOutputSender().outputString("\n");
		
		List<Playable> sortedPlayableList = new ArrayList<>(playerList);
		sortedPlayableList.sort(Comparator.comparingDouble(Playable::getCurrentHp));

		for(int i=0; i<sortedPlayableList.size(); i++)
		{
			Playable currentPlayer = sortedPlayableList.get(i);
			ioManager.getOutputSender().outputString("Pet " + (i+1));
			ioManager.getOutputSender().outputString("Pet Name: " + currentPlayer.getPetName());
			ioManager.getOutputSender().outputString("Pet Type: " + currentPlayer.getPetType());
			ioManager.getOutputSender().outputString("Current HP: " + currentPlayer.getCurrentHp());
			ioManager.getOutputSender().outputString("\n");

			// TODO output skill set
		}
		
		List<Playable> winnerList = calculateWinnerList(fight);
		
		ioManager.getOutputSender().outputString("Fight " + (fight.getFightIndex()+1) + " Over");
		ioManager.getOutputSender().outputString("Fight Winner(s)");
		
		for(Playable playable : winnerList)
		{
			ioManager.getOutputSender().outputString(playable.getPetName());
			fight.setWinnerList(winnerList);
		}
		
		ioManager.getOutputSender().outputString("\n");		
	}
	

	RngHolder getRngHolder()
	{
		return textBattleControl.getRngHolder();
	}
	
	private boolean isFightOver(Fight fight)
	{
		int awakeCount = 0;
		List<Playable> playerList = fight.getBattle().getPlayableList();

		for (Playable player : playerList)
		{
			if (player.isAwake())
			{
				awakeCount++;
			}
		}
		
		return awakeCount<=1;
	}
	
	/**
	 * Assumes fight is over
	 * 
	 * @param fight The fight that has ended
	 * @return Returns the winners of the fight
	 */
	private List<Playable> calculateWinnerList(Fight fight)
	{
		List<Playable> playerList = fight.getBattle().getPlayableList();
		List<Playable> winnerList = new ArrayList<>();
		List<Round> roundList = fight.getRoundList();
		List<PlayerRoundData> lastRoundDataList = roundList.get(roundList.size()-1).getPlayerRoundDataList();
		
		// extract pets who were awake on the last turn
		// sort by their current hp
		
		for(int i=0; i<lastRoundDataList.size(); i++)
		{
			PlayerRoundData lastRoundData = lastRoundDataList.get(i);
			
			// Were awake at the start of last turn
			if(!lastRoundData.isSleeping())
			{
				winnerList.add(playerList.get(i));
			}
		}
		
		winnerList.sort((p1, p2) -> Double.compare(p2.getCurrentHp(), p1.getCurrentHp()));
		
		for(int i=1; i<winnerList.size(); i++)
		{
			if(winnerList.get(i).getCurrentHp() < winnerList.get(i-1).getCurrentHp())
			{
				winnerList.remove(i);
				i--;
			}
		}
		
		return winnerList;
	}
}
