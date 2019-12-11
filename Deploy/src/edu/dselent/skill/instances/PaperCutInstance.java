package edu.dselent.skill.instances;

import edu.dselent.skill.Skill;

public class PaperCutInstance extends SkillInstance
{
	public PaperCutInstance()
	{
		super();
	}
	
	public PaperCutInstance(Skill skill)
	{
		super(skill, 0);
	}
	
	public PaperCutInstance(Skill skill, int currentRechargeTime)
	{
		super(skill, currentRechargeTime);
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append(super.toString());
		builder.append("PaperCutInstance []");
		return builder.toString();
	}
	
	
}
