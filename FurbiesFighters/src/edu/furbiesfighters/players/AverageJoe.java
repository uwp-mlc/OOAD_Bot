package edu.furbiesfighters.players;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Random;

import edu.furbiesfighters.events.AttackEvent;
import edu.furbiesfighters.events.FightStartEvent;
import edu.furbiesfighters.events.PlayerEventInfo;
import edu.furbiesfighters.events.RoundStartEvent;
import edu.furbiesfighters.skills.Skills;
import edu.furbiesfighters.utility.Utility;

/**
 * @author Machine Learning Club
 *
 */
public class AverageJoe extends JarvisPlayer {
	public AverageJoe(double initialHP, String name, String petName, PetTypes petType) {
		super(initialHP, name, petName, PetTypes.POWER);
	}
	
	@Override
	public Skills chooseSkill() {
		Utility.printMessage("Average Joe is choosing their skill " + super.opponentType);
		return super.learnSkill();
	}
	
}