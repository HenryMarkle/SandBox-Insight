package state;

import java.util.Set;
import java.util.HashSet;

public class House extends BaseObject 
{
	private static final long serialVersionUID = 0002;
	
	private HashSet<Space> spaces = new HashSet<Space>();
	
	// Constructors
	public House(String name)
	{
		super(name);
	}
	
	
	// Methods
	public boolean addSpace(String name)
	{
		if (name != null)
		{
			if (!spaceExists(name))
			{
				spaces.add(new Space(name));
				return true;
			}
			else
			{
				return false;
			}
		}
		return false;
	}
	
	public Space findSpace(String name)
	{
		if (name != null)
		{
			for (Space s : spaces)
			{
				if (s.name.contentEquals(name)) return s;
			}
		}
		return null;
	}
	
	public Space popSpace(String name)
	{
		if (name != null)
		{
			for (Space s : spaces)
			{
				if (s.name.contentEquals(name))
				{
					spaces.remove(s);
					return s;
				}
			}
		}
		return null;
	}
	
	public Set<Space> popAllSpaces()
	{
		HashSet<Space> listCopy = new HashSet<Space>();
		listCopy.addAll(spaces);
		spaces.clear();
		
		return listCopy;
	}
	
	public boolean deleteSpace(String name)
	{
		Space s = popSpace(name);
		
		if (s != null)
		{
			//if (Space.allNames.contains(s.name)) Space.allNames.remove(s.name);
			s.deleteAllItems();
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public void deleteAllSpaces()
	{
//		spaces.forEach(s -> {
//			if (Space.allNames.contains(s.name)) Space.allNames.remove(s.name);
//		});
		
		spaces.clear();
	}
	
	public boolean spaceExists(String name)
	{
		if (name != null)
		{
			for (Space s : spaces)
			{
				if (s.name.contentEquals(name)) return true;
			}
		}
		return false;
	}
	
	public Set<Space> spaces()
	{
		return spaces;
	}
}
