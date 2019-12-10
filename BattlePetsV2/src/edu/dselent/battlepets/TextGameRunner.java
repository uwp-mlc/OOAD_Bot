package edu.dselent.battlepets;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
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
import jneat.Network;

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
	
	private Population createPopulation(String filename) {
		return new Population(filename);
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
		
		PlayerInfo playerInfo2 = playerInfoBuilder.withPlayerType(PlayerTypes.AVERAGE_JOE)
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
	// Adds new opponent to hashmap
	public HashMap<Species, List<Organism>> addOpponent(
			HashMap<Species, List<Organism>> opponents, 
			Species species, 
			Organism new_opp,
			int max_generations){
		// species exists in hash map
		if(opponents.containsKey(species)) {
			// cached species is less than cap append organism
			if(opponents.get(species).size() <= max_generations) {
				List<Organism> organisms = opponents.get(species);
				organisms.add(new_opp);
				opponents.put(species, organisms);
			// cached species is greater than cap
			// remove oldest organism and append newest
			} else {
				List<Organism> organisms = opponents.get(species);
				organisms.remove(0);
				organisms.add(new_opp);
				opponents.put(species, organisms);
			}
		//species does not exist so add new species with opponent
		} else {
			List<Organism> organisms = new ArrayList<Organism>();
			organisms.add(new_opp);
			opponents.put(species, organisms);
		}
		return opponents;
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

		int trainingGenerationLen = 5;
		
		
		
		Population neatPop = this.createPopulation("C:\\Users\\nickl\\Documents\\OOAD_Bot\\BattlePetsV2\\SavedPopulationCKPT_1.txt");
		HashMap<Species, List<Organism>> opponents = new HashMap<Species, List<Organism>>();
		for(Object o : neatPop.getSpecies()) {
			Species s = (Species)o;
			s.compute_average_fitness();
			s.compute_max_fitness();
			Organism best_org = s.getBest_Organism();
			
			if(best_org != null)
				opponents = addOpponent(opponents, s, s.getBest_Organism(), trainingGenerationLen);
		}
		int generation = 0;
		int maxGenerations = 1;
		
		System.out.println(opponents.toString());
		
		neatPop = this.createPopulation();
		
		List<Double> fitnesses = new ArrayList<Double>();
		
		while(generation < maxGenerations) {
			System.out.println("Starting Generation: " + generation);
			Iterator iterOrgs = neatPop.getOrganisms().iterator();
			neatPop.setHighest_fitness(-100.00);
			while(iterOrgs.hasNext()) {
				Organism _org = (Organism) iterOrgs.next();
				int numSimulations = 0;
				double fitness = 0;
				for(List<Organism> orgs : opponents.values()) {
					for(Organism o : orgs) {
						numSimulations++;
						GameSettings gameSettings = new GameSettings(r.nextInt(10000), numPlayers, fightCount);
						List<Organism> orgList = new ArrayList<Organism>();
						orgList.add(_org);
						orgList.add(o);
						List<PlayerInfo> playerInfoList = this.createPlayerInfoList();
						List<Playable> playableList = PlayableInstantiator.instantiatePlayables(playerInfoList, orgList);
						TextBattleControl battleControl = new TextBattleControl(gameSettings, playableList);
						battleControl.runBattle();
						
						double aiHp = playableList.get(0).getCurrentHp();
						double opponentHp = playableList.get(1).getCurrentHp();
						if(opponentHp < 0) {
							opponentHp = 0;
						}
						double healthDiff = aiHp - opponentHp;
						fitness += healthDiff;
					}
				}
				fitness = fitness / numSimulations;
				System.out.println("Fitness is: " + fitness);
				_org.setFitness(fitness);
				fitnesses.add(fitness);
				//System.out.println("Org Fitness: " + gs.getFitness());
			}
			Set<Species> speciesSet = new HashSet<>(opponents.keySet());
			for(Species s : speciesSet) {
				if(!neatPop.getSpecies().contains(s)) {
					opponents.remove(s);
				}
			}
			for(Object o : neatPop.getSpecies()) {
				Species s = (Species)o;
				s.compute_average_fitness();
				s.compute_max_fitness();
				Organism best_org = s.getBest_Organism();
				
				if(best_org != null)
					opponents = addOpponent(opponents, s, s.getBest_Organism(), trainingGenerationLen);
			}
			//neatPop.viewtext();
			for(Species s : opponents.keySet()) {
				List<Organism> orgs = opponents.get(s);
			}
			neatPop.epoch(generation++);
			System.out.println("Finished Generation: " + generation);
			neatPop.print_to_file_by_species("SavedPopulation.txt");
			
		}
		
		System.out.println("\n" + fitnesses.toString());
		System.out.println("Species size: " + neatPop.getSpecies().size());
		for(Object o : neatPop.getSpecies()) {
			Species s = (Species)o;
			System.out.println("Best species fitness: " + s.getMax_fitness_ever());
		}
		
		Organism best_org = this.getBestOrganismFromSpecies(neatPop);
		
		this.saveNetwork(best_org.getNet());
	}
	
	private Organism getBestOrganismFromSpecies(Population neatPop) {
		Organism best_org = null;
		for(Object o : neatPop.getSpecies()) {
			Species s = (Species)o;
			s.compute_average_fitness();
			s.compute_max_fitness();
			Organism tmp_best_org = s.getBest_Organism();
			if(best_org == null || tmp_best_org.getFitness() > best_org.getFitness())
				best_org = tmp_best_org;
		}
		
		return best_org;
	}
	
	private void saveNetwork(Network net) {
        try
        {    
            //Saving of object in a file 
            FileOutputStream file = new FileOutputStream("saved_net.ser"); 
            ObjectOutputStream out = new ObjectOutputStream(file); 
              
            // Method for serialization of object 
            out.writeObject(net); 
              
            out.close(); 
            file.close(); 
              
            System.out.println("Network has been serialized"); 
  
        } 
          
        catch(IOException ex) 
        { 
            System.out.println("IOException is caught " + ex.toString()); 
        } 
	}

	
}
