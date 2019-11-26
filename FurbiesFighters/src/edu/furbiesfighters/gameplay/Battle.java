package edu.furbiesfighters.gameplay;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Observable;
import java.util.Set;

import edu.furbiesfighters.players.Playable;
import edu.furbiesfighters.players.Player;
import edu.furbiesfighters.skills.Skills;
import edu.furbiesfighters.events.FightStartEvent;
import edu.furbiesfighters.events.PlayerEventInfo;
import edu.furbiesfighters.events.FightStartEvent.FightStartEventBuilder;
import edu.furbiesfighters.players.AIPlayer;
import edu.furbiesfighters.players.Human;
import edu.furbiesfighters.players.JarvisPlayer;
import edu.furbiesfighters.players.AverageJoe;
import edu.furbiesfighters.players.PetTypes;
import edu.furbiesfighters.utility.Utility;


/**
 * A class for controlling the flow of a battle. It will coordinate the
 * battle and the fights within the battle. It will create the battle
 * and prompt the players to enter all the required data for each battle.
 */
public class Battle extends Observable
{
	private static final int MINIMUM_PLAYERS = 2;
	private static final int MINIMUM_FIGHTS = 1;
	private static final int PLAYER_HEALTH = 100;
	
	protected List<Fight> fightList;
	protected Referee ref;

	private int humanAmount, aiAmount, smartAiAmount;
	protected int fightAmount;
	
	protected Fight currentFight;
	
	int neat_count;
	int opponent_count;
	int human_count;
	
	List<String> neat_names;
	List<String> opponent_names;
	List<String> human_names;
	
	List<Integer> neat_types;
	List<Integer> opponent_types;
	List<Integer> human_types;
	
	GameSettings gs;
	
	/**
	 * Instantiates the Battle class. It will instantiate the variables.
	 */
	public Battle()
	{
		fightList = new ArrayList<Fight>();
		this.ref = new Referee();
	}
	
	/**
	 * Instantiates the Battle class. It will take a ref parameter. This
	 * constructor is used for the GUI version where the ref is already
	 * created.
	 */
	public Battle(Referee ref, int fightAmount)
	{
		fightList = new ArrayList<Fight>();
		this.ref = ref;
		this.fightAmount = fightAmount;
	}
	
	public Battle(int neat_count, int opponent_count, int human_count, 
			List<String> neat_names, List<String> opponent_names, List<String> human_names, int fight_amount,
			List<Integer> neat_types, List<Integer> opponent_types, List<Integer> human_types) {
		this.neat_count = neat_count;
		this.opponent_count = opponent_count;
		this.human_count = human_count;
		this.neat_names = neat_names;
		this.opponent_names = opponent_names;
		this.human_names = human_names;
		this.fightAmount = fight_amount;
		this.human_types = human_types;
		this.opponent_types = opponent_types;
		this.neat_types = neat_types;
		
		fightList = new ArrayList<Fight>();
		this.ref = new Referee();
	}
	
	public Battle(GameSettings gs) {
		this.gs = gs;
		this.neat_count = gs.neat_count;
		this.opponent_count = gs.opponent_count;
		this.human_count = gs.human_count;
		this.neat_names = gs.neat_names;
		this.opponent_names = gs.opponent_names;
		this.human_names = gs.human_names;
		this.fightAmount = gs.fight_amount;
		this.human_types = gs.human_types;
		this.opponent_types = gs.opponent_types;
		this.neat_types = gs.neat_types;
		
		fightList = new ArrayList<Fight>();
		this.ref = new Referee();
	}
	

	/**
	 * Method to instantiate the variables for the class. It gets all
	 * the player information, fight information, and prints it out.
	 */
	public void setUpBattle()
	{
		Utility.printEndline();

		this.getAllNeatAIPlayerInformation();
		this.getAllSmartAIPlayerInformation();
		this.getAllHumanPlayerInformation();
		setFights();
		
		announceBattleInformation();
	}
	
	/**
	 * Method for playing the battle. It will iterate through each fight
	 * until the end of the battle.
	 */
	public void playBattle()
	{
		AIPlayer.resetAICount();
		for (int i = 0; i < fightAmount; i++)
		{
			this.ref.resetRandomDifference();
			ArrayList<PlayerEventInfo> peL =  new ArrayList<PlayerEventInfo>();
			Set<Skills> skillSet = new HashSet<Skills>();
			skillSet.add(Skills.PAPER_CUT);
			skillSet.add(Skills.SCISSOR_POKE);
			skillSet.add(Skills.ROCK_THROW);
			skillSet.add(Skills.SHOOT_THE_MOON);
			skillSet.add(Skills.REVERSAL_OF_FORTUNE);
			
			for( int  j = 0 ; j < ref.getAllPlayables().size(); j++ )
			{
				Playable temp = ref.getAllPlayables().get(j);
				PlayerEventInfo.PlayerEventInfoBuilder peb = new PlayerEventInfo.PlayerEventInfoBuilder();
				PlayerEventInfo tempInfo =  peb.withPetType(temp.getPetType()).withPetName(temp.getPetName())
						.withPlayerType(temp.getPlayerType()).withStartingHp(temp.getPlayerFullHP()).withSkillSet(skillSet).build();
				peL.add(tempInfo);
			}
			
			FightStartEvent.FightStartEventBuilder fsb = new FightStartEvent.FightStartEventBuilder();
			currentFight = fightList.get(i);
			fsb.withFightNumber(i);
			Utility.printLargeBanner(currentFight.getName() + ". Beginning");
			fsb.withPlayerEventInfo(peL); 
			this.ref.addEvent(fsb.build());
			currentFight.playFight();
			announceFightResults();
		}
	}
	
	/**
	 * Method for instantiating the fights in the battle. It will create a
	 * new fight and send it the playerList.
	 */
	protected void setFights()
	{
		Fight tempFight;
		String tempFightName;
		int tempFightIndex;
		
		for (int i = 0; i < fightAmount; i++)
		{
			tempFightIndex = i + 1;
			tempFightName = "Fight " + tempFightIndex;
			
			tempFight = new Fight(this.ref, tempFightName);
			
			fightList.add(tempFight);
		}
	}
	
	/**
	 * Method for getting all the player information. It will loop for the
	 * playerAmount, ask for the player information, and instantiate the
	 * player's information.
	 */
	private void getAllHumanPlayerInformation()
	{
		String playerName;
		String petName;
		PetTypes playerType;
		int playerIndex;
		int playerHealth;
		Playable player;
		
		for (int i = 0; i < this.human_count; i++)
		{
			playerIndex = i + 1;
			playerName = this.human_names.get(i);
			playerType = PetTypes.values()[this.human_types.get(i)];
			playerHealth = 100;
			petName = this.human_names.get(i);
			
			Utility.printEndline();
			
			player = new Human(playerHealth, playerName, petName, playerType);
			
			this.ref.addPlayer(player);
		}
		
		this.ref.resetFightWinCount();
	}
	
	/**
	 * Method for getting all the player information. It will loop for the
	 * playerAmount, ask for the player information, and instantiate the
	 * player's information.
	 */
	private void getAllSmartAIPlayerInformation()
	{
		String playerName;
		String petName;
		PetTypes playerType;
		int playerHealth;
		Playable player;
		
		PetTypes[] petVals = PetTypes.values();
		for (int i = 0; i < this.opponent_count; i++)
		{
			playerName = "Jarvis " + (i + 1);
			Utility.printMessage(playerName + ":");
			int typeInt = this.opponent_types.get(i);
			playerType = petVals[typeInt];
			playerHealth = 100;
			petName = this.opponent_names.get(i);
			
			Utility.printEndline();
			
			player = new JarvisPlayer(playerHealth, playerName, petName, playerType);
			
			this.ref.addPlayer(player);
		}
	}
	
	/**
	 * Method for getting all the player information. It will loop for the
	 * playerAmount, ask for the player information, and instantiate the
	 * player's information.
	 */
	private void getAllNeatAIPlayerInformation()
	{
		String playerName;
		String petName;
		PetTypes playerType;
		int playerHealth;
		Playable player;
		
		for (int i = 0; i < this.neat_count; i++)
		{
			playerName = "Average Joe " + (i + 1);
			Utility.printMessage(playerName + ":");
			playerType = PetTypes.values()[this.neat_types.get(i)];
			playerHealth = 100;
			petName = this.neat_names.get(i);
			
			Utility.printEndline();
			
			player = new AverageJoe(playerHealth, playerName, petName, playerType,gs);
			
			this.ref.addPlayer(player);
		}
	}


	/**
	 * Method for announcing the information for all players. It will
	 * loop through the array of players and print out all their
	 * information.
	 */
	protected void announcePlayerInformation()
	{
		int playerIndex;

		Utility.printSmallBanner("Player List");
		
		for (int i = 0; i < aiAmount + humanAmount; i++)
		{
			playerIndex = i + 1;
			
			Utility.printMessage(playerIndex + ". Name = " + ref.getPlayable(i).getPlayerName() 
								+ ", Type = " + ref.getPlayable(i).getPetType().toString()
								+ ", pet's name = " + ref.getPlayable(i).getPetName());
		}
		
		Utility.printEndline();
	}

	/**
	 * Method for announcing the information for the battle.
	 */
	protected void announceBattleInformation()
	{
		Utility.printSmallBanner("Battle Information");
		Utility.printMessage("Amount of fights:  " + fightAmount);
		Utility.printMessage("Amount of players: " + humanAmount);
		Utility.printMessage("Amount of players: " + aiAmount);
		Utility.printEndline();
	}

	/**
	 * Method for announcing the information for a fight.
	 */
	private void announceFightResults()
	{
		List<Playable> winners = this.currentFight.determineFightWinners();
		
		if(winners == null)
			Utility.printMessage("A tie occured in fight " + this.currentFight.getName());
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
	 * Returns the list of players who won the battle.
	 * @return
	 */
	public List<Playable> getBattleWinner()
	{
		int max = -1;
		List<Playable> winningPlayables = new ArrayList<Playable>();
		for(Playable p : this.ref.getPlayerWinCount().keySet())
		{
			if(this.ref.getPlayerWinCount().get(p) > max)
			{
				max = this.ref.getPlayerWinCount().get(p);
				winningPlayables.clear();
				winningPlayables.add(p);
			}
			else if(this.ref.getPlayerWinCount().get(p) == max)
			{
				max = this.ref.getPlayerWinCount().get(p);
				winningPlayables.add(p);
			}
		}
		return winningPlayables;
	}
	
	/**
	 * Announces how many fights each player has won in the battle. 
	 */
	public void announceFightWinCount()
	{
		for(String s : this.ref.getPlayableFightWinList())
		{
			Utility.printMessage(s);
		}
	}
}