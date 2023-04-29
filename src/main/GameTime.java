package src.main;

public class GameTime implements Runnable {    
    // Atributes
    private int day;
    private int initialTimeRemaining = Consts.ONE_MINUTE * 12;
    private int timeRemaining;
    private int decrements;
    private Thread timeThread;
    
    // CONSTRUCTOR
    public GameTime(int day, int timeRemaining) {
        this.day = day;
        this.timeRemaining = timeRemaining;
        this.decrements = 0;
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
    public int getDay() {
        return day;
    }

    public int getTimeRemaining() {
        return timeRemaining;
    }

    public int getDecrements() {
        return decrements;
    }

    public int getInitialTimeRemaining() {
        return initialTimeRemaining;
    }

    public Thread getThread() {
        return timeThread;
    }

    public boolean isAlive() {
        return timeThread.isAlive();
    }

    // SETTERS
    public synchronized void decrementTimeRemaining() {
        timeRemaining--;
        if (timeRemaining == 0)
        {
            timeRemaining = initialTimeRemaining;
            incrementDay();
        }
    }

    private void incrementDay() {
        day++;
    }

    public void setDecrements(int decrements) {
        if (decrements > this.decrements) {
            this.decrements = decrements;
        }
    }
    
    // OTHERS
    public Thread startDecrementTimeRemaining(int decrements) {
        setDecrements(decrements);
        timeThread = new Thread(this);
        timeThread.start();

        return timeThread;
    }

    public void decreaseTimeRemaining(int time) {
        timeRemaining -= time;
        if (timeRemaining <= 0){
            int timeLeft = 0 - timeRemaining;
            timeRemaining = initialTimeRemaining - timeLeft;
            incrementDay();
        }
    }
}
