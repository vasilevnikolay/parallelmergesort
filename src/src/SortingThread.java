package src;

public class SortingThread implements Runnable {
    private int first;
    private int middle;
    private int last;
    private double[] array;
    private double[] aux;
    private static SortingThread[] sortingThreads;
    private static Thread[] threads;
    private int ind;

    public static void initSortinThread(int size){
        sortingThreads = new SortingThread[size];
        threads = new Thread[size];
    }

    public void addToSortinThreads(int ind, Thread t){
        this.ind = ind;
        sortingThreads[ind] = this;
        threads[ind] = t;
    }
    public SortingThread(double[] array, double[] aux, int first, int last){
        this.first = first;
        this.middle = -1;
        this.last = last;
        this.array = array;
        this.aux = aux;
    }

    private int getLast() {
        return last;
    }

    public void setLast(int last) {
        this.last = last;
    }

    private static void insertionsort(double[] taulukko, int ala, int yla){
        for(int i = ala+1; i <= yla; i++){
            int j = ala;
            double lisattava = taulukko[i];
            while(j < i && lisattava >= taulukko[j])
                j++;
            int k = i;
            while(k > j) {
                taulukko[k] = taulukko[k - 1];
                k--;
            }
            taulukko[j] = lisattava;
        }
    }
    private static void mergesort(double[] taulukko, int ala, int yla, double[] apu) {
        if(yla - ala > 75){
            int mid = (ala+yla)/2;
            mergesort(taulukko,ala,mid,apu);
            mergesort(taulukko,mid+1,yla,apu);
            merge(taulukko,ala,mid,yla,apu);
        }
        else
            insertionsort(taulukko, ala, yla);
    }
    private static void merge(double[] taulukko, int ala, int mid, int yla, double[] apu) {
        for (int i = ala; i <= yla; i++)
            apu[i] = taulukko[i];

        int i = ala;
        int j = mid + 1;
        int ind = ala;

        while(i <= mid && j <= yla) {
            if (apu[i] <= apu[j]) {
                taulukko[ind] = apu[i];
                i++;
            } else {
                taulukko[ind] = apu[j];
                j++;
            }
            ind++;
        }

        while(i <= mid){
            taulukko[ind] = apu[i];
            i++;
            ind++;
        }
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        mergesort(this.array, this.first, this.last, this.aux);

        for(int i = 2; i <= sortingThreads.length; i <<= 1){
            if (ind % i == 0){
                try {
                    threads[ind+i/2].join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.err.println("Aw, snap!");
                    System.exit(-1);
                }
                this.middle = this.last;
                this.last = sortingThreads[ind+i/2].getLast();
                merge(this.array, this.first, this.middle, this.last, this.aux);
            }
        }
    }
}
