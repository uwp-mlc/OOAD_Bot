package edu.dselent.player;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.dselent.observer.Observer;
import edu.dselent.skill.Skills;

public interface Playable extends Observer
{
	// TODO check if this will work with custom skill sets in the future
	// TODO remove default implementation and tell class about this
	default Set<Skills> getSkillSet()
	{
		List<Skills> skillList = Arrays.asList(Skills.values());
		Set<Skills> skillSet = new HashSet<>(skillList);
        return skillSet;
    }

    int getPlayableUid(); // TODO tell students
	String getPlayerName();
	String getPetName();
	PlayerTypes getPlayerType();
	PetTypes getPetType();
	double getStartingHp();
	double getCurrentHp();
	Skills chooseSkill();
	void updateHp(double hp);
	void resetHp(); // TODO may not need this method in the interface?
	void setCurrentHp(double currentHp); // TODO may not need this method in the interface
	boolean isAwake();
	Skills getSkillPrediction();
	int getSkillRechargeTime(Skills skill);
	double calculateHpPercent(); // Convenience method since this can be calculcated from the current / starting hp
	void reset();
	void decrementRechargeTimes();
	void setRechargeTime(Skills skill, int rechargeTime);

}
