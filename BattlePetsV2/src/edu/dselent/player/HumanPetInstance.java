package edu.dselent.player;

import java.util.Observable;

import edu.dselent.settings.PlayerInfo;
import edu.dselent.skill.Skills;

public class HumanPetInstance extends PetInstance
{
	public HumanPetInstance(int playableUid, PlayerInfo playerInfo)
	{
		super(playableUid, playerInfo);
	}

	@Override
	public Skills chooseSkill()
	{
		// TODO
		return null;
	}

	@Override
	public void update(Object event)
	{
		// TODO Auto-generated method stub
		
	}
	
}
