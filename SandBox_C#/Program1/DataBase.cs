using System.Collections.Generic;
using SBLib;

namespace SandBox
{
    internal class DataBase : IModifyable
    {
        public IDictionary <string, House> Houses { get; private set; }

        public House LHouse { get; private set; }
        public Space LSpace { get; private set; }

        private House Dhouse = new House ("DEFAULT HOUSE");
        private Space DSpace = new Space ("DEFAULT SPACE");

        /* Constructor(s) */

        public DataBase ()
        {
            Houses = new Dictionary <string, House> ();

            LHouse = Dhouse;
            LSpace = DSpace;
        }

        /* Methods */

        public void SetLHouseToDefault () => LHouse = Dhouse;
        public void SetLSpaceToDefault () => LSpace = DSpace;
    }
}
