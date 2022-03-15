package xyz.cloudcoffee.atlas.util;

public class Await<T>
{
    private static final Object LOCK = new Object();

    private T result = null;

    public T waitfor(long millis) throws InterruptedException {
        synchronized (this){
            this.wait(millis);
        }
        if(result == null)
            throw new RuntimeException("Result was not posted");
        return result;
    }
    public T waitfor() throws InterruptedException {
        return waitfor(1000 * 10);
    }
    public void post(T result){
        synchronized (this){
            this.result = result;
            this.notify();
        }
    }

    public static void pause(long millis) throws InterruptedException {
        synchronized (LOCK) {
            LOCK.wait(millis);
        }
    }
}
