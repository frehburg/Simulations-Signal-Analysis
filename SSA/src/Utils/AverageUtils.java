package Utils;

import java.util.ArrayList;
import java.util.List;

public class AverageUtils {
    public static double avgI(ArrayList<Integer> al) {
        int sum = 0;
        for(int i : al) {
            sum += i;
        }

        double avg = (double)sum / (double)al.size();
        return avg;
    }

    public static double avgD(List<Double> al) {
        double sum = 0;
        for(double i : al) {
            sum += i;
        }

        double avg = sum / (double)al.size();
        return avg;
    }


}
