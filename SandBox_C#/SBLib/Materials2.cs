using System.Collections.Generic;
using System;

namespace SBLib.Experimental
{
    public interface IBasic { string Name { get; internal set; } }

    public interface IUsable { void Use (); }

    public interface IKey { string ID { get; } }

    public interface ISpace { ISet <IBasic> Items { get; } }

    public interface ILockable
    {
        bool Locked { get; }

        bool Lock (object key);

        bool Unlock (object key);
    }

    public interface IHouse { ISet <ISpace> Spaces { get; } }

    public interface IContainer <T> 
    { 
        ISet <T> Content { get; }
        void Clear (); 
    }

    public interface IModifyable { ISet <IHouse> Houses { get; } }

    public static class Tools
    {
        public static void Rename (IBasic obj, string name) => obj.Name = name;

        public static bool Exists <T> (IContainer<T> container, Predicate <T> predicate)
            where T : IBasic
        {
            foreach (T t in container.Content)
            {
                if (predicate (t)) return true;
            }
            return false;
        }

        public static bool Remove <T> (IContainer <T> container, Predicate <T> predicate)
            where T : IBasic
        {
            foreach (T t in container.Content)
            {
                if (predicate (t))
                {
                    if (t is IContainer <IBasic> c) c.Clear ();
                    return container.Content.Remove (t);
                }
            }
            return false;
        }

        public static T Pop <T> (IContainer <T> container, Predicate<T> predicate)
            where T : IBasic
        {
            foreach (T t in container.Content)
            {
                if (predicate (t))
                {
                    container.Content.Remove (t);
                    return t;
                }
            }
            return default (T);
        }

        public static bool Add <T> (IContainer <T> container, T obj)
            where T : IBasic => container.Content.Add (obj);

        public static bool Move <T> (IContainer <T> from, IContainer <T> to, Predicate <T> predicate)
            where T : IBasic
        {
            foreach (T t in from.Content)
            {
                if (predicate (t))
                    return from.Content.Remove (t) && to.Content.Add (t);
            }
            return false;
        }
    }
}