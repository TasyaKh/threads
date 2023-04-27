import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;


/**
 * Все варианты заданий подразумевают использование промежуточных буферов, представляющих
 * собой динамические массивы.
 * Максимальный размер буферов - N чисел. N определяется для каждого варианта.
 * Потоки, помещающие числа в буферы, следят за переполнение буферов.
 * Потоки, извлекающие числа из буферов, могут производить дан- ную операцию
 * в произвольный момент времени вне зависимости от того, заполнен ли бу- фер полностью или нет.
 **/


//22. Значения констант и реализуемые потоками функции:
//N=8
//Первый поток - генерирует в буфер 1200 случайных чисел из интервала от 0 до 4.
// Второй поток - переводит получившееся в буфере
// число из пятеричной системы исчисления в десятеричную,
// выводит полученный результат на экран и очищает буфер.


public class SharedData {
    private boolean isDataAvailableForRead = true;           //этот параметр определяет будет ли мы  считывать данные
    private boolean isDataAvailableForWrite = true;           //этот параметр определяет будет ли мы записывать


    private final int limitCapacity = 7;              //ограничение на емкость буфера [0-7]
    public final ArrayList<Integer> buffer = new ArrayList<>(limitCapacity);  //буфер
    private int indexWrite = 0;                      //индекс ткущего считываемого/записываемого элемента
    private int indexRead = 0;
    static int limit = 1200;                   //число элементов для генерации

    //запись данных
    public synchronized void write(int newData, int iteration) {
//System.out.println("writer data  indexWrite " + indexWrite  +  "  indexRead " +  indexRead);
        //если данные доступны для чтения
        while (!isDataAvailableForWrite) {
            try {
               // System.out.println("Writer wait  iteration " + iteration);
                wait(); //отсанавливаем поток для записи
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        //  System.out.println("WriterThread wrote: " + newData);
        //если достигли лимита буфера, то прекратить запись данных

//        System.out.println("indexWrite " +  indexWrite + " limitCapacity " + limitCapacity);
        if (buffer.size() >= limitCapacity || limit==iteration) {
            isDataAvailableForWrite = false;
//            System.out.println("Writer notify  " + iteration);
            //index = 0;
            notify();
        } else{
//            if(indexWrite >=  buffer.size()-1)
//                indexWrite = buffer.size();

            buffer.add( indexWrite, newData);
            isDataAvailableForRead = true;
            notify();
        }

       // System.out.println("Writer wait  iteration " + iteration);
        System.out.println("1 Thread Writer" + buffer + "   iteration " + iteration);
    }

    //для чтения данных
    public synchronized void read(int iteration) {
        //System.out.println("enetr read");
        while (!isDataAvailableForRead) {
            try {
               //System.out.println("reader wait indexRead" + indexRead + " indexWrite " + indexWrite);
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

//перевести из 5тиричной в десятичную

       // System.out.println("reader indexRead " + indexRead + " indexWrite " + indexWrite);

        if(buffer.size()!=0 && indexRead <= indexWrite && buffer.get(indexRead) !=-1){
            System.out.println("2 Thread Reader  "  + Long.toString(buffer.get(indexRead), 10) + "   ");
//            buffer.set(indexRead, -1); //очистить буфер

            int write = indexWrite;
            indexWrite=indexRead;
            buffer.remove(indexRead); //очистить буфер
            //isDataAvailableForWrite = true;

            indexRead = write;
//            indexRead++;

        }else {
//            System.out.println("isDataAvailableForRead  " + false);
            isDataAvailableForRead = false;
            isDataAvailableForWrite = true;
            notify();
        }
//        boolean wrEq = this.indexWrite <= this.indexRead && limitCapacity < this.indexRead ;
//        if (wrEq || (iteration == limit && indexWrite<indexRead)) {
//          // System.out.println("read notify  ");
//           // isDataAvailableForRead = false;
//            isDataAvailableForWrite = true;
//            this.indexRead = this.indexWrite= 0;
//            buffer.clear(); //очистить буфер
//            notify();
//        }

    }
}


