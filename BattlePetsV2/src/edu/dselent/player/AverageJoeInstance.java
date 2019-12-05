package edu.dselent.player;

import java.util.ArrayList;

import neat.helpers.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Random;

import edu.dselent.settings.PlayerInfo;
import edu.dselent.skill.Skills;
import jneat.NNode;
import jneat.Network;
import jneat.Organism;

/**
 * @author Machine Learning Club
 *
 */
public class AverageJoeInstance extends JarvisPlayer {
	private final Skills[] outputOrder = {Skills.PAPER_CUT, Skills.REVERSAL_OF_FORTUNE, Skills.ROCK_THROW,
            Skills.SCISSORS_POKE, Skills.SHOOT_THE_MOON,Skills.PAPER_CUT, Skills.REVERSAL_OF_FORTUNE, 
            Skills.ROCK_THROW, Skills.SCISSORS_POKE};
	private Organism org;
	
	public AverageJoeInstance(int playableUid, PlayerInfo playerInfo, Organism org) {
		super(playableUid, playerInfo);
		this.org = org;
	}
	
	@Override
	public Skills chooseSkill() {
		List<Double> input = new ArrayList<Double>();
		
		Map<Skills, Integer> rechargeTimes = new HashMap<Skills, Integer>();
		for(Skills skill : this.outputOrder) {
			rechargeTimes.put(skill, super.getSkillRechargeTime(skill));
		}
		
		input.addAll(Helpers.generateOneHot(super.opponentType, PetTypes.values().length));
		input.addAll(Helpers.generateOneHot(super.getPlayerType(), PetTypes.values().length));
		input.addAll(Helpers.boundedRechargeTime(rechargeTimes));
		input.addAll(Helpers.boundedRechargeTime(super.rechargingOpponentSkills));
		input.addAll(Helpers.generateOneHot(super.lastAttackSkill, Skills.values().length));
		input.addAll(Helpers.generateOneHot(super.lastOpponentAttackSkill, Skills.values().length));
		input.add(super.calculateHpPercent());
		input.add(super.opponentHealth);
		
		//System.out.println("Input array: " + input.size());
		
		return this.getOuputChoice(input);
		
		//return super.learnSkill();
	}
	
	private Skills getOuputChoice(List<Double> input) {
		Network brain = this.org.getNet();
		
		input.add(-1.0); // Add bias to the input
		
		double[] arrInputs = input.stream().mapToDouble(d -> d).toArray();
		
		brain.load_sensors(arrInputs);
		
		int maxNetDepth = brain.max_depth();
		
		for(int relax = 0; relax <= maxNetDepth; relax++) {
			brain.activate();
		}
		
		List<Double> outputs = new ArrayList<Double>();

		for(Object n : brain.getOutputs()) {
			NNode node = (NNode) n;
			outputs.add(node.getActivation());
		}
		
		//System.out.println(outputs);
		Skills chosen = this.getBestSkill(outputs);
		//System.out.println(super.rechargingSkills.keySet() + " : " + super.rechargingSkills.values());
		//System.out.println("AJ Chose: " + chosen.toString());
		return chosen;
	}
	
	private Skills getBestSkill(List<Double> outputs) {
		Map<Skills, Integer> rechargeTimes = new HashMap<Skills, Integer>();
		for(Skills skill : this.outputOrder) {
			rechargeTimes.put(skill, super.getSkillRechargeTime(skill));
		}
		
		int startIndex = 0;
		Skills skill = this.outputOrder[startIndex];
		while(startIndex < this.outputOrder.length && rechargeTimes.get(skill) != 0) {
			startIndex++;
			skill = this.outputOrder[startIndex];
		}
		
		int maxIndex = startIndex;
		
		for(int i = startIndex + 1; i < 4; i++) {
			skill = this.outputOrder[i];
			if(rechargeTimes.get(skill) == 0) {
				if(outputs.get(maxIndex) < outputs.get(i))
					maxIndex = i;
			}
		}
		
		return this.outputOrder[maxIndex];
	}
}