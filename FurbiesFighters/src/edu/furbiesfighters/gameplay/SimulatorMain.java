package edu.furbiesfighters.gameplay;

import java.util.ArrayList;
import jneat.*;
import java.util.List;
import edu.furbiesfighters.gameplay.*;

public class SimulatorMain {
	public static void main(String[] args) {
		List<String> opponent_names = new ArrayList<String>();
		opponent_names.add("Nick");
		opponent_names.add("Greg");
		List<String> neat_names = new ArrayList<String>();
		neat_names.add("Garrett");
		List<String> human_names = new ArrayList<String>();
		
		List<Integer> neat_types = new ArrayList<Integer>();
		neat_types.add(1);
		List<Integer> opponent_types = new ArrayList<Integer>();
		opponent_types.add(0);
		opponent_types.add(1);
		List<Integer> human_types = new ArrayList<Integer>();
		
		int fight_amount = 1;
		
		Population neatPop = new Population(30 /* population size */, 9 /* network inputs */ , 2 /* network outputs */, 5 /* max index of nodes */, true /* recurrent */, 0.5 /* probability of connecting two nodes */ );
		
		
		Game game = new Game(1,2,0,neat_names,opponent_names,human_names,fight_amount,neat_types,opponent_types,human_types);
		game.play();
	}
}
