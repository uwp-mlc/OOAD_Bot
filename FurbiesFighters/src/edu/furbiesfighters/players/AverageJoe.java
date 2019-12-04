package edu.furbiesfighters.players;

import java.util.ArrayList;

import neat.helpers.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Random;

import edu.furbiesfighters.events.AttackEvent;
import edu.furbiesfighters.events.FightStartEvent;
import edu.furbiesfighters.events.PlayerEventInfo;
import edu.furbiesfighters.events.RoundStartEvent;
import edu.furbiesfighters.gameplay.GameSettings;
import edu.furbiesfighters.skills.Skills;
import edu.furbiesfighters.utility.Utility;
import jneat.NNode;
import jneat.Network;

/**
 * @author Machine Learning Club
 *
 */
public class AverageJoe extends JarvisPlayer {
	private GameSettings gs;
	private final Skills[] outputOrder = {Skills.PAPER_CUT, Skills.REVERSAL_OF_FORTUNE, Skills.ROCK_THROW,
            Skills.SCISSOR_POKE, Skills.SHOOT_THE_MOON,Skills.PAPER_CUT, Skills.REVERSAL_OF_FORTUNE, 
            Skills.ROCK_THROW, Skills.SCISSOR_POKE};
	
	public AverageJoe(double initialHP, String name, String petName, PetTypes petType) {
		super(initialHP, name, petName, PetTypes.POWER);
	}
	
	public AverageJoe(double initialHP, String name, String petName, PetTypes petType, GameSettings gs) {
		super(initialHP, name, petName, PetTypes.POWER);
		this.gs = gs;
	}
	
	@Override
	public Skills chooseSkill() {
		Utility.printMessage("Average Joe is choosing their skill ");
		List<Double> input = new ArrayList<Double>();
		
		input.addAll(Helpers.generateOneHot(super.opponentType, PetTypes.values().length));
		input.addAll(Helpers.generateOneHot(super.getPlayerType(), PetTypes.values().length));
		input.addAll(Helpers.boundedRechargeTime(super.jarvisRechargingSkills));
		input.addAll(Helpers.boundedRechargeTime(super.rechargingOpponentSkills));
		input.addAll(Helpers.generateOneHot(super.lastAttackSkill, Skills.values().length));
		input.addAll(Helpers.generateOneHot(super.lastOpponentAttackSkill, Skills.values().length));
		input.add((double)super.getCurrentHp() / (double)super.getPlayerFullHP());
		input.add((double)super.opponentHealth / (double)super.getPlayerFullHP());
		
		//System.out.println("Input array: " + input.size());
		
		this.getOuput(input);
		
		return super.learnSkill();
	}
	
	private List<Double> getOuput(List<Double> input) {
		Network brain = this.gs.organism.getNet();
		
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
		return outputs;
	}
	
	//private
}