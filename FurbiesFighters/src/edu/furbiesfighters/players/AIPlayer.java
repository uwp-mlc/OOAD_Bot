package edu.furbiesfighters.players;
import java.util.Observable;
import java.util.Random;

import edu.furbiesfighters.skills.Skills;
import edu.furbiesfighters.utility.Utility;
import edu.furbiesfighters.events.AttackEvent;
import edu.furbiesfighters.events.BaseEvent;
import edu.furbiesfighters.events.FightStartEvent;
import edu.furbiesfighters.events.RoundStartEvent;

public class AIPlayer extends Player
{
	private Random random;
	private  final int  RANDOM_RANGE = 5;
	private static int numberOfAIPlayers = 0;
	
	/**
	 * Constructor for the AIPlayer.
	 * @param initialHP - The HP for the AI.
	 * @param name - The name.
	 * @param petName - The Pet's name.
	 * @param petType - The pet's type.
	 */
	public AIPlayer(double initialHP, String name, String petName, PetTypes petType)
	{
		super(initialHP, AIPlayer.generateName(),  petName,  petType, PlayerTypes.AI);
		random = new Random(4376002l);
	}
	
	/**
	 * Method for choosing the AI player's skill. It will check to see if the
	 * skill is valid.
	 */
	@Override
	public Skills chooseSkill() 
	{
		boolean validSkill = false;
		int rechargeTime = 0;
		
		String rechargeString = "";
		Utility.printEndline();
		for(Skills skill : super.rechargingSkills.keySet())
		{
			int time = this.rechargingSkills.get(skill);
			if(time != 0)
				rechargeString += super.name + " has " + skill.toString() + " is recharging for " + time + " plays.\n\n";
		}
		if(Utility.isGUI)
			rechargeString = "";
		if(rechargeString.length() == 0)
			Utility.printMessage(this.name + " has no skills recharging and has chosen a skill.\n");
		else
			Utility.printMessage(rechargeString + super.name + " has chosen a skill.");
		
		while(!validSkill)
		{
			int rngSkill = randomSkillRNG();
			switch(rngSkill)
			{
			case 1 :
				rechargeTime = this.getSkillRechargeTime(Skills.ROCK_THROW);
				if(rechargeTime == 0){
					validSkill = true;
					this.chosenSkill = Skills.ROCK_THROW;
					return Skills.ROCK_THROW;
				}
				break;
			case 2 :
				rechargeTime = this.getSkillRechargeTime(Skills.SCISSOR_POKE);
				if(rechargeTime == 0){
					validSkill = true;
					this.chosenSkill = Skills.SCISSOR_POKE;
					return Skills.SCISSOR_POKE;
				}
				break;
			case 3 :
				rechargeTime = this.getSkillRechargeTime(Skills.PAPER_CUT);
				if(rechargeTime == 0){
					validSkill = true;
					this.chosenSkill = Skills.PAPER_CUT;
					return Skills.PAPER_CUT;
				}
				break;
			case 4 :
				rechargeTime = this.getSkillRechargeTime(Skills.SHOOT_THE_MOON);
				if(rechargeTime == 0){
					validSkill = true;
					this.chosenSkill = Skills.SHOOT_THE_MOON;
					return this.shootTheMoon();
				}
				break;
			case 5 :
				rechargeTime = this.getSkillRechargeTime(Skills.REVERSAL_OF_FORTUNE);
				if(rechargeTime == 0){
					validSkill = true;
					this.chosenSkill = Skills.REVERSAL_OF_FORTUNE;
					return Skills.REVERSAL_OF_FORTUNE;
				}
				break;
			default :
				break;
			}
		}
		return null;
	}
	

	/**
	 * return the random integer generated
	 */
	private int randomSkillRNG()
	{
		return (int)Math.ceil(random.nextFloat() * RANDOM_RANGE);
	}
	
	
	/**
	 * Actions for the shoot the moon skill for an AI player
	 * @return the shoot the moon skill
	 */
	private Skills shootTheMoon() 
	{
		int predictedSkillNum = randomSkillRNG();
		if(predictedSkillNum == 1)
			predictedSkill = Skills.ROCK_THROW;
		if(predictedSkillNum == 2)
			predictedSkill = Skills.SCISSOR_POKE;
		if(predictedSkillNum == 3)
			predictedSkill = Skills.PAPER_CUT;
		if(predictedSkillNum == 4)
			predictedSkill = Skills.SHOOT_THE_MOON;
		if(predictedSkillNum == 5)
			predictedSkill = Skills.REVERSAL_OF_FORTUNE;
		
		return Skills.SHOOT_THE_MOON;
	}
	
	/**
	 * Generate an automatic AI Name
	 * @return
	 */
	public static String generateName()
	{
		return "AI Player " + ++AIPlayer.numberOfAIPlayers;
	}
	
	/**
	 * Method for resetting the AI count.
	 */
	public static void resetAICount()
	{
		AIPlayer.numberOfAIPlayers = 0;
	}
	
	/**
	 * The method for updating the observable.
	 */
	@Override
	public void update(Observable arg0, Object event) 
	{
		if(event instanceof FightStartEvent)
		{
			FightStartEvent fse = (FightStartEvent) event;
			//assign fight start variables
		}
		if(event instanceof RoundStartEvent)
		{
			RoundStartEvent rse = (RoundStartEvent) event;
			//assign round start variables
		}         
		if(event instanceof AttackEvent)
		{
			AttackEvent  ae = (AttackEvent) event;
			//System.out.println(this.name + " RECEIVED attack event with " + (ae.getRandomDamage()));
			//assign attack variables
		}
	}
}
	
	