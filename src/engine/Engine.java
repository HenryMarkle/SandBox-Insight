package engine;

// TODO Learn synchronization methods.
// TODO Learn multithreading programs.

/*
	Can't decide whether I want to make Engine static or not..
	
	Engine needs to me designed to run on a separate thread to 
	implement a functioning GUI.
*/

import java.util.Scanner;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

public class Engine implements Runnable {
	
	private boolean allowCustomCommands = false;
	
	private DataBase data;	// PLaning on paramatizing this.
	
	private HashMap<String, Command> customCommands = new HashMap<String, Command>();
	
	private final Scanner scanner;	// Instantiating more than one Engine would be problematic because of this.
	
	// Constructors
	
	public Engine()
	{
		this.allowCustomCommands = false;
		this.data = new DataBase();
		this.scanner = new Scanner(System.in);
	}
	
	public Engine(boolean allowCustomCommands)
	{
		this.allowCustomCommands = allowCustomCommands;
		this.data = new DataBase();
		this.scanner = new Scanner(System.in);
	}
	
	public Engine(boolean allowCustomCommands, DataBase data)
	{
		this.allowCustomCommands = allowCustomCommands;
		this.data = data;
		this.scanner = new Scanner(System.in);
	}
	
	public Engine(boolean allowCustomCommands, DataBase data, Set<Command> customCommands)
	{
		this.allowCustomCommands = allowCustomCommands;
		this.data = data;
		customCommands.forEach(s -> this.customCommands.put(s.name, s));
		this.scanner = new Scanner(System.in);
	}
	
	public Engine(boolean allowCustomCommands, DataBase data, Set<Command> customCommands, Scanner scanner)
	{
		this.allowCustomCommands = allowCustomCommands;
		this.data = data;
		customCommands.forEach(s -> this.customCommands.put(s.name, s));
		this.scanner = scanner;
	}
	
	// Methods
	
	public final void setCustomCommands(Set<Command> newCustomCommands)
	{
		customCommands.clear();
		newCustomCommands.forEach(s -> customCommands.put(s.name, s));
	}
	

	public final void setDataBase(DataBase d)	// Find a way to pass by value
	{
		if (d != null) data = d;
	}
	
	private boolean duplicateCommands()	// Fix ugly code..
	{
		if (customCommands.size() > 0)
		{
			Set<String> names = new HashSet<String>();
			
			for (Command c : customCommands.values())
			{
				if (names.contains(c.name)) return true;
				
				names.add(c.name);
				continue;
			}
		}
		return false;
	}
	
	public void allowCustomCommands()
	{
		this.allowCustomCommands = true;
	}
	
	public void closeScanner()	// Once closed, other instances would have closed input stream. 
	{
		scanner.close();
	}
	
	@Override
	public void run()
	{
		// Look for duplicate commands first
		if (duplicateCommands()) allowCustomCommands = false;
		
		// Initialize commands
		StandardCommands.init();
		
		boolean done = false;
		
		// Start the loop
		do
		{
			// Take input
			
			System.out.print("Console Command: ");
			String input = scanner.nextLine();
			
			if (input == null || input.isEmpty() || input.isBlank()) continue;	// May not be necessary..
			
			if (input.contentEquals("exit") || input.contentEquals("exit")) break;	// A way to exit the loop.
			
			String[] line = input.split(" ");	// Make this variable re-usable rather than re-instantiating it again and again.
			
			System.out.printf("\nCommand: \"%s\"\n", line[0]);	// Debug
			
			// Identify the command
			
			Command command;
			
				// Look in custom commands first.
			
			if (allowCustomCommands && customCommands.containsKey(line[0]))
				command = customCommands.get(line[0]);
			
				// Then look in the standard commands.
			
			else if (StandardCommands.COMMANDS.containsKey(line[0])) 
				command = StandardCommands.COMMANDS.get(line[0]);
			
				// If non-existent, declare that it is not.
			
			else
			{
				System.out.printf("\nUnknown command \"%s\".\n", line[0]);
				continue;
			}
			
			
			// Execute the command
			
			boolean result = command.execute(line, data);
			System.out.printf("\n%s evaluated to %s.\n", command.name, result ? "true" : "false");  // Debug
			
			// Extra stuff then repeat
			
			System.out.println();
			continue;
			
		} while(!done);
		
		System.out.printf("\nEngine.start() has ended.\n");
	}
	
	public static void main(String[] args) throws Throwable
	{
		StandardCommands.init();  // Must run this function first, or standard commands will not load.
		
		try {
			Engine engine = new Engine();
			engine.run();
		}
		catch (Exception e) {
			System.out.println("Program failed to run.");
		}
	}
}
