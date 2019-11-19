package edu.furbiesfighters.players;
/**
 * 
 */

import java.util.Observable;

import edu.furbiesfighters.skills.Skills;
import edu.furbiesfighters.utility.Utility;

/**
 * Class for maintaining the state of a human.
 * @author Furbies Fighters
 *
 */
public class Human extends Player
{
	
	/**
	 * Contructor for creating a human.
	 */
	public Human(double initialHP, String name, String petName, PetTypes petType) 
	{
		// TODO Auto-generated constructor stub
		//To be generated in a future version...
		super(initialHP, name, petName, petType, PlayerTypes.HUMAN);
	}

	/**
	 * Method for choosing the Human player's skill. It will check to see if the
	 * skill is valid.
	 */
	@Override
	public Skills chooseSkill() 
	{
		boolean validSkill = false;
		int recharge = 0;
		
		String rechargeString = "";
		Utility.printEndline();
		for(Skills skill : this.rechargingSkills.keySet())
		{
			int time = this.rechargingSkills.get(skill);
			if(time != 0)
				rechargeString += this.name + " has " + skill.toString() + " is recharging for " + time + " plays.\n";
		}
		
		if(rechargeString.length() == 0)
			Utility.printMessage(this.name + " has no skills recharging.\n");
		else
			Utility.printMessage(rechargeString);
		
		//Loop until a valid skill is chosen.
		while(!validSkill)
		{
			String skill = Utility.prompt("Please enter 1 for ROCK THROW, 2 for SCISSOR POKE, "
					+ "3 for PAPER CUT, 4 for SHOOT THE MOON, or 5 for REVERSAL OF FORTUNE: ");
			
			switch(skill)
			{
			case "1" :
				recharge = this.getSkillRechargeTime(Skills.ROCK_THROW);
				if(recharge == 0){
					validSkill = true;
					this.chosenSkill = Skills.ROCK_THROW;
					return Skills.ROCK_THROW;
				}
				else{
					Utility.printMessage("Rock Throw is recharging!");
					break;
				}
			case "2" :
				recharge = this.getSkillRechargeTime(Skills.SCISSOR_POKE);
				if(recharge == 0){
					validSkill = true;
					this.chosenSkill = Skills.SCISSOR_POKE;
					return Skills.SCISSOR_POKE;
				}
				else{
					Utility.printMessage("Scissor Poke is recharging!");
					break;
				}
			case "3" :
				recharge = this.getSkillRechargeTime(Skills.PAPER_CUT);
				if(recharge == 0){
					validSkill = true;
					this.chosenSkill = Skills.PAPER_CUT;
					return Skills.PAPER_CUT;
				}
				else {
					Utility.printMessage("Paper Cut is recharging!");
					break;
				}
			case "4" :
				recharge = this.getSkillRechargeTime(Skills.SHOOT_THE_MOON);
				if(recharge == 0){
					validSkill = true;
					this.chosenSkill = Skills.SHOOT_THE_MOON;
					return shootTheMoon();
				}
				else {
					Utility.printMessage("Shoot the Moon is recharging!");
					break;
				}
			case "5" :
				recharge = this.getSkillRechargeTime(Skills.REVERSAL_OF_FORTUNE);
				if(recharge == 0){
					validSkill = true;
					this.chosenSkill = Skills.REVERSAL_OF_FORTUNE;
					return Skills.REVERSAL_OF_FORTUNE;
				}
				else {
					Utility.printMessage("Reversal Of Fortune is recharging!");
					break;
				}
			default :
				Utility.printMessage("Invalid entry, please re-enter.");
				break;
			}
		}
		return null;
	}
	
	/**
	 * Sets the predicted skill.
	 * @param skill
	 */
	public void setPredictedSkill(Skills skill)
	{
		this.predictedSkill = skill;
	}
	
	/**
	 * Actions for the shoot the moon skill
	 * @return the shoot the moon skill
	 */
	private Skills shootTheMoon() 
	{
		String choice = Utility.prompt("Enter a skill prediction using  1, 2, 3, 4, or 5:  ");
		if(choice.equals("1"))
			predictedSkill = Skills.ROCK_THROW;
		else if(choice.equals("2"))
			predictedSkill = Skills.SCISSOR_POKE;
		else if(choice.equals("3"))
			predictedSkill = Skills.PAPER_CUT;
		else if(choice.equals("4"))
			predictedSkill = Skills.SHOOT_THE_MOON;
		else if(choice.equals("5"))
			predictedSkill = Skills.REVERSAL_OF_FORTUNE;
		
		return Skills.SHOOT_THE_MOON;
	}
	
	
	@Override
	public void update(Observable arg0, Object arg1) 
	{
		/* NO-OP */
	}
	
	
}
