package src;//import static org.junit.Assert.assertTrue;

import java.util.Random;

import static org.junit.Assert.assertTrue;

public class parallelmergesort {

    private static void alusta(double[] taulukko){
        Random gen = new Random(2016);
        for (int i = 0; i < taulukko.length; i++)
            taulukko[i] = gen.nextDouble();
    }

    public static void main(String[] args) {

        final int TAULUKONPITUUS = 40000000;
        final int SAIKEITA = 512;
        final int PERSAIE = (TAULUKONPITUUS + SAIKEITA - 1) / SAIKEITA;

        double[] taulukko = new double[TAULUKONPITUUS];
        double[] apu = new double[TAULUKONPITUUS];
        Thread[] saikeet = new Thread[SAIKEITA];
        SortingThread[] lajittelusaikeet = new SortingThread[SAIKEITA];
        alusta(taulukko);
        /*mergesort(taulukko,0,TAULUKONPITUUS-1,apu);
        for (int i = 1; i < TAULUKONPITUUS; i++)
            assertTrue(taulukko[i-1] <= taulukko[i]);
        */
        //System.out.println(taulukko[i]);
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
        for (int i = 1; i < TAULUKONPITUUS; i++)
            assertTrue(taulukko[i-1] <= taulukko[i]);
    }
}
