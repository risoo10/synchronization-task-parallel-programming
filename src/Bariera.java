import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Riso on 11/29/2017.
 */
public class Bariera {

    int limit;
    int vnutri = 0;
    boolean vsetciVnutri = false;
    private boolean stop = false;

    private Lock lock = new ReentrantLock();
    private Condition bariera = lock.newCondition();

    public Bariera(int limit){
        this.limit = limit;
    }

    public void stopSimulation(){
        stop = true;
        bariera.signalAll();
    }


    public void vstupDoBarriery() throws InterruptedException {
        lock.lock();
        try {
            vnutri++;
            if (vnutri == limit) {
                vsetciVnutri = true;
                bariera.signalAll();
            } else {
                while (!vsetciVnutri) {
                    // End simulation
                    if(stop){
                        return;
                    }
                    bariera.await();
                }
            }
            // End simulation
            if(stop){
                return;
            }
            vnutri--;
            if (vnutri == 0) {
                vsetciVnutri = false;
                bariera.signalAll();
            } else {
                while (vsetciVnutri) {
                    // End simulation
                    if(stop){
                        return;
                    }
                    bariera.await();
                }
            }
        } finally {
            lock.unlock();
        }

    }


}
