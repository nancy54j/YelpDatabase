package ca.ece.ubc.cpen221.mp5;

import java.util.Arrays;

import static java.lang.Math.min;

public class WHATAMIDOING {

    public static void main(String[] args){
        double[][] a = {{5.5, 3.4, 6.7}, {-0.5, 1.4, 4.5}, {123, 54.6, -9.2}};
        double[][] b = {{12.3, 2.3, 8.0}, {3.3, 1.74, -8.123}, {3.432, -6.2, -3.2}};
        double[][] c = new double[3][3];
        int n = 3;
        int bsize = 3;

        for (int jj=0; jj<n; jj+=bsize) {
            for (int kk=0; kk<n; kk+=bsize) {
                for (int i=0; i<n; i++) {
                    for (int j=jj; j < min(jj+bsize,n); j++) {
                        double sum = 0.0;
                        for (int k=kk; k < min(kk+bsize,n); k++) {
                            sum += a[i][k] * b[k][j];
                        }
                        c[i][j] += sum;
                    }
                }
            }
        }

        System.out.println(Arrays.deepToString(c));
    }
}
