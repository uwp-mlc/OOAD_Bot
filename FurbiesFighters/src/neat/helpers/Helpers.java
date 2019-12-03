package neat.helpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.furbiesfighters.skills.Skills;

public class Helpers {
	public static List<Double> generateOneHot(Enum choice, int size){
		List<Double> oneHot = new ArrayList<Double>();
		try {
			// System.out.println("TEST " + (String[])choice.getClass().getMethod("values", null).invoke(choice));
			
			int selectionChoice = choice.ordinal(); 
			for(int i = 0; i < size; i++) {
				if(selectionChoice == i) 
					oneHot.add(1.0);
				else
					oneHot.add(0.0);
			}
		} catch(Exception e) {
			int i = 0;
			while(i < size) {
				oneHot.add(0.0);
				i++;
			}
		}
		
		return oneHot;
	}
	
	public static List<Double> boundedRechargeTime(Map<Skills, Integer> recharging){
		List<Double> boundedTimes = new ArrayList<Double>();
		Skills[] skillOrder = new Skills[] {Skills.PAPER_CUT, Skills.REVERSAL_OF_FORTUNE, Skills.ROCK_THROW,
		                  Skills.SCISSOR_POKE, Skills.SHOOT_THE_MOON};
		
		for(Skills skill : skillOrder) {
			boundedTimes.add((double)recharging.get(skill) / 5.0);
		}
		
		return boundedTimes;
	}
}
