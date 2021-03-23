package engine;

/*
	For every command to be executed in Engine, it must extend the abstract Command class
	and override the execute() function.
	
	The class has the follwing members:
		
		- Name: the name the command is identified with; used for matching.
		
		- Number of arguments: initially used to pre-verify that the player's
		command has enough arguments for the function to execute, not used, currently.
		
		- An array of strings to give a breif desription of every argument the function takes.
		Note: will be changed to an enum type.

	Next goal is to change String[] arguments member to an enum type.
	Can't implement the Runnable interface without ruining the execute() function.
*/

public abstract class Command {
	public final String name;			// The name of the command
	public final int argNum;			// How many arguments
	public final String[] arguments;	// Description of every argument
	
	public abstract boolean execute(String[] line, Modifyable data);
	
	protected Command(
			String name, 
			int argNum, 
			String[] arguments)
	{
		this.name = name;
		this.argNum = argNum;
		this.arguments = arguments;
	}
}
