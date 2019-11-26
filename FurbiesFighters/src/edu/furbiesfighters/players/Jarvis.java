/**
 * 
 */
package edu.furbiesfighters.players;

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
 * @author nickl
 *
 */
public class Jarvis  extends Player { //extends Pet for doug
	protected Map<Skills, Integer> rechargingOpponentSkills, jarvisRechargingSkills;
	private int roundNumber, fightNumber, opponentIndex, jarvisIndex;
	private String opponentName;
	private PetTypes opponentType;
	private Map<Integer, Double> randomDifference;
	private double opponentHealth;
	private Random random;
	
	
	public Jarvis(double initialHP, String name, String petName, PetTypes petType) {
		super(initialHP, name, petName, PetTypes.POWER, PlayerTypes.AI);
		this.randomDifference = new HashMap<Integer, Double>();
		this.rechargingOpponentSkills = new HashMap<Skills, Integer>();
		this.rechargingOpponentSkills.put(Skills.ROCK_THROW, 0);
		this.rechargingOpponentSkills.put(Skills.SCISSOR_POKE, 0);
		this.rechargingOpponentSkills.put(Skills.PAPER_CUT, 0);
		this.rechargingOpponentSkills.put(Skills.SHOOT_THE_MOON, 0);
		this.rechargingOpponentSkills.put(Skills.REVERSAL_OF_FORTUNE, 0);
		this.random  = new Random(10320l);
	}
	
	/**
	 * Choose a skill 
	 */
	@Override
	public Skills chooseSkill()
	{
		System.out.println("SFIONDSJFKLFJarvis is choosing..... against " + this.opponentType);
		return learnSkill();
	}

	/**
	 * Return the best skill after trying them all. 
	 * @return
	 */
	private Skills learnSkill()
	{
		Map<Skills, Double> outputDamageMap;
		Map<Skills, Double> inputDamageMap;
		Map<Skills, Double> differenceMap;
		Skills skill;
		
		outputDamageMap = populateOutputMap();
		skill = getBestSkillGivenDifferences(outputDamageMap);
		//this.decrementRechargeTimes();
		this.setRechargeTime(skill, (skill == Skills.REVERSAL_OF_FORTUNE || skill == Skills.SHOOT_THE_MOON) ? 6 : 1);
		
		return skill;
	}
	
	/**
	 * Optimizes for the best skill preferences
	 * @return
	 */
	private Map<Skills, Double> populateOutputMap()
	{
		Map<Skills, Double> outputDamageMap = new HashMap<Skills, Double>();
		if(super.getSkillRechargeTime(Skills.ROCK_THROW) == 0)
		{			
			if(this.rechargingOpponentSkills.get(Skills.ROCK_THROW) == 0 
					&& (this.rechargingOpponentSkills.get(Skills.SCISSOR_POKE) != 0
					|| this.rechargingOpponentSkills.get(Skills.PAPER_CUT) != 0))
			{
				outputDamageMap.put(Skills.ROCK_THROW, (5.0 * 3.0) - this.rockConsequence());
			}
			else
				outputDamageMap.put(Skills.ROCK_THROW, -1 * this.rockConsequence());
		}
		
		if(super.getSkillRechargeTime(Skills.SCISSOR_POKE) == 0)
		{
			if(this.rechargingOpponentSkills.get(Skills.PAPER_CUT) == 0 
					&& (this.rechargingOpponentSkills.get(Skills.SCISSOR_POKE) != 0
					|| this.rechargingOpponentSkills.get(Skills.ROCK_THROW) != 0))
			{
				outputDamageMap.put(Skills.SCISSOR_POKE, (5.0 * 3.0) - this.scissorConsequence());
			}
			else
				outputDamageMap.put(Skills.SCISSOR_POKE, -1 * this.scissorConsequence());
		}
		
		if(super.getSkillRechargeTime(Skills.PAPER_CUT) == 0)
		{
			if(this.rechargingOpponentSkills.get(Skills.ROCK_THROW) == 0 
					&& (this.rechargingOpponentSkills.get(Skills.SCISSOR_POKE) != 0
					|| this.rechargingOpponentSkills.get(Skills.PAPER_CUT) != 0))
			{
				outputDamageMap.put(Skills.PAPER_CUT, (5.0 * 3.0) - this.paperConsequence());
			}
			else
				outputDamageMap.put(Skills.PAPER_CUT, -1 * this.paperConsequence());
		}
		
		if(this.getSkillRechargeTime(Skills.REVERSAL_OF_FORTUNE) == 0 &&
				this.randomDifference.get(this.jarvisIndex) > 10.0)
		{
			outputDamageMap.put(Skills.REVERSAL_OF_FORTUNE, this.randomDifference.get(this.jarvisIndex));
		}
		
		if (this.getSkillRechargeTime(Skills.SHOOT_THE_MOON) == 0 &&
				this.getOpponentSkillRechargeTime(Skills.REVERSAL_OF_FORTUNE) > 0 && 
				this.getOpponentSkillRechargeTime(Skills.SHOOT_THE_MOON) > 0 &&
				   (this.getOpponentSkillRechargeTime(Skills.ROCK_THROW) > 0 ||
					this.getOpponentSkillRechargeTime(Skills.SCISSOR_POKE) > 0 ||
					this.getOpponentSkillRechargeTime(Skills.PAPER_CUT) > 0))
		{
			outputDamageMap.put(Skills.SHOOT_THE_MOON, Double.MAX_VALUE);
		}
		
		return outputDamageMap;
	}
	
	/**
	 * Find possible damage when using rock
	 * @return
	 */
	private double rockConsequence()
	{
		double healthPercent = super.getCurrentHp() / super.getPlayerFullHP();
		if(this.opponentType == PetTypes.SPEED && healthPercent < .75 && healthPercent >= .25)
			return 10.0;
		else if (this.opponentType == PetTypes.SPEED && healthPercent < .25)
			return 10.0;
		else if (this.opponentType == PetTypes.POWER && this.rechargingOpponentSkills.get(Skills.PAPER_CUT) == 0)
			return 20.0;
		else if(this.opponentType == PetTypes.INTELLIGENCE && this.rechargingOpponentSkills.get(Skills.ROCK_THROW) == 0)
			return 3.0;
		else if(this.opponentType == PetTypes.INTELLIGENCE && this.rechargingOpponentSkills.get(Skills.PAPER_CUT) == 0)
			return 2.0;
		else 
			return 0.0;
	}
	
	/**
	 * Calculate possible consequences of using the paper skill.
	 * @return
	 */
	private double paperConsequence()
	{
		double healthPercent = super.getCurrentHp() / super.getPlayerFullHP();
		if (this.opponentType == PetTypes.SPEED && healthPercent >= .75)
			return 10.0;
		else if (this.opponentType == PetTypes.SPEED && healthPercent < .25)
			return 10.0;
		else if (this.opponentType == PetTypes.POWER && this.rechargingOpponentSkills.get(Skills.SCISSOR_POKE) == 0)
			return 20.0;
		else if(this.opponentType == PetTypes.INTELLIGENCE && this.rechargingOpponentSkills.get(Skills.SCISSOR_POKE) == 0)
			return 3.0;
		else if(this.opponentType == PetTypes.INTELLIGENCE && this.rechargingOpponentSkills.get(Skills.PAPER_CUT) == 0)
			return 2.0;
		else
			return 0.0;
	}
	
	/**
	 * Calculate possible consequences of using the scissor skill.
	 * @return
	 */
	private double scissorConsequence()
	{
		double healthPercent = super.getCurrentHp() / super.getPlayerFullHP();
		if (this.opponentType == PetTypes.SPEED && healthPercent >= .75)
			return 10.0;
		else if (this.opponentType == PetTypes.SPEED && healthPercent < .75 && healthPercent >= .25)
			return 10.0;
		else if (this.opponentType == PetTypes.POWER && this.rechargingOpponentSkills.get(Skills.ROCK_THROW) == 0)
			return 20.0;
		else if(this.opponentType == PetTypes.INTELLIGENCE && this.rechargingOpponentSkills.get(Skills.ROCK_THROW) == 0)
			return 3.0;
		else if(this.opponentType == PetTypes.INTELLIGENCE && this.rechargingOpponentSkills.get(Skills.SCISSOR_POKE) == 0)
			return 2.0;
		else
			return 0.0;
	}
	
	private Map<Skills, Double> getBlankSkillToIntegerMap()
	{
		Map<Skills, Double> map;
		
		map = new HashMap<Skills, Double>();
		
		map.put(Skills.ROCK_THROW, 0.0);
		map.put(Skills.SCISSOR_POKE, 0.0);
		map.put(Skills.PAPER_CUT, 0.0);
		map.put(Skills.SHOOT_THE_MOON, 0.0);
		map.put(Skills.REVERSAL_OF_FORTUNE, 0.0);
		
		return map;
	}
	
	private Skills getBestSkillGivenDifferences(Map<Skills, Double> differenceMap)
	{
		Skills skill;
		double bestDifference;
		
		skill = null;
		bestDifference = (-1) * Double.MAX_VALUE;
		
		for (Map.Entry<Skills, Double> pair : differenceMap.entrySet())
		{
			System.out.println("CHECKING : " + pair.getKey().toString() + "   " + pair.getValue());
			if (pair.getValue() > bestDifference && this.getSkillRechargeTime(pair.getKey()) == 0)
			{
				bestDifference = pair.getValue();
				skill = pair.getKey();
			}
		}
		
		if(skill == null)
			skill = this.getRandomSelection();
		
		return skill;
	}
	
	/**
	 * return the random integer generated
	 */
	private int randomSkillRNG()
	{
		return (int)Math.ceil(random.nextFloat() * 3);
	}
	
	/**
	 * Return a random skill selection
	 * @return
	 */
	private Skills getRandomSelection()
	{
		boolean validSkill = false;
		int rechargeTime = 0;
		while(!validSkill)
		{
			int rngSkill = randomSkillRNG();
			switch(rngSkill)
			{
			case 1 :
				rechargeTime = this.getSkillRechargeTime(Skills.ROCK_THROW);
				if(rechargeTime == 0 && this.rockConsequence() == 0){
					validSkill = true;
					return Skills.ROCK_THROW;
				}
				break;
			case 2 :
				rechargeTime = this.getSkillRechargeTime(Skills.SCISSOR_POKE);
				if(rechargeTime == 0 && this.scissorConsequence() == 0){
					validSkill = true;
					return Skills.SCISSOR_POKE;
				}
				break;
			case 3 :
				rechargeTime = this.getSkillRechargeTime(Skills.PAPER_CUT);
				if(rechargeTime == 0 && this.paperConsequence() == 0){
					validSkill = true;
					this.chosenSkill = Skills.PAPER_CUT;
					return Skills.PAPER_CUT;
				}
				break;
			default :
				break;
			}
		}
		return null;
	}
	
	public int getOpponentSkillRechargeTime(Skills skill)
	{
		return this.rechargingOpponentSkills.get(skill);
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
			rememberFightStartEvent(fse);
		}
		if(event instanceof RoundStartEvent)
		{
			RoundStartEvent rse = (RoundStartEvent) event;
			rememberRoundStartEvent(rse);
		}         
		if(event instanceof AttackEvent)
		{
			AttackEvent ae = (AttackEvent) event;
			this.rememberAttackEvent(ae);
		}
	}
	
	private void rememberFightStartEvent(FightStartEvent event) {
		this.fightNumber = event.getFightNumber();
		List<PlayerEventInfo> playerEventInfo = event.getPlayerEventinfo();
		this.rechargingOpponentSkills.forEach((key,value) -> value = 0);
		
		//Get Opponent information (index and name)
		for(int i = 0; i < playerEventInfo.size(); i++)
		{
			PlayerEventInfo playerInfo = playerEventInfo.get(i);
			this.randomDifference.put(i, 0.0);
			if(playerInfo.getPetName().equals(super.getPetName()))
			{
				this.jarvisIndex = i;
				this.opponentIndex = (i + 1) % playerEventInfo.size();
				this.opponentHealth = playerEventInfo.get((i+1) % playerEventInfo.size()).getStartingHp();
				this.opponentName = playerEventInfo.get(this.opponentIndex).getPetName();
				this.opponentType = playerEventInfo.get(this.opponentIndex).getPetType();
			}
		}
	}

	private void rememberRoundStartEvent(RoundStartEvent event){
		this.roundNumber = event.getRoundNumber();
	}
	
	private void rememberAttackEvent(AttackEvent event)
	{
		if(event.getAttackingPlayerIndex() == opponentIndex)
		{
			int rechargeTime = (event.getAttackingSkillChoice() == Skills.REVERSAL_OF_FORTUNE || event.getAttackingSkillChoice() == Skills.SHOOT_THE_MOON) ? 6 : 1;
			this.decrementOpponentRechargeTimes();
			this.rechargingOpponentSkills.put(event.getAttackingSkillChoice(), rechargeTime);
			
			this.randomDifference.put(event.getAttackingPlayerIndex(), this.randomDifference.get(event.getAttackingPlayerIndex()) - event.getRandomDamage());
			this.randomDifference.put(event.getVictimPlayerIndex(), this.randomDifference.get(event.getVictimPlayerIndex()) + event.getRandomDamage());
		}
		else if(event.getAttackingPlayerIndex() == jarvisIndex)
		{
			this.opponentHealth -= (event.getConditionalDamage() + event.getRandomDamage());
			
			this.randomDifference.put(event.getAttackingPlayerIndex(), this.randomDifference.get(event.getAttackingPlayerIndex()) - event.getRandomDamage());
			this.randomDifference.put(event.getVictimPlayerIndex(), this.randomDifference.get(event.getVictimPlayerIndex()) + event.getRandomDamage());
		}
		
	}
	
	private void decrementOpponentRechargeTimes()
	{
		for (Map.Entry<Skills, Integer> pair : rechargingOpponentSkills.entrySet())
		{
			int val = pair.getValue();
			Skills s = pair.getKey();
			
			if (val > 0)
			{
				rechargingOpponentSkills.put(s, --val);
			}
		}
	}
}

