package engine;

import java.util.Set;

public interface Modifyable {
	public Set<state.House> houses();
	public Set<state.Item> inventory();
	
	public state.House hLocation();
	public state.Space sLocation();
	
	public boolean setHouseLocation(state.House house);
	public boolean setSpaceLocation(state.Space space);
	public void houseToDefault();
	public void spaceToDefault();
}
