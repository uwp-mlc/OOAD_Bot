package edu.dselent.battlepets;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Vector;

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
import jneat.Species;
import jneat.Neat;

public class TextGameRunner implements GameRunner
{
	private Population createPopulation() {
		int popSize = 100;
		
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
		
		return new Population(popSize, inputSize, outputSize, maxNodes, recurrent, prob);
		
	}
	
	private List<PlayerInfo> createPlayerInfoList(){
		List<PlayerInfo> playerInfoList = new ArrayList<>();
		PlayerInfoBuilder playerInfoBuilder = new PlayerInfoBuilder();
		
		PlayerInfo playerInfo = playerInfoBuilder.withPlayerType(PlayerTypes.AVERAGE_JOE)
				.withPetType(PetTypes.POWER)
				.withPlayerName("Garrett")
				.withPetName("Garrett Pet")
				.withStartingHp(100.0)
				.withSkillSet(EnumSet.allOf(Skills.class))
				.build();
		
		PlayerInfo playerInfo2 = playerInfoBuilder.withPlayerType(PlayerTypes.JARVIS)
				.withPetType(PetTypes.POWER)
				.withPlayerName("Nick")
				.withPetName("Nick Pet")
				.withStartingHp(100.0)
				.withSkillSet(EnumSet.allOf(Skills.class))
				.build();
		
		playerInfoList.add(playerInfo);
		playerInfoList.add(playerInfo2);
		
		return playerInfoList;
	}
	
	@Override
	public void runGame()
	{
		Neat.initbase();
		Inputtable textInputtable = new TextInputGetter();
		Outputtable textOutputtable = new TextOutputSender();
		IoManager textIoManager = new IoManager(textInputtable, textOutputtable);

		ApplicationConfigurations.INSTANCE.setIoManager(textIoManager);
		
		GameSettingsControl gameSettingsControl = new GameSettingsControl();
		//GameSettings gameSettings = gameSettingsControl.retrieveGameSettings();
	
		//PlayerSettingsControl playerSettingsControl = new PlayerSettingsControl(gameSettings);
		//List<PlayerInfo> playerInfoList = playerSettingsControl.retrievePlayerInfoList();
		
		Random r = new Random();
		
		int numPlayers = 2;
		int fightCount = 1;
		
		
		
		Population neatPop = this.createPopulation();
		Vector neatOrgs = neatPop.getOrganisms();
		int generation = 0;
		int maxGenerations = 100;
		
		List<Double> fitnesses = new ArrayList<Double>();
		
		while(generation < maxGenerations) {
			Iterator iterOrgs = neatOrgs.iterator();
			neatPop.setHighest_fitness(-100.00);
			while(iterOrgs.hasNext()) {
				Organism _org = (Organism) iterOrgs.next();
				
				GameSettings gameSettings = new GameSettings(r.nextInt(10000), numPlayers, fightCount);
				List<PlayerInfo> playerInfoList = this.createPlayerInfoList();
				List<Playable> playableList = PlayableInstantiator.instantiatePlayables(playerInfoList, _org);
				TextBattleControl battleControl = new TextBattleControl(gameSettings, playableList);
				battleControl.runBattle();
				
				double healthDiff = playableList.get(0).getCurrentHp() - playableList.get(1).getCurrentHp();
				_org.setFitness(healthDiff);
				fitnesses.add(healthDiff);
				//System.out.println("Org Fitness: " + gs.getFitness());
			}

			//neatPop.viewtext();
			neatPop.epoch(++generation);
			for(Object o : neatPop.getSpecies()) {
				Species s = (Species)o;
				s.compute_average_fitness();
				s.compute_max_fitness();
			}
			System.out.println("\nHigh Fitness: " + neatPop.getHighest_fitness());
			neatPop.print_to_file_by_species("SavedPopulation.txt");
		}
		
		System.out.println("\n" + fitnesses.toString());
	}
	
}
