package fleet;

public class Utility extends Vehicle{

    private static final double UTILITY_RATE = 0.40;

    public Utility(String plate) {
        super(plate);
    }

    public Utility(String plate, Date obtained, Make make, int mileage) {
        super(plate, obtained, make, mileage);
    }

    @Override
    public double charge(int mileageUsed){
        return 1.0;
    }

    @Override
    public double surcharge(int mileageUsed, boolean surcharge){
        return 1.0;
    }
}
