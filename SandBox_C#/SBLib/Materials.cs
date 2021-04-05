using System.Collections.Generic;

namespace SBLib
{
    public abstract class BaseObject
    {
        public string Name { get; internal set; }

        public BaseObject (string name) => Name = name;
    }

    public class Item : BaseObject
    {
        public Item (string name) : base (name) { }
    }

    public class Space : BaseObject
    {
        internal readonly SortedDictionary<string, Item> items;

        /* Constructor */

        public Space (string name) : base (name) => 
            items = new SortedDictionary<string, Item> ();

        /* Indexer */

        public Item this [string name]
        {
            get
            {
                if (items.ContainsKey (name)) return items [name];
                else return null;
            }
        }
    
        /* Methods */

        public IEnumerable<string> Items () => items.Keys;

        public bool Exists (string name) => items.ContainsKey (name);

        public bool Add (string name)
        {
            if (!items.ContainsKey (name))
            {
                items.Add (name, new Item (name));
                return true;
            }
            return false;
        }

        public bool Remove (string name) => items.Remove (name);

        public Item Pop (string name)
        {
            if (items.ContainsKey (name))
            {
                Item i = items [name];
                items. Remove (name);
                return i;
            }
            else return null;
        }

        public void ClearItems () => items.Clear();

        /* Destructor */

        ~Space () => ClearItems ();
    }

    public class House : BaseObject
    {
        internal readonly SortedDictionary<string, Space> spaces;

        /* Constructor */

        public House (string name) : base (name) =>
            spaces = new SortedDictionary<string, Space>();

        public Space this [string name] 
        { 
            get
            {
                if (spaces.ContainsKey (name)) return spaces [name];
                else return null;
            } 
        }

        /* Methods */

        public IEnumerable<string> Spaces () => spaces.Keys;

        public bool Exists (string name) => spaces.ContainsKey (name);

        public bool Add (string name)
        {
            if (!spaces.ContainsKey (name))
            {
                spaces.Add (name, new Space (name));
                return true;
            }
            return false;
        }

        public bool Remove (string name) => spaces.Remove (name);

        public bool TryGet (string name, out Space space)
        {
            try
            {
                space = spaces [name];
                return true;
            }
            catch (KeyNotFoundException)
            {
                space = null;
                return false;
            }
        }

        public Space Get (string name)
        {
            try
            {
                return spaces [name];
            }
            catch (KeyNotFoundException)    { return null; }
        }

        public Space Pop (string name)
        {
            if (spaces.ContainsKey (name))
            {
                Space s = spaces [name];
                spaces.Remove (name);
                return s;
            }
            else return null;
        }

        public void ClearSpaces ()
        {
            foreach (Space s in spaces.Values)
            {
                s.ClearItems ();
            }

            spaces.Clear();
        }

        /* Destructor */

        ~House () => ClearSpaces ();
    }
}
