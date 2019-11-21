package edu.furbiesfighters.gameplay;

import java.util.ArrayList;
import java.util.List;
import edu.furbiesfighters.gameplay.*;

public class SimulatorMain {
	public static void main(String[] args) {
		List<String> opponent_names = new ArrayList<String>();
		opponent_names.add("Nick");
		List<String> neat_names = new ArrayList<String>();
		neat_names.add("Garrett");
		List<String> human_names = new ArrayList<String>();
		
		List<Integer> neat_types = new ArrayList<Integer>();
		neat_types.add(1);
		List<Integer> opponent_types = new ArrayList<Integer>();
		opponent_types.add(2);
		List<Integer> human_types = new ArrayList<Integer>();
		
		int fight_amount = 1000;
		
		Game game = new Game(1,1,0,neat_names,opponent_names,human_names,fight_amount,neat_types,opponent_types,human_types);
		game.play();
	}
}
