
public class parallelmergesort {

    public static void main(String[] args){
        final int taulukkoPituus = 10000;
        int[] taulukko = new int[taulukkoPituus];
        int[] apu = new int[taulukkoPituus];
        mergesort(taulukko,0,taulukkoPituus-1,apu);
    }

    private static void mergesort(int[] taulukko, int ala, int yla, int[] apu) {
        if(ala < yla){
            int mid = (ala+yla)/2;
            mergesort(taulukko,ala,mid,apu);
            mergesort(taulukko,mid+1,yla,apu);
            merge(taulukko,ala,mid,yla,apu);
        }
    }

    private static void merge(int[] taulukko, int ala, int mid, int yla, int[] apu) {
        int i = ala;
        int j = mid + 1;
        int ind = ala;

        while(i <= mid && j <= yla) {
            if (taulukko[i] <= taulukko[j]) {
                apu[ind] = taulukko[i];
                i++;
            } else {
                apu[ind] = taulukko[j];
                j++;
            }
            ind++;
        }
    }
}
