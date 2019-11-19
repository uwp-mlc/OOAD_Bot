package edu.furbiesfighters.gameplay;
import java.util.List;

import edu.furbiesfighters.players.Playable;
import edu.furbiesfighters.utility.Utility;

/**
 * A class for controlling the flow of battles. It manages the player's
 * flow through the battle. It will let the player play as many battles
 * as they want.
 */
public class BattleController 
{
	private static final String YES_COMMAND = "Y";
	private static final String NO_COMMAND = "N";
	
	protected Battle currentBattle;					// The current battle
													// being played.
	
	protected Referee ref;							
	
	
	/**
	 * Constructor for Battle. It instantiates the currentBattle and input.
	 */
	public BattleController()
	{
		currentBattle = new Battle();
	}
	
	/**
	 * Method for playing each battle. It will loop and let the player
	 * play as many battles as they would like. It will announce each
	 * battle and announce the end of the game.
	 */
	public void play()
	{
		boolean keepPlaying;
		
		keepPlaying = true;
		
		while (keepPlaying)
		{
			Utility.printLargeBanner("New Battle Beginning");
			
			this.ref = new Referee();
			currentBattle = new Battle();
			currentBattle.setUpBattle();
			currentBattle.playBattle();
			
			String winningString = "The following player(s) won the battle: ";
			
			List<Playable> winners = currentBattle.getBattleWinner();
			for(Playable p : winners)
				winningString += p.getPlayerName() + ", ";
			
			Utility.printEndline();
			Utility.printMessage(winningString.substring(0, winningString.length()-2));
			this.currentBattle.announceFightWinCount();
			
			keepPlaying = keepPlaying();
		}
		
		Utility.printLargeBanner("Game Ending, Thank you for playing");
	}
	
	/**
	 * Method for determining if the player wants to keep playing and play
	 * another battle. It will valid the user's input to make sure it is
	 * correct input.
	 * @return true if the user wants to keep playing, false if the user
	 * wants to quit.
	 */
	public boolean keepPlaying()
	{
		String answer;
		boolean isAnswerValid;
		boolean keepPlaying;
		
		isAnswerValid = false;
		keepPlaying = false;
		
		answer = Utility.prompt("Would you like to start another battle? (Y/N)");

		while (!isAnswerValid)
		{
			if (answer.equals(YES_COMMAND) || answer.equals(NO_COMMAND))
			{
				isAnswerValid = true;
				
				if (answer.equals(YES_COMMAND))
				{
					keepPlaying = true;
				}
			}
			else
			{
				answer = Utility.prompt("Please type \\\"Y\\\" for yes or \\\"N\\\" for no.");
			}
		}
		return keepPlaying;
	}
}
