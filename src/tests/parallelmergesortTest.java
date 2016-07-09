package tests;

import org.junit.Before;
import org.junit.Test;
import src.parallelmergesort;

import java.util.Arrays;
import java.util.Random;

import static org.junit.Assert.*;

public class parallelmergesortTest {
    private static final int taulukkoPituus = 10000000;
    private static double[] taulukko = new double[taulukkoPituus];
    private static double[] apu = new double[taulukkoPituus];

    @Before
    public void setUp() throws Exception {
        Random gen = new Random(2016);
        for (int i = 0; i < taulukko.length; i++)
            taulukko[i] = gen.nextDouble();
    }

    @Test
    public void mergesort() throws Exception {
        parallelmergesort.mergesort(taulukko,0,taulukkoPituus-1,apu);
        //parallelmergesort.insertionsort(taulukko,0,taulukkoPituus-1);
        //Arrays.sort(taulukko);
        for (int i = 1; i < taulukkoPituus; i++)
          assertTrue(taulukko[i-1] <= taulukko[i]);
    }

}