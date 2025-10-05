package fleet;

import Util.Date;

public class Truck extends Vehicle{
    private static final double TRUCK_RATE = 0.50;

    public Truck(String plate) {
        super(plate);
    }

    public Truck(String plate, Date obtained, Make make, int mileage) {
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
