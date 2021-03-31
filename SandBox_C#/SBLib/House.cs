using System.Collections.Generic;

namespace SBLib
{
    public class House : BaseObject
    {
        private readonly SortedDictionary<string, Space> spaces;

        /* Constructor */

        public House (string name) : base (name) =>
            spaces = new SortedDictionary<string, Space>();

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

        public Space Pop (string name)
        {
            if (spaces.ContainsKey (name)) return spaces[name];
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