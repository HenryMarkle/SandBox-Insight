namespace SandBox
{
    class Program
    {
        static void Main(string[] args)
        {
            Engine.Start ();

            System.Console.WriteLine ("Program has exited; Press any key to continue..");
            _ = System.Console.ReadLine ();
        }
    }
}
