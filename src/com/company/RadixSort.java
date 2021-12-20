package com.company;




public class RadixSort {

    public static void main(String[] args) {
        RadixSort radixSort = new RadixSort();
    }

    // массив служит для хранения индексов, которые в спомогательном массиве "выделяют" ячейки под числа с
    // определенной цифрой в i-м разряде
    private int[] indexArr = new int[11];
    private int[] pow10;    /// тут храним степени числа 10, чтобы не вычислять каждйы раз

    private int ARR_SIZE = 10;
    private int[] helpArr;  //
    private int[] arr ;

    {
        // инициализация массива степеней 10ки
        pow10 = new int[10];
        pow10[0] = 1;
        for (int i = 1; i < pow10.length; i++)
            pow10[i] = pow10[i - 1] * 10;
    }


    public RadixSort() {
        arr = new int[ARR_SIZE];
    }

    public RadixSort(int arrSize) {
        this.ARR_SIZE = arrSize;
        arr = new int[ARR_SIZE];
    }

    public void start(){
        for (int i = 0; i < arr.length; i++) {
            arr[i] = getRandomNumber(0, 1000);
        }
        System.out.print("Исходный массив:         ");
        printArr(arr);

        this.sort();

        System.out.print("Отсортированный массив:  ");
        printArr(arr);
    }



    /**
     * Получаем цифру числа заданного ранга
     *
     * @param number      число
     * @param number_rank ранг
     * @return цифра
     */
    public int getNumberByRank(int number, int number_rank) {
        return number % pow10[number_rank + 1] / pow10[number_rank];
    }

    /**
     * Сбрасываем индексы
     */
    public void reloadIndex() {
        for (int i = 0; i < indexArr.length; i++) {
            indexArr[i] = 0;
        }
    }



    public void sort() {

        int sum = 0;
        int rank = 0;
        do {
            helpArr = new int[ARR_SIZE];
            reloadIndex();
            sum = 0;
            // считаем количество чисел в каждой группе (сколько чисел rank которых = 0, = 1, ... , = 9)
            for (int i = 0; i < arr.length; i++) {
                int tmp = getNumberByRank(arr[i], rank);
                indexArr[tmp + 1]++;
            }
            // теперь мы знаем, какие индексы будут занимать группы во вспомогательном массиве
            // например ,если arr = [849, 401, 307, 784, 32, 951, 952, 404, 407, 521]
            // то массив индексов имеет вид [0, 0, 3, 2, 0, 2, 0, 0, 2, 0, 1]

            // определяем интервалы
            for (int i = 1; i < indexArr.length; i++) {
                indexArr[i] = indexArr[i - 1] + indexArr[i];
            }

            // Теперь массив индексов имеет следующий вид
            // [0, 0, 3, 5, 5, 7, 7, 7, 9, 9, 10]
            // т.е. 0я группа находится по индексу с 0 по 0 (т.е. отсутствует)
            //      1я группу начинаем записывать с 0 по 3, т.е. 0й 1й 2й элемент
            //      2я группа начинается записываться с [3 по 5)
            // сортируем числа по текущему разряду
            for (int i = 0; i < arr.length; i++) {
                // определяем группу рассматриваемого числа
                int group = getNumberByRank(arr[i], rank);

                sum += group; // считаем, если хоть 1 группа не нулевая, значит есть числа больше
                // значит нужна будет еще одна итерация

                // получаем индекс куда будем записывать очередное число
                // увеличивает индекс для следующей записи числа из этой группы (ранга)
                int tmp = indexArr[group]++;
                helpArr[tmp] = arr[i];
            }
            rank++;

            //System.arraycopy(helpArr, 0, arr, 0, arr.length);
            arr = helpArr;

            System.out.print("сортировка по " + (rank - 1) + " разряду: ");
            printArr(arr);

        } while (sum != 0);



    }

    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public void printArr(int[] arr) {
        System.out.print("[ ");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + "  ");
        }
        System.out.println("]");

    }
}