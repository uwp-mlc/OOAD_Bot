/**
 * 
 */
package edu.furbiesfighters.presenters;



import java.io.IOException;
import java.util.List;

import edu.furbiesfighters.gameplay.Main;
import edu.furbiesfighters.gameplay.Referee;
import edu.furbiesfighters.players.Playable;
import edu.furbiesfighters.skills.Skills;
import edu.furbiesfighters.utility.Utility;
import edu.furbiesfighters.views.PostBattleView;
import edu.furbiesfighters.views.PreBattleView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Class for maintaining state related to the Post Battle View and
 * presenter logic.
 * @author Furbies Fighters
 *
 */
public class PostBattlePresenter implements Presentable{
	private Main mainApp;
	private PostBattleView view;
	private Referee ref;
	
	/**
	 * Constructor for the postbattle presenter.
	 * @param view
	 */
	public PostBattlePresenter(PostBattleView view)
	{
		this.view = view;
	}
	
	/**
	 * Method for setting the referee.
	 */
	@Override
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
		Utility.isGUI = false;
		List<String> winnerList = this.ref.getPlayableBattleWinList();
		List<String> fightList = this.ref.getPlayableFightWinList();
		String winners = "";
		String fights = "";
		
		for (int i = 0; i < winnerList.size(); i++)
		{
			winners += winnerList.get(i) + '\n';
		}
		
		for (int i = 0; i < fightList.size(); i++)
		{
			fights += fightList.get(i) + '\n';
		}
		
		this.view.getTxtAreaWinners().setText(winners);
		this.view.getTxtAreaResults().setText(fights);
	}
	
	/**
	 * Method for setting the main application.
	 */
	@Override
	public void setMainApp(Main mainApp)
	{
		this.mainApp = mainApp;
	}

	/**
	 * Method for handle the event related to the new battle button being
	 * clicked.
	 */
	public void newBattleBtnClicked() 
	{
		try {
			this.mainApp.showMenu();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
