/**
 * This class acts as the controller for the GUI game.
 */
package edu.furbiesfighters.gameplay;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.text.html.HTMLDocument.Iterator;

import edu.furbiesfighters.damage.Calculatable;
import edu.furbiesfighters.damage.CalculateDamage;
import edu.furbiesfighters.damage.Pair;
import edu.furbiesfighters.events.AttackEvent;
import edu.furbiesfighters.events.FightStartEvent;
import edu.furbiesfighters.events.PlayerEventInfo;
import edu.furbiesfighters.events.RoundStartEvent;
import edu.furbiesfighters.players.Human;
import edu.furbiesfighters.players.Playable;
import edu.furbiesfighters.players.PlayerTypes;
import edu.furbiesfighters.presenters.GamePlayPresenter;
import edu.furbiesfighters.skills.Skills;
import edu.furbiesfighters.utility.Utility;
import javafx.application.Platform;

/**
 * Class for maintaining the GUI players, fights, and overall information regarding
 * a battle.
 * @author Furbies Fighters
 */
public class GUIReferee extends Referee{
	private final int MINIMUM_PLAYERS = 2;
	private boolean stillChosingSkills;
	private List<Playable> currentRound;
	private int fightNumber;
	private int currentAliveIndex;
	private GamePlayPresenter presenter;
	
	/**
	 * Constructor for GUI referee simply instantiate variables.
	 */
	public GUIReferee()
	{
		super();
		this.fightNumber = 1;
		stillChosingSkills = true;
		this.currentRound = null;
		this.currentAliveIndex = 0;
		super.damageCalculator = new CalculateDamage(this);
	}
	
	/**
	 * Allows all players to choose their skill. And checks for sleeping
	 * players in the round. 
	 */
	@Override
	public void allChooseSkill()
	{
		/*No operation in GUI version*/
	}

	/**
	 * Method for getting the current playable's skill list. It
	 * will return a list of strings that contain all skills that are
	 * not recharging.
	 */
	@Override
	public List<Skills> getCurrentPlayableSkillStringList()
	{
		if(this.currentAliveIndex > this.getAlivePlayables().size()-1)
			return new ArrayList<Skills>();
		Playable currentPlayer = this.getNextPlayerInfo();
		
		String message = currentPlayer.getPlayerName() + " has the following recharging:";
		boolean noRecharging = true;;
		List<Skills> l;
		
		l = new ArrayList<Skills>();
		
		if (currentPlayer.getSkillRechargeTime(Skills.ROCK_THROW) == 0)
		{
			l.add(Skills.ROCK_THROW);
		}
		else
		{
			noRecharging = false;
			message += " " + Skills.ROCK_THROW.toString();
		}
		if (currentPlayer.getSkillRechargeTime(Skills.PAPER_CUT) == 0)
		{
			l.add(Skills.PAPER_CUT);
		}

		else
		{
			noRecharging = false;
			message += " " + Skills.PAPER_CUT.toString();
		}
		if (currentPlayer.getSkillRechargeTime(Skills.SCISSOR_POKE) == 0)
		{
			l.add(Skills.SCISSOR_POKE);
		}
		else
		{
			noRecharging = false;
			message += " " + Skills.SCISSOR_POKE.toString();
		}
		if (currentPlayer.getSkillRechargeTime(Skills.SHOOT_THE_MOON) == 0)
		{
			l.add(Skills.SHOOT_THE_MOON);
		}
		else
		{
			noRecharging = false;
			message += " " + Skills.SHOOT_THE_MOON.toString();
		}
		if (currentPlayer.getSkillRechargeTime(Skills.REVERSAL_OF_FORTUNE) == 0)
		{
			l.add(Skills.REVERSAL_OF_FORTUNE);
		}
		else
		{
			noRecharging = false;
			message += " " + Skills.REVERSAL_OF_FORTUNE.toString();
		}
		
		if(noRecharging)
			message += " (none)\n";
		if(currentPlayer.getPlayerType() != PlayerTypes.AI)
			Utility.printMessage(message);
		
		return l;
	}
		
	/**
	 * Method for converting a string to a skill.
	 * @param string, the skill in string form
	 * @return the string in skill form.
	 */
	private Skills stringToSkillsConversion(String string)
	{
		Skills s;
		
		s = null;
		
		switch (string)
		{
		case "rock throw" :
			s = Skills.ROCK_THROW;
			break;
		case "scissor poke" :
			s = Skills.SCISSOR_POKE;
			break;
		case "paper cut" :
			s = Skills.PAPER_CUT;
			break;
		case "shoot the moon" :
			s = Skills.SHOOT_THE_MOON;
			break;
		case "reversal of fortune" :
			s = Skills.REVERSAL_OF_FORTUNE;
			break;
		}
		
		return s;
	}
	
	/**
	 * Method for returning the specified recharge time for a given skill.
	 */
	public int getRechargeTimeForSkill(Skills s)
	{
		int time;
		
		time = 1;
		
		switch (s)
		{
		case SHOOT_THE_MOON :
			time = this.STM_RECHARGE_TIME;
			break;
		case REVERSAL_OF_FORTUNE :
			time = this.ROF_RECHARGE_TIME;
			break;
		}
		
		return time;
	}
	
	/**
	 * Method for print out the results of the round. It will print out the 
	 * player and their hp.
	 */
	@Override
	public void announceRoundResults()
	{
		List<Playable> playables = getAllPlayables();
		Playable tempPlayable;
		int playerIndex;
		Utility.printMessage("\nSTATUS REPORT:");
		for (int i = 0; i < playables.size(); i++)
		{
			playerIndex = i + 1;
			tempPlayable = playables.get(i);
			String playableMessage = "";
			
			if(!tempPlayable.isAwake())
				playableMessage = "SLEEPING";
			else
				playableMessage += tempPlayable.getCurrentHp();
			
			Utility.printMessage(playerIndex + ". " + tempPlayable.getPlayerName() + ", " + playableMessage);
		}
		Utility.printEndline();
	}
	
	/**
	 * Returns a list of all alive players. This method called after
	 * determining there is a winner. If there is a tie, return null. 
	 * If there is still players left in the game, then this method should
	 * not be called, but return null anyways. 
	 * @return a list of the playables alive.
	 */
	private List<Playable> determineFightWinners()
	{
		List<Playable> playables = getAlivePlayables();
		if(playables.size() >= this.MINIMUM_PLAYERS)
			return null;
		else if(playables.size() == 0)
			return calculateTie();
		getAlivePlayables().forEach(playable-> addFightWonToPlayer(playable));
		return playables;
	}
	
	/**
	 * Method for announcing the information for a fight.
	 */
	private void announceFightResults()
	{
		List<Playable> winners = this.determineFightWinners();
		
		if(winners == null)
			Utility.printMessage("A tie occured in fight " + this.fightNumber);
		else
		{
			Utility.printSmallBanner("Fight Ended");
			Utility.printMessage("Winner(s): ");
		}
		
		String winningString = "";
		for (int i = 0; winners != null && i < winners.size(); i++)
		{
			if(i == winners.size()-1)
				winningString += winners.get(i).getPlayerName();
			else
				winningString += winners.get(i).getPlayerName() + ", ";
		}
		Utility.printMessage(winningString);
		
		Utility.printEndline();
	}
	
	/**
	 * Uses alivePlayerHistory in the round class to get the
	 * list of alive players before the round started.
	 * @return the list of playables that are tied.
	 */
	private List<Playable> calculateTie()
	{
		if(this.currentRound == null)
		{
			getAllPlayables().forEach(playable-> addFightWonToPlayer(playable));
			return getAllPlayables();
		}
		else
		{
			this.currentRound.forEach(playable-> addFightWonToPlayer(playable));
			return this.currentRound;
		}
	}
	
	/**
	 * Method for driving a round. It will iterate and play for all
	 * AI players until it hits a human player or the end of the list.
	 * It will check for a winner once it reaches that point.
	 */
	@Override
	public void play()
	{
		List<Playable> players = this.getAlivePlayables();
		int i = this.currentAliveIndex;
		while( i < players.size() && players.get(i).getPlayerType() != PlayerTypes.HUMAN)
		{
			Skills chosen = players.get(i).chooseSkill();//AI Choose skill
			players.get(i).decrementRechargeTimes();
			players.get(i).setRechargeTime(chosen, (chosen == Skills.REVERSAL_OF_FORTUNE || chosen == Skills.SHOOT_THE_MOON) ? 6 : 1);
			this.chosenSkills.put(this.getIndexOfPlayable(players.get(i)), chosen);
			this.currentAliveIndex = ++i;
		}

		if(this.currentAliveIndex > players.size()-1)//End of round...
		{
			this.calculateAllDamages();
			this.announceRoundResults();
			this.handleRoundStartEvent();
			this.currentAliveIndex = 0; //Reset index to zero
		}
		
		if(this.hasWinner())
		{
			this.announceFightResults();
			
			this.presenter.setFightNumber(++this.fightNumber);
			this.handleFightStartEvent();
			if(this.fightNumber > this.getFightCount())
			{
				Platform.runLater(() -> this.presenter.showPostBattle());
			}
			else
			{
				this.updateHP();
				this.currentAliveIndex = 0;
				if(this.getNextPlayerInfo().getPlayerType() != PlayerTypes.HUMAN) 
					play();
			}
		}
		else if(this.getNextPlayerInfo().getPlayerType() != PlayerTypes.HUMAN)
		{
			play();
		}
	}
	
	/**
	 * Method for creating a new round. It will also increment round number.
	 */
	protected void handleRoundStartEvent()
	{
		RoundStartEvent.RoundStartEventBuilder rsb = new  RoundStartEvent.RoundStartEventBuilder();
		rsb.withRoundNumber(this.getCurrentFightNumber());
		RoundStartEvent  rse  = rsb.build();
		super.addEvent(rse);
	}
	
	/**
	 * Handle all event creation for fight start events. 
	 */
	@Override
	public void handleFightStartEvent()
	{
		ArrayList<PlayerEventInfo> peL =  new ArrayList<PlayerEventInfo>();
		Set<Skills> skillSet = new HashSet<Skills>();
		skillSet.add(Skills.PAPER_CUT);
		skillSet.add(Skills.SCISSOR_POKE);
		skillSet.add(Skills.ROCK_THROW);
		skillSet.add(Skills.SHOOT_THE_MOON);
		skillSet.add(Skills.REVERSAL_OF_FORTUNE);
		
		for( int  j = 0 ; j < super.getAllPlayables().size(); j++ )
		{
			Playable temp = super.getAllPlayables().get(j);
			PlayerEventInfo.PlayerEventInfoBuilder peb = new PlayerEventInfo.PlayerEventInfoBuilder();
			PlayerEventInfo tempInfo =  peb.withPetType(temp.getPetType()).withPetName(temp.getPetName())
					.withPlayerType(temp.getPlayerType()).withStartingHp(temp.getCurrentHp()).withSkillSet(skillSet).build();
			peL.add(tempInfo);
		}
		
		FightStartEvent.FightStartEventBuilder fsb = new FightStartEvent.FightStartEventBuilder();
		fsb.withFightNumber(this.fightNumber);
		fsb.withPlayerEventInfo(peL); 
		FightStartEvent fse = fsb.build();
		super.addEvent(fse);
	}
	
	/**
	 * Method for playing for a human rather than an AI. It will assign
	 * the skill choice for a human.
	 */
	@Override
	public void play(String playerName, String skillChoice)
	{
		addChosenHumanSkill(playerName, skillChoice);
			
		play();//play until next human player
	}
	
	/**
	 * Method for choosing a human's skills.
	 */
	@Override
	public void addChosenHumanSkill(String playerName, String skillChoice)
	{
		Skills skill = stringToSkillsConversion(skillChoice);
		if(skill == Skills.SHOOT_THE_MOON)
		{
			Playable p = this.getNextPlayerInfo();
			String string = this.presenter.getView().getChoiceBoxShootTheMoon().getSelectionModel().getSelectedItem().toString();
			Skills skillPrediction = this.stringToSkillsConversion(string);
			p.setSkillPrediction(skillPrediction);
		}
		
		Playable player = null;
		for(Playable p : this.getAllPlayables())
			if(p.getPlayerName().equals(playerName))
				player = p;
		if(player == null)
			System.out.println("NO PLAYER");
		else
		{
			this.chosenSkills.put(this.getIndexOfPlayable(player), skill);
			this.currentAliveIndex++;
			player.decrementRechargeTimes();
			player.setRechargeTime(skill, getRechargeTimeForSkill(skill));
		}
	}
	
	/**
	 * Method for getting the current fight number.
	 */
	@Override
	public int getCurrentFightNumber() {return this.fightNumber;}
	
	/**
	 * Method for getting the playable that is next in the list.
	 */
	@Override
	public Playable getNextPlayerInfo()
	{
		return this.getAlivePlayables().get(currentAliveIndex);
	}
	
	/**
	 * Method for setting the presenter.
	 */
	@Override
	public void setUpGUI(GamePlayPresenter presenter) 
	{
		this.presenter = presenter;
	}
	
	/**
	 * Method for getting the fight win list. It will iterate through the
	 * list and get each player's win amount and organize it by most wins
	 * first.
	 */
	//@Override
	public List<String> getPlayableFightWinList2()
	{ 
		ArrayList<String> list = new ArrayList<String>();
		ArrayList<Playable> playerList;
		HashMap<Playable, Integer> tempPlayers = (HashMap<Playable, Integer>) this.getWinCountMap();

		playerList = getOrganizedPlayableListByWinner();
		
		for (int i = 0; i < playerList.size(); i++)
		{
			list.add(tempPlayers.get(playerList.get(i)) 
					+ " Fights won by: " + playerList.get(i).getPlayerName());
		}
		
		return list;
	}
	
	/**
	 * Method for getting the wins for each player from the HashMap.
	 * @return a list of the playables in the order than won.
	 */
	private ArrayList<Playable> getOrganizedPlayableListByWinner2()
	{
		HashMap<Playable, Integer> tempPlayers = (HashMap<Playable, Integer>) this.getWinCountMap();
		int index = 0;
		boolean inserted = false;
		ArrayList<Playable> playerList = new ArrayList<Playable>();
		
		for (HashMap.Entry<Playable, Integer> entry : tempPlayers.entrySet()) 
		{
		    Playable key = entry.getKey();
		    Integer value = entry.getValue();
		    
		    while (!inserted)
		    {		    	
		    	if (index == playerList.size())
		    	{
		    		playerList.add(key);
		    		inserted = true;
		    	}
		    	else if (tempPlayers.get(playerList.get(index)) <= value)
		    	{
		    		playerList.add(index, key);
		    		inserted = true;
		    	}
		    	else
		    	{
		    		index++;
		    	}
		    }
		    
		    inserted = false;
		    index = 0;
		}
		
		return playerList;
	}
	
	/**
	 * Method for getting the top list of players that won the battle.
	 * Winning the battle is equivalent to winning the most fights.
	 */
	@Override
	public List<String> getPlayableBattleWinList()
	{ 
		ArrayList<String> list = new ArrayList<String>();
		ArrayList<Playable> playerList;
		HashMap<Playable, Integer> tempPlayers = (HashMap<Playable, Integer>) this.getWinCountMap();
		int maxWins = 0;
		
		playerList = super.getOrganizedPlayableListByWinner();
		
		for (int i = 0; i < playerList.size(); i++)
		{
			if (maxWins <= tempPlayers.get(playerList.get(i)))
			{
				maxWins = tempPlayers.get(playerList.get(i));
				
				list.add(tempPlayers.get(playerList.get(i)) 
				+ " Fights won by: " + playerList.get(i).getPlayerName());
			}
		}
		
		return list;
	}
}
