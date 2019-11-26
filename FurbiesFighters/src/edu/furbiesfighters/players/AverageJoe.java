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

/**
 * @author Machine Learning Club
 *
 */
public class AverageJoe extends JarvisPlayer {
	private GameSettings gs;
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
		List<Float> input = new ArrayList<Float>();
		input.addAll(Helpers.generateOneHot(super.opponentType.ordinal(), PetTypes.values().length));
		System.out.println(input);
		return super.learnSkill();
	}
	
	
}