package Java.tubes;

import java.util.ArrayList;

public class Inventory<T extends Item>
{
    private ArrayList<T> inventory;

    public Inventory()
    {
        inventory = new ArrayList<T>();
    }
    
    public ArrayList<T> getContents()
    {
        return inventory;
    }

    public int size()
    {
        return inventory.size();
    }

    public Item get(int index)
    {
        return inventory.get(index);
    }

    public int indexOf(String name)
    {
        int index = -1;
        for (int i = 0; i < size(); i++)
        {
            if (get(i).getName().equals(name))
            {
                index = i;
                break;
            }
        }
        return index;
    }

    public void addItem(T item)
    {
        inventory.add(item);
    }

    public void removeItem(String name)
    {
        try
        {
            inventory.remove(indexOf(name));
        }
        catch (IndexOutOfBoundsException e)
        {
            System.out.println("Item cannot be removed. Index out of bounds.");
        }
    }
}
