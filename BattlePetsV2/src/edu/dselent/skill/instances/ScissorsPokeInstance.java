package edu.dselent.skill.instances;

import edu.dselent.skill.Skill;

public class ScissorsPokeInstance extends SkillInstance
{
	public ScissorsPokeInstance()
	{
		super();
	}
	
	public ScissorsPokeInstance(Skill skill)
	{
		super(skill, 0);
	}
	
	public ScissorsPokeInstance(Skill skill, int currentRechargeTime)
	{
		super(skill, currentRechargeTime);
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append(super.toString());
		builder.append("ScissorsPokeInstance []");
		return builder.toString();
	}
	
	
}
