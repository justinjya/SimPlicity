package Java.tubes;

public class RawConsumables extends Item
{
    private static String[] names =
    {
        "Nasi",
        "Kentang",
        "Ayam",
        "Sapi",
        "Wortel",
        "Bayam",
        "Kacang",
        "Susu"
    };
    private static int[] prices =
    {
        5,
        3,
        10,
        12,
        3,
        3,
        2,
        2,
    };
    private static int[] hungers =
    {
        5,
        4,
        8,
        15,
        2,
        2,
        2,
        1
    };

    private int price;
    private int hunger;

    public RawConsumables(int type) // type starts from 0
    {
        super(names[type]);
        price = prices[type];
        hunger = hungers[type];
    }

    public int getPrice()
    {
        return price;
    }

    public int getHunger()
    {
        return hunger;
    }

    public static String[] getNames()
    {
        return names;
    }

    public static int[] getPrices()
    {
        return prices;
    }

    public static int[] getHungers()
    {
        return hungers;
    }
}
