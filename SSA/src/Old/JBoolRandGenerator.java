package Old;

import java.util.ArrayList;

public class JBoolRandGenerator implements RandGenerator {
    @Override
    public int getInt(int min, int max) {
        int intervalLength = max - min;
        //simulate random interval (0,intervalLength), later offset by min

        //calculate log with change of base formula
        int amtBits = (int) Math.ceil(Math.log(intervalLength)/Math.log(2));
        ArrayList<Boolean> bits = new ArrayList<>();

        JavaRandGenerator r = new JavaRandGenerator();
        for(int i = 0; i < amtBits; i++) {
            bits.add(r.getBoolean());
        }
        //calculate the random number
        int sum = 0;
        for(int i = 0; i < amtBits; i++) {
            boolean curBit = bits.get(i);
            if(curBit) sum += Math.pow(2,i);
        }

        if(sum <= intervalLength) {
            return sum + min;
        } else {
            return getInt(min, max);
        }
    }

    @Override
    public int getInt(int min, int max, int seed) {
        int intervalLength = max - min;
        //simulate random interval (0,intervalLength), later offset by min

        //calculate log with change of base formula
        int amtBits = (int) Math.ceil(Math.log(intervalLength)/Math.log(2));
        ArrayList<Boolean> bits = new ArrayList<>();

        JavaRandGenerator r = new JavaRandGenerator();
        for(int i = 0; i < amtBits; i++) {
            bits.add(r.getBoolean(seed));
        }
        //calculate the random number
        int sum = 0;
        for(int i = 0; i < amtBits; i++) {
            boolean curBit = bits.get(i);
            if(curBit) sum += Math.pow(2,i);
        }

        if(sum <= intervalLength) {
            return sum + min;
        } else {
            return getInt(min, max);
        }
    }

    @Override
    public double getDouble(double min, double max) {
        //TODO
        return 0;
    }

    @Override
    public double getDouble(double min, double max, int seed) {
        return 0;
    }

    @Override
    public boolean getBoolean() {
        return false;
    }

    @Override
    public boolean getBoolean(int seed) {
        return false;
    }
}
