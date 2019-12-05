package edu.dselent.player;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.dselent.settings.PlayerInfo;
import edu.dselent.skill.Skill;
import edu.dselent.skill.SkillRegistry;
import edu.dselent.skill.Skills;
import edu.dselent.skill.instances.PaperCutInstance;
import edu.dselent.skill.instances.ReversalOfFortuneInstance;
import edu.dselent.skill.instances.RockThrowInstance;
import edu.dselent.skill.instances.ScissorsPokeInstance;
import edu.dselent.skill.instances.ShootTheMoonInstance;
import edu.dselent.skill.instances.SkillInstance;


public abstract class PetInstance implements Playable
{
	private int playableUid;
	private Pet pet;
	private double startingHp;
	private double currentHp;
	private Map<Skills, SkillInstance> skillInstanceMap;
	
	public PetInstance(int playableUid, PlayerInfo playerInfo)
	{
		Player player = new Player(playerInfo.getPlayerName(), playerInfo.getPlayerType());
		pet = new Pet(player, playerInfo.getPetType(), playerInfo.getPetName());

		this.playableUid = playableUid;
		startingHp = playerInfo.getStartingHp();
		currentHp = startingHp;
		
		// Create skill map from skills
		skillInstanceMap = new EnumMap<>(Skills.class);
		List<Skill> allSkillList = SkillRegistry.INSTANCE.getSkillList();
		Set<Skills> skillSet = playerInfo.getSkillSet();
		
		// TODO better way to make skill instances? possibly not instantiating different classes based on enum
		for(Skills skill : skillSet)
		{
			Skill tempSkill = null;
			
			// Slightly inefficient
			for(Skill registrySkill : allSkillList)
			{
				if(registrySkill.getSkillEnum() == skill)
				{
					tempSkill = registrySkill;
				}
			}
			
			SkillInstance skillInstance;
			
			if(skill == Skills.ROCK_THROW)
			{
				skillInstance = new RockThrowInstance(tempSkill);
			}
			else if(skill == Skills.SCISSORS_POKE)
			{
				skillInstance = new ScissorsPokeInstance(tempSkill);
			}
			else if(skill == Skills.PAPER_CUT)
			{
				skillInstance = new PaperCutInstance(tempSkill);
			}
			else if(skill == Skills.SHOOT_THE_MOON)
			{
				skillInstance = new ShootTheMoonInstance(tempSkill);
			}
			else if(skill == Skills.REVERSAL_OF_FORTUNE)
			{
				skillInstance = new ReversalOfFortuneInstance(tempSkill);
			}
			else
			{
				// TODO custom exception, for invalid skill
				throw new RuntimeException("Invalid skill: " + skill);
			}
			
			skillInstanceMap.put(skill, skillInstance);
		}
	}

	@Override
	public int getPlayableUid()
	{
		return playableUid;
	}

	@Override
	public String getPlayerName()
	{
		return pet.getPlayer().getName();
	}
	
	@Override
	public PlayerTypes getPlayerType()
	{
		return pet.getPlayer().getPlayerType();
	}
	
	@Override
	public PetTypes getPetType()
	{
		return pet.getPetType();
	}
	
	@Override
	public String getPetName()
	{
		return pet.getName();
	}

	@Override
	public double getStartingHp()
	{
		return startingHp;
	}

	@Override
	public void setCurrentHp(double currentHp)
	{
		this.currentHp = currentHp;
	}
	
	@Override
	public double getCurrentHp()
	{
		return currentHp;
	}
	
	@Override
	public void updateHp(double hp)
	{
		currentHp = currentHp - hp;
	}
	
	@Override
	public void setRechargeTime(Skills skill, int rechargeTime)
	{
		skillInstanceMap.get(skill).setCurrentRechargeTime(rechargeTime);
	}
	
	@Override
	public int getSkillRechargeTime(Skills skill)
	{
		return skillInstanceMap.get(skill).getCurrentRechargeTime();
	}
	
	@Override
	public double calculateHpPercent()
	{
		return currentHp / startingHp;
	}
	
	@Override
	public boolean isAwake()
	{
		return currentHp>0;
	}
	
	@Override
	public void reset()
	{
		resetHp();
		resetSkills();
	}

	// TODO remove from interface and make private?
	@Override
	public void resetHp()
	{
		currentHp = startingHp;
	}
	
	@Override
	public void decrementRechargeTimes()
	{
		skillInstanceMap.forEach((key, value) -> value.decrementRecharge());
	}
	
	@Override
	public Skills getSkillPrediction()
	{
		ShootTheMoonInstance stmInstance = (ShootTheMoonInstance)skillInstanceMap.get(Skills.SHOOT_THE_MOON);
		return stmInstance.getPredictedSkillEnum();
	}
	
	
	private void resetSkills()
	{
		skillInstanceMap.forEach((key, value) -> value.reset());
	}
	
	protected Map<Skills, SkillInstance> getSkillInstanceMap()
	{
		return skillInstanceMap;
	}

	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder("PetInstance{");
		sb.append("playableUid=").append(playableUid);
		sb.append(", pet=").append(pet);
		sb.append(", startingHp=").append(startingHp);
		sb.append(", currentHp=").append(currentHp);
		sb.append(", skillInstanceMap=").append(skillInstanceMap);
		sb.append(", playerName='").append(getPlayerName()).append('\'');
		sb.append(", playerType=").append(getPlayerType());
		sb.append(", petType=").append(getPetType());
		sb.append(", petName='").append(getPetName()).append('\'');
		sb.append(", calculateHpPercent=").append(calculateHpPercent());
		sb.append(", awake=").append(isAwake());
		sb.append(", skillPrediction=").append(getSkillPrediction());
		sb.append(", skillSet=").append(getSkillSet());
		sb.append('}');
		return sb.toString();
	}
}
