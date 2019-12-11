package edu.dselent.battlepets;

public class GameMain
{
	private static void outputUsage()
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("Usage: [args...]");
		sb.append("\n");
		sb.append("Arguments include a single integer argument for which version of the game to run");
		sb.append("\n");
		sb.append("0: Text version");
		sb.append("\n");
		sb.append("1: UI version");
		sb.append("\n");
		
		System.out.println(sb.toString());
	}
	
	public static void main(String[] args)
	{
		// TODO have option to load default game settings from properties file?
				
		GameRunner gameRunner = null;
		
		// Default to text version if no program arguments are specified for easier use
		if(args.length < 1)
		{
			gameRunner = new TextGameRunner();
		}
		else
		{
			try
			{
				int gameChoice = Integer.parseInt(args[0]);
				
				if(gameChoice == 0)
				{
					gameRunner = new TextGameRunner();
				}
				else if(gameChoice == 1)
				{
					gameRunner = new UiGameRunner();
				}
				else
				{
					outputUsage();
				}
			}
			catch(NumberFormatException nfe)
			{
				outputUsage();
			}
		}
		
		if(gameRunner != null)
		{
			gameRunner.runGame();
		}
		
	}

}
