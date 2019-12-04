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
	
	int neat_count;
	int opponent_count;
	int human_count;
	
	List<String> neat_names;
	List<String> opponent_names;
	List<String> human_names;
	
	int fight_amount;
	
	List<Integer> neat_types;
	List<Integer> opponent_types;
	List<Integer> human_types;
	
	GameSettings gs;
	
	public BattleController(GameSettings gs) {
		this.currentBattle = new Battle(gs);
		this.gs = gs;
		this.play(gs);
	}
	
	/**
	 * Constructor for Battle. It instantiates the currentBattle and input.
	 */
	public BattleController(int neat_count, int opponent_count, int human_count, 
			List<String> neat_names, List<String> opponent_names, List<String> human_names, int fight_amount,
			List<Integer> neat_types, List<Integer> opponent_types, List<Integer> human_types)
	{
		this.neat_count = neat_count;
		this.opponent_count = opponent_count;
		this.human_count = human_count;
		this.neat_names = neat_names;
		this.opponent_names = opponent_names;
		this.human_names = human_names;
		this.fight_amount = fight_amount;
		this.human_types = human_types;
		this.opponent_types = opponent_types;
		this.neat_types = neat_types;
		
		this.currentBattle = new Battle(neat_count, opponent_count, human_count, neat_names, 
				opponent_names, human_names, fight_amount, neat_types, opponent_types, human_types);
		//this.currentBattle = new Battle();
	}
	
	/**
	 * Method for playing each battle. It will loop and let the player
	 * play as many battles as they would like. It will announce each
	 * battle and announce the end of the game.
	 */
	public void play(GameSettings gs)
	{
		boolean keepPlaying;
		
		keepPlaying = true;
		
		while (keepPlaying)
		{
			Utility.printLargeBanner("New Battle Beginning");
			
			this.ref = new Referee();
			
			currentBattle.setUpBattle();
			currentBattle.playBattle();
			
			String winningString = "The following player(s) won the battle: ";
			
			List<Playable> winners = currentBattle.getBattleWinner();
			for(Playable p : winners)
				winningString += p.getPlayerName() + ", ";
			
			Playable aiPlayer = this.getAiFromName(this.currentBattle.ref.getAllPlayables(), this.gs.ai_name);
			Playable opponentPlayer = this.getOpponent(this.currentBattle.ref.getAllPlayables(), this.gs.ai_name);
			
			double fitness = aiPlayer.getCurrentHp() - opponentPlayer.getCurrentHp();
			this.gs.setFitness(fitness);
			
			Utility.printEndline();
			Utility.printMessage(winningString.substring(0, winningString.length()-2));
			this.currentBattle.announceFightWinCount();
			
			keepPlaying = false; //keepPlaying();
			break;
		}
		
		Utility.printLargeBanner("Game Ending, Thank you for playing");
	}
	
	public Playable getAiFromName(List<Playable> players, String name) {
		for(Playable p : players) {
			if(p.getPetName().equals(name)) {
				return p;
			}
		}
		System.out.println("Bad error here.. AI not found (Stack trace to BattleController)");
		return null;
	}
	
	public Playable getOpponent(List<Playable> players, String name) {
		for(Playable p : players) {
			if(! p.getPetName().equals(name)) {
				return p;
			}
		}
		System.out.println("Bad error here.. Opponent not found (Stack trace to BattleController)");
		return null;
	}
	
	/**
	 * Method for playing each battle. It will loop and let the player
	 * play as many battles as they would like. It will announce each
	 * battle and announce the end of the game.
	 */
	public void play()
	{
		boolean keepPlaying;
		
		keepPlaying = false;//true;
		
		while (keepPlaying)
		{
			Utility.printLargeBanner("New Battle Beginning");
			
			this.ref = new Referee();
			//currentBattle = new Battle();
			currentBattle.setUpBattle();
			currentBattle.playBattle();
			
			String winningString = "The following player(s) won the battle: ";
			
			List<Playable> winners = currentBattle.getBattleWinner();
			for(Playable p : winners) {
				winningString += p.getPlayerName() + ", ";
			}
			
			Utility.printEndline();
			Utility.printMessage(winningString.substring(0, winningString.length()-2));
			this.currentBattle.announceFightWinCount();
			
			keepPlaying = false;//keepPlaying();
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
