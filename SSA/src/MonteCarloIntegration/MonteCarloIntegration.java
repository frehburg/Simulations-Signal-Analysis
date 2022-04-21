package MonteCarloIntegration;

import Old.JavaRandGenerator;
import Old.RandGenerator;

import java.util.ArrayList;

import static Utils.RoundUtils.round;

public class MonteCarloIntegration {
    public static double integral(double a, double b, Function f, int n) {
        RandGenerator r = new JavaRandGenerator();
        //1. generate n random X in between a and b
        ArrayList<Double> X = new ArrayList<>();
        for(int i = 0; i < n; i++) {
            double curX = r.getDouble(a,b);
            X.add(curX);
        }
        //2. evaluate the function at that point
        ArrayList<Double> gX = new ArrayList<>();
        double sum = 0;
        for(int i = 0; i < n; i++) {
            double curX = X.get(i);
            double curgX = f.evaluate(curX);
            gX.add(curgX);
            sum += curgX;
        }
        //3. calculate sampling mean
        double EY = ((b - a)/n)*sum;//expected value of Y approximated by the sampling mean
        System.out.println("The integral of " + f.toString()+ " between " + a +" and " + b + " is equal to " + round(EY,3) + "(3dp)");
        double error = calculateError(X, n);
        System.out.println("Error " + error);
        System.out.println("Confidence interval: (" + (EY - error) +","+ (EY + error) + ")");
        return EY;
    }

    /**
     * Error: standard deviation / sqrt(n)
     * @param X
     * @return
     */
    public static double calculateError(ArrayList<Double> X, int n) {
        double mean = 0;
        for(double i : X) {
            mean += i;
        }
        mean = mean/n;

        double deviation = 0;
        for(double i : X) {
            deviation += Math.pow((i - mean),2);
        }
        deviation = deviation/n;
        deviation = Math.sqrt(deviation);

        double sqrtN = Math.sqrt(n);
        double error = deviation/ sqrtN;
        return error;
    }
}
