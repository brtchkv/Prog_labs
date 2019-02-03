import java.util.Arrays;


public class Lab1 {
    public static void main(String[] args) {
        int[] y = new int[17];
        double[] x = new double[15];
        double[][] p = new double[17][15];


        int i = 4;
        while (i <= 20) {
            y[i - 4] = i;
            i++;
        }

        for (int n = 0; n < x.length; n++) {
            x[n] = Math.random() * 26 - 14;
        }

        for (i = 0; i < 17; i++) {
            for (int j = 0; j < 15; j++) {
                if (y[i] == 12) {
                    p[i][j] = Math.pow(Math.E, Math.asin(Math.pow(Math.E, (Math.abs(x[j]) * -1))));
                }
                else if (y[i] == 4 || y[i] == 6 || y[i] == 8 || y[i] == 10 || y[i] == 11 || y[i] == 13 || y[i] == 16 || y[i] == 20)
                {
                    p[i][j] = Math.cos(Math.pow((Math.PI * Math.tan(x[j])) , 3));
                }
                else
                {
                    p[i][j] = Math.tan(Math.asin(Math.sin(Math.pow(Math.cos(x[j])/(Math.atan((x[j]-1)/26) + 0.25d),2))));
                }
            }
        }

        for (i = 0; i < 17; i++) {
            for (int j = 0; j < 15; j++) {
                System.out.format("%8.3f",p[i][j]);
            }
            System.out.println();
        }

    }
}
