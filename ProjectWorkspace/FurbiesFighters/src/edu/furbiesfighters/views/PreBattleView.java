/**
 * 
 */
package edu.furbiesfighters.views;

import edu.furbiesfighters.gameplay.GUIReferee;
import edu.furbiesfighters.gameplay.Main;
import edu.furbiesfighters.gameplay.Referee;
import edu.furbiesfighters.presenters.MenuPresenter;
import edu.furbiesfighters.presenters.PreBattlePresenter;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * Class for encapsulating the data related to the PreBattleView.
 * @author Furbies Fighters
 *
 */
public class PreBattleView extends AnchorPane implements Viewable{

	private PreBattlePresenter presenter;
	
	@FXML
	private TextField txtFightNumber;
	
	@FXML
	private TextArea txtPlayerList;
	
	@FXML
	private Button btnStart;
	
	@FXML
	public void initialize()
	{
		//Initialize presenter, GUI elements if needed
		this.presenter = new PreBattlePresenter(this);
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
	 * Method for setting up the main application.
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
	
	//All events here, below is an example one you may want
	
	/**
	 * @return the number of fights
	 */
	public TextField getTxtFightNumber() 
	{
		return txtFightNumber;
	}
	
	/**
	 * @return the the list of players
	 */
	public TextArea getTxtPlayerList() {
		return txtPlayerList;
	}
	
	/**
	 * Method handling the start button clicked event.
	 */
	@FXML
	public void startButtonClicked()
	{
		if(this.presenter.startButtonClicked())
			this.presenter.changeViewToGamePlay();
	}
}
