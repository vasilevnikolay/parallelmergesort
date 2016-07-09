package src;//import static org.junit.Assert.assertTrue;

import java.util.Random;

public class parallelmergesort {

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
    public static void mergesort(double[] taulukko, int ala, int yla, double[] apu) {
        if(yla - ala > 20){
            int mid = (ala+yla)/2;
            mergesort(taulukko,ala,mid,apu);
            mergesort(taulukko,mid+1,yla,apu);
            merge(taulukko,ala,mid,yla,apu);
        }
        else
            insertionsort(taulukko, ala, yla);
    }

    private static void alusta(double[] taulukko){
        Random gen = new Random(2016);
        for (int i = 0; i < taulukko.length; i++)
            taulukko[i] = gen.nextDouble();
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
    public static void main(String[] args){
        final int taulukkoPituus = 20;
        double[] taulukko = new double[taulukkoPituus];
        double[] apu = new double[taulukkoPituus];
        alusta(taulukko);
        //mergesort(taulukko,0,taulukkoPituus-1,apu);
        for (int i = 1; i < taulukkoPituus; i++)
           // assertTrue(taulukko[i-1] <= taulukko[i]);
        System.out.println(taulukko[i]);
    }
}
