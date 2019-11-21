/**
 * 
 */
package edu.furbiesfighters.views;

import edu.furbiesfighters.gameplay.Game;
import edu.furbiesfighters.gameplay.Referee;
import edu.furbiesfighters.presenters.GamePlayPresenter;
import edu.furbiesfighters.presenters.MenuPresenter;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * Class for encapsulating the data related to the GamePlayView.
 * @author Furbies Fighters. 
 *
 */
public class GamePlayView extends AnchorPane implements Viewable{
	private GamePlayPresenter presenter;
	
	@FXML
	private Label labelPlayerName;
	
	@FXML
	private TextField txtFieldName;
	
	@FXML
	private TextArea txtArea;	
	
	@FXML
	private ChoiceBox choiceBoxSkill;
	
	@FXML
	private Button buttonConfirmSkill;

	@FXML
	private Label lblFightNumber;
	
	@FXML
	private ChoiceBox choiceBoxShootTheMoon;
	
	/**
	 * Initialize is called when view is loaded into context.
	 */
	@FXML
	public void initialize()
	{
		this.presenter = new GamePlayPresenter(this);
	}
	
	/**
	 * Method for setting up a view.
	 */
	@Override
	public void setUpView()
	{
		this.presenter.initializeView();
	}
	
	/**
	 * set the mainApp reference 
	 * @param mainApp
	 */
	@Override
	public void setMainApp(Game mainApp)
	{
		this.presenter.setMainApp(mainApp);
	}
	
	/**
	 * Method for setting ther referee.
	 */
	@Override
	public void setReferee(Referee ref)
	{
		this.presenter.setRef(ref);
	}
	
	/**
	 * Method for getting the label name.
	 * @return
	 */
	public Label getLabelName()
	{
		return labelPlayerName;
	}
	
	/**
	 * Method for getting the text field name.
	 * @return
	 */
	public TextField gettxtFieldName()
	{
		return txtFieldName;
	}
	
	/**
	 * Method for getting the choice box skill.
	 * @return
	 */
	public ChoiceBox getChoiceBoxSkill()
	{
		return choiceBoxSkill;
	}
	
	/**
	 * Method for getting the confirm button.
	 * @return
	 */
	public Button getButtonConfirmSkill()
	{
		return buttonConfirmSkill;
	}
	
	/**
	 * @return the presenter
	 */
	public GamePlayPresenter getPresenter() {
		return presenter;
	}
	
	/**
	 * @return the choiceBoxShootTheMoon
	 */
	public ChoiceBox getChoiceBoxShootTheMoon() {
		return choiceBoxShootTheMoon;
	}

	/**
	 * Handle the buttonConfirmSkill button click event
	 */
	@FXML
	public void buttonConfirmSkillClicked()
	{
		this.presenter.confirmSkillClicked();
	}

	/**
	 * @return the txtArea
	 */
	public TextArea getTxtArea() {
		return txtArea;
	}
	
	/**
	 * Method for getting the fight number label.
	 * @return
	 */
	public Label getLblFightNumber()
	{
		return this.lblFightNumber;
	}
}
