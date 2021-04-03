using System.Collections.Generic;
using System.Collections.Immutable;
using SBLib;
using System;

namespace SandBox
{
    public static class StdCommands
    {
        internal static ImmutableDictionary <string, Engine.Executor> CMD 
        { get; private set; }

        /* Initializer */

        static StdCommands ()
        {
            var names = new string []
            {
                "check", "enter", "use",
                "store", "inventory",
                "create-house", "create-space", "create-item",
                "delete-house", "delete-space", "delete-item"
            };

            var cmds = new Engine.Executor []
            {
                Check, Enter, Use,
                Store, Inventory,
                CreateHouse, CreateSpace, CreateItem,
                DeleteHouse, DeleteSpace, DeleteItem
            };

            var commands = new Dictionary <string, Engine.Executor> ();

            if (names.Length != cmds.Length) 
                throw new Exception ("StdCommands: Failed to load Standard commands.");
            else
            {
                for (int i = 0; i < names.Length; i++) 
                    commands.Add (names [i], cmds [i]);
            }

            CMD = commands.ToImmutableDictionary ();
        }

        /* Standard Commands */

        private static bool Check       (DataBase data, params string [] line)
        {
            /*
            
                Only 3 possible paths:

                0 args:
                or
                1 args: <Space name>
                or
                2 args: <House name> <Space name>
            
            */
            
            try
            {
                switch (line.Length)
                {
                    case 1:
                        Console.WriteLine (data.LSpace.Items ());
                        return true;
                    case 2:
                        Console.WriteLine (data.LHouse.Get (line [1]).Items ());
                        return true;
                    case 3:
                        Console.WriteLine (data.Houses [line [1]].Get (line [2]).Items ());
                        return true;
                    default:
                        return false;
                }
            }
            catch ( NullReferenceException ) { return false; }
            catch ( KeyNotFoundException   ) { return false; }
        }

        private static bool Enter       (DataBase data, params string [] line)
        {
            /*

                Only 3 possible paths:
            
                1 args: <House> <House name>
                or
                2 args: <Space> <House name> <Space name>
                or
                3 args: <Space> <Space name> (Within current house)

            */
            
            /* Check if this throws an exception or not. */
            try
            {
                switch (line [1])
                {
                    case "house":
                        data.LHouse = data.Houses [line [2]]    ?? throw new NullReferenceException ();
                        data.LSpace = data.DSpace;
                        return true;

                    case "space":
                        if (line.Length > 3)
                        {
                            House h = data.Houses [line [2]]    ?? throw new NullReferenceException ();
                            Space s = h[line [3]]               ?? throw new NullReferenceException ();
                            data.LSpace = s;
                            return true;
                        }
                        else if (line.Length > 2)
                        {
                            Space ss = data.LHouse [line [2]]   ?? throw new NullReferenceException ();
                            data.LSpace = ss;
                            return true;
                        }
                        return false;

                    default:
                    return false;
                }
            }
            catch (IndexOutOfRangeException)    { return false; }
            catch (KeyNotFoundException)        { return false; }
            catch (NullReferenceException)      { return false; }
        }

        private static bool Use         (DataBase data, params string [] line)
        {
            try { return data.Inventory.Remove (line [1]); }
            catch ( IndexOutOfRangeException )    { return false; }
        }

        private static bool Store       (DataBase data, params string [] line)
        {
            /* Can only pick up items from current space. */

            try
            {
                Item i = data.LSpace.Pop (line [1]);
                data.Inventory.Add (i.Name, i);
                return false;
            }
            catch (IndexOutOfRangeException)    { return false; }
            catch (NullReferenceException)      { return false; }
        }

        private static bool CreateHouse (DataBase data, params string [] line)
        {
            try
            {
                return data.Add <House> (line [1]);
            }
            catch (IndexOutOfRangeException)    { return false; }
        }

        private static bool CreateSpace (DataBase data, params string [] line) =>
        
            /*
            
                Only 2 possible paths:

                1 args: <Space name>
                or
                2 args: <House name> <Space name>

            */
            
            line.Length switch
            {
                2 => data.Add <Space> (data.LHouse.Name, line [1]),
                3 => data.Add <Space> (line [1], line [2]),
                _ => false
            };

        private static bool CreateItem  (DataBase data, params string [] line) =>
            
            /*
            
                Only 3 possible paths:

                1 args: <Item name>
                or
                2 args: <Space name> <Item name>
                or
                3 args: <House name> <Space name> <Item name>
            
            */

            line.Length switch
            {
                2 => data.Add <Item> (data.LHouse.Name, data.LSpace.Name, line [1]),
                3 => data.Add <Item> (data.LHouse.Name, line [1], line [2]),
                4 => data.Add <Item> (line [1], line [2], line [3]),
                _ => false
            };

        private static bool DeleteHouse (DataBase data, params string [] line)
        {
            try
            {
                return data.Remove <House> (line [1]);
            }
            catch (IndexOutOfRangeException) { return false; }
        }

        private static bool DeleteSpace(DataBase data, params string [] line) =>
            
            /* 
            
                Only 2 possible paths:

                1 args:     <space name>
                or
                2 args:     <house name> <space name>

            */

            line.Length switch
            {
                2 => data.Remove <Space> (data.LHouse.Name, line [1]),
                3 => data.Remove <Space> (line [1], line [2]),
                _ => false
            };

        private static bool DeleteItem (DataBase data, params string [] line) =>
            
            /* Only 3 possible paths:

                1 args:    <item name>
                or
                2 args:    <space name> <item name>
                or
                3 args:    <house name> <space name> <item name>

            */

            line.Length switch
            {
                2 => data.Remove <Item> (data.LHouse.Name, data.LSpace.Name, line [1]),
                3 => data.Remove <Item> (data.LHouse.Name, line [1], line [2]),
                4 => data.Remove <Item> (line [1], line [2], line [3]),
                _ => false
            };

        private static bool Inventory (DataBase data, params string [] line)
        {
            try 
            {
                Console.WriteLine (data.Inventory.Keys);
                return true;
            }
            catch ( System.IO.IOException  )  { return false; }
            catch ( NullReferenceException )  { return false; }
        }
    }
}