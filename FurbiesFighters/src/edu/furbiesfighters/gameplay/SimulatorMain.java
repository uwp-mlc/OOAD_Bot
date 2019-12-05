package edu.furbiesfighters.gameplay;

import java.util.ArrayList;
import java.util.Iterator;

import jneat.*;
import java.util.List;
import java.util.Vector;

import edu.furbiesfighters.gameplay.*;

public class SimulatorMain {
	public static void main(String[] args) {
		String ai_name = "Garrett";
		
		List<String> opponent_names = new ArrayList<String>();
		opponent_names.add("Nick");
		List<String> neat_names = new ArrayList<String>();
		neat_names.add("Garrett");
		List<String> human_names = new ArrayList<String>();
		
		List<Integer> neat_types = new ArrayList<Integer>();
		neat_types.add(1);
		List<Integer> opponent_types = new ArrayList<Integer>();
		opponent_types.add(0);
		List<Integer> human_types = new ArrayList<Integer>();
		
		int fight_amount = 10;
		int oppenentCount = opponent_names.size();
		int neatCount = neat_names.size();
		int humanCount = human_names.size();
		
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
		
		Vector neatOrgs = neatPop.getOrganisms();
		int generation = 0;
		int maxGenerations = 100;
		
		while(generation < maxGenerations) {
			Iterator iterOrgs = neatOrgs.iterator();
			neatPop.setHighest_fitness(-100.00);
			while(iterOrgs.hasNext()) {
				Organism _org = (Organism) iterOrgs.next();
				GameSettings gs = new GameSettings(neatCount,
						oppenentCount,humanCount,
						neat_names,opponent_names,human_names,
						fight_amount,neat_types,opponent_types,human_types,_org, ai_name);
				
				Game game = new Game(gs);
				
				_org.setFitness(gs.getFitness());
				//System.out.println("Org Fitness: " + gs.getFitness());
			}

			neatPop.viewtext();
			neatPop.epoch(++generation);
			
			System.out.println("\nHigh Fitness: " + neatPop.getHighest_fitness());
		}
		
		
	}
}
