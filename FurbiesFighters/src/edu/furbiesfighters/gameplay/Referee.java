package edu.furbiesfighters.gameplay;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Random;
import java.util.Set;

import edu.furbiesfighters.damage.Calculatable;
import edu.furbiesfighters.damage.CalculateDamage;
import edu.furbiesfighters.damage.Pair;
import edu.furbiesfighters.events.AttackEvent;
import edu.furbiesfighters.events.BaseEvent;
import edu.furbiesfighters.events.FightStartEvent;
import edu.furbiesfighters.events.PlayerEventInfo;
import edu.furbiesfighters.players.AIPlayer;
import edu.furbiesfighters.players.Human;
import edu.furbiesfighters.players.PetTypes;
import edu.furbiesfighters.players.Playable;
import edu.furbiesfighters.players.Player;
import edu.furbiesfighters.presenters.GamePlayPresenter;
import edu.furbiesfighters.skills.Skills;
import edu.furbiesfighters.utility.Utility;

/**
 * @author Furbies Fighters
 * Class: Referee
 * Def: Main class in charge of calculating and dealing damage
 * 		where damage is due. 
 */
public class Referee extends Observable
{
	protected final int ROF_RECHARGE_TIME = 6, STM_RECHARGE_TIME = 6;
	protected final int NORMAL_RECHARGE_TIME = 1;
	private int fightCount;
	private List<Playable> playables;
	protected Calculatable damageCalculator;
	private final static int MINIMUM_PLAYERS = 2;
	protected Map<Integer, Skills> chosenSkills; //Integer is index in alivePlayables
	private Map<Playable, Integer> winCountMap;
	protected Map<Playable, Double> randomDifference;
	
	/**
	 * Constructor for a referee. 
	 */
	public Referee()
	{
		this.playables = new ArrayList<Playable>();
		this.chosenSkills = new HashMap<Integer, Skills>();
		this.randomDifference = new HashMap<Playable, Double>();
		this.winCountMap = new HashMap<Playable, Integer>();
		this.fightCount = 0;
		this.resetFightWinCount();
	}
	
	/**
	 * Calculates damages on all non-sleeping playables. Calls 
	 * the damageCalculator to calculate each damage amount. 
	 */
	public void calculateAllDamages()
	{
		int nextPlayableIndex;
		Playable playable, nextPlayable;
		Skills opponentSkill, playableSkill;
		Pair damage;
		List<Playable> alivePlayables = this.getAlivePlayables();
		double opponentHP;
		
		/* Loop that calculates and deals damage for each non-sleeping player */
		for (int i = 0; i < alivePlayables.size(); i++)
		{
			AttackEvent.AttackEventBuilder aeb = new AttackEvent.AttackEventBuilder();
			playable = alivePlayables.get(i);
			playableSkill = this.chosenSkills.get(i);
			nextPlayableIndex = (i + 1) % alivePlayables.size();
			nextPlayable = alivePlayables.get(nextPlayableIndex);
			opponentSkill = this.chosenSkills.get(nextPlayableIndex);
			opponentHP = alivePlayables.get(nextPlayableIndex).getCurrentHp();

			if(playableSkill == Skills.SHOOT_THE_MOON)
			{
				Skills prediction = playable.getSkillPrediction();
				damageCalculator.setSkillChoice(prediction);
				aeb.withPredictedSkillEnum(prediction);
			}

			damage = this.damageCalculator.calculateDamage(this.getIndexOfPlayable(playable), this.getIndexOfPlayable(nextPlayable));
			this.randomDifference.put(playable, this.randomDifference.get(playable) - damage.getRandom());
			this.randomDifference.put(nextPlayable, this.randomDifference.get(nextPlayable) + damage.getRandom());
			
			if(playableSkill == Skills.REVERSAL_OF_FORTUNE)
				this.randomDifference.put(playable, 0.0);
			
			Utility.printMessage(playable.getPlayerName()  +" dealt " + damage.getRandom() 
				+ " random damage and " + damage.getConditional() 
				+ " conditional damage on " + nextPlayable.getPlayerName()
				+ " with skill " + playableSkill.toString());

			super.setChanged();
			AttackEvent ae = aeb.withAttackingPlayerIndex(this.getIndexOfPlayable(playable))
					.withAttackingSkillChoice(playableSkill)
					.withConditionalDamage(damage.getConditional())
					.withRandomDamage(damage.getRandom())
					.withVictimPlayerIndex(this.getIndexOfPlayable(nextPlayable)).Build();
			super.notifyObservers(ae);
			
			nextPlayable.updateHp(damage.getConditional() + damage.getRandom());
		}
	}
	
	/**
	 * Adds a player to the list. This is used in set up.
	 * @param newPlayer
	 */
	public void addPlayer(Playable newPlayer)
	{
		this.playables.add(newPlayer);
		this.randomDifference.put(newPlayer, 0.0);
		super.addObserver(newPlayer);// Support observable pattern
	}
	
	/**
	 * Returns a playable from the list. 
	 * @param index
	 * @return
	 */
	public Playable getPlayable(int index)
	{
		return this.playables.get(index);
	}
	
	/**
	 * Returns the amount a winner won by.
	 * @return the amount of winners.
	 */
	private int getWinnerAmount()
	{
		Playable tempPlayer;
		int playersLeft;
		int winnerAmount;
		
		playersLeft = 0;
		winnerAmount = 0;
		
		for (int i = 0; i < this.playables.size(); i++)
		{
			tempPlayer = this.playables.get(i);
			
			if (tempPlayer.isAwake())
			{
				playersLeft++;
			}
		}
		
		if (playersLeft == 1)
		{
			winnerAmount = 1;
		}
		else
		{
			winnerAmount = this.playables.size();
		}
		
		return winnerAmount;
	}
	
	/**
	 * Gets a list of all winners in the fight. 
	 * @return winnerList, the winners.
	 */
	public List<Playable> getWinners()
	{
		int winnerAmount;
		List<Playable> winnerList = new ArrayList<Playable>();
		Playable winner;
		
		winnerAmount = getWinnerAmount();
		
		if (winnerAmount == 1)
		{
			winner = getWinner();
			winnerList.add(winner);
		}
		else
		{
			winnerList = this.playables;
		}
		
		return winnerList;
	}
	
	/**
	 * Determines who is the winner of the game. 
	 * @return winner, the person who won the game.
	 */
	private Playable getWinner()
	{
		Playable tempPlayer;
		Playable winner;
		
		winner = null;
		
		for (int i = 0; i < this.playables.size(); i++)
		{
			tempPlayer = this.playables.get(i);
			
			if (tempPlayer.isAwake())
			{
				winner = tempPlayer;
			}
		}
		
		return winner;
	}

	/**
	 * Returns the playables list
	 * @return
	 */
	public List<Playable> getAllPlayables()
	{
		return this.playables;
	}
	
	/**
	 * Return the map with players and wins. 
	 * @return
	 */
	public Map<Playable, Integer> getPlayerWinCount()
	{
		return this.winCountMap;
	}
	
	/**
	 * Returns all alive playables in the battle. This may be used for
	 * checking winners or actual game-play.
	 * @return
	 */
	public List<Playable> getAlivePlayables()
	{
		List<Playable> alivePlayables = new ArrayList<Playable>();
		for(Playable p : this.playables)
			if(p.isAwake())
				alivePlayables.add(p);
		return alivePlayables;
	}
	
	/**
	 * Returns all sleeping playables in the battle.
	 * @return
	 */
	public List<Playable> getSleepingPlayables()
	{
		List<Playable> sleepingPlayables = new ArrayList<Playable>();
		for(Playable p : this.playables)
			if(!p.isAwake())
				sleepingPlayables.add(p);
		return sleepingPlayables;
	}
	
	/**
	 * Resets the playables list to a new list.
	 */
	public void clearPlayables()
	{
		this.playables = new ArrayList<Playable>();
	}
	
	/**
	 * Resets HP and cool-downs for all pets. Also changes
	 * isSleeping to false.
	 */
	public void updateHP()
	{
		for(Playable p : this.playables)
			p.resetHp();
	}
	
	/**
	 * True if there is a winner, false otherwise
	 * for the fight to continue.
	 * @return 
	 */
	public boolean hasWinner()
	{
		List<Playable> playables = this.getAlivePlayables();
		return playables.size() < this.MINIMUM_PLAYERS;
	}
	
	/**
	 * Allows all players to choose their skill. And checks for sleeping
	 * players in the round. 
	 */
	public void allChooseSkill()
	{
		Playable tempPlayable;
		List<Playable> playables = getAlivePlayables();
		List<Playable> sleepingPlayables = getSleepingPlayables();
		
		if(sleepingPlayables.size() > 0)
		{
			String sleepingString = "";
			for(Playable p : sleepingPlayables)
				sleepingString += p.getPlayerName() + ", ";
			sleepingString = sleepingString.substring(0, sleepingString.length() - 2);
			Utility.printMessage("Sleeping players are: " + sleepingString);
		}
		
		Utility.printEndline();
		
		for(int i = 0; i < playables.size(); i++)
		{
			Skills chosen = playables.get(i).chooseSkill();
			this.chosenSkills.put(this.getIndexOfPlayable(playables.get(i)), chosen);
			playables.get(i).decrementRechargeTimes();
			playables.get(i).setRechargeTime(chosen, (chosen == Skills.REVERSAL_OF_FORTUNE || chosen == Skills.SHOOT_THE_MOON) ? 6 : 1);
		}
		Utility.printEndline();
	}

	/**
	 * Resets all playable wins to 0
	 */
	public void resetFightWinCount()
	{
		for(Playable p : this.getAllPlayables())
			this.winCountMap.put(p, 0);
		this.damageCalculator = new CalculateDamage(this);
	}
	
	/**
	 * Reset the random difference, used at the beginning
	 * of each fight.
	 */
	public void resetRandomDifference()
	{
		this.randomDifference.forEach((key,value) -> value = 0.0); 
	}
	
	/**
	 * Increments the player's win count in the map
	 * @param p
	 */
	public void addFightWonToPlayer(Playable p)
	{
		this.winCountMap.put(p, this.winCountMap.get(p) + 1);
	}
	
	/**
	 * Gets the playable at a given index
	 * @param indexOfPlayable
	 * @return
	 */
	public Skills getChosenSkillByIndex(int indexOfPlayable)
	{
		return this.chosenSkills.get(indexOfPlayable);
	}
	
	/**
	 * Gets the index of a playable in the playable list. 
	 * @param p
	 * @return
	 */
	public int getIndexOfPlayable(Playable p)
	{
		return this.playables.indexOf(p);
	}
	
	/**
	 * Gets the index of an alive playable in the playable list. 
	 * @param p
	 * @return
	 */
	public int getIndexOfAlivePlayable(Playable p)
	{
		return this.getAlivePlayables().indexOf(p);
	}
	
	/**
	 * Gets the index of a playable in the playable list. 
	 * @param p
	 * @return
	 */
	public int getIndexOfPlayable(String nameOfPlayable)
	{
		for(Playable p : this.playables)
			if(p.getPetName().equals(nameOfPlayable))
				return this.playables.indexOf(p);
		return -1;
	}
	
	/**
	 * Returns the cumulative random difference
	 * @param indexOfPlayable
	 * @return
	 */
	public double getRandomDifference(int indexOfPlayable)
	{
		return this.randomDifference.get(this.getPlayable(indexOfPlayable));
	}
	
	/**
	 * Adds an event to the referee (an observer)
	 * @param be
	 */
	public void addEvent(BaseEvent be)
	{
		super.setChanged();
		super.notifyObservers(be);
	}
	
	/**
	 * GUI ONLY: Adds a skill to the choosenSkills map and return true 
	 * if the player is the last one to choose for the round, false otherwise.
	 * @param player
	 * @param skill
	 * @return
	 */
	public boolean individualChooseSkill(Playable player, Skills skill)
	{
		return false;//NO-OP for non-GUI versions
	}
	
	/**
	 * Method for getting the amount of fights.
	 * @return
	 */
	public int getFightCount()
	{
		return this.fightCount;
	}
	
	/**
	 * Method for setting the amount of fights.
	 * @param fightCount
	 */
	public void setFightCount(int fightCount)
	{
		this.fightCount = fightCount;
	}
	
	/**
	 * Method for getting the winCountMap().
	 * @return
	 */
	public Map<Playable, Integer> getWinCountMap()
	{
		return this.winCountMap;
	}
	
	/**
	 * Method for getting the wins for each player from the HashMap.
	 * @return a list of the playables in the order than won.
	 */
	protected ArrayList<Playable> getOrganizedPlayableListByWinner()
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
	 * Method for getting the fight win list. It will iterate through the
	 * list and get each player's win amount and organize it by most wins
	 * first.
	 */
	public List<String> getPlayableFightWinList()
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
	
	// GUI (ALL NO-OPS)--------------------------------------------------------------------------
	
	public void instantiateGUIVariables(){}
	
	public Playable getNextAlivePlayable() {return null;}

	public List<Skills> getCurrentPlayableSkillStringList()	{return null;}
	
	public Playable getGUICurrentPlayer(){return null;}
	
	public void addChosenSkillToCurrentPlayable(String s){}
	
	public void toNextHumanPlayer(){}
	
	public boolean areMorePlayersChosingSkills(){return false;}
	
	public int getAmountOfPlayers(){return this.playables.size() - 1;}
	
	public boolean getStillChosingSkills(){return false;}
	
	public void resetToBeginningOfSKillSelection(){}
	
	public int getRechargeTimeForSkill(Skills s){return 0;}
	
	public void announceRoundResults(){}
	
	public void startBattle() {}
	
	public int getCurrentFightNumber() {return 0;}
	
	public Playable getNextPlayerInfo(){return null;}

	public void setUpGUI(GamePlayPresenter presenter) {}
	
	public void guiWinner() {}
	
	public void play(){}
	
	public void play(String playerName, String skillChoice){}
	
	public void addChosenHumanSkill(String playerName, String skillChoice){ }

	public void handleFightStartEvent(){ }
	
	public List<String> getPlayableBattleWinList(){ return new ArrayList<String>(); }
}
