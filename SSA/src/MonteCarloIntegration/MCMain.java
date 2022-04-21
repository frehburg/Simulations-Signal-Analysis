package MonteCarloIntegration;

public class MCMain {
    static Function g = new Function() {
        @Override
        public double evaluate(double x) {
            return Math.sin(3*Math.pow(x,5)+7*Math.pow(x,3)+9*x+8)+ Math.sqrt(Math.sin(x)*Math.pow(x,3));
        }

        @Override
        public String toString() {
            return "f(x) = sin(3*x^5+7*x^3+9*x+8) + sqrt(sin(x)*x^3)";
        }
    };
    static Function threeXSquared = new Function() {
        @Override
        public double evaluate(double x) {
            return 3*Math.pow(x,2);
        }

        @Override
        public String toString() {
            return "3*x^2";
        }
    };
    static Function sinParabola = new Function() {
        //f(x) = 12*sin(2*pi*x)+7.5*x^2+123
        @Override
        public double evaluate(double x) {
            return 12*Math.sin(2*Math.PI*x)+7.5*Math.pow(x,2)+123;
        }

        @Override
        public String toString() {
            return "f(x) = 12*sin(2*pi*x)+7.5*x^2+123";
        }
    };
    public static void main(String[] args) {
        Function f = g;

        double a = -2, b = 2;
        int n = 100000;
        double I = MonteCarloIntegration.integral(a,b,f,n);
    }
}
