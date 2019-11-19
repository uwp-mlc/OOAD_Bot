package edu.furbiesfighters.damage;
import edu.furbiesfighters.skills.Skills;

/**
 *@author Furbies Fighters
 *		  Interface: Calculatable
 *			Gets implemented by CalculateDamage
 */
public interface Calculatable 
{
	public Pair calculateDamage(int attackingPetIndex, int victimPetIndex);
	
	public double getRandom();
	
	public void SetOpponentRng(double f);
	
	public void SetPlayerRng(double f);
	
	public void setSkillChoice(Skills predictedSkill);
}
