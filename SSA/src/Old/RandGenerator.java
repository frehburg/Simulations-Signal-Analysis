package Old;

public interface RandGenerator {
    public int getInt(int min, int max);
    public int getInt(int min, int max, int seed);
    public double getDouble(double min, double max);
    public double getDouble(double min, double max, int seed);
    public boolean getBoolean();
    public boolean getBoolean(int seed);
}
