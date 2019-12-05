package edu.dselent.io;

import java.io.InputStream;
import java.util.Scanner;

// Can make variants to read from different input streams and/or not use a scanner
public class TextInputGetter implements Inputtable
{
	private Scanner scanner;
	
	public TextInputGetter()
	{
		scanner = new Scanner(System.in);
	}
	
	public TextInputGetter(InputStream inputStream)
	{
		scanner = new Scanner(inputStream);
	}
	
	// Can add more methods as needed
	
	@Override
	public String getString()
	{
		return scanner.nextLine();
	}

	@Override
	public void close() throws Exception
	{		
		scanner.close();
	}

}
