package Java.tubes;

public class Profession 
{
    private static String[] names =
    {
        "Badut Sulap",
        "Koki",
        "Polisi",
        "Programmer",
        "Dokter"
    };
    private static int[] salaries =
    {
        15,
        30,
        35,
        45,
        50
    };
    private static int count = names.length;

    private String name;
    private int salary;
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
