package engine;

/*
	Next goal is to change String[] arguments member to an enum type.
	Can't implement the Runnable interface without ruining the execute() function.
*/

public abstract class Command {
	public final String name;		// The name of the command
	public final int argNum;		// How many arguments
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
