 package edu.furbiesfighters.gameplay;
/**
 * 
 */
import java.util.ArrayList;
import java.util.List;

import edu.furbiesfighters.events.RoundStartEvent;
import edu.furbiesfighters.players.Playable;
import edu.furbiesfighters.utility.Utility;

/**
 * Class for managing and controlling the flow of a fight. It will
 * store all the players and variables related to each fight.
 */
public class Fight
{
	private final int MINIMUM_PLAYERS = 2;
	protected Referee ref;
	protected List<Round> rounds;
	protected Round currentRound;
	
	private String name;
	
	/**
	 * Constructor that instantiates the class. It will create a new
	 * alive player list given the inPlayerList and set the variables.
	 * @param inPlayerList, all players in the fight.
	 * @param inName, name of the fight.
	 */
	public Fight(Referee ref, String inName) 
	{
		name = inName;
		this.ref = ref;
		
		this.rounds = new ArrayList<Round>();
	}
	
	/**
	 * Method for playing the fight. It will loop through the rounds in the fight.
	 * It will determine if the fight is over.
	 */
	public void playFight()
	{
		this.ref.updateHP();
		boolean keepPlaying;
		
		keepPlaying = true;
		
		while(keepPlaying)
		{
			this.createNewRound();
			
			Utility.printSmallBanner("Round " + this.rounds.size());
			
			this.currentRound.executeRoud();
			
			announceRoundResults();
			
			keepPlaying = !this.ref.hasWinner();
		}
	}

	/**
	 * Method for print out the results of the round. It will print out the 
	 * player and their hp.
	 */
	private void announceRoundResults()
	{
		List<Playable> playables = ref.getAllPlayables();
		Playable tempPlayable;
		int playerIndex;
		
		for (int i = 0; i < playables.size(); i++)
		{
			playerIndex = i + 1;
			tempPlayable = playables.get(i);
			String playableMessage = "";
			
			if(!tempPlayable.isAwake())
				playableMessage = "SLEEPING";
			else
				playableMessage += tempPlayable.getCurrentHp();
			
			Utility.printMessage(playerIndex + ". " + tempPlayable.getPlayerName() + ", " + playableMessage);
		}
	}

	/**
	 * Method for creating a new round. It will also increment round number.
	 */
	protected void createNewRound()
	{
		RoundStartEvent.RoundStartEventBuilder rsb = new  RoundStartEvent.RoundStartEventBuilder();
		currentRound = new Round(this.ref);
		this.rounds.add(currentRound);
		rsb.withRoundNumber(rounds.size());
		RoundStartEvent  rse  = rsb.build();
		this.ref.addEvent(rse);
	}
	
	/**
	 * Gets the name of the fight.
	 * @return name, the name of the fight.
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * Returns a list of all alive players. This method called after
	 * determining there is a winner. If there is a tie, return null. 
	 * If there is still players left in the game, then this method should
	 * not be called, but return null anyways. 
	 * @return
	 */
	public List<Playable> determineFightWinners()
	{
		List<Playable> playables = this.ref.getAlivePlayables();
		if(playables.size() >= this.MINIMUM_PLAYERS)
			return null;
		else if(playables.size() == 0)
			return calculateTie();
		this.ref.getAlivePlayables().forEach(playable-> this.ref.addFightWonToPlayer(playable));
		return playables;
	}
	
	/**
	 * Uses alivePlayerHistory in the round class to get the
	 * list of alive players before the round started.
	 * @return
	 */
	private List<Playable> calculateTie()
	{
		if(this.rounds.size() == 1)
		{
			this.ref.getAlivePlayables().forEach(playable-> this.ref.addFightWonToPlayer(playable));
			return this.ref.getAllPlayables();
		}
		else
		{
			this.currentRound.getAlivePlayerHistory().forEach(playable-> this.ref.addFightWonToPlayer(playable));
			return this.currentRound.getAlivePlayerHistory();
		}
	}
	
	/**
	 * Gets the round list
	 * @return
	 */
	public List <Round> getRounds()
	{
		return rounds;
	}
	
	/**
	 * Gets the fight's referee
	 * @return
	 */
	public Referee getRef()
	{
		return ref;
	}
	
	/**
	 * Gets the current round
	 * @return
	 */
	public Round getCurrentRound()
	{
		return currentRound;
	}
}
