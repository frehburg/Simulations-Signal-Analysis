package Utils;

import java.util.ArrayList;

public class AllDivisors {
    public static ArrayList<Integer> getDivisors(int n) {
        ArrayList<Integer> divisors = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            if (n % i == 0) {
                divisors.add(i);
            }
        }
        return divisors;
    }
}
