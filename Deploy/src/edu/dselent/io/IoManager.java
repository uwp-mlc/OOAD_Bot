package edu.dselent.io;

public class IoManager
{
	private Inputtable inputGetter;
	private Outputtable outputSender;
	
	public IoManager(Inputtable inputGetter, Outputtable outputSender)
	{
		this.inputGetter = inputGetter;
		this.outputSender = outputSender;
	}

	public Inputtable getInputGetter()
	{
		return inputGetter;
	}

	public Outputtable getOutputSender()
	{
		return outputSender;
	}
	
	public void closeInput()
	{
		try
		{
			inputGetter.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void closeOutput()
	{
		try
		{
			outputSender.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
