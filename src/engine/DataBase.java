package engine;

import java.util.Set;
import java.util.HashSet;
import java.io.Serializable;

import state.House;
import state.Space;
import state.Item;

public class DataBase implements Modifyable, Serializable
{	
	private static final long serialVersionUID = 0005;
	
	private final Set<House> houses;
	
	private House house;
	private Space space;
	private final Set<Item> inventory;
	
	private final House defaultHouse = new House("DEFAULT HOUSE");
	private final Space defaultSpace = new Space("DEFAULT SPACE");
	
	// Constructors
	public DataBase()
	{
		houses = new HashSet<House>();
		inventory = new HashSet<Item>();
		
		house = defaultHouse;
		space = defaultSpace;
	}
	
	public DataBase(Set<House> houses)
	{
		this.houses = houses;
		inventory = new HashSet<Item>();
		
		house = defaultHouse;
		space = defaultSpace;
	}
	
	public DataBase(Set<House> houses, Set<Item> inventory)
	{
		this.houses = houses;
		this.inventory = inventory;
		
		house = defaultHouse;
		space = defaultSpace;
	}
	
	public DataBase(Set<House> houses, Set<Item> inventory, House houseLocation)
	{
		this.houses = houses;
		this.inventory = inventory;
		
		house = houseLocation;
		space = defaultSpace;
	}
	
	public DataBase(Set<House> houses, Set<Item> inventory, House houseLocation, Space spaceLocation)
	{
		this.houses = houses;
		this.inventory = inventory;
		
		house = houseLocation;
		space = house.spaces().contains(spaceLocation) ? spaceLocation : defaultSpace;
	}
	
	// Methods
	public Set<House> houses() { return houses; }
	public Set<Item> inventory() { return inventory; }
	public House hLocation() { return house; }
	public Space sLocation() { return space; }
	
	public boolean setHouseLocation(House house)
	{
		if (house != null && houses.contains(house))
		{
			this.house = house;
			return true;
		}
		return false;
	}
	
	public boolean setSpaceLocation(Space space)
	{
		if (space != null && house.spaces().contains(space))
		{
			this.space = space;
			return true;
		}
		return false;
	}
	
	public void houseToDefault()
	{
		house = defaultHouse;
	}
	
	public void spaceToDefault()
	{
		space = defaultSpace;
	}
}
