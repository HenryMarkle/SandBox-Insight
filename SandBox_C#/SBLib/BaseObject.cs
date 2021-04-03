namespace SBLib
{
    public abstract class BaseObject
    {
        public string Name { get; }

        public BaseObject (string name) => Name = name;
    }
}
