/**
 * 
 */
package edu.furbiesfighters.views;

import edu.furbiesfighters.gameplay.GUIReferee;
import edu.furbiesfighters.gameplay.Main;
import edu.furbiesfighters.gameplay.Referee;
import edu.furbiesfighters.presenters.MenuPresenter;
import edu.furbiesfighters.presenters.PostBattlePresenter;
import edu.furbiesfighters.presenters.PreBattlePresenter;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * Class for encapsulating the data related to the view.
 * @author Furbies Fighters
 */
public class PostBattleView extends AnchorPane implements Viewable{

	private PostBattlePresenter presenter;
	
	@FXML
	private TextArea txtAreaWinners;
	
	@FXML
	private TextArea txtAreaResults;
	
	@FXML
	private Button btnNewBattle;
	
	/**
	 * Method for initiailize the view.
	 */
	@FXML
	public void initialize()
	{
		//Initialize presenter, GUI elements if needed
		this.presenter = new PostBattlePresenter(this);
		
	}
	
	/**
	 * Method for setting up the view.
	 */
	@Override
	public void setUpView()
	{
		this.presenter.initializeView();
	}
	
	/**
	 * Method for setting the main application.
	 */
	@Override
	public void setMainApp(Main mainApp)
	{
		this.presenter.setMainApp(mainApp);
	}
	
	/**
	 * Method for setting the referee.
	 */
	@Override
	public void setReferee(Referee ref)
	{
		this.presenter.setRef(ref);
	}
	
	/**
	 * Method for getting the battle button click event.
	 */
	@FXML
	public void newBattleBtnClicked()
	{
		this.presenter.newBattleBtnClicked();
	}

	/**
	 * @return the presenter
	 */
	public PostBattlePresenter getPresenter() {
		return presenter;
	}

	/**
	 * @return the txtAreaWinners
	 */
	public TextArea getTxtAreaWinners() {
		return txtAreaWinners;
	}

	/**
	 * @return the txtAreaResults
	 */
	public TextArea getTxtAreaResults() {
		return txtAreaResults;
	}

	/**
	 * @return the btnNewBattle
	 */
	public Button getBtnNewBattle() {
		return btnNewBattle;
	}
}
