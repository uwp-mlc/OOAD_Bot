package edu.dselent.control;

import java.util.*;

import edu.dselent.io.IoManager;
import edu.dselent.config.ApplicationConfigurations;
import edu.dselent.io.Inputtable;
import edu.dselent.io.Outputtable;
import edu.dselent.player.PetTypes;
import edu.dselent.player.PlayerTypes;
import edu.dselent.settings.GameSettings;
import edu.dselent.settings.PlayerInfo;
import edu.dselent.settings.PlayerInfo.PlayerInfoBuilder;
import edu.dselent.settings.SettingsConstants;
import edu.dselent.skill.Skills;
import edu.dselent.utils.StringConstants;
import edu.dselent.utils.StringConstants.StringKeys;

public class PlayerSettingsControl
{
	private IoManager ioManager;
	private Outputtable output;
	private Inputtable input;
	
	private GameSettings gameSettings;
	private List<PlayerInfo> playerInfoList;
	
	public PlayerSettingsControl(GameSettings gameSettings)
	{		
		this.ioManager = ApplicationConfigurations.INSTANCE.getIoManager();
		this.output = ioManager.getOutputSender();
		this.input = ioManager.getInputGetter();
		
		this.gameSettings = gameSettings;
		this.playerInfoList = new ArrayList<>();
	}

	public List<PlayerInfo> getPlayerInfoList()
	{
		return playerInfoList;
	}

	
	public List<PlayerInfo> retrievePlayerInfoList()
	{
		int numberOfPlayers = gameSettings.getNumberOfPlayers();
		
		List<PlayerInfo> playerInfoList = new ArrayList<>();
		
		for(int i=0; i<numberOfPlayers; i++)
		{
			PlayerInfoBuilder playerInfoBuilder = new PlayerInfoBuilder();
			
			PlayerTypes playerType = retrievePlayerType(i);
			PetTypes petType = retrievePetType(i);
			String playerName = retrievePlayerName(i);
			String petName = retrievePetName(playerName);
			double startingHp = retrieveStartingHp(petName);
			Set<Skills> skillSet = getSkillSet();

			PlayerInfo playerInfo = playerInfoBuilder.withPlayerType(playerType)
					.withPetType(petType)
					.withPlayerName(playerName)
					.withPetName(petName)
					.withStartingHp(startingHp)
					.withSkillSet(skillSet)
					.build();
			
			playerInfoList.add(playerInfo);
		}
		
		return playerInfoList;
	}

	private PlayerTypes retrievePlayerType(int playerIndex)
	{
		PlayerTypes playerType = null;
		int playerTypeInt;
		
		while(playerType == null)
		{
			String outputMessage1 = StringConstants.getFormattedString(StringConstants.StringKeys.ENTER_PLAYER_TYPE_KEY, playerIndex+1);
			output.outputString(outputMessage1);
			
			String outputMessage2 = StringConstants.getFormattedString(StringConstants.StringKeys.ENTER_PLAYER_TYPE_CHOICES_KEY);
			output.outputString(outputMessage2);
				
			String playerTypeString = input.getString();
			
			try
			{
				playerTypeInt = Integer.parseInt(playerTypeString);
				playerType = PlayerTypes.values()[playerTypeInt-1];
			}
			catch(Exception e)
			{
				String errorMessage = StringConstants.getFormattedString(StringKeys.INVALID_PLAYER_TYPE_KEY, playerTypeString);
				output.outputString(errorMessage);
			}
		}
		
		return playerType;
	}
	
	private PetTypes retrievePetType(int playerIndex)
	{
		PetTypes petType = null;
		int petTypeInt;
		
		while(petType == null)
		{
			
			String outputMessage1 = StringConstants.getFormattedString(StringConstants.StringKeys.ENTER_PET_TYPE_KEY, (playerIndex+1));
			output.outputString(outputMessage1);
			
			String outputMessage2 = StringConstants.getFormattedString(StringConstants.StringKeys.ENTER_PET_TYPE_CHOICES_KEY);
			output.outputString(outputMessage2);
						
			String petTypeString = input.getString();
			
			try
			{
				petTypeInt = Integer.parseInt(petTypeString);
				petType = PetTypes.values()[petTypeInt-1];
			}
			catch(Exception e)
			{				
				String errorMessage = StringConstants.getFormattedString(StringKeys.INVALID_PET_TYPE_KEY, petTypeString);
				output.outputString(errorMessage);
			}
		}
		
		return petType;
	}
	
	private String retrievePlayerName(int playerIndex)
	{	
		String playerName = null;
		
		while(playerName == null)
		{
			String outputMessage = StringConstants.getFormattedString(StringConstants.StringKeys.ENTER_PLAYER_NAME_KEY, (playerIndex+1));
			output.outputString(outputMessage);
			
			playerName = input.getString();
		}
		
		return playerName;
	}
	
	private String retrievePetName(String playerName)
	{	
		String petName = null;
		
		while(petName == null)
		{
			String outputMessage = StringConstants.getFormattedString(StringConstants.StringKeys.ENTER_PET_NAME_KEY, playerName);
			output.outputString(outputMessage);

			petName = input.getString();
		}
		
		return petName;
	}
	
	private double retrieveStartingHp(String petName)
	{	
		double startingHp = -1.0;
		boolean validStartingHp = false;
		
		while(!validStartingHp)
		{
			String outputMessage = StringConstants.getFormattedString(StringConstants.StringKeys.ENTER_STARTING_HP_KEY, petName);
			output.outputString(outputMessage);
			
			String startingHpString = input.getString();
			
			try
			{
				startingHp = Double.parseDouble(startingHpString);

				if(startingHp <= SettingsConstants.MINIMUM_STARTING_HP)
				{
					String errorMessage = StringConstants.getFormattedString(StringKeys.INVALID_STARTING_HP_KEY, startingHpString);
					output.outputString(errorMessage);
				}
				else
				{
					validStartingHp = true;
				}
			}
			catch(Exception e)
			{
				String errorMessage = StringConstants.getFormattedString(StringKeys.INVALID_STARTING_HP_KEY, startingHpString);
				output.outputString(errorMessage);
			}
		}
		
		return startingHp;
	}
	
	// TODO hard-coded skill choice for now
	// Future work to let users choose skills from the list of all possible skills
	private Set<Skills> getSkillSet()
	{
		Set<Skills> skillSet = getDefaultSkillSet();

		/*
		if()
		{
			skillSet = getCustomSkillSet();
		}
		else
		{
			skillSet = getDefaultSkillSet();
		}
		*/

		return skillSet;
	}

	// TODO not done yet
	private Set<Skills> getCustomSkillSet()
	{

		Set<Skills> coreSkillSet = EnumSet.noneOf(Skills.class);
		Set<Skills> specialSkillSet = EnumSet.noneOf(Skills.class);
		Set<Skills> allSkills = EnumSet.allOf(Skills.class);



		while(coreSkillSet.size() < SettingsConstants.NUMBER_OF_CONDITIONAL_SKILLS)
		{
			// output current core skills
			// output possible remaining core skill choices

			// ... continue
		}


		Set<Skills> skillSet = EnumSet.noneOf(Skills.class);
		return skillSet;
	}

	private Set<Skills> getDefaultSkillSet()
	{
		return EnumSet.allOf(Skills.class);
	}
}
