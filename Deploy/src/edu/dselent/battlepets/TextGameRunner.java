package edu.dselent.battlepets;

import java.util.List;

import edu.dselent.config.ApplicationConfigurations;
import edu.dselent.control.TextBattleControl;
import edu.dselent.control.GameSettingsControl;
import edu.dselent.control.PlayerSettingsControl;
import edu.dselent.io.TextInputGetter;
import edu.dselent.io.TextOutputSender;
import edu.dselent.player.Playable;
import edu.dselent.player.PlayableInstantiator;
import edu.dselent.io.Inputtable;
import edu.dselent.io.IoManager;
import edu.dselent.io.Outputtable;
import edu.dselent.settings.GameSettings;
import edu.dselent.settings.PlayerInfo;

public class TextGameRunner implements GameRunner
{
	@Override
	public void runGame()
	{
		Inputtable textInputtable = new TextInputGetter();
		Outputtable textOutputtable = new TextOutputSender();
		IoManager textIoManager = new IoManager(textInputtable, textOutputtable);

		ApplicationConfigurations.INSTANCE.setIoManager(textIoManager);
		
		GameSettingsControl gameSettingsControl = new GameSettingsControl();
		GameSettings gameSettings = gameSettingsControl.retrieveGameSettings();
	
		PlayerSettingsControl playerSettingsControl = new PlayerSettingsControl(gameSettings);
		List<PlayerInfo> playerInfoList = playerSettingsControl.retrievePlayerInfoList();


		// TODO add tournament mode
		List<Playable> playableList = PlayableInstantiator.instantiatePlayables(playerInfoList);
		TextBattleControl battleControl = new TextBattleControl(gameSettings, playableList);
		battleControl.runBattle();
	}
	
}
