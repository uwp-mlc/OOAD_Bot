package edu.furbiesfighters.gameplay;

import java.util.List;

import jneat.Organism;

public class GameSettings {
	public Organism organism;
	int neat_count;
	int opponent_count;
	int human_count;
	
	List<String> neat_names;
	List<String> opponent_names;
	List<String> human_names;
	
	int fight_amount;
	
	List<Integer> neat_types;
	List<Integer> opponent_types;
	List<Integer> human_types;
	
	public GameSettings(int neat_count, int opponent_count, int human_count, List<String> neat_names, 
			List<String> opponent_names, List<String> human_names, int fight_amount, List<Integer> neat_types,
			List<Integer> opponent_types, List<Integer> human_types, Organism organism) {
		this.neat_count = neat_count;
		this.opponent_count = opponent_count;
		this.human_count = human_count;
		this.neat_names = neat_names;
		this.opponent_names = opponent_names;
		this.human_names = human_names;
		this.fight_amount = fight_amount;
		this.human_types = human_types;
		this.opponent_types = opponent_types;
		this.neat_types = neat_types;
		this.organism = organism;
	}	
	
}
