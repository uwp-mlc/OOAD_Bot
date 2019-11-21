package edu.furbiesfighters.players;
import java.util.List;

import edu.furbiesfighters.skills.Skills;
import java.util.Observer;
/**
 * 
 */

/**
 * @author Furbies Fighters
 *
 */
public interface Playable extends Observer
{

	/**
	 * Calculate damage based off skill chosen and return that number. Choosing
	 * is done differently for AI and human, hence abstract method.
	 */
	public Skills chooseSkill();
	
	/**
	 * Adjust damage for the playable type.
	 * @param amount
	 */
	public void updateHp(double amount);
	
	/**
	 * Accessor for isSleeping boolean.
	 */
	public boolean isAwake();

	/**
	 * @return the petType
	 */
	public PetTypes getPetType();

	/**
	 * @return the hp
	 */
	public double getCurrentHp();
	/**
	 * @return the name
	 */
	public String getPlayerName();
	
	/**
	 * Sets HP to 100
	 */
	public void resetHp();
	
	/**
	 * Gets the player's pet name
	 * @return petName
	 */
	public String getPetName();
	
	/**
	 * Returns the predicted skill for Shoot the Moon.
	 * @return
	 */
	public Skills getSkillPrediction();
	
	/**
	 * Returns the predicted skill for Shoot the Moon.
	 * @return
	 */
	public void setSkillPrediction(Skills skill);
	
	/**
	 * Returns the full HP.
	 * @return
	 */
	public double getPlayerFullHP();
	
	/**
	 * Gets the re-charge time on a skill 
	 * @param currentSkill
	 * @return
	 */
	public int getSkillRechargeTime(Skills currentSkill);
	
	/**
	 * Percentage of the current HP to the full HP
	 * @return
	 */
	public double calculateHpPercent();
	
	/**
	 * Sets the re-charge time for a specific skill to a 
	 * specific re-charge time 
	 * @param skill
	 * @param time
	 */
	public void setRechargeTime(Skills skill, int time);
	
	/**
	 * Decrement the re-charge times of all skills in the list
	 */
	public void decrementRechargeTimes();
	
	/**
	 * Reset the status of a playable
	 */
	public void reset();
	
	/**
	 * Get the most recent chosen skill
	 * @return
	 */
	public Skills getChosenSkill();
	
	/**
	 * Get the player type.
	 * @return
	 */
	public PlayerTypes getPlayerType();
}
