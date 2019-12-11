package edu.dselent.skill.instances;

import edu.dselent.skill.Skill;

public class SkillInstance
{
	private Skill skill;
	private int currentRechargeTime;
	
	public SkillInstance()
	{
		this.skill = null;
		this.currentRechargeTime = 0;
	}
	
	public SkillInstance(Skill skill, int currentRechargeTime)
	{
		this.skill = skill;
		this.currentRechargeTime = currentRechargeTime;
	}
	
	// TODO 
	// copy constructor?

	public Skill getSkill()
	{
		return skill;
	}

	public int getCurrentRechargeTime()
	{
		return currentRechargeTime;
	}

	public void setCurrentRechargeTime(int currentRechargeTime)
	{
		this.currentRechargeTime = currentRechargeTime;
	}
	
	public void decrementRecharge()
	{
		if(currentRechargeTime > 0)
		{
			currentRechargeTime--;
		}
	}
	
	public void reset()
	{
		currentRechargeTime = 0;
	}
	
	public boolean isRecharging()
	{
		boolean recharging = false;
		
		if(currentRechargeTime > 0)
		{
			recharging = true;
		}
		
		return recharging;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("SkillInstance [skill=");
		builder.append(skill);
		builder.append(", currentRechargeTime=");
		builder.append(currentRechargeTime);
		builder.append("]");
		return builder.toString();
	}
	
	

}
