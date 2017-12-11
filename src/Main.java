public class Main {
    public static void main(String[] args) {
        MasterThread main = new MasterThread();
        main.start();
        try {
            main.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
