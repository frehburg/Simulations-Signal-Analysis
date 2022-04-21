package BuffonsNeedle;

import Old.JavaRandGenerator;
import Old.RandGenerator;

import java.util.ArrayList;

public class BuffonsNeedle {
    public static final double DIN_A4_HEIGHT = 40.6, DIN_A4_WIDTH = 30.3;

    public static double buffonsNeedle(long n, double delta, double l) {
        //0. calculate delta and establish intervals
        //there are amtLines + 1 spaces

        //1. generate n random angles theta and points (x,y) on the paper
        RandGenerator r = new JavaRandGenerator();
        ArrayList<Double> thetas = new ArrayList<>();
        ArrayList<Double> xs = new ArrayList<>();
        ArrayList<Double> ys = new ArrayList<>();
        for(int i = 0; i < n; i++) {
            thetas.add(r.getDouble(0,Math.toRadians(180))); // a little bit stupid because we need pi to generate random angles between 0 and pi (180°), but in the real world be can just throw them
            //TODO: implement a method that does not require pi to generate a random angle
            ys.add(r.getDouble(0,delta));
        }
        //2. for each calculate A: the distance to the closest line above
        ArrayList<Double> as = new ArrayList<>();
        ArrayList<Double> lsinthetas = new ArrayList<>();
        ArrayList<Boolean> needleCrossing = new ArrayList<>();
        int amtNeedleCrossings = 0;
        for(int i = 0; i < n; i++) {
            double a = delta - ys.get(i); // just shifts the space to [0, delta)
            as.add(a);
            double lsintheta = l*Math.sin(thetas.get(i));
            lsinthetas.add(lsintheta);
            //have needle crossing if a < l sin theta
            if(a < lsintheta) {
                needleCrossing.add(true);
                amtNeedleCrossings++;
            } else {
                needleCrossing.add(false);
            }
        }
        double piEstimate = ((n)/(double)amtNeedleCrossings)*((2*l)/delta);
        //probability of a needle crossing is 2*l/delta*pi
        //expected number of crossings is E(M)=2*n*l/delta*pi
        //pi estimate: 2*n*l/E(M)*delta
        return piEstimate;
    }
}
