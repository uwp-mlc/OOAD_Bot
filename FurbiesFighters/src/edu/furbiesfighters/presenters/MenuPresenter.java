package edu.furbiesfighters.presenters;

import java.io.IOException;

import edu.furbiesfighters.gameplay.GUIReferee;
import edu.furbiesfighters.gameplay.Game;
import edu.furbiesfighters.gameplay.Referee;
import edu.furbiesfighters.players.AIPlayer;
import edu.furbiesfighters.players.Human;
import edu.furbiesfighters.players.JarvisPlayer;
import edu.furbiesfighters.players.PetTypes;
import edu.furbiesfighters.players.Playable;
import edu.furbiesfighters.players.PlayerTypes;
import edu.furbiesfighters.views.MenuView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;

/**
 * Class for maintaining state related to the menu.
 * @author Lucas Frey
 */
public class MenuPresenter implements Presentable{
	private Referee ref;
	private Game mainApp;
	private MenuView view;
	
	/**
	 * Constructor for the menu presenter
	 * @param view
	 */
	public MenuPresenter(MenuView view) 
	{
		this.ref = new GUIReferee();
		this.view = view;
	}
	
	/**
	 * Handle initialization of all view elements
	 * and populate list elements. 
	 */
	@Override
	public void initializeView()
	{
		this.view.getChoiceBoxPlayerType().setItems(FXCollections.observableArrayList("Human","AI","Jarvis"));
		this.view.getChoiceBoxPlayerType().getSelectionModel().selectFirst();
		this.view.getChoiceBoxPetType().setItems(FXCollections.observableArrayList("Power","Intelligence","Speed"));
		this.view.getChoiceBoxPetType().getSelectionModel().selectFirst();
		this.view.getBtnEnterFights().setDisable(true);
		
		AIPlayer.resetAICount();
		
		this.view.getChoiceBoxPlayerType().valueProperty().addListener(new ChangeListener<String>() {
	        @Override public void changed(ObservableValue ov, String t, String t1) {
	            playerTypeChanged(t1);
	          }    
	      });
	}
	
	/**
	 * Method to handle the event that the player type was changed.
	 * If it is an AI, it will set the name of the player.
	 * @param s
	 */
	private void playerTypeChanged(String s)
	{
		if(s != null && s.equals("AI"))
		{
			this.view.getTxtPlayerName().setText("AI name automatically generated!");
			this.view.getTxtPlayerName().setEditable(false);
			this.view.getChoiceBoxPetType().setDisable(false);
		}
		else if(s != null && s.equals("Jarvis"))
		{
			this.view.getTxtPlayerName().setText("Jarvis");
			this.view.getTxtPlayerName().setEditable(false);
			this.view.getChoiceBoxPetType().getSelectionModel().select(1);
			this.view.getChoiceBoxPetType().setDisable(true);
		}
		else
		{
			this.view.getTxtPlayerName().setEditable(true);
			this.view.getChoiceBoxPetType().setDisable(false);
		}
			
	}
	
	/**
	 * Set the reference to the main app to be able to switch views
	 */
	@Override
	public void setMainApp(Game mainApp)
	{
		this.mainApp = mainApp;
	}
	
	/**
	 * Change the context to the next window.
	 */
	public void changeViewToPreBattle()
	{
		try {
			this.mainApp.showPreBattleView(this.ref);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Add a player to the list in the GUIReferee. 
	 * @param isHuman
	 * @param playerName
	 * @param petName
	 * @param startingHealth
	 * @param petType
	 * @return
	 */
	public boolean addPlayer(boolean isHuman, String playerName, String petName, 
			double startingHealth, PetTypes petType)
	{
		if(playerName.equals("") || petName.equals("") || startingHealth == 0.0)
			return false;
		
		Playable newPlayer;
		if(this.view.getChoiceBoxPlayerType().getSelectionModel().getSelectedIndex() == 2)
			newPlayer = new JarvisPlayer(startingHealth, playerName, petName, petType);
		else if(this.view.getChoiceBoxPlayerType().getSelectionModel().getSelectedIndex() == 1)
			newPlayer = new AIPlayer(startingHealth, playerName, petName, petType);
		else
			newPlayer = new Human(startingHealth, playerName, petName, petType);
		
		this.ref.addPlayer(newPlayer);
		return true;
	}
	
	/**
	 * Handle presentation logic for the addPlayerClicked event. 
	 */
	public void addPlayerClicked()
	{
		PlayerTypes playerType = PlayerTypes.HUMAN;
		if(this.view.getChoiceBoxPlayerType().getSelectionModel().getSelectedIndex() == 1)
			playerType = PlayerTypes.AI;
		PetTypes petType = PetTypes.POWER;
		if(this.view.getChoiceBoxPlayerType().getSelectionModel().getSelectedIndex() == 1)
			petType = PetTypes.INTELLIGENCE;
		if(this.view.getChoiceBoxPlayerType().getSelectionModel().getSelectedIndex() == 1)
			petType = PetTypes.SPEED;
		
		try
		{
			Double.parseDouble(this.view.getTxtStartingHealth().getText());
			if(addPlayer(playerType.equals(PlayerTypes.HUMAN), this.view.getTxtPlayerName().getText().toLowerCase(), 
					this.view.getTxtPetName().getText().toString(), Double.parseDouble(this.view.getTxtStartingHealth().getText()), petType))
			{
				this.view.getTxtPetName().setText("");
				this.view.getTxtPlayerName().setText("");
				this.view.getTxtStartingHealth().setText("");
				this.view.getChoiceBoxPlayerType().getSelectionModel().selectFirst();
				this.view.getChoiceBoxPetType().getSelectionModel().selectFirst();
			}
		}
		catch(NumberFormatException e)
		{
			this.view.getTxtStartingHealth().setText("");
		}
		
		this.view.getLblPlayerCount().setText("Player Count: " + getPlayerCount());

		if(this.ref.getAllPlayables().size() >= 2)
			this.view.getBtnEnterFights().setDisable(false);
	}
	
	/**
	 * Return the referee reference. 
	 * @return
	 */
	public Referee getRef()
	{
		return this.ref;
	}
	
	/**
	 * Calculate how many players are in the game
	 * @return
	 */
	public int getPlayerCount()
	{
		return this.ref.getAllPlayables().size();
	}

	/**
	 * Method for setting the referee.
	 */
	@Override
	public void setRef(Referee ref) {
		this.ref = ref;
	}
}
