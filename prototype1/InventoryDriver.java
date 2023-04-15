package Java.tubes;

public class InventoryDriver {
    public static void main(String[] args)
    {
        Inventory<Item> inventory = new Inventory();
        inventory.addItem(new Interactables(0));
        inventory.addItem(new Interactables(5));
        inventory.addItem(new RawConsumables(0));
        inventory.addItem(new RawConsumables(1));
        inventory.addItem(new BakedConsumables(3));
        inventory.addItem(new BakedConsumables(4));
        System.out.println("----- Inventory -----");
        for (int i = 0; i < 2; i++)
        {
            if (i == 1)
            {
                inventory.removeItem("Kasur Single");
                inventory.removeItem("Bistik");
                System.out.println();
                System.out.println("Removing 'Kasur Single' and 'Bistik' from inventory");
            }
            
            for (Item item : inventory.getContents())
            {
                if (item instanceof Interactables)
                {
                    System.out.println("== Interactables ==");
                    System.out.println(item.getName());
                    System.out.println(((Interactables) item).getHeight() + " x " + (((Interactables) item).getWidth()));
                    System.out.println(((Interactables) item).getPrice());
                    System.out.println(((Interactables) item).getAction());
                }
                else if (item instanceof RawConsumables)
                {
                    System.out.println("== RawConsumables ==");
                    System.out.println(item.getName());
                    System.out.println(((RawConsumables) item).getPrice());
                    System.out.println(((RawConsumables) item).getHunger());
                }
                else if (item instanceof BakedConsumables)
                {
                    System.out.println("== BakedConsumables ==");
                    System.out.println(item.getName());
                    System.out.println(((BakedConsumables) item).getHunger());
                }
                System.out.println("---------------------");
            }
        }
    }
}
