package src.actions;

public class Sim extends Entity implements ActiveActions {
    // kode sementara
    
    @Override
    public void work() {
        // Sim harus bekerja
        // Selama bekerja, sim kekenyangan dan mood Sim akan berkurang
        // Kerja juga akan menghasilkan uang dengan jumlah yang bergantung pada pekerjaan dari Sim
        if (!isBusy() && !currentRoom.isAddingObject()) {
            setStatus("Working");
            setBusy(true);
            setHunger(getHunger() - 5); // Mengurangi kekenyangan Sim sebanyak 5 satuan
            setMood(getMood() - 5); // Mengurangi mood Sim sebanyak 5 satuan

            // Mendapatkan uang berdasarkan pekerjaan Sim
            int salary = calculateSalary(); // Menghitung gaji berdasarkan pekerjaan
            setMoney(getMoney() + salary); // Menambahkan uang Sim dengan gaji yang didapatkan

            // Mengatur waktu kerja
            try {
                Thread.sleep(5000); // Simulasi waktu kerja selama 5 detik
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            setStatus("Idle");
            setBusy(false);
        }
    }

    @Override
    public void eat() {
        // Makan berarti Sim mengambil makanan yang ada di Inventory untuk kemudian dikonsumsi
        // Konsumsi makanan akan mengurangi jumlah makanan terkait pada inventory sejumlah 1 buah
        // dan meningkatkan tingkat kekenyangan Sim sejumlah satuan kekenyangan makanan terkait
        if (!isBusy() && !currentRoom.isAddingObject()) {
            setStatus("Eating");
            setBusy(true);

            // Mengkonsumsi makanan yang ada di inventory
            Inventory inventory = currentRoom.getInventory();
            Food food = inventory.takeFood(); // Mengambil makanan dari inventory
            if (food != null) {
                int hungerIncrease = food.getSaturation(); // Mendapatkan jumlah kekenyangan dari makanan yang dikonsumsi
                setHunger(getHunger() + hungerIncrease); // Menambahkan kekenyangan Sim dengan jumlah kekenyangan makanan yang dikonsumsi
            }

            // Mengatur waktu makan
            try {
                Thread.sleep(3000); // Simulasi waktu makan selama 3 detik
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            setStatus("Idle");
            setBusy(false);
        }
    }

    // Helper method untuk menghitung gaji Sim berdasarkan pekerjaan
    private int calculateSalary() {
        // Implementasi logika untuk menghitung gaji berdasarkan pekerjaan Sim
        // Contoh implementasi sederhana yang menghasilkan gaji acak antara 10-20
        return (int) (Math.random() * 11) + 10;
    }
}
