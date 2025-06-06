public class Watch {
    private long startTime;
    private long endTime;
    private boolean running = false;

    public void start() {
        startTime = System.nanoTime();
        running = true;
    }

    public void stop() {
        endTime = System.nanoTime();
        running = false;
    }
    
    public long getElapsedTimeMicros() {
        long elapsed;
        if (running) {
            elapsed = System.nanoTime() - startTime;
        } else {
            elapsed = endTime - startTime;
        }
        return elapsed / 1_000; // Convert to microseconds
    }

    public long getElapsedTimeMillis() {
        long elapsed;
        if (running) {
            elapsed = System.nanoTime() - startTime;
        } else {
            elapsed = endTime - startTime;
        }
        return elapsed / 1_000_000; // Convert to milliseconds
    }

    public long getElapsedTimeSeconds() {
        return getElapsedTimeMillis() / 1000;
    }
}