using SBLib;
using System.Collections.Generic;

namespace SandBox
{
    public interface IItem
    {
        string Name { get; }
    }

    public interface ISpace
    {
        string Name { get; }
        IDictionary <string, IItem> Items { get; }
    }

    public interface IHouse
    {
        string Name { get; }
        IDictionary <string, ISpace> Spaces { get; }
    }

    public interface IDataBase
    {
        IDictionary <string, IHouse> Houses { get; }
        IDictionary <string, IItem> Inventory { get; }
        
        IHouse CurrentHouse { get; set; }
        ISpace CurrentSpace { get; set; }
    }
}