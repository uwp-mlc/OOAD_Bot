package edu.furbiesfighters.gameplay;
import java.util.ArrayList;

import java.util.List;

import edu.furbiesfighters.players.Playable;
import edu.furbiesfighters.utility.Utility;
/**
 * @author Furbies Fighters
 * Class: Round
 * Def: Class that has all features and information that occurs during a round.
 */
public class Round 
{
	private Referee ref;
	private final List<Playable> alivePlayerHistory;
	
	/**
	 * Main constructor for round where the lists are
	 * initialized.
	 */
	public Round(Referee ref)
	{
		this.ref = ref;
		this.alivePlayerHistory = new ArrayList<Playable>();
		for(Playable p : this.ref.getAlivePlayables())
			this.alivePlayerHistory.add(p);
	}
	
	/**
	 * Executes one round of a fight where each player chooses their
	 * skill and then deals damage. 
	 */
	public void executeRoud()
	{
		this.ref.allChooseSkill();
		this.ref.calculateAllDamages();
	}
	
	/**
	 * Gets the round's instance of the referee.
	 * @return
	 */
	public Referee getReferee()
	{
		return this.ref;
	}
	
	/**
	 * Returns the instance of the list of alive players before
	 * the round started. This is used later for calculating a
	 * tie in the fight. 
	 * @return
	 */
	public List<Playable> getAlivePlayerHistory()
	{
		return this.alivePlayerHistory;
	}
}
