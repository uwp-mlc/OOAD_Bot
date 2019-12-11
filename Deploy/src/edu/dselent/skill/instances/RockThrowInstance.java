package edu.dselent.skill.instances;

import edu.dselent.skill.Skill;

public class RockThrowInstance extends SkillInstance
{
	public RockThrowInstance()
	{
		super();
	}
	
	public RockThrowInstance(Skill skill)
	{
		super(skill, 0);
	}
	
	public RockThrowInstance(Skill skill, int currentRechargeTime)
	{
		super(skill, currentRechargeTime);
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append(super.toString());
		builder.append("RockThrowInstance []");
		return builder.toString();
	}
	
	
}
