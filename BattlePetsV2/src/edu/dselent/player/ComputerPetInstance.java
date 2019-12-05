package edu.dselent.player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Random;

import edu.dselent.settings.PlayerInfo;
import edu.dselent.skill.Skills;
import edu.dselent.skill.instances.ShootTheMoonInstance;
import edu.dselent.skill.instances.SkillInstance;

public class ComputerPetInstance extends PetInstance
{
	private static final int DEFAULT_RANDOM_SEED = 7777;
	private long randomSeed;
	private Random random;

	public ComputerPetInstance(int playableUid, PlayerInfo playerInfo)
	{
		super(playableUid, playerInfo);
		
		this.randomSeed = DEFAULT_RANDOM_SEED;
		random = new Random(randomSeed);
	}
	
	public ComputerPetInstance(int playableUid, PlayerInfo playerInfo, long randomSeed)
	{
		super(playableUid, playerInfo);
		
		this.randomSeed = randomSeed;
		random = new Random(randomSeed);
	}
	
	public long getRandomSeed()
	{
		return randomSeed;
	}
	
	@Override
	public Skills chooseSkill()
	{
		Skills skillChoice = null;
		
		Map<Skills, SkillInstance> skillInstanceMap = getSkillInstanceMap();
		int numberOfSkills = skillInstanceMap.size();
		List<Skills> skillList = new ArrayList<Skills>(skillInstanceMap.keySet());
		
		int randomChoiceInt = random.nextInt(numberOfSkills);
		skillChoice = skillList.get(randomChoiceInt);
		
		while(skillInstanceMap.get(skillChoice).isRecharging())
		{
			randomChoiceInt = random.nextInt(numberOfSkills);
			skillChoice = skillList.get(randomChoiceInt);
		}
		
		if(skillChoice == Skills.SHOOT_THE_MOON)
		{
			// TODO assumes opponent has same skills
			// TODO fix so this generalizes to different skill sets
			int randomPredictInt = random.nextInt(numberOfSkills);
			Skills randomPrediciton = skillList.get(randomPredictInt);
			ShootTheMoonInstance stmInstance = (ShootTheMoonInstance)skillInstanceMap.get(Skills.SHOOT_THE_MOON);
			stmInstance.setPredictedSkillEnum(randomPrediciton);
		}
				
		return skillChoice;
	}

	@Override
	public void update(Object event)
	{
		// TODO Auto-generated method stub
		
	}

	

}
