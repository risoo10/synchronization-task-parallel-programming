/**
 * Created by Riso on 11/29/2017.
 */

public class Podavac extends Thread {

    public int podaneLopticky = 0;

    private static final int LIMIT_LOPTY = 3;

    public boolean stop = false;

    public int index;
    ZasobaLopticiek lopticky;
    Bariera bariera;

    public Podavac(ZasobaLopticiek zl, Bariera br, int index){
        super("PODAVAC");
        this.index = index;
        this.lopticky = zl;
        this.bariera = br;
    }

    @Override
    public void run(){
        while(!stop){
            podavaj();
            prestavka();
        }
    }

    private void podavaj(){

        // Zober novu loptu
        try {
            sleep(2000);
            lopticky.pridajLoptu();
            podaneLopticky++;
            System.out.printf("PODAVAC %d: nova lopticka | lopticky %d | podane lopticky %d \n", index, lopticky.lopticky, podaneLopticky);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void prestavka(){

        // Znovupouzitelna barriera
        if(podaneLopticky > 0 && podaneLopticky % LIMIT_LOPTY == 0){
            try {
                bariera.vstupDoBarriery();
                sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
