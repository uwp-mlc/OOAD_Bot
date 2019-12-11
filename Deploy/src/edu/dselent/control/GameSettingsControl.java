package edu.dselent.control;

import edu.dselent.customexceptions.InvalidNumberOfPlayersException;
import edu.dselent.io.IoManager;
import edu.dselent.config.ApplicationConfigurations;
import edu.dselent.io.Inputtable;
import edu.dselent.io.Outputtable;
import edu.dselent.settings.GameSettings;
import edu.dselent.settings.SettingsConstants;
import edu.dselent.utils.StringConstants;
import edu.dselent.utils.StringConstants.StringKeys;

public class GameSettingsControl
{
	// Since all methods use the same IoManager, I made it a class variable rather than retrieving it each time
	// Design choice
	private IoManager ioManager;
	private Outputtable output;
	private Inputtable input;

	private GameSettings gameSettings;
	
	public GameSettingsControl()
	{		
		ioManager = ApplicationConfigurations.INSTANCE.getIoManager();
		output = ioManager.getOutputSender();
		input = ioManager.getInputGetter();
	}

	public GameSettings getGameSettings()
	{
		return gameSettings;
	}

	/**
	 * Facade for simple call to get all game settings
	 * 
	 * @return All necessary settings to run the game
	 */
	public GameSettings retrieveGameSettings()
	{
		long randomSeed = retrieveRandomSeed();
		int numberOfPlayers = retrieveNumberOfPlayers();
		int numberOfFights = retrieveNumberOfFights();
	
		gameSettings = new GameSettings(randomSeed, numberOfPlayers, numberOfFights);

		return gameSettings;
	}
	
	private Long retrieveRandomSeed()
	{
		Long randomSeed = null;
		
		while(randomSeed == null)
		{
			String outputMessage = StringConstants.getFormattedString(StringConstants.StringKeys.ENTER_RANDOM_SEED_KEY);
			output.outputString(outputMessage);
			
			String randomSeedString = input.getString();
			
			try
			{
				randomSeed = Long.parseLong(randomSeedString);
			}
			catch(Exception e)
			{
				String errorMessage = StringConstants.getFormattedString(StringKeys.INVALID_RANDOM_SEED_KEY, randomSeedString);
				output.outputString(errorMessage);
			}

		}
		
		return randomSeed;
	}
	
	private int retrieveNumberOfPlayers()
	{
		int numberOfPlayers = -1;
		boolean validNumberOfPlayers = false;
		
		while(!validNumberOfPlayers)
		{
			String outputMessage = StringConstants.getFormattedString(StringConstants.StringKeys.ENTER_NUMBER_OF_PLAYERS_KEY);
			output.outputString(outputMessage);
			
			String numberOfPlayersString = input.getString();
			
			try
			{
				numberOfPlayers = Integer.parseInt(numberOfPlayersString);

				if(numberOfPlayers < SettingsConstants.MINIMUM_NUMBER_OF_PLAYERS)
				{
					String errorMessage = StringConstants.getFormattedString(StringKeys.INVALID_NUMBER_OF_PLAYERS_KEY, numberOfPlayersString);
					output.outputString(errorMessage);
				}
				else
				{
					validNumberOfPlayers = true;
				}
			}
			catch(Exception e)
			{
				String errorMessage = StringConstants.getFormattedString(StringKeys.INVALID_NUMBER_OF_PLAYERS_KEY, numberOfPlayersString);
				output.outputString(errorMessage);
			}
		}
		
		return numberOfPlayers;
	}

	private int retrieveNumberOfFights()
	{	
		int numberOfFights = -1;
		boolean validNumberOfFights = false;
		
		while(!validNumberOfFights)
		{
			String outputMessage = StringConstants.getFormattedString(StringConstants.StringKeys.ENTER_NUMBER_OF_FIGHTS_KEY);
			output.outputString(outputMessage);
			
			String numberOfFightsString = input.getString();
			
			try
			{
				numberOfFights = Integer.parseInt(numberOfFightsString);

				if(numberOfFights < SettingsConstants.MINIMUM_NUMBER_OF_PLAYERS)
				{
					String errorMessage = StringConstants.getFormattedString(StringKeys.INVALID_NUMBER_OF_FIGHTS_KEY, numberOfFightsString);
					output.outputString(errorMessage);
				}
				else
				{
					validNumberOfFights = true;
				}
			}
			catch(Exception e)
			{
				String errorMessage = StringConstants.getFormattedString(StringKeys.INVALID_NUMBER_OF_FIGHTS_KEY, numberOfFightsString);
				output.outputString(errorMessage);
			}
		}
		
		return numberOfFights;
	}
	
}
