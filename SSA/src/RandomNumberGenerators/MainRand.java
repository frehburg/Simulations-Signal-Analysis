package RandomNumberGenerators;

import EmpiricalTestForRandomNumberGenerators.ChiSquareTest;
import EmpiricalTestForRandomNumberGenerators.RunsUpTest;

public class MainRand {
    public static void main(String[] args) throws Exception {
        UnifRandGenerator r = new LinCombCompositeGenerator((long) Math.pow(2,17),new UnifRandGenerator[]{new JavaUG(), LinearCongruentialUG.RANDU},new long[]{1,5},0);
        completeTest(r);
    }

    public static void completeTest(UnifRandGenerator p) {
        // runs up test
        int n = (int) Math.pow(2,14);
        boolean runsUp = RunsUpTest.runsUpTest(p,n, 0.05);
        if(runsUp) {
            // chi squared test
            int k = (int) Math.pow(2, 12);
            ChiSquareTest.chiSquareTest(p, k, n, 0.05);
        } else {
            System.out.println("Since the Chi Squared Uniformity test assumes independence, it does not apply to this sequence.");
        }
    }

    public static void testRunsUpTest() {
        UnifRandGenerator r = LinearCongruentialUG.PMMLCG_2;
        int n = 5000;
        RunsUpTest.runsUpTest(r,n, 0.05);
    }

    public static void testChiSquareTest() {
        // according to the book page 410
        UnifRandGenerator r = LinearCongruentialUG.PMMLCG_2;
        int k = (int) Math.pow(2,12);
        int n = (int) Math.pow(2,15);
        ChiSquareTest.chiSquareTest(r, k, n,0.05);
    }

    public static void testFibonacci() throws Exception {
        FibonacciUG r = new FibonacciUG((long) (Math.pow(2,31)-1),54223);
        r.getRandomNumber(999999);
    }



    public static void testLCG() throws Exception {
        //exampple from book
        LinearCongruentialUG r = new LinearCongruentialUG(16, 5, 3, 7);
        r.getRandomNumber(4);
        System.out.println("---------------------");
        // very big example that has a good distribution over [0,1)
        LinearCongruentialUG p = new LinearCongruentialUG(1243124, 244, 16452, 93752);
        p.getRandomNumber(1000000);
    }

    public static void testMidSquareMethod() throws Exception {
        //example from the book
        MidSquareUG r = new MidSquareUG(7182);
        r.getRandomNumber(15);
        System.out.println("-----------------------------");
        // this willl show it quickly goes to zero
        MidSquareUG p = new MidSquareUG(1009);
        p.getRandomNumber(12);
        System.out.println("-----------------------------");
        // even quicker to 0
        MidSquareUG q = new MidSquareUG(1000);
        q.getRandomNumber(3);
    }
}
