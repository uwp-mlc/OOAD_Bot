package edu.dselent.skill;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Stores all possible skills to choose from for any game
 * The purpose of this class is to store all possible skills, which can lead to unique and different skill seats for each pet in the future
 * Dynamic skill creation is not possible without major infrastructure changes, which would end up being for the worse
 * 
 * I always worry about using singletons, I believe this is a valid use
 * There can be only one SkillRegistry for a run of the application to hold all possible skills
 * 
 * @author Doug
 *
 */
public enum SkillRegistry
{
	INSTANCE;
	
	/**
	 * Chose a list instead of a set since the Skills could be mutable
	 * Having order is useful too
	 */
	private List<Skill> skillList;
	
	private SkillRegistry()
	{
		skillList = new ArrayList<>();
		
		Skill rockThrow = new Skill(Skills.ROCK_THROW, SkillTypes.CORE, 1);
		Skill scissorsPoke = new Skill(Skills.SCISSORS_POKE, SkillTypes.CORE, 1);
		Skill paperCut = new Skill(Skills.PAPER_CUT, SkillTypes.CORE, 1);
		Skill shootTheMoon = new Skill(Skills.SHOOT_THE_MOON, SkillTypes.SPECIAL, 6);
		Skill reversalOfFortune = new Skill(Skills.REVERSAL_OF_FORTUNE, SkillTypes.SPECIAL, 6);
	
		skillList.add(rockThrow);
		skillList.add(scissorsPoke);
		skillList.add(paperCut);
		skillList.add(shootTheMoon);
		skillList.add(reversalOfFortune);
	}

	public List<Skill> getSkillList()
	{
		return Collections.unmodifiableList(skillList);
	}
	
}
