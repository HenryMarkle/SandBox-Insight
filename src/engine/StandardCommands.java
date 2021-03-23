package engine;

import java.util.HashMap;
import state.*;
import java.io.*;
import java.io.File;

/*
	TODO: Move every standard command definition to its own file.
	
	Warning: Ugly code ahead; proceed with caution.
*/ 

public abstract class StandardCommands 
{
	private static Command[] d = 
		{
			new Command("enter", 2, new String[] { "Space/House: BaseObjectType", "Name: string" }) 
			{
				@Override
				public boolean execute(String[] line, Modifyable data)
				{
					if (line != null && line.length - 1 >= this.argNum)
					{
						switch (line[1])
						{
						case "house":
							if (data.houses() == null || data.houses().isEmpty()) return true;
							
							for (House h : data.houses())
							{
								if (h.name.contentEquals(line[2]))
								{
									data.setHouseLocation(h);
									data.spaceToDefault();
									
									return true;
								}
								System.out.printf("\nMismach with \"%s\" was found.\n", h.name);
							}
							System.out.printf("\n\"%s\" not found.\n", line[2]);
							return false;
							
						case "space":
							if (data.hLocation().spaces() != null && !data.hLocation().spaces().isEmpty())
							{
								for (Space s : data.hLocation().spaces())
								{
									if (s.name.contentEquals(line[2]))
									{
										data.setSpaceLocation(s);
										return true;
									}
								}
								
								return false;
							}
							System.out.printf("\n\"%s\" not found.\n", line[2]);
							return false;
							
						default:
							System.out.printf("\nUnknown type \"%s\".\n", line[1]); // Debug
							return false;
						}
					}
					System.out.printf("\nNo arguments received.\n");
					return false;
				}
			},
			new Command("inventory", 0, new String[] {})
			{
				@Override
				public boolean execute(String[] line, Modifyable data)
				{
					if (line != null && line.length - 1 >= this.argNum)
					{
						data.inventory().forEach(s -> System.out.print(s.name + " "));
						return true;
					}
					else
					{
						return false;
					}
				}
			},
			new Command("check", 1, new String[] {"House/Space: BaseObjectType"})
			{
				@Override
				public boolean execute(String[] line, Modifyable data)
				{
					if (line != null)
					{
						if (line.length - 1 >= this.argNum)
						{
							switch (line[1])
							{
							case"house":
								if (data.houses() != null && !data.houses().isEmpty())
								{
									data.houses().forEach(s -> System.out.printf("\nHouse: \"%s\"\n", s.name));
								}
								return true;
								
							case"space":
								if (data.hLocation().spaces() != null && !data.hLocation().spaces().isEmpty())
								{
									data.hLocation().spaces().forEach(s -> System.out.printf("\nSpace: \"%s\"\n", s.name));
								}
								return true;
								
							default:
								System.out.printf("\nUnknown type \"%s\".\n", line[1]);  // Debug
								return false;
							}
						}
						else if (line.length == 1)
						{
							System.out.printf("\"%s\": \"%s\":\n", data.hLocation().name, data.sLocation().name);
							
							if (data.sLocation().items() != null && !data.sLocation().items().isEmpty())
							{
								data.sLocation().items().forEach(i -> System.out.println(i.name));
							}
							return true;
						}
						else
						{
							return false;
						}
						
					}
					else
					{
						return false;
					}
				}
			},
			new Command("create-house", 1, new String[] { "Name: string" })
			{
				@Override
				public boolean execute(String[] line, Modifyable data)
				{
					if (line != null && line.length - 1 >= this.argNum)
					{
						for (House h : data.houses())
						{
							if (h.name == line[1])
							{
								System.out.printf("\n\"%s\" already exists.\n", h.name);
								return false;
							}
						}
						
						data.houses().add(new House(line[1]));
						return true;
					}
					else
					{
						return false;
					}
				}
			},
			new Command("create-space", 2, new String[] 
					{ "Name: string", "House Name: string" })
			{
				@Override
				public boolean execute(String[] line, Modifyable data)
				{
					if (line != null)
					{
						if (line.length - 1 >= this.argNum)
						{
							for (House h : data.houses())
							{
								if (h.name.contentEquals(line[2]))
								{
									h.addSpace(line[1]);
									return true;
								}
							}
							return false;	
						}
						else if (line.length == 2)
						{
							if (data.hLocation().spaces() != null && 
									!data.hLocation().spaceExists(line[1]))
							{
								data.hLocation().spaces().add(new Space(line[1]));
								return true;
							}
							else
							{
								System.out.printf("\n\"%s\" already exists or spaces set was null.\n", line[1]);
								return false;
							}
						}
						else
						{
							return false;
						}
					}
					else
					{
						return false;
					}
				}
			},
			new Command("create-item", 3, new String[] 
					{ "Name: string", "Space Name: string", "House Name: string" })
			{
				@Override
				public boolean execute(String[] line, Modifyable data)
				{
					if (line != null)
					{
						switch (line.length - 1)
						{
						case 3:
							for (House h : data.houses())
							{
								if (h.name.contentEquals(line[3]) && h.spaceExists(line[2]))
								{
									Space s = h.findSpace(line[2]);
									return s.addItem(line[1]);
								}
								else
								{
									return false;
								}
							}
							return false;
							
						case 2:
							if (data.hLocation().spaceExists(line[2]))
							{
								Space s = data.hLocation().findSpace(line[2]);
								boolean res = s.addItem(line[1]);
								System.out.printf("\n\"addItem() has returned %s.", res);
								return res;
							}
							else
							{
								System.out.printf("\n\"%s\" was not found.\n", line[2]);
								return false;
							}
							
						case 1:
							for (Item i : data.inventory())
							{
								if (i.name.contentEquals(line[1]))
								{
									return false;
								}
							}
							data.inventory().add(new Item(line[1]));
							return true;
							
						default:
							return false;
						}
					}
					else
					{
						return false;
					}
				}
			},
			new Command("delete-house", 1, new String[] { "Name: string" })
			{
				@Override
				public boolean execute(String[] line, Modifyable data)
				{
					if (line != null)
					{
						if (line.length > 1)
						{
							for (House h : data.houses())
							{
								if (h.name .contentEquals(line[1]))
								{
									h.spaces().forEach(s -> s.removeAllItems());
									h.spaces().clear();
									data.houses().remove(h);
									return true;
								}
							}
							return false;
						}
						else if (line.length == 1)
						{
							data.houseToDefault();
							data.spaceToDefault();
							
							House h = data.hLocation();
							
							h.spaces().forEach(s -> s.removeAllItems());
							h.spaces().clear();
							data.houses().remove(h);
							
							return true;
						}
						else
						{
							return false;
						}
					}
					else
					{
						return false;
					}
				}
			},
			new Command("delete-space", 2, new String[] 
					{ "Name: string", "House Name: string" })
			{
				@Override
				public boolean execute(String[] line, Modifyable data)
				{
					if (line != null)
					{
						if (line.length - 1 >= this.argNum)
						{
							for (House h : data.houses())
							{
								if (h.name .contentEquals(line[3]) && h.spaceExists(line[1]))
								{
									Space s = h.findSpace(line[1]);
									
									s.removeAllItems();
									
									return h.deleteSpace(line[1]);
								}
							}
							return false;
						}
						else if (line.length == 2)
						{
							House h = data.hLocation();
							
							if (h.spaceExists(line[1]))
							{
								Space s = h.findSpace(line[1]);
								s.removeAllItems();
								return h.spaces().remove(s);
							}
							else
							{
								return false;
							}
						}
						else if (line.length == 1)
						{
							Space s = data.sLocation();
							
							s.deleteAllItems();
							return data.hLocation().spaces().remove(s);
						}
						else
						{
							return false;
						}
					}
					else
					{
						return false;
					}
				}
			},
			new Command("delete-item", 4, new String[] 
					{ "Name: string", "Space Name: string", "House Name: string" })
			{
				@Override
				public boolean execute(String[] line, Modifyable data)
				{
					if (line != null)
					{
						switch (line.length)
						{
						case 4:
							for (House h : data.houses())
							{
								if (h.name .contentEquals(line[3]) && h.spaceExists(line[2]))
								{
									Space s = h.findSpace(line[2]);
									if (s.itemExists(line[1])) return s.removeItem(line[1]);
								}
							}
							return false;
							
						case 3:
							if(data.hLocation().spaceExists(line[2]))
							{
								Space s = data.hLocation().findSpace(line[2]);
								if (s.itemExists(line[1])) return s.removeItem(line[1]);
							}
							return false;
							
						case 2:
							if (data.sLocation().itemExists(line[1])) return data.sLocation().removeItem(line[1]);
							return false;
						
						default:
							return false;
						}
					}
					else
					{
						return false;
					}
				}
			},
			new Command("store", 1, new String[] { "Name: string" })
			{
				@Override
				public boolean execute(String[] line, Modifyable data)
				{
					if (line != null)
					{
						if (line.length - 1 >= this.argNum)
						{
							if (data.sLocation().items() != null)
							{
								if (data.sLocation().itemExists(line[1]))
								{
									data.inventory().add(data.sLocation().popItem(line[1]));
									return true;
								}
								System.out.printf("\n\"%s\" was not found.\n", line[1]);
								return false;
								
							}
							else
							{
								System.out.printf("\nItems set was null.\n");
								return false;
							}
						}
						else
						{
							System.out.printf("\nNot enough arguments.\n");
							return false;
						}
					}
					else
					{
						return false;
					}
				}
			},
			new Command("use", 1, new String[] { "Name: string" })
			{
				@Override
				public boolean execute(String[] line, Modifyable data)
				{
					if (line != null)
					{
						if (line.length - 1 >= this.argNum)
						{
							for (Item i : data.inventory())
							{
								if (i.name .contentEquals(line[1])) return data.inventory().remove(i);
							}
							return false;
						}
						else
						{
							return false;
						}
					}
					else
					{
						return false;
					}
				}
			},
			/* The save/load functionality only works on linux platforms at the moment. */
			new Command("save", 0, new String[] {})
			{
				@Override
				public boolean execute(String[] line, Modifyable data)
				{
					try
					{
						File file = new File("To get path.txt");
						String[] path = file.getAbsolutePath().split("/");
						String finalPath = String.format("/home/%s/Eclipse Project Save Files/sandbox.ser", path[2]);
						
//						System.out.println();
//						System.out.println(java.util.Arrays.toString(path));
//						System.out.println("House target: " + path[2]);
//						System.out.println("Path: " + finalPath);
						
						FileOutputStream fileOut = 
								new FileOutputStream(finalPath);
						
						ObjectOutputStream objectOut = 
								new ObjectOutputStream(fileOut);
						
						objectOut.writeObject((DataBase) data);
						
						fileOut.close();
						objectOut.close();
					}
					catch (FileNotFoundException f)
					{
						System.out.printf("\nError: File not found.\n");
						return false;
					}
					catch(IOException i)
					{
						System.out.printf("\nError: Object was not written (save failure).\n");
						return false;
					}
					
					System.out.printf("\nProgress saved.\n");
					return true;
				}
			},
			new Command("load", 0, new String[] {})
			{
				@Override
				public boolean execute(String[] line, Modifyable data)
				{
					if (line != null)
					{
						try
						{
							File file = new File("To get path.txt");
							String[] path = file.getAbsolutePath().split("/");
							
							FileInputStream fileIn =
									new FileInputStream(String
											.format("/home/%s/Eclipse Project Save Files/sandbox.ser",path[2]));
							
							ObjectInputStream objectIn =
									new ObjectInputStream(fileIn);
							
							data = (DataBase) objectIn.readObject();
							
							fileIn.close();
							objectIn.close();
						}
						catch(FileNotFoundException f)
						{
							System.out.printf("\nError: File not found.\n");
							return false;
						}
						catch(IOException i)
						{
							System.out.printf("\nError: Object was not written (load failure).\n");
							return false;
						}
						catch(ClassNotFoundException c)
						{
							System.out.printf("\nError: Class Not Found Exception was catched.\n");
							return false;
						}
						
						System.out.printf("\nProgress loaded.\n");
						return true;
					}
					return false;
				}
			}
		};
	
	protected final static HashMap<String, Command> COMMANDS = new HashMap<String, Command>();
	
	private static boolean initDone = false;
	public final static void init()
	{
		if (!initDone)
		{
			for (Command c : d) 
			{
				COMMANDS.put(c.name, c);
				initDone = true;
			}	
		}
	}
}	
