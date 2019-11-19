package edu.furbiesfighters.presenters;

import java.io.IOException;
import java.util.List;

import edu.furbiesfighters.gameplay.GUIReferee;
import edu.furbiesfighters.gameplay.Main;
import edu.furbiesfighters.gameplay.Referee;
import edu.furbiesfighters.players.Playable;
import edu.furbiesfighters.skills.Skills;
import edu.furbiesfighters.utility.Utility;
import edu.furbiesfighters.views.GamePlayView;
import edu.furbiesfighters.views.MenuView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Class for maintaining state related the presenting the GamePlayView.
 * @author Lucas Frey
 *
 */
public class GamePlayPresenter implements Presentable{
	private Referee ref;
	private Main mainApp;
	private GamePlayView view;
	
	/**
	 * Constructor for the GamePlayPresenter
	 * @param view
	 */
	public GamePlayPresenter(GamePlayView view)
	{
		this.view = view;
	}
	
	/**
	 * Initiaizes the view.
	 */
	@Override
	public void initializeView()
	{
		Utility.setPresenter(this);
		Utility.isGUI = true;
		this.ref.handleFightStartEvent();

		this.view.getChoiceBoxShootTheMoon().setItems(FXCollections
				.observableArrayList("rock throw","scissor poke", "paper cut", "shoot the moon", "reversal of fortune"));
		this.view.getChoiceBoxShootTheMoon().setVisible(false);
		this.view.getChoiceBoxSkill().valueProperty().addListener(new ChangeListener<String>() {
	        @Override public void changed(ObservableValue ov, String t, String t1) {
	            skillChoiceChanged(t1);
	          }    
	      });
		this.ref.resetFightWinCount();
		this.view.getChoiceBoxSkill().getSelectionModel().selectFirst();
		this.view.gettxtFieldName().setEditable(false);
		this.view.gettxtFieldName().setText(this.ref.getNextPlayerInfo().getPlayerName());
		
		this.ref.setUpGUI(this);
		this.view.getLblFightNumber().setText("Fight: 1");
		this.ref.play();
		
		if(!this.ref.hasWinner())
			this.resetForNextPlayer();
	}
	
	/**
	 * Event for the skillchoice change. It will take a string then assign the
	 * skill.
	 * @param value the string skill
	 */
	public void skillChoiceChanged(String value)
	{
		if(value != null && value.equals("shoot the moon"))
		{
			this.view.getChoiceBoxShootTheMoon().setVisible(true);
			this.view.getChoiceBoxShootTheMoon().getSelectionModel().selectFirst();
		}
		else
		{
			this.view.getChoiceBoxShootTheMoon().setVisible(false);
		}
	}
	
	/**
	 * Add text to the console window
	 * @param message
	 */
	public void setConsoleText(String message)
	{
		this.view.getTxtArea().setText(this.view.getTxtArea().getText() + message);
		this.view.getTxtArea().setScrollTop(Double.MAX_VALUE);
	}
	
	/**
	 * Show the post battle scene
	 */
	public void showPostBattle()
	{
		try {
			this.mainApp.showPostBattleView(this.ref);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Sets main app reference to facilitate context switching.
	 * @param mainApp
	 */
	@Override
	public void setMainApp(Main mainApp)
	{
		this.mainApp = mainApp;
	}
	
	/**
	 * Sets the referee.
	 * @param ref
	 */
	@Override
	public void setRef(Referee ref)
	{
		this.ref = ref;
	}
	
	/**
	 * Returns the view
	 * @return
	 */
	public GamePlayView getView()
	{
		return this.view;
	}
	
	/**
	 * Method for setting the number of fights.
	 * @param num
	 */
	public void setFightNumber(int num)
	{
		this.view.getLblFightNumber().setText("Fight: " + num);
	}

	/**
	 * Reset the presentation logic for the next player by changing the name
	 * and updating the skill choice list.
	 */
	public void resetForNextPlayer()
	{
		List<Skills> skillList = ref.getCurrentPlayableSkillStringList();
		ObservableList<String> oList = FXCollections.observableArrayList();
		
		for(Skills s : skillList)
			oList.add(s.toString());
		
		this.view.getChoiceBoxSkill().setItems(oList);
		if(!this.ref.hasWinner())
		{
			this.view.gettxtFieldName().setText(this.ref.getNextPlayerInfo().getPlayerName());
			this.view.getChoiceBoxSkill().getSelectionModel().selectFirst();
		}
		
		this.view.getLblFightNumber().setText("Fight: " + this.ref.getCurrentFightNumber());
	}
	
	/**
	 * Click event presentation logic.
	 */
	public void confirmSkillClicked()
	{
		ref.addChosenSkillToCurrentPlayable(this.view.getChoiceBoxSkill().getSelectionModel().getSelectedItem().toString());
		Utility.printMessage(this.view.gettxtFieldName().getText() + " has chosen a skill.");
		ref.play(this.view.gettxtFieldName().getText(), this.view.getChoiceBoxSkill().getSelectionModel().getSelectedItem().toString());
		
		this.resetForNextPlayer();
	}
}
