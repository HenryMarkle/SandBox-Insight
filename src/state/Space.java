package state;

import java.util.Set;
import java.util.HashSet;

public class Space extends BaseObject 
{
	private static final long serialVersionUID = 0004;
	
	private HashSet<Item> items = new HashSet<Item>();
	
	// Constructor
	public Space(String name)
	{
		super(name);
	}
	
	// Methods
	public boolean addItem(String name)
	{
		if (name != null)
		{
			if (!itemExists(name) || items.size() == 0)
			{
				items.add(new Item(name));
				return true;
			}
			return false;
		}
		return false;
	}
	
	public Item findItem(String name)
	{
		if (name != null)
		{
			for (Item i : items)
			{
				if (i.name.contentEquals(name)) return i;
			}
		}
		return null;
	}
	
	public Item popItem(String name)
	{
		if (name != null)
		{
			for (Item i : items)
			{
				if (i.name.contentEquals(name))
				{
					items.remove(i);
					return i;
				}
			}
		}
		return null;
	}
	
	public Set<Item> popAllItems() 
	{ 
		HashSet<Item> listCopy = new HashSet<Item>();
		listCopy.addAll(items);
		items.clear();
		
		return listCopy;
	}
	
	public boolean removeItem(Item item)
	{
		if (item != null && items.contains(item)) return removeItem(item.name);
		else return false;
	}
	
	public boolean removeItem(String name)
	{
		if (name != null)
		{
			for (Item i : items)
			{
				if (i.name.contentEquals(name)) return items.remove(i);
			}
		}
		return false;
	}
	
	public void removeAllItems()
	{
		items.clear();
	}
	
	public boolean deleteItem(Item item)
	{
		if (item != null && items.contains(item)) return deleteItem(item);
		else return false;
	}
	
	public boolean deleteItem(String name)
	{
		Item i = popItem(name);
		if (i != null)
		{
			//Item.allNames.remove(i.name);
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public void deleteAllItems()
	{
//		items.forEach(s -> {
//			if (Item.allNames.contains(s.name)) Item.allNames.remove(s.name);
//			});
		items.clear();
	}
	
	public boolean itemExists(String name)
	{
		for (Item i : items)
		{
			if (i.name.contentEquals(name)) return true;
		}
		return false;
	}
	
	public Set<Item> items()
	{
		return items;
	}
}
