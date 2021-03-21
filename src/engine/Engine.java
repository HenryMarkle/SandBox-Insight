package engine;

// TODO Learn synchronization methods.
// TODO Learn multithreading programs.

import java.util.Scanner;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

public class Engine implements Runnable {
	
	private boolean allowCustomCommands = false;
	
	private DataBase data;
	
	private HashMap<String, Command> customCommands = new HashMap<String, Command>();
	
	private final Scanner scanner;
	
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
	
	// Find a way to pass by value
	public final void setDataBase(DataBase d)
	{
		if (d != null) data = d;
	}
	
	private boolean duplicateCommands()
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
	
	public void closeScanner()
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
			
			if (input == null || input.isEmpty() || input.isBlank()) continue;
			
			if (input.contentEquals("exit") || input.contentEquals("exit")) break;
			
			String[] line = input.split(" ");
			
			System.out.printf("\nCommand: \"%s\"\n", line[0]); // Debug
			
			// Identify command
			
			Command command;
			
			if (allowCustomCommands && customCommands.containsKey(line[0]))
			{
				command = customCommands.get(line[0]);
			}
			else if (StandardCommands.COMMANDS.containsKey(line[0]))
			{
				command = StandardCommands.COMMANDS.get(line[0]);
			}
			else
			{
				System.out.printf("\nUnknown command \"%s\".\n", line[0]);
				continue;
			}
			
			
			// Execute command
			
			boolean result = command.execute(line, data);
			System.out.printf("\n%s evaluated to %s.\n", command.name, result ? "true" : "false");  // Debug
			
			// Extra stuff then repeat
			System.out.println();
			continue;
		}
		while(!done);
		System.out.printf("\nEngine.start() has ended.\n");
	}
	
	public static void main(String[] args) throws Throwable
	{
	}
}
