package RandomNumberGenerators;

public class MainRand {
    public static void main(String[] args) throws Exception {
        LCGUnifGenerator lcg = LCGUnifGenerator.PMMLCG_2;
        RandomVariatesGenerator r = new GeneralRandVariatesGenerator(lcg);
        int sum = 0;
        int n = 70091068;
        for(int i = 0; i < n; i++) {
            boolean b = r.getBoolean();
            if(b) sum++;
        }
        System.out.println((double)sum/(double)n);
    }

    public static void testFibonacci() throws Exception {
        FibonacciUnifGenerator r = new FibonacciUnifGenerator((long) (Math.pow(2,31)-1),54223);
        r.getRandomNumber(999999);
    }



    public static void testLCG() throws Exception {
        //exampple from book
        LCGUnifGenerator r = new LCGUnifGenerator(16, 5, 3, 7);
        r.getRandomNumber(4);
        System.out.println("---------------------");
        // very big example that has a good distribution over [0,1)
        LCGUnifGenerator p = new LCGUnifGenerator(1243124, 244, 16452, 93752);
        p.getRandomNumber(1000000);
    }

    public static void testMidSquareMethod() throws Exception {
        //example from the book
        MidSquareUnifGenerator r = new MidSquareUnifGenerator(7182);
        r.getRandomNumber(15);
        System.out.println("-----------------------------");
        // this willl show it quickly goes to zero
        MidSquareUnifGenerator p = new MidSquareUnifGenerator(1009);
        p.getRandomNumber(12);
        System.out.println("-----------------------------");
        // even quicker to 0
        MidSquareUnifGenerator q = new MidSquareUnifGenerator(1000);
        q.getRandomNumber(3);
    }
}
