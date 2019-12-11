package edu.dselent.skill.instances;

import edu.dselent.skill.Skill;
import edu.dselent.skill.Skills;

public class ShootTheMoonInstance extends SkillInstance
{
	private Skills predictedSkillEnum;
	
	public ShootTheMoonInstance()
	{
		super();
		predictedSkillEnum = null;
	}
	
	public ShootTheMoonInstance(Skill skill)
	{
		super(skill, 0);
		predictedSkillEnum = null;
	}
	
	public ShootTheMoonInstance(Skill skill, int currentRechargeTime)
	{
		super(skill, currentRechargeTime);
		predictedSkillEnum = null;
	}

	public Skills getPredictedSkillEnum()
	{
		return predictedSkillEnum;
	}

	public void setPredictedSkillEnum(Skills predictedSkillEnum)
	{
		this.predictedSkillEnum = predictedSkillEnum;
	}
	
	@Override
	public void reset()
	{
		super.reset();
		predictedSkillEnum = null;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append(super.toString());
		builder.append("ShootTheMoonInstance [predictedSkillEnum=");
		builder.append(predictedSkillEnum);
		builder.append("]");
		return builder.toString();
	}
	
	
}
