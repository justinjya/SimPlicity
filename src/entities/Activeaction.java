package src.entities;

import src.main.Consts;
import src.main.GameTime;
import src.main.Consts;

public class Activeaction {
    public void work (Sim sim, GameTime time){
        Thread working = new Thread() {
            @Override
            public void run() {
                // if it has not ever changed profession or it's been one day since it changed its profession
                if (!sim.getHasChangedProfession() || 
                ((((time.getDay() - 1) * 720 + 720 - time.getTimeRemaining())) 
                - ((sim.getChangeProfessionTime().getDay() - 1) * 720 + 720 - sim.getChangeProfessionTime().getTimeRemaining())) >= 720) { 
                    try {
                        sim.setStatus("Working");
                        time.startDecrementTimeRemaining(240000);
                        Thread.sleep(240000);

                        sim.setDurationOfWork(sim.getDurationOfWork() + 240); // sim's duration of work + 4 mins
                        sim.setMood(sim.getMood() - 80); // -80 mood per 4 mins
                        sim.setHunger(sim.getHunger() - 80); // -80 hunger per 4 mins
                        
                
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
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    
                }
            }
        };
        working.start();
    }


    public void exercise (Sim sim, GameTime time){
        Thread exercising = new Thread() {
            @Override
            public void run() {
                try {
                    sim.setStatus("Exercising");
                    // count the time
                    time.startDecrementTimeRemaining(20000);
                    Thread.sleep(20000);
                    sim.setHealth(sim.getHealth() + 5); // increase sim's health
                    sim.setHunger(sim.getHunger() - 5); // decrease sim's hunger
                    sim.setMood(sim.getMood() + 10); // increase sim's mood
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        exercising.start();
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
                Thread.sleep(Consts.ONE_SECOND);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            sim.setStatus("Idle");
            sim.changeIsBusyState();
        }
    }
}
