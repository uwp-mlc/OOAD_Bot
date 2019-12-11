package edu.dselent.skill;

import java.util.Objects;

// TODO Future potential idea
// This class leads to more dynamic skill construction, which is a really cool feature
// recharge time should not be allowed to vary independently from the enum
// Note there is a difference between choosing pre-made skills and ability to make skills

// ***
// Might just setting for choosing and not creating and remove this class and store information in the enumeration
// I think I have to, the enumeration allows me to refer to skills by name rather than abstracting their information
	// Too much abstraction can be a bad thing, need to draw the line somewhere
// ***
public class Skill
{
	private Skills skillEnum;
	private int rechargeTime;

	// Planning for the future with core and special skills
	private SkillTypes skillType;
	
	public Skill(Skills skillEnum, SkillTypes skillType, int rechargeTime)
	{		
		this.skillEnum = skillEnum;
		this.skillType = skillType;
		this.rechargeTime = rechargeTime;
	}
	
	public Skill(Skill otherSkill)
	{
		this.skillEnum = otherSkill.getSkillEnum();
		this.skillType = otherSkill.getSkillType();
		this.rechargeTime = otherSkill.getRechargeTime();
	}

	public Skills getSkillEnum()
	{
		return skillEnum;
	}

	public void setSkillEnum(Skills skillEnum)
	{
		this.skillEnum = skillEnum;
	}

	public SkillTypes getSkillType()
	{
		return skillType;
	}

	public void setSkillType(SkillTypes skillType)
	{
		this.skillType = skillType;
	}

	public int getRechargeTime()
	{
		return rechargeTime;
	}

	public void setRechargeTime(int rechargeTime)
	{
		this.rechargeTime = rechargeTime;
	}
	
	public String getSkillName()
	{
		return skillEnum.toString();
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (o == null || getClass() != o.getClass())
		{
			return false;
		}
		Skill skill = (Skill) o;
		return rechargeTime == skill.rechargeTime && skillEnum == skill.skillEnum && skillType == skill.skillType;
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(skillEnum, rechargeTime, skillType);
	}

	@Override
	public String toString()
	{
		return skillEnum.toString();
	}
}
