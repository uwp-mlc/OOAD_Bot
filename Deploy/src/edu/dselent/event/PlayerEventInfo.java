package edu.dselent.event;

import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;

import edu.dselent.player.PetTypes;
import edu.dselent.player.PlayerTypes;
import edu.dselent.skill.Skills;

public class PlayerEventInfo
{
	private final int playableUid;
	private final String petName;
	private final PetTypes petType;
	private final PlayerTypes playerType;
	private final double startingHp;
	private final Set<Skills> skillSet;
	
	private PlayerEventInfo(PlayerEventInfoBuilder builder)
	{
		// TODO ensure valid state

		playableUid = builder.playableUid;
		petName = builder.petName;
		petType = builder.petType;
		playerType = builder.playerType;
		startingHp = builder.startingHp;
		skillSet = builder.skillSet;
	}

	public PlayerEventInfo(PlayerEventInfo otherPlayerEventInfo)
	{
		// TODO ensure valid state

		this.playableUid = otherPlayerEventInfo.playableUid;
		this.petName = otherPlayerEventInfo.petName;
		this.petType = otherPlayerEventInfo.petType;
		this.playerType = otherPlayerEventInfo.playerType;
		this.startingHp = otherPlayerEventInfo.startingHp;
		this.skillSet = EnumSet.copyOf(otherPlayerEventInfo.skillSet);
	}

	
	public int getPlayableUid()
	{
		return playableUid;
	}
	
	public String getPetName()
	{
		return petName;
	}

	public PetTypes getPetType()
	{
		return petType;
	}

	public PlayerTypes getPlayerType()
	{
		return playerType;
	}

	public double getStartingHp()
	{
		return startingHp;
	}

	public Set<Skills> getSkillSet()
	{
		return skillSet;
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
		PlayerEventInfo that = (PlayerEventInfo) o;
		return playableUid == that.playableUid && Double.compare(that.startingHp, startingHp) == 0 && Objects.equals(petName, that.petName) && petType == that.petType && playerType == that.playerType && Objects.equals(skillSet, that.skillSet);
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(playableUid, petName, petType, playerType, startingHp, skillSet);
	}

	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder("PlayerEventInfo{");
		sb.append("playableUid=").append(playableUid);
		sb.append(", petName='").append(petName).append('\'');
		sb.append(", petType=").append(petType);
		sb.append(", playerType=").append(playerType);
		sb.append(", startingHp=").append(startingHp);
		sb.append(", skillSet=").append(skillSet);
		sb.append('}');
		return sb.toString();
	}

	public static class PlayerEventInfoBuilder
	{
		private int playableUid;
		private String petName;
		private PetTypes petType;
		private PlayerTypes playerType;
		private Double startingHp;
		private Set<Skills> skillSet;

		public PlayerEventInfoBuilder withPlayableUid(int playableUid)
		{
			this.playableUid = playableUid;
			return this;
		}
		
		public PlayerEventInfoBuilder withPetName(String petName) 
		{
			this.petName = petName;
			return this;
		}
		
		public PlayerEventInfoBuilder withPetType(PetTypes petType) 
		{
			this.petType = petType;
			return this;
		}
		
		public PlayerEventInfoBuilder withPlayerType(PlayerTypes playerType) 
		{
			this.playerType = playerType;
			return this;
		}
		
		public PlayerEventInfoBuilder withStartingHp(Double startingHp) 
		{
			this.startingHp = startingHp;
			return this;
		}
		
		public PlayerEventInfoBuilder withSkillSet(Set<Skills> skillSet) 
		{
			this.skillSet = skillSet;
			return this;
		}
		
		public PlayerEventInfo build()
		{
			return new PlayerEventInfo(this);
		}
	}

}
