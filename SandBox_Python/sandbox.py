class Item:
    def __init__(self, name):
        self.name = name

    def __str__(self):
        return self.name

class Space:
    def __init__(self, name):
        self.name: str = name
        self.__items: dict = {}

    def __str__(self):
        return self.name

    def exists(self, item: str):
        for i in self.__items.keys():
            if i == item:
                return True
        else:
            return False

    def add(self, item: str):
        for x in self.__items.keys():
            if x == item:
                return False
        else:
            self.__items[item] = Item(item)
            return True

    def remove(self, item: str):
        if self.exists(item):
            return self.__items.pop(item, None)
        else:
            return None

    def clear(self) -> None:
        self.__items.clear()

class House:
    def __init__(self, name):
        self.name: list = name
        self.__spaces: dict = {}

    def __str__(self):
        return self.name

    def exitst(self, space: str):
        for s in self.__spaces.keys():
            if s == space:
                return True
        else:
            return False

    def add(self, space: str):
        if self.exitst(space):
            return False
        else:
            self.__spaces[space] = Space(space)
            return True

    def remove(self, space: str):
        if self.exitst(space):
            return self.__spaces.pop(space, None)
        else:
            return None

    def clear(self) -> None:
        for space in self.__spaces.values():
            space.clear()
        self.__spaces.clear()

class DataBase:
    def __init__(self):
        self.houses: dict = {}

        self.__inventory: dict = {}

        self.__default_house: House = House("DEFAULT HOUSE")
        self.__default_space: Space = Space("DEFAULT SPACE")

        self.current_house: House = self.__default_house
        self.current_space: Space = self.__default_space
    
    def clear(self) -> None:
        for house in self.houses.values():
            house.clear()
        self.houses.clear()


"""             Functions               """

# Note: Will implement soon

def _check(line: list, data: DataBase) -> bool:
    pass

def _store(line: list, data: DataBase) -> bool:
    pass

def _use(line: list, data: DataBase) -> bool:
    pass

def _create_house(line: list, data: DataBase) -> bool:
    pass

def _create_space(line: list, data: DataBase) -> bool:
    pass

def _create_item(line: list, data: DataBase) -> bool:
    pass

def _delete_house(line: list, data: DataBase) -> bool:
    pass

def _delete_space(line: list, data: DataBase) -> bool:
    pass

def _delete_item(line: list, data: DataBase) -> bool:
    pass


_execute: dict = {
    
    "check"         :   _check          ,
    "store"         :   _store          ,
    "use"           :   _use            ,
    "create-house"  :   _create_house   ,
    "create-space"  :   _create_space   ,
    "create-item"   :   _create_item    ,
    "delete-house"  :   _delete_house   ,
    "delete-space"  :   _delete_space   ,
    "delete-item"   :   _delete_item
}

# Main function

def start():

    data = DataBase()

    done: bool = False
    
    while(not done):

        player_input = input("Console Command: ")

        if player_input == "exit" or player_input == "quit":
            break

        line: list = player_input.split(" ")

        result: bool
        
        try:
            result = _execute [player_input[0]] (line, data)
        except KeyError:
            print("Command not found.")
            continue

        print(format("Command \"%s\" has returned %s." % (player_input, result)))
    
    data.clear()
    _ = input("Game has ended. Press any key to continue...")
