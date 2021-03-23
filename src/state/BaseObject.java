package state;

import java.io.Serializable;

public abstract class BaseObject implements Serializable {
	
	public String name;
	
	private final static long serialVersionUID = 0001;
	
	public BaseObject(String name) this.name = name;
}
