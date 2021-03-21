package engine;

public class Program {
	public static void main(String[] args) throws InterruptedException
	{
		Engine engine = new Engine();
		Thread engineThread = new Thread(engine);
		
		try
		{
			engineThread.start();
			engineThread.join();
		}
		catch (InterruptedException i)
		{
			System.out.print("\nMain thread interrupted.\n");
		}
		finally
		{
			System.out.print("\nMain program hs ended.\n");
		}
	}
}
