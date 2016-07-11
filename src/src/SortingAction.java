package src;

import java.util.concurrent.RecursiveAction;

public class SortingAction extends RecursiveAction{
    private int first;
    private int last;
    private double[] array;
    private double[] aux;

    public SortingAction(double[] array, double[] aux, int first, int last){
        this.first = first;
        this.last = last;
        this.array = array;
        this.aux = aux;
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

    @Override
    protected void compute() {
        if(last - first < 500){
            mergesort(array,first,last,aux);
            return;
        }

        int mid = (first + last)/2;
        SortingAction left = new SortingAction(array,aux,first,mid);
        SortingAction right = new SortingAction(array, aux, mid+1, last);
        invokeAll(left, right);
        merge(array, first, mid, last, aux);
    }
}
