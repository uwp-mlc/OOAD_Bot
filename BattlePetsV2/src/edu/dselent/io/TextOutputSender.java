package edu.dselent.io;

public class TextOutputSender implements Outputtable
{
	@Override
	public void outputString(String outputString)
	{
		//System.out.println(outputString);
	}

	@Override
	public void close() throws Exception
	{
		// No stream to close for this implementation
	}

}
