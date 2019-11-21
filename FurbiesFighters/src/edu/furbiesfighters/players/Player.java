package edu.furbiesfighters.players;
/**
 * Player class implements playable
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import edu.furbiesfighters.skills.Skills;
import edu.furbiesfighters.utility.Utility;

/**
 * @author Furbies Fighters
 */
public abstract class Player implements Playable{
	protected final int ROF_RECHARGE_TIME = 7, STM_RECHARGE_TIME = 7;//6 + 1
	protected final int NORMAL_RECHARGE_TIME = 2;//1 + 1
	private PetTypes petType;
	private double fullHP;
	private double hp;
	protected String name, petName;
	protected Skills predictedSkill;
	private boolean isSleeping;
	protected Map<Skills, Integer> rechargingSkills;
	protected Skills chosenSkill;
	private PlayerTypes playerType;
	
	/**
	 * Initialize key values for a player and setting the seed of the rng;
	 */
	public Player(double initialHP, String name, String petName, PetTypes petType, PlayerTypes playerType)
	{
		this.hp = initialHP;
		this.fullHP = initialHP;
		this.petType = petType;
		this.name = name;
		this.isSleeping = false;
		this.rechargingSkills = new HashMap<Skills, Integer>();
		this.rechargingSkills.put(Skills.PAPER_CUT, 0);
		this.rechargingSkills.put(Skills.SCISSOR_POKE, 0);
		this.rechargingSkills.put(Skills.SHOOT_THE_MOON, 0);
		this.rechargingSkills.put(Skills.REVERSAL_OF_FORTUNE, 0);
		this.rechargingSkills.put(Skills.ROCK_THROW, 0);
		this.petName = petName;
		this.playerType = playerType;
	}
	
	/**
	 * Adjust hp to account for damage.
	 */
	@Override
	public void updateHp(double amount)
	{
		this.hp -= amount;
		if(this.hp <= 0f)
			this.isSleeping = true;
	}
	
	/**
	 * Accessor for isSleeping boolean.
	 */
	@Override
	public boolean isAwake()
	{
		return !this.isSleeping;
	}
	

	/**
	 * Validate the skill and return the recharge time
	 * @param currentSkill
	 * @return -1 if valid, else recharge time. 
	 */
	@Override
	public int getSkillRechargeTime(Skills currentSkill){
		return this.rechargingSkills.get(currentSkill);
	}
	/**
	 * @return the petType
	 */
	@Override
	public PetTypes getPetType() 
	{
		return petType;
	}

	/**
	 * @return the hp
	 */
	@Override
	public double getCurrentHp() 
	{
		return hp;
	}

	/**
	 * @return the name
	 */
	@Override
	public String getPlayerName() 
	{
		return name;
	}
	
	/**
	 * Sets HP to 100
	 */
	@Override
	public void resetHp()
	{
		this.hp = fullHP;
		this.isSleeping = false;
		this.restoreCoolDowns();
	}
	
	/**
	 * Resets all cool-downs
	 */
	public void restoreCoolDowns()
	{
		for(Skills skill : this.rechargingSkills.keySet())
			this.rechargingSkills.replace(skill, 0);
	}
	
	/**
	 * Gets the player's pet name
	 * @return petName
	 */
	@Override
	public String getPetName()
	{
		return petName;
	}

	/**
	 * Returns the predicted skill for Shoot The Moon.
	 */
	@Override
	public Skills getSkillPrediction() {
		return this.predictedSkill;
	}
	
	/**
	 * Returns the predicted skill for Shoot the Moon.
	 * @return
	 */
	@Override
	public void setSkillPrediction(Skills skill)
	{
		this.predictedSkill = skill;
	}
	
	/**
	 * Method for getting the full HP of a player.
	 */
	@Override
	public double getPlayerFullHP()
	{
		return fullHP;
	}

	/**
	 * Method for calculating the hp percent.
	 */
	@Override
	public double calculateHpPercent() {
		return hp / fullHP;
	}

	/**
	 * Sets the recharge time for a skill
	 */
	@Override
	public void setRechargeTime(Skills skill, int time) 
	{
		this.rechargingSkills.replace(skill, time);
	}

	/**
	 * Decrement 1 from all nonzero re-charge times
	 */
	@Override
	public void decrementRechargeTimes() {
		for(Skills skill : this.rechargingSkills.keySet())
		{
			int time = this.rechargingSkills.get(skill);
			if(time !=0)
				this.rechargingSkills.replace(skill, --time);
		}
	}

	/**
	 * Resets the HP to the initial amount and restores 
	 * all cool downs for the player
	 */
	@Override
	public void reset() 
	{
		resetHp();
		restoreCoolDowns();
	}
	
	/**
	 * Return the last chosen skill
	 */
	@Override
	public Skills getChosenSkill()
	{
		return this.chosenSkill;
	}
	
	/**
	 * Method for getting the player type.
	 */
	@Override
	public PlayerTypes getPlayerType()
	{
		return this.playerType;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((petName == null) ? 0 : petName.hashCode());
		result = prime * result + ((petType == null) ? 0 : petType.hashCode());
		result = prime * result + ((playerType == null) ? 0 : playerType.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		Player other;
		if (obj instanceof Human)
			other = (Human) obj;
		else if(obj instanceof AIPlayer)
			other = (AIPlayer) obj;
		else if(!(obj instanceof Player))
			return false;
		else
			other = (Player) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (petName == null) {
			if (other.petName != null)
				return false;
		} else if (!petName.equals(other.petName))
			return false;
		if (petType != other.petType)
			return false;
		if (playerType != other.playerType)
			return false;
		return true;
	}
}
