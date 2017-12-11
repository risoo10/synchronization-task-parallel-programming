import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Riso on 11/29/2017.
 */
public class ZasobaLopticiek {

    private final Lock mutex = new ReentrantLock();
    private final Condition vsetkyLopty = mutex.newCondition();
    private final Condition ziadneLopty = mutex.newCondition();
    private boolean stop = false;

    private static final int MAX_LOPTY = 6;
    int lopticky;

    public ZasobaLopticiek(){
        lopticky = 0;
    }

    public void stopSimulation(){
        mutex.lock();
        try {
            stop = true;
            vsetkyLopty.signalAll();
            ziadneLopty.signalAll();
        }finally {
            mutex.unlock();
        }
    }

    public void pridajLoptu() throws InterruptedException {
        mutex.lock();
        try{
            while(lopticky == MAX_LOPTY){
                // End simulation
                if(stop){
                    return;
                }
                vsetkyLopty.await();
            }

            // End simulation
            if(stop){
                return;
            }

            lopticky++;
            ziadneLopty.signal();

        } finally {
            mutex.unlock();
        }
    }

    public void zoberLoptu() throws InterruptedException {
        mutex.lock();
        try{

            while(lopticky == 0){
                ziadneLopty.await();
            }
            lopticky--;
            vsetkyLopty.signal();

        } finally {
            mutex.unlock();
        }

    }


}
