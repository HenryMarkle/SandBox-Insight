using System.Collections.Generic;

namespace SBLib
{
    public class Space : BaseObject
    {
        private readonly SortedDictionary<string, Item> items;

        /* Constructor */

        public Space (string name) : base (name) => 
            items = new SortedDictionary<string, Item> ();

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
}