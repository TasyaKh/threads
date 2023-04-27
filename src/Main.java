import java.util.Random;

class Main {

    //генерирует случаные числа от 0-4
    public static int generate() {

        Random rnd = new Random();

        return rnd.nextInt(0, 5);
    }

    public static void main(String[] args) {
        //класс для синхронного чтения и записи данных
        SharedData sharedData = new SharedData();

        //поток для чтения
        WriterThread writerThread = new WriterThread(sharedData);
        //поток для записи
        ReaderThread readerThread = new ReaderThread(sharedData);

        //сздать и запустить потоки
        Thread t1 = new Thread(writerThread);
        Thread t2 = new Thread(readerThread);

        t1.start();
        t2.start();

        //ждать пока потоки завершатся
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
