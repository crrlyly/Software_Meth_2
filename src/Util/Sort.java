package Util;
import fleet.Booking;

public class Sort {

    public static void sortByLocationThenPlateThenDate(List<Booking> list) {
        for (int i = 1; i < list.Size(); i++) {
            Booking key = list.get(i);
            int index = i - 1;

            while (index >= 0 && (
                    // compare plate alphabetically
                    list.get(index).getVehicle().getPlate().compareTo(key.getVehicle().getPlate()) > 0 ||

                            // if plates are equal, compare by beginDate
                            (list.get(index).getVehicle().getPlate().equals(key.getVehicle().getPlate()) &&
                                    list.get(index).getBegin().compareTo(key.getBegin()) > 0)
            )) {
                list.set(index + 1, list.get(index));
                index--;
            }
            list.set(index + 1, key);
        }
    }

    public static void sortByDeptThenEmployee(List<Booking> list) {
        for (int i = 1; i < list.Size(); i++) {
            Booking key = list.get(i);
            int index = i - 1;

            while (index >= 0 && (
                    // compare department alphabetically
                    list.get(index).getEmployee().getDept().name()
                            .compareTo(key.getEmployee().getDept().name()) > 0 ||

                            // if departments are equal, compare employees alphabetically by name
                            (list.get(index).getEmployee().getDept().name()
                                    .equals(key.getEmployee().getDept().name()) &&
                                    list.get(index).getEmployee().name()
                                            .compareTo(key.getEmployee().name()) > 0)
            )) {
                list.set(index + 1, list.get(index));
                index--;
            }
            list.set(index + 1, key);
        }
    }
}