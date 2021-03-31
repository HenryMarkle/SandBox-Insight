using SBLib;
using System.Collections.Generic;

namespace SandBox
{
    public interface IModifyable
    {
        IDictionary <string, House> Houses { get; }
        
        House LHouse { get; }
        Space LSpace { get; }

        void SetLHouseToDefault ();
        void SetLSpaceToDefault ();
    }

    public interface IExecutable
    {
        string Name { get; }

        bool Execute (string [] line, IModifyable data);
    }
}