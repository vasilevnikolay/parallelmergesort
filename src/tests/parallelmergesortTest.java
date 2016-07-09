package tests;

import org.junit.Before;
import org.junit.Test;
import src.SortingThread;
import src.parallelmergesort;

import java.util.Arrays;
import java.util.Random;

import static org.junit.Assert.*;

public class parallelmergesortTest {
    private static final int TAULUKONPITUUS = 20000000;
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
        final int SAIKEITA = 512;
        final int PERSAIE = (TAULUKONPITUUS + SAIKEITA - 1) / SAIKEITA;

        Thread[] saikeet = new Thread[SAIKEITA];
        SortingThread[] lajittelusaikeet = new SortingThread[SAIKEITA];

        for (int i = 0; i < SAIKEITA; i++) {
            lajittelusaikeet[i] = new SortingThread(taulukko, apu, i * PERSAIE, Math.min((i + 1) * PERSAIE, TAULUKONPITUUS) - 1);
            saikeet[i] = new Thread(lajittelusaikeet[i]);
            saikeet[i].start();
        }

        for (int i = 0; i < SAIKEITA; i++)
            try {
                saikeet[i].join();
            } catch (InterruptedException e) {
                System.err.println("Aw, snap!");
                e.printStackTrace();
            }
        for (int i = 2; i <= SAIKEITA; i = i << 1) {
            for (int j = 0; j < SAIKEITA; j += i) {
                lajittelusaikeet[j].setMiddle(lajittelusaikeet[j].getLast());
                lajittelusaikeet[j].setLast(lajittelusaikeet[j + i / 2].getLast());
                saikeet[j] = new Thread(lajittelusaikeet[j]);
                saikeet[j].start();
            }

            for (int j = 0; j < SAIKEITA; j += i) {
                try {
                    saikeet[j].join();
                } catch (InterruptedException e) {
                    System.err.println("Aw, snap!");
                    e.printStackTrace();
                }
            }
        }
        //Arrays.sort(taulukko);
        for (int i = 1; i < TAULUKONPITUUS; i++)
            assertTrue(taulukko[i-1] <= taulukko[i]);
    }

}