namespace SBLib
{
    /* Soon, I'll combine all material files into one. */
     
    public abstract class BaseObject
    {
        public string Name { get; }

        public BaseObject (string name) => Name = name;
    }
}
