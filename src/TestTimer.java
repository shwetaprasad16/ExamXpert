public class TestTimer extends Thread {
    private int remainingSeconds;
    private volatile boolean running = true;
    private volatile boolean timeOver = false;

    public TestTimer(int seconds) {
        remainingSeconds = seconds;
    }

    public void stopTimer() {
        running = false;
        interrupt();
    }

    public boolean isTimeOver() {
        return timeOver;
    }

    @Override
    public void run() {
        try {
            while (remainingSeconds > 0 && running) {
                int min = remainingSeconds / 60;
                int sec = remainingSeconds % 60;
                System.out.printf("\rTime Remaining: %02d:%02d", min, sec);
                Thread.sleep(1000);
                remainingSeconds--;
            }

            if (running && remainingSeconds == 0) {
                timeOver = true;
                System.out.println("\n\nTIME OVER! Test submitted automatically.");
            }
        } catch (InterruptedException ignored) {
        }
    }
}
