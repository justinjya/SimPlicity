package src.main;

public class GameTime implements Runnable {
    private static int initialTimeRemaining = Consts.ONE_MINUTE * 12;
    public static int timeRemaining;
    public static int day;
    private static int decrements;
    private static Thread timeThread;

    private GameTime() {}

    public static void init(int day, int timeRemaining) {
        GameTime.day = day;
        GameTime.timeRemaining = timeRemaining;
        GameTime.decrements = 0;
    }

    // IMPLEMENTATION OF INTERFACE
    @Override
    public void run() {
        synchronized (this) {
            while (decrements > 0) {
                try {
                    Thread.sleep(Consts.THREAD_ONE_SECOND);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }

                decrementTimeRemaining();
                decrements--;
            }
        }
    }

    // GETTERS
    public static int getDecrements() {
        return decrements;
    }

    public static int getInitialTimeRemaining() {
        return initialTimeRemaining;
    }

    public static Thread getThread() {
        return timeThread;
    }

    public static boolean isAlive() {
        return timeThread.isAlive();
    }

    // SETTERS
    public static synchronized void decrementTimeRemaining() {
        timeRemaining--;
        if (timeRemaining == 0)
        {
            timeRemaining = initialTimeRemaining;
            incrementDay();
        }
    }

    private static void incrementDay() {
        day++;
    }

    public static void setDecrements(int decrements) {
        if (decrements > GameTime.decrements) {
            GameTime.decrements = decrements;
        }
    }
    
    // OTHERS
    public static Thread startDecrementTimeRemaining(int decrements) {
        setDecrements(decrements);
        timeThread = new Thread(new GameTime());
        timeThread.start();

        return timeThread;
    }

    public static void decreaseTimeRemaining(int time) {
        timeRemaining -= time;
        if (timeRemaining <= 0){
            int timeLeft = 0 - timeRemaining;
            timeRemaining = initialTimeRemaining - timeLeft;
            incrementDay();
        }
    }
}
