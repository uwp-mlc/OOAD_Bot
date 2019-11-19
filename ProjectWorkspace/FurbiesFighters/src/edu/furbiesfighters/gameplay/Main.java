package edu.furbiesfighters.gameplay;

import java.io.IOException;

import edu.furbiesfighters.players.AIPlayer;
import edu.furbiesfighters.players.Human;
import edu.furbiesfighters.players.JarvisPlayer;
import edu.furbiesfighters.players.PetTypes;
import edu.furbiesfighters.players.Playable;
import edu.furbiesfighters.players.PlayerTypes;

//import com.sun.xml.internal.bind.v2.runtime.unmarshaller.Loader;

import edu.furbiesfighters.presenters.MenuPresenter;
import edu.furbiesfighters.utility.Utility;
import edu.furbiesfighters.views.GamePlayView;
import edu.furbiesfighters.views.MenuView;
import edu.furbiesfighters.views.PostBattleView;
import edu.furbiesfighters.views.PreBattleView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import jneat.Neat;
import jneat.Population;
/**
 * Class for maintaining the application.
 * @author Furbies Fighters
 */
public class Main extends Application
{
	private Stage primaryStage;
	private BorderPane rootLayout;
	
	/**
	 * Main point of entry for the system. Plays the game.
	 * @param args
	 */
	public static void main(String[] args) 
	{
		Population neatPop = new Population(
				30 /* population size */, 
				9 /* network inputs */ , 
				2 /* network outputs */, 
				5 /* max index of nodes */, 
				true /* recurrent */, 
				0.5 /* probability of connecting two nodes */ );
		Utility.isGUI = false;
		String gamePlay = Utility.prompt("Type \"0\" for command line or \"1\" for GUI gameplay: ");
		if(gamePlay.contentEquals("0"))
		{
			BattleController battleController = new BattleController();
			battleController.play();
		}
		else if(gamePlay.contentEquals("1"))
		{
			//Probably create some UI game controller. 
			//Launch main menu window
			try{
				launch(args);
				Utility.isGUI = true;
			}catch(Exception e) {
				System.out.println("ERROR " + e.toString());
			}
			
		}
	}
	
	/**
	 * Called when "Launch(args)" is used. Sets up the stage and
	 * configures content to show on the stage. 
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		this.primaryStage = primaryStage;
		//this.primaryStage.setTitle("Enter Players");
		
		initRootLayout();
		
		showMenu();    //Comment out and uncomment test() to test the gameplay system. 
		//test();
	}
	
	/**
	 * Set the root layout to the BorderPane. The border pane makes it 
	 * easy to switch in and out panes from the center. 
	 * @throws IOException
	 */
	public void initRootLayout() throws IOException
	{
		String loadPath = "../views/RootLayout.fxml";
		
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(Main.class.getResource(loadPath));
		this.rootLayout = (BorderPane)fxmlLoader.load();
		
		Scene scene = new Scene(rootLayout);
		this.primaryStage.setScene(scene);
		this.primaryStage.show();
	}
	
	/**
	 * Show the menu view and set up the view logic.
	 * @throws IOException
	 */
	public void showMenu() throws IOException
	{
		String loadPath = "../views/MenuView.fxml";
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource(loadPath));
		
		AnchorPane menuView = (AnchorPane)loader.load();
		
		MenuView mp = (MenuView)loader.getController();
		mp.setMainApp(this);
		
		this.rootLayout.setCenter(menuView);
	}
	
	/**
	 * SetShows the preBattle view and transfers the referee. 
	 * @param ref
	 * @throws IOException
	 */
	public void showPreBattleView(Referee ref) throws IOException
	{
		String loadPath = "../views/PreBattleView.fxml";
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource(loadPath));
		
		AnchorPane menuView = (AnchorPane)loader.load();
		
		PreBattleView pbv = (PreBattleView)loader.getController();
		pbv.setMainApp(this);
		pbv.setReferee(ref);
		pbv.setUpView();
		
		this.rootLayout.setCenter(menuView);

		this.primaryStage.setTitle("Pre-Battle Information");
	}
	
	/**
	 * SetShows the gameplay view and transfers the referee. 
	 * @param ref
	 * @throws IOException
	 */
	public void showGamePlayView(Referee ref) throws IOException
	{
		String loadPath = "../views/GamePlayView.fxml";
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource(loadPath));
		
		AnchorPane anchor = (AnchorPane)loader.load();
		
		GamePlayView view = (GamePlayView)loader.getController();
		view.setMainApp(this);
		view.setReferee(ref);
		view.setUpView();
		
		this.rootLayout.setCenter(anchor);

		this.primaryStage.setTitle("Battle In Progress!");
	}
	
	/**
	 * SetShows the gameplay view and transfers the referee. 
	 * @param ref
	 * @throws IOException
	 */
	public void showPostBattleView(Referee ref) throws IOException
	{
		String loadPath = "../views/PostBattleView.fxml";
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource(loadPath));
		
		AnchorPane anchor = (AnchorPane)loader.load();
		
		PostBattleView view = (PostBattleView)loader.getController();
		view.setMainApp(this);
		view.setReferee(ref);
		view.setUpView();
		
		this.rootLayout.setCenter(anchor);

		this.primaryStage.setTitle("Battle Done");
		this.rootLayout.requestLayout();
	}
	
	
	/**
	 * Test stub for the program. Used to test with AI and Human players.
	 * @param ref
	 * @throws IOException
	 */
	public void test() throws IOException
	{
		Referee ref = new GUIReferee();
		Playable newPlayer = new JarvisPlayer(100, "NICK", "NICK PET", PetTypes.POWER);
		ref.addPlayer(newPlayer);
		Playable newPlayer3 = new AIPlayer(100, "Lucas", "Lucas PET", PetTypes.POWER);
		ref.addPlayer(newPlayer3);
		Playable newPlayer2 = new Human(100, "TEST", "TEST PET", PetTypes.POWER);
		ref.addPlayer(newPlayer2);
		ref.setFightCount(10);
		String loadPath = "../views/GamePlayView.fxml";
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource(loadPath));
		
		AnchorPane anchor = (AnchorPane)loader.load();
		
		GamePlayView view = (GamePlayView)loader.getController();
		view.setMainApp(this);
		view.setReferee(ref);
		view.setUpView();
		
		this.rootLayout.setCenter(anchor);

		this.primaryStage.setTitle("Battle In Progress!");
	}
}
