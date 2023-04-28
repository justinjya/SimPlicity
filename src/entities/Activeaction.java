package src.entities;

import src.main.Consts;
import src.main.GameTime;
import src.main.Consts;

public class Activeaction {
    public void work (Sim sim, GameTime time){
        // if it has not ever changed profession or it's been one day since it changed its profession
        if (!sim.getHasChangedProfession() || 
        ((((time.getDay() - 1) * 720 + 720 - time.getTimeRemaining())) 
        - ((sim.getChangeProfessionTime().getDay() - 1) * 720 + 720 - sim.getChangeProfessionTime().getTimeRemaining())) >= 720) { 
            sim.setStatus("Working");
            for (int i = 240; i > 0; i--) { // 4 minutes each work
                try {
                    Thread.sleep(Consts.ONE_SECOND);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                time.decrementTimeRemaining(time.getInitialTimeRemaining()); // decrement time by 1 second
                sim.setDurationOfWork(sim.getDurationOfWork() + 1); // sim's duration of work + 1 second
            } 
            sim.setMood(sim.getMood() - 80); // -10 mood per 30 second
            sim.setHunger(sim.getHunger() - 80); // -10 hunger per 30 seconds
    
            // add salary according to profession
            if (sim.getProfessionId() == 1) {
                sim.setMoney(sim.getMoney() + 15);
            }
            else if (sim.getProfessionId() == 2) {
                sim.setMoney(sim.getMoney() + 30);
            }
            else if (sim.getProfessionId() == 3) {
                sim.setMoney(sim.getMoney() + 35);
            }
            else if (sim.getProfessionId() == 4) {
                sim.setMoney(sim.getMoney() + 45);
            }
            else if (sim.getProfessionId() == 5) {
                sim.setMoney(sim.getMoney() + 50);
            }
        }
    }


    public void exercise (Sim sim, GameTime time){
        sim.setStatus("Exercising");
        // count the time
        for (int i = 20; i>0 ; i--) {
            try {
                Thread.sleep(Consts.ONE_SECOND);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            time.decrementTimeRemaining(time.getInitialTimeRemaining());
        }
        sim.setHealth(sim.getHealth() + 5); // increase sim's health
        sim.setHunger(sim.getHunger() - 5); // decrease sim's hunger
        sim.setMood(sim.getMood() + 10); // increase sim's mood

    }

    public void eat(Sim sim) {
        // Makan berarti Sim mengambil makanan yang ada di Inventory untuk kemudian dikonsumsi
        // Konsumsi makanan akan mengurangi jumlah makanan terkait pada inventory sejumlah 1 buah
        // dan meningkatkan tingkat kekenyangan Sim sejumlah satuan kekenyangan makanan terkait
        if (!sim.isBusy() && !currentRoom.isAddingObject()) {
            sim.setStatus("Eating");
            sim.changeIsBusyState();

            // Mengkonsumsi makanan yang ada di inventory
            Inventory inventory = currentRoom.getInventory();
            Food food = inventory.takeFood(); // Mengambil makanan dari inventory
            if (food != null) {
                int hungerIncrease = food.getSaturation(); // Mendapatkan jumlah kekenyangan dari makanan yang dikonsumsi
                sim.setHunger(sim.getHunger() + hungerIncrease); // Menambahkan kekenyangan Sim dengan jumlah kekenyangan makanan yang dikonsumsi
            }

            // Mengatur waktu makan
            try {
                Thread.sleep(Consts.ONE_SECOND); // Simulasi waktu makan selama 3 detik
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            sim.setStatus("Idle");
            sim.changeIsBusyState();
        }
    }

}
