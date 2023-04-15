package Java.tubes;

public class BakedConsumables extends Item
{
    private static String[] names =
    {
        "Nasi Ayam",
        "Nasi Kari",
        "Susu Kacang",
        "Tumis Sayur",
        "Bistik"
    };
    private static int[] hungers =
    {
        16,
        30,
        5,
        5,
        22
    };

    private int hunger;
    public BakedConsumables(int type) // type starts from 0
    {
        super(names[type]);
        hunger = hungers[type];
    }

    public int getHunger()
    {
        return hunger;
    }

    public static String[] getNames()
    {
        return names;
    }

    public static int[] getHungers()
    {
        return hungers;
    }
}
