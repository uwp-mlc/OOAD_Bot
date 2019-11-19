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

	/**
	 * Method to instantiate the variables for the class. It gets all
	 * the player information, fight information, and prints it out.
	 */
	public void setUpBattle()
	{
		int aiPlayersNeeded = this.MINIMUM_PLAYERS;
		int smartAiPlayersNeeded = 0;
		this.ref = new Referee();
		this.currentFight = null;
		this.fightList = new ArrayList<Fight>();
		humanAmount = getHumanAmount();
		aiPlayersNeeded -= humanAmount;
		if(aiPlayersNeeded < 0)
			aiPlayersNeeded = 0;
		aiAmount = getAiAmount();
		smartAiPlayersNeeded = aiPlayersNeeded - aiAmount;
		if(smartAiPlayersNeeded < 0)
			smartAiPlayersNeeded = 0;
		smartAiPlayersNeeded = getSmartAiAmount(smartAiPlayersNeeded);
		this.smartAiAmount = smartAiPlayersNeeded;
		fightAmount = getFightAmount();
		
		Utility.printEndline();

		getAllAIPlayerInformation();
		getAllSmartAIPlayerInformation();
		getAllHumanPlayerInformation();
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
			//announcePlayerInformation();
			//FightStartEvent fse = fsb.build();
			//this.ref.addEvent(fse);
		}
	}
	
	/**
	 * Method for getting the amount of players in the battle. It will
	 * check to see if the input is valid.
	 * @return playerAmount, the amount of players in the battle.
	 */
	private int getHumanAmount()
	{
		String unformattedAmount;
		int formattedAmount;
		boolean isValidInteger;
		
		
		unformattedAmount = Utility.prompt("Enter the amount of human players for this battle:");
		isValidInteger = Utility.isValidIntegerAmount(unformattedAmount, 0);
		
		while (!isValidInteger)
		{
			unformattedAmount = Utility.prompt("Enter a valid integer human player amount for this battle:");
			isValidInteger = Utility.isValidIntegerAmount(unformattedAmount, 0);
		}
		
		formattedAmount = Integer.parseInt(unformattedAmount);
		
		return formattedAmount;
	}
	
	/**
	 * Method for getting the amount of AI players in the battle. It will
	 * check to see if the input is valid.
	 * @return playerAmount, the amount of players in the battle.
	 */
	//private int getAiAmount(int aiPlayersNeeded)
	private int getAiAmount()
	{
		String unformattedAmount;
		int formattedAmount;
		boolean isValidInteger;
		
		
		unformattedAmount = Utility.prompt("Enter the amount of AI players for this battle:");
		//isValidInteger = Utility.isValidIntegerAmount(unformattedAmount, aiPlayersNeeded);
		isValidInteger = Utility.isValidIntegerAmount(unformattedAmount, 0);
		
		while (!isValidInteger)
		{
			//unformattedAmount = Utility.prompt("Must have at least " + aiPlayersNeeded + " smart AI players. Please retry:");
			unformattedAmount = Utility.prompt("Enter a valid integer AI player amount for this battle:");
			isValidInteger = Utility.isValidIntegerAmount(unformattedAmount, 0);
		}
		
		formattedAmount = Integer.parseInt(unformattedAmount);
		
		return formattedAmount;
	}
		
	/**
	 * Method for getting the amount of players in the battle. It will
	 * check to see if the input is valid.
	 * @return playerAmount, the amount of players in the battle.
	 */
	private int getSmartAiAmount(int smartAiPlayersNeeded)
	{
		String unformattedAmount;
		int formattedAmount;
		boolean isValidInteger;
		
		
		unformattedAmount = Utility.prompt("Enter the amount of smart AI players for this battle:");
		isValidInteger = Utility.isValidIntegerAmount(unformattedAmount, smartAiPlayersNeeded);
		
		while (!isValidInteger)
		{
			unformattedAmount = Utility.prompt("Must have at least " + smartAiPlayersNeeded + " smart AI players. Please retry:");
			isValidInteger = Utility.isValidIntegerAmount(unformattedAmount, smartAiPlayersNeeded);
		}
		
		formattedAmount = Integer.parseInt(unformattedAmount);
		
		return formattedAmount;
	}
	/**
	 * Method for getting the amount of fights in the battle. It will
	 * check to see if the input is valid.
	 * @return fightAmount, the amount of fights in the battle.
	 */
	private int getFightAmount()
	{
		String unformattedFightAmount;
		int formattedFightAmount;
		boolean isValid;
		
		unformattedFightAmount = Utility.prompt("Enter the amount of fights in this battle:");
		
		isValid = Utility.isValidIntegerAmount(unformattedFightAmount, MINIMUM_FIGHTS);
		
		while (!isValid)
		{
			unformattedFightAmount = Utility.prompt("Enter a valid integer amount of fights in this battle:");
			isValid = Utility.isValidIntegerAmount(unformattedFightAmount, MINIMUM_FIGHTS);
		}
		
		formattedFightAmount = Integer.parseInt(unformattedFightAmount);
		
		return formattedFightAmount;
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
		
		for (int i = 0; i < humanAmount; i++)
		{
			playerIndex = i + 1;
			playerName = getPlayerName(playerIndex);
			playerType = getPlayerType(playerName);
			playerHealth = getPlayerHealth();
			petName = getPetName();
			
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
	private void getAllAIPlayerInformation()
	{
		String playerName;
		String petName;
		PetTypes playerType;
		int playerHealth;
		Playable player;
		
		for (int i = 0; i < aiAmount; i++)
		{
			playerName = "AI Player " + (i + 1);
			Utility.printMessage(playerName + ":");
			playerType = getPlayerType(playerName);
			playerHealth = getPlayerHealth();
			petName = getPetName();
			
			Utility.printEndline();
			
			player = new AIPlayer(playerHealth, playerName, petName, playerType);
			
			this.ref.addPlayer(player);
		}
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
		
		for (int i = 0; i < this.smartAiAmount; i++)
		{
			playerName = "Jarvis " + (i + 1);
			Utility.printMessage(playerName + ":");
			playerType = getPlayerType(playerName);
			playerHealth = getPlayerHealth();
			petName = getPetName();
			
			Utility.printEndline();
			
			player = new JarvisPlayer(playerHealth, playerName, petName, playerType);
			
			this.ref.addPlayer(player);
		}
	}
	
	/**
	 * Get health
	 * @return
	 */
	private int getPlayerHealth()
	{
		String unformattedSeed;
		int formattedSeed;
		
		unformattedSeed = Utility.prompt("Enter the player's health");
		
		while (!Utility.isValidIntegerAmount(unformattedSeed, 0))
		{
			unformattedSeed = Utility.prompt("Enter a valid integer health:");
		}
		
		formattedSeed = Integer.parseInt(unformattedSeed);
		
		return formattedSeed;
	}
	
	/**
	 * Method for getting the player name. It will check to see if the
	 * player's name is valid.
	 * @param playerIndex - the index used to address the player.
	 * @return playerName, the name of the player.
	 */
	private String getPlayerName(int playerIndex)
	{
		String playerName;
		boolean isValid;
		
		playerName = "";
		isValid = false;
		
		playerName = Utility.prompt("Player " + (playerIndex) + ", enter your name:");
		
		while (!isValid)
		{
			if (playerName.length() != 0)
			{
				isValid = true;
			}
			else
			{
				playerName = Utility.prompt("Player " + (playerIndex) + ", enter a valid name:");
			}
		}
		
		return playerName;
	}
	
	/**
	 * Method for getting the pet name. It will check to see if the pet
	 * name is valid.
	 * @return petName, the name of the player's pet.
	 */
	private String getPetName()
	{
		String playerName;
		boolean isValid;
		
		playerName = "";
		isValid = false;
		
		playerName = Utility.prompt("Enter your pets name:");
		
		while (!isValid)
		{			
			if (playerName.length() != 0)
			{
				isValid = true;
			}
			else
			{
				playerName = Utility.prompt("Enter a valid pet name:");
			}
		}
		
		return playerName;
	}
	
	/**
	 * Method for getting the player's type. It will check to see if
	 * the type is valid.
	 * @param playerName - the name of the player to be addressed.
	 * @return playerType, the type of pet the player wants.
	 */
	private PetTypes getPlayerType(String playerName)
	{
		String playerSelection;
		PetTypes playerType;

		playerType = null;
		
		while (playerType == null)
		{
			playerSelection = Utility.prompt("Enter 1 for \"INTELLIGENCE\", 2 for \"SPEED\", or 3 for \"POWER\""
					+ "\n" + "Player " + playerName + ", enter your type:");
			
			switch(playerSelection)
			{
			case "1":
				playerType = PetTypes.INTELLIGENCE;
				break;
			case "2":
				playerType = PetTypes.SPEED;
				break;
			case "3":
				playerType = PetTypes.POWER;
				break;
			default:
				playerType = null;
				Utility.printMessage("Invalid entry, try again...");
				break;
			}
		}
		return playerType;
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