package fleet;

public class Sedan extends Vehicle{
    private static final double SEDAN_RATE = 0.30;

    public Sedan(String plate) {
        super(plate);
    }

    public Sedan(String plate, Date obtained, Make make, int mileage) {
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
