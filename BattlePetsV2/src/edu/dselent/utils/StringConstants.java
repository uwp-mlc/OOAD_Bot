package edu.dselent.utils;

import java.util.HashMap;
import java.util.Map;

import edu.dselent.player.PetTypes;
import edu.dselent.player.PlayerTypes;

public class StringConstants
{
	public enum StringKeys
	{
		ENTER_RANDOM_SEED_KEY,
		INVALID_RANDOM_SEED_KEY,
		ENTER_NUMBER_OF_PLAYERS_KEY,
		INVALID_NUMBER_OF_PLAYERS_KEY,
		ENTER_NUMBER_OF_FIGHTS_KEY,
		INVALID_NUMBER_OF_FIGHTS_KEY,
		ENTER_PLAYER_TYPE_KEY,
		INVALID_PLAYER_TYPE_KEY,
		ENTER_PLAYER_TYPE_CHOICES_KEY,
		ENTER_PET_TYPE_KEY,
		INVALID_PET_TYPE_KEY,
		ENTER_PET_TYPE_CHOICES_KEY,
		ENTER_PLAYER_NAME_KEY,
		ENTER_PET_NAME_KEY,
		ENTER_STARTING_HP_KEY,
		INVALID_STARTING_HP_KEY;
	}
	
	private static String ENTER_RANDOM_SEED_STRING = "Enter a random seed";
	private static String INVALID_RANDOM_SEED_STRING = "Invalid random seed: %s";
	private static String ENTER_NUMBER_OF_PLAYERS_STRING = "Enter a number of players";
	private static String INVALID_NUMBER_OF_PLAYERS_STRING = "Invalid number of players: %s";
	private static String ENTER_NUMBER_OF_FIGHTS_STRING = "Enter a number of fights";
	private static String INVALID_NUMBER_OF_FIGHTS_STRING = "Invalid number of fights: %s";
	private static String ENTER_PLAYER_TYPE_STRING = "Enter the player type for player %d";
	private static String INVALID_PLAYER_TYPE_STRING = "Invalid player type: %s";
	private static String PLAYER_TYPE_CHOICES_STRING;
	private static String ENTER_PET_TYPE_STRING = "Enter the pet type for player %d";
	private static String INVALID_PET_TYPE_STRING = "Invalid pet type: %s";
	private static String PET_TYPE_CHOICES_STRING;
	private static String ENTER_PLAYER_NAME_STRING = "Enter a name for player %s";
	private static String ENTER_PET_NAME_STRING = "%s: Enter a name for your pet";
	private static String ENTER_STARTING_HP_STRING = "Enter a starting hp for %s";
	private static String INVALID_STARTING_HP_STRING = "Invalid starting hp: %s";
	
	private static final Map<StringKeys, String> stringKeyMap;
	
	static
	{
		PlayerTypes[] playerTypes = PlayerTypes.values();
		StringBuilder sbPlayerType = new StringBuilder();
		
		for(int i=0; i<playerTypes.length; i++)
		{
			sbPlayerType.append((i+1) + ": " + playerTypes[i] + "\n");
		}
		
		PLAYER_TYPE_CHOICES_STRING = sbPlayerType.toString();
		
		//
		
		PetTypes[] petTypes = PetTypes.values();
		StringBuilder sbPetType = new StringBuilder();
		
		for(int i=0; i<petTypes.length; i++)
		{
			sbPetType.append((i+1) + ": " + petTypes[i] + "\n");
		}
		
		PET_TYPE_CHOICES_STRING = sbPetType.toString();
		
		//////////////////////////////////////////////////////////////
		
		stringKeyMap = new HashMap<>();
		
		stringKeyMap.put(StringKeys.ENTER_RANDOM_SEED_KEY, ENTER_RANDOM_SEED_STRING);
		stringKeyMap.put(StringKeys.INVALID_RANDOM_SEED_KEY, INVALID_RANDOM_SEED_STRING);
		stringKeyMap.put(StringKeys.ENTER_NUMBER_OF_PLAYERS_KEY, ENTER_NUMBER_OF_PLAYERS_STRING);
		stringKeyMap.put(StringKeys.INVALID_NUMBER_OF_PLAYERS_KEY, INVALID_NUMBER_OF_PLAYERS_STRING);
		stringKeyMap.put(StringKeys.ENTER_NUMBER_OF_FIGHTS_KEY, ENTER_NUMBER_OF_FIGHTS_STRING);
		stringKeyMap.put(StringKeys.INVALID_NUMBER_OF_FIGHTS_KEY, INVALID_NUMBER_OF_FIGHTS_STRING);
		stringKeyMap.put(StringKeys.ENTER_PLAYER_TYPE_KEY, ENTER_PLAYER_TYPE_STRING);
		stringKeyMap.put(StringKeys.INVALID_PLAYER_TYPE_KEY, INVALID_PLAYER_TYPE_STRING);
		stringKeyMap.put(StringKeys.ENTER_PLAYER_TYPE_CHOICES_KEY, PLAYER_TYPE_CHOICES_STRING);
		stringKeyMap.put(StringKeys.ENTER_PET_TYPE_KEY, ENTER_PET_TYPE_STRING);
		stringKeyMap.put(StringKeys.INVALID_PET_TYPE_KEY, INVALID_PET_TYPE_STRING);
		stringKeyMap.put(StringKeys.ENTER_PET_TYPE_CHOICES_KEY, PET_TYPE_CHOICES_STRING);
		stringKeyMap.put(StringKeys.ENTER_PLAYER_NAME_KEY, ENTER_PLAYER_NAME_STRING);
		stringKeyMap.put(StringKeys.ENTER_PET_NAME_KEY, ENTER_PET_NAME_STRING);
		stringKeyMap.put(StringKeys.ENTER_STARTING_HP_KEY, ENTER_STARTING_HP_STRING);
		stringKeyMap.put(StringKeys.INVALID_STARTING_HP_KEY, INVALID_STARTING_HP_STRING);
		
	}
	
	public static String getFormattedString(StringKeys stringKey, Object... args)
	{
		String unformattedString = stringKeyMap.get(stringKey);
		return String.format(unformattedString, args);
	}
}
