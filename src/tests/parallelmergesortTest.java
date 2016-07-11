package tests;

import org.junit.Before;
import org.junit.Test;
import src.SortingThread;
import src.parallelmergesort;

import java.util.Arrays;
import java.util.Random;

import static org.junit.Assert.*;

public class parallelmergesortTest {
    private static final int TAULUKONPITUUS = 50000000;
    private static double[] taulukko = new double[TAULUKONPITUUS];
    private static double[] apu = new double[TAULUKONPITUUS];

    @Before
    public void setUp() throws Exception {
        Random gen = new Random(2016);
        for (int i = 0; i < taulukko.length; i++)
            taulukko[i] = gen.nextDouble();
    }

    @Test
    public void mergesort() throws Exception {
        final int SAIKEITA = 256;
        final int PERSAIE = (TAULUKONPITUUS + SAIKEITA - 1) / SAIKEITA;

        SortingThread.initSortinThread(SAIKEITA);
        Thread lastThread = null;

        for (int i = 0; i < SAIKEITA; i++) {
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
        }

        //Arrays.sort(taulukko);
        //Arrays.parallelSort(taulukko);
        for (int i = 1; i < TAULUKONPITUUS; i++)
            assertTrue(taulukko[i-1] <= taulukko[i]);
    }

}