package edu.dselent.battlepets;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Random;

import edu.dselent.config.ApplicationConfigurations;
import edu.dselent.control.TextBattleControl;
import edu.dselent.control.GameSettingsControl;
import edu.dselent.control.PlayerSettingsControl;
import edu.dselent.io.TextInputGetter;
import edu.dselent.io.TextOutputSender;
import edu.dselent.player.PetTypes;
import edu.dselent.player.Playable;
import edu.dselent.player.PlayableInstantiator;
import edu.dselent.player.PlayerTypes;
import edu.dselent.io.Inputtable;
import edu.dselent.io.IoManager;
import edu.dselent.io.Outputtable;
import edu.dselent.settings.GameSettings;
import edu.dselent.settings.PlayerInfo;
import edu.dselent.settings.PlayerInfo.PlayerInfoBuilder;
import edu.dselent.skill.Skills;
import jneat.Organism;
import jneat.Population;

public class TextGameRunner implements GameRunner
{
	@Override
	public void runGame()
	{
		Inputtable textInputtable = new TextInputGetter();
		Outputtable textOutputtable = new TextOutputSender();
		IoManager textIoManager = new IoManager(textInputtable, textOutputtable);

		ApplicationConfigurations.INSTANCE.setIoManager(textIoManager);
		
		GameSettingsControl gameSettingsControl = new GameSettingsControl();
		//GameSettings gameSettings = gameSettingsControl.retrieveGameSettings();
	
		//PlayerSettingsControl playerSettingsControl = new PlayerSettingsControl(gameSettings);
		//List<PlayerInfo> playerInfoList = playerSettingsControl.retrievePlayerInfoList();
		
		// MLC ADDITION
		Random r = new Random();
		GameSettings gameSettings = new GameSettings(r.nextInt(10000), 2, 10);
		
		List<PlayerInfo> playerInfoList = new ArrayList<>();
		PlayerInfoBuilder playerInfoBuilder = new PlayerInfoBuilder();
		
		PlayerInfo playerInfo = playerInfoBuilder.withPlayerType(PlayerTypes.JARVIS)
				.withPetType(PetTypes.POWER)
				.withPlayerName("Nick")
				.withPetName("Nick Pet")
				.withStartingHp(100.0)
				.withSkillSet(EnumSet.allOf(Skills.class))
				.build();
		
		PlayerInfo playerInfo2 = playerInfoBuilder.withPlayerType(PlayerTypes.AVERAGE_JOE)
				.withPetType(PetTypes.POWER)
				.withPlayerName("Garrett")
				.withPetName("Garrett Pet")
				.withStartingHp(100.0)
				.withSkillSet(EnumSet.allOf(Skills.class))
				.build();
		
		playerInfoList.add(playerInfo);
		playerInfoList.add(playerInfo2);
		
		int popSize = 5;
		
		// 3 opponent type
		// 3 its type
		// 5 cooldowns
		// 5 opp cooldown
		// 5 last attack
		// 5 opp last attack
		// 1 health
		// 1 opp health
		int inputSize = 28;
		
		// All skills plus 4 for shoot the moon
		int outputSize = 9; 
		
		int maxNodes = 5;
		boolean recurrent = true;
		float prob = (float) 0.5;
		
		Population neatPop = new Population(popSize, inputSize, outputSize, maxNodes, recurrent, prob);
		
		
		// END MLC


		// TODO add tournament mode
		List<Playable> playableList = PlayableInstantiator.instantiatePlayables(playerInfoList, (Organism)neatPop.getOrganisms().get(0));
		TextBattleControl battleControl = new TextBattleControl(gameSettings, playableList);
		battleControl.runBattle();
	}
	
}
