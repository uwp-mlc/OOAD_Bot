package edu.dselent.event;

import edu.dselent.damage.Damage;
import edu.dselent.skill.Skills;

import java.util.Objects;

// TODO
// Should sub class this for each skill
public class AttackEvent extends BaseEvent
{
	private final int attackingPlayableUid;
	private final int victimPlayableUid;
	private final Skills attackingSkillChoice;
	private final Damage damage;
	
	public AttackEvent(int attackingPlayableUid, int victimPlayableUid, Skills attackingSkillChoice, Damage damage)
	{
		super(EventTypes.ATTACK);

		this.attackingPlayableUid = attackingPlayableUid;
		this.victimPlayableUid = victimPlayableUid;
		this.attackingSkillChoice = attackingSkillChoice;
		this.damage = damage;
	}

	public AttackEvent(AttackEvent otherEvent)
	{
		super(EventTypes.ATTACK);

		this.attackingPlayableUid = otherEvent.attackingPlayableUid;
		this.victimPlayableUid = otherEvent.victimPlayableUid;
		this.attackingSkillChoice = otherEvent.attackingSkillChoice;
		this.damage = new Damage(otherEvent.damage);
	}

	// TODO consider shorter constructor
	// Added this in
	public AttackEvent(int attackingPlayableUid, int victimPlayableUid, Skills attackingSkillChoice, Damage damage, EventTypes eventType)
	{
		super(eventType);

		this.attackingPlayableUid = attackingPlayableUid;
		this.victimPlayableUid = victimPlayableUid;
		this.attackingSkillChoice = attackingSkillChoice;
		this.damage = damage;
	}

	public int getAttackingPlayableUid()
	{
		return attackingPlayableUid;
	}

	public int getVictimPlayableUid()
	{
		return victimPlayableUid;
	}

	public Skills getAttackingSkillChoice()
	{
		return attackingSkillChoice;
	}

	// Returning a copy so pets cannot affect each other's information
	// TODO rename to getDamageCopy to prevent confusion
	public Damage getDamage()
	{
		return new Damage(damage);
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
		if (!super.equals(o))
		{
			return false;
		}
		AttackEvent that = (AttackEvent) o;
		return attackingPlayableUid == that.attackingPlayableUid && victimPlayableUid == that.victimPlayableUid && attackingSkillChoice == that.attackingSkillChoice && Objects.equals(damage, that.damage);
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(super.hashCode(), attackingPlayableUid, victimPlayableUid, attackingSkillChoice, damage);
	}

	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder("AttackEvent{");
		sb.append("attackingPlayableUid=").append(attackingPlayableUid);
		sb.append(", victimPlayableUid=").append(victimPlayableUid);
		sb.append(", attackingSkillChoice=").append(attackingSkillChoice);
		sb.append(", damage=").append(damage);
		sb.append(", eventType=").append(getEventType());
		sb.append('}');
		return sb.toString();
	}
}
