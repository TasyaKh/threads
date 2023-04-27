//поток для записи
class WriterThread implements Runnable {
    private final SharedData sharedData;
    public static int iteration = 0;

    public WriterThread(SharedData sharedData) {
        this.sharedData = sharedData;
    }

    @Override
    public void run() {
        for (int i = 0; i < SharedData.limit; i++) {
            iteration = i;
            //считывать данные
            sharedData.write(Main.generate(), i + 1);

            //System.out.println("ggg");
        }
    }
}
