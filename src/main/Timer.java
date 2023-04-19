package src.main;

public class Timer implements Runnable{
    private int duration;

    public Timer(int duration) {
        this.duration = duration * Consts.ONE_SECOND;
        Thread thread = new Thread(this);
        thread.start();
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public void run() {
        try {
            while (duration > 0) {
                Thread.sleep(Consts.ONE_SECOND);
                duration--;
            }
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
