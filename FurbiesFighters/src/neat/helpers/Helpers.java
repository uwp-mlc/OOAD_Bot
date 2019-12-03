package neat.helpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.furbiesfighters.skills.Skills;

public class Helpers {
	public static List<Float> generateOneHot(Enum choice, int size){
		List<Float> oneHot = new ArrayList<Float>();
		try {
			// System.out.println("TEST " + (String[])choice.getClass().getMethod("values", null).invoke(choice));
			
			int selectionChoice = choice.ordinal(); 
			for(int i = 0; i < size; i++) {
				if(selectionChoice == i) 
					oneHot.add(1.0f);
				else
					oneHot.add(0.0f);
			}
		} catch(Exception e) {
			int i = 0;
			while(i < size) {
				oneHot.add(0.0f);
				i++;
			}
		}
		
		return oneHot;
	}
	
	public static List<Float> boundedRechargeTime(Map<Skills, Integer> recharging){
		List<Float> boundedTimes = new ArrayList<Float>();
		Skills[] skillOrder = new Skills[] {Skills.PAPER_CUT, Skills.REVERSAL_OF_FORTUNE, Skills.ROCK_THROW,
		                  Skills.SCISSOR_POKE, Skills.SHOOT_THE_MOON};
		
		for(Skills skill : skillOrder) {
			boundedTimes.add((float)recharging.get(skill) / 5);
		}
		
		return boundedTimes;
	}
}
