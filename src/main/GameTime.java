package src.main;

public class GameTime implements Runnable {    
    // Atributes
    private int day;
    private int initialTimeRemaining;
    private int timeRemaining;
    private int decrements;
    private Thread thread;
    
    // CONSTRUCTOR
    public GameTime(int day, int initialTimeRemaining, int timeRemaining) {
        this.day = day;
        this.initialTimeRemaining = initialTimeRemaining;
        this.timeRemaining = timeRemaining;
        this.decrements = 0;
    }

    // IMPLEMENTATION OF INTERFACE
    @Override
    public void run() {
        while (decrements > 0) {
            try {
                Thread.sleep(Consts.THREAD_ONE_SECOND);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            decrementTimeRemaining(initialTimeRemaining);
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

    public Thread getThread() {
        return thread;
    }

    // SETTERS
    private void decrementTimeRemaining(int initialTimeRemaining) {
        timeRemaining--;
        decrements--;
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
        this.decrements = decrements / Consts.ONE_SECOND;
    }
    
    // OTHERS
    public void startDecrementTimeRemaining(int decrements) {
        setDecrements(decrements);
        thread = new Thread(this);
        thread.start();
    }

    public void decreaseTimeRemaining(int time) {
        timeRemaining -= time;
        if(timeRemaining <= 0){
            int timeLeft = 0-timeRemaining;
            timeRemaining = initialTimeRemaining - timeLeft;
            incrementDay();
        }
    }
}
