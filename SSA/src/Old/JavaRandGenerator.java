package Old;

import java.util.Random;

public class JavaRandGenerator implements RandGenerator {
    @Override
    public int getInt(int min, int max) {
        Random r = new Random();
        return r.nextInt(max + 1 - min) + min;
    }

    @Override
    public int getInt(int min, int max, int seed) {
        Random r = new Random(seed);
        return r.nextInt(max + 1 - min) + min;
    }

    @Override
    public double getDouble(double min, double max) {
        Random r = new Random();
        max -= 1;
        return r.nextDouble(max + 1 - min) + min;
    }

    @Override
    public double getDouble(double min, double max, int seed) {
        Random r = new Random(seed);
        max -= 1;
        return r.nextDouble(max + 1 - min) + min;
    }

    @Override
    public boolean getBoolean() {
        Random r = new Random();
        return r.nextBoolean();
    }

    @Override
    public boolean getBoolean(int seed) {
        Random r = new Random(seed);
        return r.nextBoolean();
    }
}
