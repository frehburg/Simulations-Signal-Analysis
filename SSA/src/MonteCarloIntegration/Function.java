package MonteCarloIntegration;

/**
 * This is a function object, it contains a real valued function
 */
public interface Function {
    double evaluate(double x);
    String toString();
}
