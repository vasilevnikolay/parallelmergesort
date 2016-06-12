
public class parallelmergesort {

    public static void main(String[] args){
        final int taulukkoPituus = 10000;
        int[] taulukko = new int[taulukkoPituus];
        mergesort(taulukko,0,taulukkoPituus-1);
    }

    private static void mergesort(int[] taulukko, int ala, int yla) {
        if(ala < yla){
            int mid = (ala+yla)/2;
            mergesort(taulukko,ala,mid);
            mergesort(taulukko,mid+1,yla);
            merge(taulukko,ala,mid,yla);
        }
    }

    private static void merge(int[] taulukko, int ala, int mid, int yla) {
        int min = Math.min(mid-ala+1,yla-mid);

    }
}
