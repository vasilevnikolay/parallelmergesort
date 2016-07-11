package src;//import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;

import static org.junit.Assert.assertTrue;

public class parallelmergesort {

    private static void alusta(double[] taulukko){
        Random gen = new Random(2016);
        for (int i = 0; i < taulukko.length; i++)
            taulukko[i] = gen.nextDouble();
    }

    public static void main(String[] args) {

        final int TAULUKONPITUUS = 500000000;
        final int SAIKEITA = 32;
        final int PERSAIE = (TAULUKONPITUUS + SAIKEITA - 1) / SAIKEITA;

        double[] taulukko = new double[TAULUKONPITUUS];
        double[] apu = new double[TAULUKONPITUUS];
        SortingThread.initSortinThread(SAIKEITA);
        Thread lastThread = null;
        alusta(taulukko);

        long start = System.nanoTime();
        /*for (int i = 0; i < SAIKEITA; i++) {
            SortingThread sortingThread = new SortingThread(taulukko, apu, i * PERSAIE, Math.min((i + 1) * PERSAIE, TAULUKONPITUUS) - 1);
            Thread t = new Thread(sortingThread);
            sortingThread.addToSortinThreads(i, t);
            if (i == 0)
                lastThread = t;
            t.start();
        }

        try {
            lastThread.join();
        } catch (InterruptedException e) {
            System.err.println("Aw, snap!");
            e.printStackTrace();
        }*/
        /*ForkJoinPool pool = new ForkJoinPool();
        SortingAction sortingAction = new SortingAction(taulukko, apu, 0, TAULUKONPITUUS-1);
        pool.invoke(sortingAction);*/
        //Arrays.sort(taulukko);
        Arrays.parallelSort(taulukko);

        long end = System.nanoTime();
        System.out.println("Kesto: " + (end-start)/1000000);
        for (int i = 1; i < TAULUKONPITUUS; i++)
            assertTrue(taulukko[i-1] <= taulukko[i]);
    }
}
