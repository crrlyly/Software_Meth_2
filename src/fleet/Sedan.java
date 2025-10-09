package fleet;

import Util.Date;

public class Sedan extends Vehicle{
    private static final double SEDAN_RATE = 0.30;

    public Sedan(String plate) {
        super(plate);
    }

    public Sedan(String plate, Date obtained, Make make, int mileage, Campus campus) {
        super(plate, obtained, make, mileage, campus);
    }

    @Override
    public double charge(int mileageUsed){
        return (mileageUsed * 1.79);
    }

    @Override
    public double surcharge(int mileageUsed, boolean surcharge){
        if(surcharge){
            return (.3 * mileageUsed < 35.99 ? (.3 * mileageUsed) : 35.99);
        }
        return -1;
    }
}
