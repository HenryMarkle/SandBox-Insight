using System.Collections.Generic;

namespace SBLib
{
    public class House : BaseObject
    {
        private readonly SortedDictionary<string, Space> spaces;

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