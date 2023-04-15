package Java.tubes;

import java.util.ArrayList;

public class World {
    private ArrayList<Sim> simList;
    private ArrayList<House> houseList;
    private int currentSim;
    private int currentHouse;

    public World(String name)
    {
        Sim sim = new Sim(name);
        simList = new ArrayList<>();
        houseList = new ArrayList<>();
        simList.add(sim);
        houseList.add(new House(sim));
        currentSim = 0;
        currentHouse = 0;
    }

    public ArrayList<Sim> getSimList()
    {
        return simList;
    }

    public ArrayList<House> getHouseList()
    {
        return houseList;
    }

    public Sim getCurrentSim()
    {
        return simList.get(currentSim);
    }

    public House getCurrentHouse()
    {
        return houseList.get(currentHouse);
    }

    public Sim getSim(int index)
    {
        return simList.get(index);
    }

    public House getHouse(int index)
    {
        return houseList.get(index);
    }

    public void setCurrentSim(int currentSim)
    {
        this.currentSim = currentSim;
    }

    public void setCurrentHouse(int currentHouse)
    {
        this.currentHouse = currentHouse;
    }

    public void createSim(String name)
    {
        simList.add(new Sim(name));
    }

    public void createHouse(House house)
    {
        houseList.add(house);
    }
}
