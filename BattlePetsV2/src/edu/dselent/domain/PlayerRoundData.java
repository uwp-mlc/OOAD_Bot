package edu.dselent.domain;

import edu.dselent.damage.Damage;
import edu.dselent.skill.skilldata.SkillData;

public class PlayerRoundData
{
	private SkillData skillData;
	private int victimPlayerIndex;
	private Damage damageDone;
	private boolean sleeping;
	
	public PlayerRoundData()
	{
		
	}

	public SkillData getSkillData()
	{
		return skillData;
	}

	public void setSkillData(SkillData skillData)
	{
		this.skillData = skillData;
	}

	public Damage getDamageDone()
	{
		return damageDone;
	}

	public void setDamageDone(Damage damageDone)
	{
		this.damageDone = damageDone;
	}

	public int getVictimPlayerIndex()
	{
		return victimPlayerIndex;
	}

	public void setVictimPlayerIndex(int victimPlayerIndex)
	{
		this.victimPlayerIndex = victimPlayerIndex;
	}

	public boolean isSleeping()
	{
		return sleeping;
	}

	public void setSleeping(boolean sleeping)
	{
		this.sleeping = sleeping;
	}
	
}
