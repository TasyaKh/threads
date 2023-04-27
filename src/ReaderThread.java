//поток для чтения
class ReaderThread implements Runnable {
    private final SharedData sharedData;

    public ReaderThread(SharedData sharedData) {
        this.sharedData = sharedData;
    }

    @Override
    public void run() {
        while (WriterThread.iteration+1 < SharedData.limit || sharedData.buffer.size()>0) {
            //считывать данные
            sharedData.read(WriterThread.iteration +1);
          // System.out.println("ReaderThread read: " + (i+1));
        }
    }
}
