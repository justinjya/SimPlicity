package src.main.time;

import java.util.ConcurrentModificationException;
import java.util.ArrayList;

import src.main.Consts;
import src.entities.sim.Sim;
import src.main.UserInterface;

public class GameTime implements Runnable {
    private static GameTime gt = new GameTime();
    private static Thread gtThread = new Thread(gt);

    public static int initialTimeRemaining = Consts.ONE_MINUTE * 12;
    public static int timeRemaining;
    public static int day;
    private static ArrayList<ActivityTimer> listOfActiveActivities = new ArrayList<>();

    private GameTime() {}

    public static void init(int day, int timeRemaining) {
        GameTime.day = day;
        GameTime.timeRemaining = timeRemaining;
        gtThread.start();
    }

    // GETTERS
    public static GameTime getInstance() {
        return gt;
    }

    public static Thread getThreadInstance() {
        return gtThread;
    }

    public static ArrayList<ActivityTimer> getListOfActiveActivities() {
        return listOfActiveActivities;
    }

    public static ActivityTimer getActivityTimer(Sim sim, String activity) {
        try {
            for (ActivityTimer activityTimer : listOfActiveActivities) {
                Sim activeSim = activityTimer.getSim();
        
                boolean simIsActive = sim.getName().equals(activeSim.getName());
                boolean activityIsActive = activity.equals(activityTimer.getActivity());
    
                if (simIsActive && activityIsActive) {
                    return activityTimer;
                }
            }
        }
        catch (ConcurrentModificationException cme) {}

        return null;
    }

    // IMPLEMENTATION OF INTERFACE
    @Override
    public void run() {
        while (gtThread.isAlive()) {
            if (UserInterface.isPaused()) continue;
            if (listOfActiveActivities.isEmpty()) continue;

            try {
                Thread.sleep(Consts.THREAD_ONE_SECOND);
                for (ActivityTimer activityTimer : listOfActiveActivities) {
                    int timeRemaining = activityTimer.getTimeRemaining();

                    if (timeRemaining <= 0) listOfActiveActivities.remove(activityTimer);
                    
                    activityTimer.setTimeRemaining(timeRemaining - 1);
                }
                decrementTimeRemaining();
            }
            catch (InterruptedException ie) {}
            catch (ConcurrentModificationException cme) {}
        }
    }

    public static boolean isAlive(Sim sim, String activity) {
        try {
            for (ActivityTimer activityTimer : listOfActiveActivities) {
                Sim activeSim = activityTimer.getSim();
    
                boolean simIsActive = sim.getName().equals(activeSim.getName());
                boolean activityIsActive = activity.equals(activityTimer.getActivity());
    
                if (simIsActive && activityIsActive) {
                    return true;
                }
                continue;
            }
        }
        catch (ConcurrentModificationException cme) {}
        
        return false;
    }

    // SETTERS
    public static void addActivityTimer(Sim sim, String activity, int timeRemaining, int duration) {
        ActivityTimer activityTimer = new ActivityTimer(sim, activity, timeRemaining, duration);
        listOfActiveActivities.add(activityTimer);
    }

    public static void decrementTimeRemaining() {
        timeRemaining--;
        if (timeRemaining == 0)
        {
            timeRemaining = initialTimeRemaining;
            incrementDay();
        }
    }

    public static void incrementDay() {
        day++;
    }

    public static void decreaseTimeRemaining(int time) {
        timeRemaining -= time;

        for (ActivityTimer activityTimer : listOfActiveActivities) {
            int timeRemaining = activityTimer.getTimeRemaining();
            
            activityTimer.setTimeRemaining(timeRemaining - time);
        }

        if (timeRemaining <= 0){
            int timeLeft = 0 - timeRemaining;
            timeRemaining = initialTimeRemaining - timeLeft;
            incrementDay();
        }
    }
}
