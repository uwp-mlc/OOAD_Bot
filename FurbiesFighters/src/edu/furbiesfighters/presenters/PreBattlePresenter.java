/**
 * 
 */
package edu.furbiesfighters.presenters;



import java.io.IOException;
import java.util.List;

import edu.furbiesfighters.gameplay.Game;
import edu.furbiesfighters.gameplay.Referee;
import edu.furbiesfighters.players.Playable;
import edu.furbiesfighters.views.PreBattleView;
import javafx.collections.FXCollections;

/**
 * A class maintaining state related to the pre battle presenter.
 * @author Furbies Fighters
 *
 */
public class PreBattlePresenter implements Presentable{
	private Game mainApp;
	private PreBattleView view;
	private Referee ref;
	
	/**
	 * Constructor for the prebattle presenter.
	 * @param view
	 */
	public PreBattlePresenter(PreBattleView view)
	{
		this.view = view;
	}
	
	/**
	 * Method for setting the referee.
	 */
	public void setRef(Referee ref)
	{
		this.ref = ref;
	}
	
	/**
	 * Handle initialization of all view elements 
	 */
	@Override
	public void initializeView()
	{
		String playerList = "";
		List <Playable> list= this.ref.getAllPlayables();
		for(int i = 0; i < list.size(); i++)
		{
			 playerList += (i + 1) + ": "+ list.get(i).getPlayerName() + "\n";
		}
		this.view.getTxtPlayerList().setText(playerList);	
		this.view.getTxtPlayerList().setEditable(false);
	}
	
	/**
	 * Method for setting the main application.
	 */
	@Override
	public void setMainApp(Game mainApp)
	{
		this.mainApp = mainApp;
	}
	
	/**
	 * Starts the fight
	 */
	public boolean startButtonClicked()
	{
		try
		{
			int fightcount = Integer.parseInt(this.view.getTxtFightNumber().getText());
			if(startGame(fightcount))
			{
				this.ref.setFightCount(fightcount);
				this.view.getTxtFightNumber().setText("");
				return true;
			}
			else
				this.view.getTxtFightNumber().setText("");
		}
		catch(NumberFormatException e)
		{
			this.view.getTxtFightNumber().setText("");
		}
		
		return false;
	}
	
	/**
	 * Method for checking to see if the game should be started.
	 * @param fightNumber
	 * @return
	 */
	public boolean startGame(int fightNumber)
	{
		return fightNumber > 0;
	}
	
	/**
	 * Method for changing the view to the game play.
	 */
	public void changeViewToGamePlay()
	{
		try {
			this.mainApp.showGamePlayView(this.ref);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
