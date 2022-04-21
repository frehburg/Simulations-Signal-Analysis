package Utils;

public class Interval {
    private final boolean upperInclusive, upperExclusive;
    private final boolean lowerInclusive, lowerExclusive;
    private final double lowerBound, upperBound;

    public Interval(double lowerBound, double upperBound, boolean lowerInclusive, boolean upperInclusive) {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.lowerInclusive = lowerInclusive;
        this.lowerExclusive = !lowerInclusive;
        this.upperInclusive = upperInclusive;
        this.upperExclusive = !upperInclusive;
    }

    /**
     * Returns true if the number d is contained within the interval
     * @param d
     * @return
     */
    public boolean isIn(double d) {
        if(lowerInclusive && upperInclusive) {
            if(d >= lowerBound && d <= upperBound) {
                return true;
            }
            return false;
        } else if(lowerInclusive && upperExclusive) {
            if(d >= lowerBound && d < upperBound) {
                return true;
            }
            return false;
        } else if(lowerExclusive && upperInclusive) {
            if(d > lowerBound && d <= upperBound) {
                return true;
            }
            return false;
        } else if(lowerExclusive && upperExclusive) {
            if(d > lowerBound && d < upperBound) {
                return true;
            }
            return false;
        }
        return false;
    }

    public boolean isUpperInclusive() {
        return upperInclusive;
    }

    public boolean isUpperExclusive() {
        return upperExclusive;
    }

    public boolean isLowerInclusive() {
        return lowerInclusive;
    }

    public boolean isLowerExclusive() {
        return lowerExclusive;
    }

    public double getLowerBound() {
        return lowerBound;
    }

    public double getUpperBound() {
        return upperBound;
    }
}
