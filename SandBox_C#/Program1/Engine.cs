using System;
using System.Collections.Generic;
using System.Text.Json; /* For implementing save/load functionality */

namespace SandBox
{
    internal static class Engine
    {
        public static bool AllowCustomCommands { get; private set; }

        private static DataBase data = new DataBase ();

        internal static Dictionary <string, Func <string [], IModifyable, bool>> CustomCommands
            { get; set; } = new Dictionary <string, Func <string [], IModifyable, bool>> ();
        
        /* A way to take input */

        internal delegate string InputProvider ();
        internal static InputProvider GetInput { get; set; } = Console.ReadLine;

        /* The main function */

        internal static void Start ()
        {
            /* Initializing */

            StdCommands.Initialize ();

            /* Start the loop */
            
            bool done = false;

            do
            {
                /* Get input */

                string [] input = GetInput ().Split (" ");

                if (input [0] == "exit" || input [0] == "quit") break;

                /* Execute command */
                
                try 
                {
                  /*        bool             Dictionary     string      Func args */  
                  if (AllowCustomCommands) CustomCommands [input [0]] (input, data);

                  else StdCommands.CMD [input [0]] (input, data);
                }
                catch (KeyNotFoundException) { continue; }

            } while (!done);
        }
    }
}