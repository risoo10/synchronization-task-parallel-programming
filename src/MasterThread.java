import com.sun.corba.se.impl.oa.poa.POACurrent;

/**
 * Created by Riso on 11/29/2017.
 */
public class MasterThread extends Thread {

    private static final int POCET_PODAVACOV = 3;

    Tenista tenista;
    Podavac podavaci[] = new Podavac[POCET_PODAVACOV];

    boolean stop;

    public MasterThread(){
        super("MASTER");
    }

    @Override
    public void run(){

        // Vytvor synchronizacne struktury
        ZasobaLopticiek zl = new ZasobaLopticiek();
        Bariera br = new Bariera(POCET_PODAVACOV);

        // Spusti vlakna pre akterov
        Tenista tenista = new Tenista(zl);
        tenista.start();
        for(int i = 0; i < POCET_PODAVACOV; i++){
            podavaci[i] = new Podavac(zl, br, i);
            podavaci[i].start();
        }


        // Cas simulacie 30s
        try {
            sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.printf("Koniec simulacie !!!!\n");

        // Stopni tenistu a podavacov
        tenista.stop = true;
        for(int i = 0; i < POCET_PODAVACOV; i++) podavaci[i].stop = true;
        zl.stopSimulation();
        br.stopSimulation();

        // Join Threads
        try {
            tenista.join();
            for(int i = 0; i < POCET_PODAVACOV; i++) podavaci[i].join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        System.out.printf("TENISTA : Natrenovanych lopt: %d\n", tenista.trenovaneLopticky);
        for(int i = 0; i < POCET_PODAVACOV; i++) System.out.printf("PODAVAC %d : podanych lopt: %d\n", i, podavaci[i].podaneLopticky);

    }




}
