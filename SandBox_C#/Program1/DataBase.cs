using System.Collections.Generic;
using SBLib;
using System;

namespace SandBox
{
    internal class DataBase
    {
        public IDictionary <string, House> Houses { get; private set; }
        public IDictionary <string, Item> Inventory { get; private set; }

        public House LHouse { get; set; }
        public Space LSpace { get; set; }

        public House DHouse { get; } = new House ("DEFAULT HOUSE");
        public Space DSpace { get; } = new Space ("DEFAULT SPACE");

        /* Constructor(s) */

        public DataBase ()
        {
            Houses = new Dictionary <string, House> ();
            Inventory = new Dictionary <string, Item> ();

            LHouse = DHouse;
            LSpace = DSpace;
        }

        public DataBase (IEnumerable<House> houses)
        {
            foreach (House h in houses) Houses.Add (h.Name, h);
            Inventory = new Dictionary <string, Item> ();

            LHouse = DHouse;
            LSpace = DSpace;
        }

        public DataBase (IEnumerable<House> houses, IEnumerable<Item> inventory)
        {
            foreach (House h in houses) Houses.Add (h.Name, h);
            foreach (Item i in inventory) Inventory.Add (i.Name, i);

            LHouse = DHouse;
            LSpace = DSpace;
        }

        /* Basic Operation Methods */

        public bool Add <T> (params string [] args) where T : BaseObject
        {
            /* <House name> */

            if (typeof (T) == typeof (House) && 
            !Houses.ContainsKey (args [0]))
            {
                Houses.Add (args [0], new House (args [0]));
                return true;
            }

            /* <House name> <Space name>*/

            else if (typeof (T) == typeof (Space))
            {
                try
                {
                    return Houses [args [0]].Add (args [1]);
                }
                catch ( KeyNotFoundException ) { return false; }
            }

            /* <House name> <Space name> <Item name> */

            else if (typeof (T) == typeof (Item))
            {
                try
                {
                    Space s = Houses [args [0]].Get (args [1]);
                    return s.Add (args [2]);
                }
                catch ( IndexOutOfRangeException    ) { return false; }
                catch ( KeyNotFoundException        ) { return false; }
                catch ( NullReferenceException      ) { return false; }
            }
            else return false;
        }

        public bool Remove <T> (params string [] args) where T : BaseObject
        {

            /* <House name> */

            if (typeof (T) == typeof (House))
            {
                try
                {
                    if (LHouse == Houses [args [0]])
                    {
                        LHouse = DHouse;
                        LSpace = DSpace;
                    }
                    Houses [args [0]].ClearSpaces ();
                    return Houses.Remove (args [0]);
                }
                catch ( KeyNotFoundException     ) { return false; }
                catch ( IndexOutOfRangeException ) { return false; }
                catch ( NullReferenceException   ) { return false; }
            }

            /* <House name> <space name> */

            else if (typeof (T) == typeof (Space))
            {
                try
                {
                    House house = Houses [args [0]] ?? throw new NullReferenceException();
                    Space s = house.Get (args [1])  ?? throw new NullReferenceException();
                
                    if (LSpace == s) LSpace = DSpace;

                    s.ClearItems ();
                    return house.Remove (s.Name);
                }
                catch ( NullReferenceException     ) { return false; }
                catch ( KeyNotFoundException       ) { return false; }
                catch ( IndexOutOfRangeException   ) { return false; }
            }

            /* <House name> <Space name> <Item name> */

            else if (typeof (T) == typeof (Item))
            {
                try
                {
                    House h = Houses [args [0]]     ?? throw new NullReferenceException();
                    Space space = h.Get (args [1])  ?? throw new NullReferenceException();

                    return space.Remove (args [2]);
                }
                catch ( NullReferenceException     ) { return false; }
                catch ( KeyNotFoundException       ) { return false; }
                catch ( IndexOutOfRangeException   ) { return false; }
            }
            else return false;
        }
    }
}