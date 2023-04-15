package Java.tubes;

import java.util.Arrays;

public class Interactables extends Item
{
    private static String[] names =
    {
        "Kasur Single",
        "Kasur Queen Size",
        "Kasur King Size",
        "Toilet",
        "Kompor Gas",
        "Kompor Listrik",
        "Meja dan Kursi",
        "Jam"
    };
    private static int[] heights =
    {
        4,
        4,
        5,
        1,
        2,
        1,
        3,
        1
    };
    private static int[] widths =
    {
        1,
        2,
        2,
        1,
        1,
        1,
        3,
        1
    };
    private static int[] prices =
    {
        50,
        100,
        150,
        50,
        100,
        200,
        50,
        10
    };
    private static String[] actions =
    {
        "Tidur",
        "Tidur",
        "Tidur",
        "Buang air",
        "Memasak",
        "Memasak",
        "Makan",
        "Melihat waktu"
    };

    private int height;
    private int width;
    private int price;
    private String action;

    public Interactables(int index)
    {
        super(names[index]);
        height = heights[index];
        width = widths[index];
        price = prices[index];
        action = actions[index];
    }

    public int getHeight()
    {
        return height;
    }

    public int getWidth()
    {
        return width;
    }

    public int getPrice()
    {
        return price;
    }

    public String getAction()
    {
        return action;
    }

    public static String[] getNames()
    {
        return names;
    }

    public static int[] getHeights()
    {
        return heights;
    }

    public static int[] getWidths()
    {
        return widths;
    }

    public static int[] getPrices()
    {
        return prices;
    }

    public static String[] getActions()
    {
        return actions;
    }

    public void interact(Sim sim)
    {
        switch (this.getAction())
        {
            case "Tidur":
                System.out.println();
                System.out.println("Sim Anda sedang tidur...");
                System.out.println("Mood Sim Anda bertambah 30!");
                System.out.println("Kesehatan Sim Anda bertambah 20!");
                sim.setMood(sim.getMood() + 30);
                sim.setHealth(sim.getHealth() + 20);
                break;

            case "Buang air":
                break;

            case "Memasak":
                break;

            case "Makan":
                break;

            case "Melihat waktu":
                break;
        }
    }
}
