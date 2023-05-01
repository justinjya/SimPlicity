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
    
    public Profession()
    {
        name = names[randomizer];
        salary = salaries[randomizer];
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
