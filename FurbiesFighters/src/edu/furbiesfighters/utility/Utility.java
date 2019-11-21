package edu.furbiesfighters.utility;
import java.util.Scanner;

import edu.furbiesfighters.gameplay.Referee;
import edu.furbiesfighters.presenters.GamePlayPresenter;

/**
 * General purpose utility class for input, input validation, and output
 * from the user. It is static. There is only one copy of the class needed.
 * @author furbiesFighters
 */
public class Utility 
{
	private static final String BANNER_BORDER = "|------------------------------------------------|";
	
	public static boolean isGUI = false;
	
	private static GamePlayPresenter presenter = null;
	
	private static Scanner stdin = new Scanner(System.in);
	
	private static Referee ref;
		
	/**
	 * Method for printing a large banner. It will print a small banner
	 * bordered by BANNER_BORDERs.
	 * @param message - The message to be put in the banner.
	 */
	public static void printLargeBanner(String message)
	{
		String convertedMessage;
		
		convertedMessage = convertStringToBanner(message);
		if(Utility.isGUI && presenter != null)
		{
			//Utility.gp.getTxtOutput().setText("add to the text");
			Utility.presenter.setConsoleText(convertedMessage + "\n");
		}
		else 
		{
			System.out.println();
			System.out.println(BANNER_BORDER);
			System.out.println(convertedMessage);
			System.out.println(BANNER_BORDER);
			System.out.println();
		}
	}
	
	/**
	 * Method for printing a small banner. It will only print a one line
	 * banner. The banner is not bordered by BANNER_BORDERs.
	 * @param message - The message to be put in the banner.
	 */
	public static void printSmallBanner(String message)
	{
		String convertedMessage;
		
		convertedMessage = convertStringToBanner(message);
		
		if(Utility.isGUI && presenter != null)
		{
			//Utility.gp.getTxtOutput().setText("add to the text");
			Utility.presenter.setConsoleText(convertedMessage + "\n");
		}
		else 
		{
			System.out.println();
			System.out.println(convertedMessage);
			System.out.println();
		}
	}
	
	/**
	 * Method for printing an endline character.
	 */
	public static void printEndline()
	{
		if(Utility.isGUI && presenter != null)
		{
			//Utility.gp.getTxtOutput().setText("add to the text");
			Utility.presenter.setConsoleText("\n");
		}
		else 
		{
			System.out.println();
		}
	}
	
	/**
	 * Method for printing a message followed by an endline character.
	 * @param message - The messsage to be printed.
	 */
	public static void printMessage(String message)
	{
		if(Utility.isGUI && presenter != null)
		{
			//Utility.gp.getTxtOutput().setText("add to the text");
			Utility.presenter.setConsoleText(message + "\n");
		}
		else 
		{
			System.out.println(message);
		}
	}
	
	/**
	 * Method for prompting the user to do something with a string prompt
	 * and returning the user's answer
	 * @param promptMessage	- The prompt action for the user.
	 * @return answer, the user's answer.
	 */
	public static String prompt(String promptMessage)
	{
		String answer;
		
		System.out.print(promptMessage + " ");
		
		answer = stdin.nextLine();
		
		return answer;
	}
	
	/**
	 * Method for checking to see if an integer is valid. It will compare
	 * to the minimumAmount passed to it.
	 * @param unformattedInteger - The unformatted integer.
	 * @param minimumAmount - The amount the unformatted integer is
	 * suppose to be compared to.
	 * @return isValid, if unformatted integer is valid, then true. False if 
	 * else.
	 */
	public static boolean isValidIntegerAmount(String unformattedInteger, int minimumAmount)
	{
		boolean isValid;
		int amount;
		
		isValid = false;
		
		try
		{
			amount = Integer.parseInt(unformattedInteger);
			
			if (amount >= minimumAmount)
			{
				isValid = true;
			}
		}
		catch (Exception e)
		{
			isValid = false;
		}
		
		return isValid;
	}
	
	/**
	 * Method for converting a string announcement to a small banner. It will
	 * Find the length of the string and center it in a banner
	 * @param string - The string to be centered in the banner.
	 * @return stringInBanner, a banner with the string in it.
	 */
	private static String convertStringToBanner(String string)
	{
		int bannerLength;
		int startIndex;
		String stringInBanner;
		
		bannerLength = BANNER_BORDER.length();
		startIndex = (bannerLength / 2) - (string.length() / 2);
		stringInBanner = "|";
		
		for (int i = 0; i < startIndex - 2; i++)
		{
			stringInBanner += "-";
		}
		
		stringInBanner += " " + string + " ";
		
		for (int i = 0; i < startIndex - 2; i++)
		{
			stringInBanner += "-";
		}
		stringInBanner += "|";
		
		return stringInBanner;
	}
	
	public static void setPresenter(GamePlayPresenter presenter)
	{
		Utility.presenter = presenter;
	}

}
