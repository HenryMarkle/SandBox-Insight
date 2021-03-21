package engine;

public abstract class Command {
	public final String name;
	public final int argNum;
	public final String[] arguments;
	
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
