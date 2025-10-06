package fleet;

public class Utility extends Vehicle{
    public Utility(String plate) {
        super(plate);
    }

    public Utility(String plate, Date obtained, Make make, int mileage, Campus campus) {
        super(plate, obtained, make, mileage, campus);
    }

    @Override
    public double charge(int mileageUsed){
        return (mileageUsed * 1.99);
    }

    @Override
    public double surcharge(int mileageUsed, boolean surcharge){
        if(surcharge){
            return (mileageUsed * 1.79) + (.25*mileageUsed < 32.99 ? (.25*mileageUsed) : 32.99);
        }
        return -1;
    }
}
