package edu.furbiesfighters.events;

import edu.furbiesfighters.skills.Skills;

/**
 * An event class for the Attack Event. Has information relevant to 
 * an attack.
 * @author Furbies Fights
 */
public final class AttackEvent extends BaseEvent {
	private static final double DOUBLE_DEFAULT = 0.0;
	private static final int INT_DEFAULT = 0;
	
	private final int attackingPlayerIndex;
	private final int victimPlayerIndex;
	private final Skills attackingSkillChoice;
	private final Skills predictedSkillEnum;
	private final double randomDamage;
	private final double conditionalDamage;
	
	/**
	 * Constructor for the Attack Event Class. It will take a builder and
	 * determine if the builder has valid values for the variables.
	 * @param builder
	 */
	private AttackEvent(AttackEventBuilder builder)
	{
		super(EventTypes.ATTACK);
		
		if(builder.attackingPlayerIndex == null)
		{
			attackingPlayerIndex = INT_DEFAULT;
		}
		else
		{
			attackingPlayerIndex = builder.attackingPlayerIndex;
		}
		
		if(builder.victimPlayerIndex == null)
		{
			victimPlayerIndex = INT_DEFAULT;
		}
		else
		{
			victimPlayerIndex = builder.victimPlayerIndex;
		}
		
		if(builder.attackingSkillChoice == null)
		{
			attackingSkillChoice = Skills.ROCK_THROW;
		}
		else
		{
			attackingSkillChoice = builder.attackingSkillChoice;
		}
		
		if(builder.predictedSkillEnum == null)
		{
			predictedSkillEnum = Skills.ROCK_THROW;
		}
		else
		{
			predictedSkillEnum = builder.predictedSkillEnum;
		}
		
		if(builder.randomDamage == null)
		{
			randomDamage = DOUBLE_DEFAULT;
		}
		else
		{
			 randomDamage = builder.randomDamage;
		}
		
		if(builder.conditionalDamage == null)
		{
			conditionalDamage = DOUBLE_DEFAULT;
		}
		else
		{
			conditionalDamage = builder.conditionalDamage;
		}
	}
	
	/**
	 * Getter method for returning the attackingPlayerIndex.
	 */
	public int getAttackingPlayerIndex()
	{
		return attackingPlayerIndex;
	}
	
	/**
	 * Getter method for returning the victimPlayerIndex.
	 */
	public int getVictimPlayerIndex()
	{
		return victimPlayerIndex;
	}
	
	/**
	 * Getter method for returning the attackingSkillChoice.
	 */
	public Skills getAttackingSkillChoice()
	{
		return attackingSkillChoice;
	}
	
	/**
	 * Getter method for returning the predictedSkillEnum.
	 */
	public Skills getPredictedSkillEnum()
	{
		return predictedSkillEnum;
	}
	
	/**
	 * Getter method for returning the randomDamage.
	 */
	public double getRandomDamage()
	{
		return randomDamage;
	}
	
	/**
	 * Getter method for returning the conditionalDamage.
	 */
	public double getConditionalDamage()
	{
		return conditionalDamage;
	}
	
	/**
	 * Overridden method to return the string representation of the class.
	 */
	@Override
	public String toString() {
	return "AttackEvent [attackingPlayerIndex=" + attackingPlayerIndex + ", victimPlayerIndex=" + 
	victimPlayerIndex + ", attackingSkillChoice=" + attackingSkillChoice + ", predictedSkillEnum="
			+ predictedSkillEnum + ", randomDamge=" + randomDamage + ", conditionalDamage=" 
			+ conditionalDamage + "]";
	}
	
	/**
	 * Overridden hashCode method that returns the hashcode representation 
	 * for the class.
	 */
	@Override
	public int hashCode()
	{
	final int prime = 31;
	int result = 1;
	result = prime * result + attackingPlayerIndex;
	result = prime * result + victimPlayerIndex;
	result = prime * result + ((attackingSkillChoice == null) ? 0 : attackingSkillChoice.hashCode());
	result = prime * result + ((predictedSkillEnum == null) ? 0 : predictedSkillEnum.hashCode());
	long temp;
	temp = Double.doubleToLongBits(randomDamage);
	result = prime * result + (int) (temp ^ (temp >>> 32));
	temp = Double.doubleToLongBits(randomDamage);
	result = prime * result + (int) (temp ^ (temp >>> 32));
	return result;
	}
	
	/**
	 * Overridden method to determine if one Attack event class equals 
	 * another.
	 */
	@Override
	public boolean equals(Object obj)
	{
		AttackEvent other = (AttackEvent) obj;
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		if(attackingPlayerIndex != other.attackingPlayerIndex)
			return false;
		if(victimPlayerIndex != other.victimPlayerIndex)
			return false;
		if(attackingSkillChoice == null)
		{
			if(other.attackingSkillChoice != null)
				return false;
		}
		else if(!attackingSkillChoice.equals(other.attackingSkillChoice))
			return false;
		if(predictedSkillEnum == null)
		{
			if(other.predictedSkillEnum != null)
				return false;
		}
		else if(!predictedSkillEnum.equals(other.predictedSkillEnum))
			return false;
		if (Double.doubleToLongBits(randomDamage) != Double.doubleToLongBits(other.randomDamage))
			return false;
		if (Double.doubleToLongBits(conditionalDamage) != Double.doubleToLongBits(other.conditionalDamage))
			return false;
		return true;
	}
	
	/**
	 * A builder class that builds a new Attack Event given a certain
	 * amount of constructor input.
	 */
	public static class AttackEventBuilder 
	{
		private Integer attackingPlayerIndex;
		private Integer victimPlayerIndex;
		private Skills attackingSkillChoice;
		private Skills predictedSkillEnum;
		private Double randomDamage;
		private Double conditionalDamage;
		
		/**
		 * An empty constructor. All variables instantiated later on.
		 */
		public AttackEventBuilder()
		{
			
		}

		/**
		 * Method for added the attackingPlayerIndex parameter to the builder.
		 * It returns the builder to be further added to.
		 */
		public AttackEventBuilder withAttackingPlayerIndex(int attackingPlayerIndex) 
		{
			this.attackingPlayerIndex = attackingPlayerIndex;
			return this;
		}

		/**
		 * Method for added the victimPlayerIndex parameter to the builder.
		 * It returns the builder to be further added to.
		 */
		public AttackEventBuilder withVictimPlayerIndex(int victimPlayerIndex) 
		{
			this.victimPlayerIndex = victimPlayerIndex;
			return this;
		}

		/**
		 * Method for added the attackingSkillChoice parameter to the builder.
		 * It returns the builder to be further added to.
		 */
		public AttackEventBuilder withAttackingSkillChoice(Skills attackingSkillChoice) 
		{
			this.attackingSkillChoice = attackingSkillChoice;
			return this;
		}

		/**
		 * Method for added the predictedSkillEnum parameter to the builder.
		 * It returns the builder to be further added to.
		 */
		public AttackEventBuilder withPredictedSkillEnum(Skills predictedSkillEnum) 
		{
			this.predictedSkillEnum = predictedSkillEnum;
			return this;
		}

		/**
		 * Method for added the randomDamage parameter to the builder.
		 * It returns the builder to be further added to.
		 */
		public AttackEventBuilder withRandomDamage(double randomDamage) 
		{
			this.randomDamage = randomDamage;
			return this;
		}

		/**
		 * Method for added the conditionalDamage parameter to the builder.
		 * It returns the builder to be further added to.
		 */
		public AttackEventBuilder withConditionalDamage(double conditionalDamage) 
		{
			this.conditionalDamage = conditionalDamage;
			return this;
		}

		/**
		 * Method to actually instantiate the new AttackEvent using the
		 * parameters passed to the builder.
		 */
		public AttackEvent Build()
		{
			return new AttackEvent(this);
		}
	}
}
