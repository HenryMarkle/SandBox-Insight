using System.Collections.Generic;
using System.Collections.Immutable;
using SBLib;
using System;

namespace SandBox
{
    public static class StdCommands
    {
        internal static bool Initialized { get; private set; } = false;

        internal static ImmutableDictionary <string, Func <string [], DataBase, bool>> CMD 
        { get; private set; }

        /* Standard Commands */
        /* Note: Will implement proper commands soon. */

        private static bool Check (string [] line, DataBase data)
        {
            return false;
        }

        /* Initializer */

        internal static void Initialize ()
        {
            if (!Initialized)
            {
                Dictionary <string, Func <string [], DataBase, bool>> commands =
                new Dictionary <string, Func <string [], DataBase, bool>> ();

                commands.Add ("check", Check);

                CMD = commands.ToImmutableDictionary ();

                Initialized = true;
            }
        }
    }
}
