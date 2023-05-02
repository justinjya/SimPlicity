package src.entities.sim;

public class Profession 
{
    private static String[] names =
    {
        "Clown",
        "Chef",
        "Police",
        "Programmer",
        "Doctor",
        "Barista",
        "Model",
        "Dentist",
        "Security"
    };

    private static int[] salaries =
    {
        15,
        30,
        35,
        45,
        50,
        20,
        45,
        40,
        15
    };

    private String name;
    private int salary;

    private static int count = names.length;
    private int randomizer = (int) (Math.random() * 100) % count;
    
    // for creating a new sim
    public Profession()
    {
        name = names[randomizer];
        salary = salaries[randomizer];
    }

    // for changing the profession of a sim
    public Profession(int index)
    {
        name = names[index];
        salary = salaries[index];
    }

    public String getName()
    {
        return name;
    }

    public int getSalary()
    {
        return salary;
    }
}
