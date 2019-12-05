package edu.furbiesfighters.damage;
import java.util.Random;

import edu.furbiesfighters.players.Playable;
import edu.furbiesfighters.players.PetTypes;
import edu.furbiesfighters.skills.Skills;
import edu.furbiesfighters.gameplay.*;

/**
 * 
 */

/**
 * @author Furbies Fighters
 * Class: Calculate Damage
 * Def: takes in parameters, PlayerType, PlayerSkill,OpponentSkill and Opponent HP; Uses params 
 * 		to calculate damage done, conditional and random. Returns a pair.
 */
public class CalculateDamage implements Calculatable 
{
	private Random rng = new Random();
	private Referee ref;
	private PetTypes playerType; 
	private Skills opponentSkill, playerSkill, predictedSkill;
	private double opponentHP, opponentTotalRNG, playerTotalRNG;
	private final int RANDOM_RANGE = 5;
	private final double DAMAGE_MULTIPLIER = 5.0f;
	private final double DAMAGE_ADD_THREE = 3f;
	private final double DAMAGE_ADD_TWO = 2f;
	private final double DAMAGE_ADD_TEN = 10f;
	private final double DAMAGE_ADD_TWENTY = 20f;
	private double randomDamage;
	private Playable player;
	private Playable opponent;

	//Constructor for class
	public CalculateDamage(Referee ref)
	{
		this.rng = new Random();//387563l
		this.ref = ref;
	}
	
	/**
	 * Return double value of the damage
	 * @return the damage calculated
	 */
	@Override
	public Pair calculateDamage(int attackingPetIndex, int victimPetIndex) {
		this.player = ref.getPlayable(attackingPetIndex);
		this.playerType = this.player.getPetType();
		this.playerSkill = this.ref.getChosenSkillByIndex(attackingPetIndex);
		this.opponent = ref.getPlayable(victimPetIndex);
		this.opponentSkill = this.ref.getChosenSkillByIndex(victimPetIndex);
		Pair damage;
		if(this.playerSkill == Skills.PAPER_CUT)
			damage = this.paper();
		else if(this.playerSkill == Skills.SCISSOR_POKE)
			damage = this.scissor();
		else if(this.playerSkill == Skills.ROCK_THROW)
			damage = this.rockThrow();
		else if(this.playerSkill == Skills.SHOOT_THE_MOON)
			damage = this.shootTheMoon();
		else if(this.playerSkill == Skills.REVERSAL_OF_FORTUNE)
			damage = this.reversalOfFortune(attackingPetIndex);
		else 
			damage = new Pair(0, 0);
		return damage;
	}
	
	/**
	 * Use skill for all scissor poke skills. It will return the
	 * damage to be dealt to the other players. 
	 */
	public Pair scissor() {
		opponentHP = this.opponent.getCurrentHp();
		double random = rng.nextFloat() * RANDOM_RANGE;
		this.randomDamage = random;
		double conditional = 0;
		if(playerType.equals(PetTypes.POWER))
		{
			if(opponentSkill.equals(Skills.PAPER_CUT))
				conditional = random * DAMAGE_MULTIPLIER;
		}
		if(playerType.equals(PetTypes.INTELLIGENCE))
		{
			//if shoot the moon is re-charging... future release
			if(opponent.getSkillRechargeTime(Skills.PAPER_CUT) != 0)
				conditional += DAMAGE_ADD_THREE;
			if(opponent.getSkillRechargeTime(Skills.SHOOT_THE_MOON) != 0 || opponent.getSkillRechargeTime(Skills.SCISSOR_POKE) != 0)
				conditional += DAMAGE_ADD_TWO;
		}
		if(playerType.equals(PetTypes.SPEED))
		{
			if(((opponent.getPlayerFullHP() * .25) <= opponentHP)
					&& (opponentHP < (opponent.getPlayerFullHP() * .75)) 
					&& (opponentSkill.equals(Skills.ROCK_THROW) 
					|| opponentSkill.equals(Skills.PAPER_CUT)))
				conditional = DAMAGE_ADD_TEN;
		}
		
		return new Pair(random, conditional);
	}
	
	/**
	 * Use skill actions for all rock throw skills in the game. Returns
	 * the damage to be dealt
	 */
	public Pair rockThrow() {
		// TODO Auto-generated method stub
		opponentHP = this.opponent.getCurrentHp();
		double random = rng.nextFloat() * RANDOM_RANGE;
		this.randomDamage = random;
		double conditional = 0;
		if(this.playerType.equals(PetTypes.POWER))
		{
			if(this.opponentSkill.equals(Skills.SCISSOR_POKE))
				conditional = random * DAMAGE_MULTIPLIER;
		}
		if(this.playerType.equals(PetTypes.INTELLIGENCE))
		{
			//if shoot the moon is re-charging... future release
			if(opponent.getSkillRechargeTime(Skills.SCISSOR_POKE) != 0)
				conditional += DAMAGE_ADD_THREE;
			if(opponent.getSkillRechargeTime(Skills.SHOOT_THE_MOON) != 0 || opponent.getSkillRechargeTime(Skills.ROCK_THROW) != 0)
				conditional += DAMAGE_ADD_TWO;
		}
		if(this.playerType.equals(PetTypes.SPEED))
		{
			if(opponentHP >= (opponent.getPlayerFullHP() * .75) 
					&& (opponentSkill.equals(Skills.SCISSOR_POKE) 
					|| opponentSkill.equals(Skills.PAPER_CUT)))
				conditional = DAMAGE_ADD_TEN;
		}
		
		return new Pair(random, conditional);
	}
	
	/**
	 * Returns the amount of damage caused by the skill on the opponent. 
	 */
	public Pair paper()
	{
		// TODO Auto-generated method stub
		double random = rng.nextFloat() * RANDOM_RANGE;
		this.randomDamage = random;
		double conditional = 0;
		opponentHP = this.opponent.getCurrentHp();
		//Below are if statements for conditional damage...
		if(playerType.equals(PetTypes.POWER))
		{
			if(opponentSkill.equals(Skills.ROCK_THROW))
				conditional = random * DAMAGE_MULTIPLIER;
		}
		
		if(playerType.equals(PetTypes.INTELLIGENCE))
		{
			//if shoot the moon is re-charging... future release
			if(opponent.getSkillRechargeTime(Skills.ROCK_THROW) != 0)
				conditional += DAMAGE_ADD_THREE;
			if(opponent.getSkillRechargeTime(Skills.SHOOT_THE_MOON) != 0 || opponent.getSkillRechargeTime(Skills.PAPER_CUT) != 0)
				conditional += DAMAGE_ADD_TWO;
		}
		if(playerType.equals(PetTypes.SPEED))
		{
			if(opponentHP >= 0 
					&& opponentHP < (opponent.getPlayerFullHP() * .25) 
					&& (opponentSkill.equals(Skills.ROCK_THROW) 
					|| opponentSkill.equals(Skills.SCISSOR_POKE)))
			{
				conditional = DAMAGE_ADD_TEN;
			}
		}
		return new Pair(random, conditional);
	}
	/**
	 * Returns the random damage done from this round
	 */
	@Override
	public double getRandom() {
		// TODO Auto-generated method stub
		return randomDamage;
	}	
	
	/**
	 * Sets predicted skill chosen by player
	 */
	public void setSkillChoice(Skills predictedSkill)
	{
		this.predictedSkill = predictedSkill;
	}
	
	/**
	 * Returns the amount of damage caused by the skill on the opponent. 
	 */
	public Pair shootTheMoon()
	{
		// TODO Auto-generated method stub
		double random = rng.nextFloat() * RANDOM_RANGE;
		double conditional = 0;
		//Below are if statements for conditional damage...
		if(opponentSkill.equals(predictedSkill))
				conditional = DAMAGE_ADD_TWENTY;
		return new Pair(random, conditional);
	}	
	
	/**
	 * Calculates damage done for skill Reversal Of Fortune
	 * Returns Damage calculated.
	 */
	public Pair reversalOfFortune(int index) {
		// TODO Auto-generated method stub
		double random = rng.nextFloat() * RANDOM_RANGE;
		this.randomDamage = random;
		double conditional = this.ref.getRandomDifference(index);
		//conditional = opponentTotalRNG - playerTotalRNG;
		return new Pair(random, conditional);
	}
	
	/**
	 * Sets opponents total random damage done
	 */
	@Override
	public void SetOpponentRng(double f) {
		opponentTotalRNG = f;
	}
	
	/**
	 * Sets current player's total random damage done
	 */
	@Override
	public void SetPlayerRng(double f)
	{
		playerTotalRNG = f;
	}
}
