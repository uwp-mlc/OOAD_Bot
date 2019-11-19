package edu.furbiesfighters.views;

import edu.furbiesfighters.gameplay.Main;
import edu.furbiesfighters.gameplay.Referee;
import edu.furbiesfighters.players.PetTypes;
import edu.furbiesfighters.players.PlayerTypes;
import edu.furbiesfighters.presenters.MenuPresenter;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

/**
 * Class for encapsulating the data related to the MenuView.
 * @author Lucas Frey
 */
public class MenuView extends AnchorPane implements Viewable{
	
	private MenuPresenter presenter;
	@FXML
	private Button btnEnterFights;
	
	@FXML
	private Button btnAddPlayers;
	
	@FXML
	private TextField txtPetName;
	
	@FXML
	private TextField txtPlayerName;
	
	@FXML
	private TextField txtStartingHealth;
	
	@FXML
	private ChoiceBox choiceBoxPetType;
	
	@FXML
	private ChoiceBox choiceBoxPlayerType;
	
	@FXML
	private Label lblPlayerCount;
	
	/**
	 * Initialize is called when view is loaded into context.
	 */
	@FXML
	public void initialize()
	{
		this.presenter = new MenuPresenter(this);
		this.presenter.initializeView();
	}
	
	/**
	 * set the mainApp reference 
	 * @param mainApp
	 */
	@Override
	public void setMainApp(Main mainApp)
	{
		this.presenter.setMainApp(mainApp);
	}
	
	/**
	 * Handle the addPlayer button click event
	 */
	@FXML
	public void addPlayerClicked()
	{
		this.presenter.addPlayerClicked();
	}
	
	/**
	 * @return the presenter
	 */
	public MenuPresenter getPresenter() {
		return presenter;
	}

	/**
	 * @return the btnEnterFights
	 */
	public Button getBtnEnterFights() {
		return btnEnterFights;
	}

	/**
	 * @return the btnAddPlayers
	 */
	public Button getBtnAddPlayers() {
		return btnAddPlayers;
	}

	/**
	 * @return the txtPetName
	 */
	public TextField getTxtPetName() {
		return txtPetName;
	}

	/**
	 * @return the txtPlayerName
	 */
	public TextField getTxtPlayerName() {
		return txtPlayerName;
	}

	/**
	 * @return the txtStartingHealth
	 */
	public TextField getTxtStartingHealth() {
		return txtStartingHealth;
	}

	/**
	 * @return the choiceBoxPetType
	 */
	public ChoiceBox getChoiceBoxPetType() {
		return choiceBoxPetType;
	}

	/**
	 * @return the choiceBoxPlayerType
	 */
	public ChoiceBox getChoiceBoxPlayerType() {
		return choiceBoxPlayerType;
	}

	/**
	 * @return the lblPlayerCount
	 */
	public Label getLblPlayerCount() {
		return lblPlayerCount;
	}

	/**
	 * Handle the enterFights button click event
	 */
	@FXML
	public void enterFightsButtonClick()
	{
		this.presenter.changeViewToPreBattle();
	}

	/**
	 * Method for setting up the view.
	 */
	@Override
	public void setUpView() 
	{
		/* NO_OP for Menu-View */
	}

	/**
	 * Method for setting the referee.
	 */
	@Override
	public void setReferee(Referee ref) 
	{
		this.presenter.setRef(ref);
	}
}