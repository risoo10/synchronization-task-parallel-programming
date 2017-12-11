/**
 * Created by Riso on 11/29/2017.
 */
public class Tenista extends Thread {

    public boolean stop = false;

    private static final int LIMIT_TRENOVANIA = 8;

    public int trenovaneLopticky = 0;

    ZasobaLopticiek lopticky;

    public Tenista(ZasobaLopticiek zl){
        super("TENISTA");
        this.lopticky = zl;
    }

    @Override
    public void run(){
        while(!stop){
            trenuj();
            oddychuj();
        }
    }


    private void trenuj(){
        try {
            lopticky.zoberLoptu();
            sleep(1000);
            trenovaneLopticky++;
            System.out.printf("TENISTA dalse podanie ! | lopticky %d | trenovania %d\n", lopticky.lopticky, trenovaneLopticky );
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void oddychuj(){
        if(trenovaneLopticky % LIMIT_TRENOVANIA == 0 && trenovaneLopticky > 0){
            try {
                sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
