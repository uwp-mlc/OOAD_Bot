package edu.dselent.customexceptions;

// Ended up not needing this
public class InvalidNumberOfPlayersException extends RuntimeException
{
    private int numberOfPlayers;

    public InvalidNumberOfPlayersException(String message, Throwable cause, int numberOfPlayers)
    {
        super(message, cause);
        this.numberOfPlayers = numberOfPlayers;
    }

    public InvalidNumberOfPlayersException(String message, int numberOfPlayers)
    {
        super(message);
        this.numberOfPlayers = numberOfPlayers;
    }

    public InvalidNumberOfPlayersException(int numberOfPlayers)
    {
        this.numberOfPlayers = numberOfPlayers;
    }

    public int getNumberOfPlayers()
    {
        return numberOfPlayers;
    }
}
