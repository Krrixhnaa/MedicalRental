package medcare.util;

import medcare.model.Booking;
import medcare.model.Equipment;
import java.util.ArrayList;
import java.util.List;

/** Static sample/demo data used to populate the GUI prototype screens. */
public class DummyData {

    public static List<Equipment> equipmentList() {
        List<Equipment> list = new ArrayList<>();
        list.add(new Equipment("EQ-1042", "Foldable Wheelchair (Deluxe)", "Mobility Aids",
                "CareWell Medical Supplies", 60, 1500, "Good", "Available", 4.6, "Jaipur",
                "30 Aug 2026, 10:15 AM", "R. Kumar", "UV-C Sterilisation + Chemical Wipe", 98));
        list.add(new Equipment("EQ-1108", "Electric Hospital Bed", "Hospital Beds & Furniture",
                "MedRent Jaipur", 250, 5000, "New", "Available", 4.8, "Jaipur",
                "02 Sep 2026, 09:00 AM", "S. Verma", "Autoclave + UV-C Sterilisation", 100));
        list.add(new Equipment("EQ-1183", "Oxygen Concentrator 5L", "Respiratory Support",
                "LifeCare Rentals", 180, 4000, "Good", "Rented", 4.5, "Jaipur",
                "28 Aug 2026, 04:30 PM", "R. Kumar", "Chemical Disinfection", 95));
        list.add(new Equipment("EQ-1225", "CPAP Machine (Auto)", "Respiratory Support",
                "CareWell Medical Supplies", 220, 6000, "Good", "Available", 4.4, "Jaipur",
                "01 Sep 2026, 11:45 AM", "A. Sharma", "UV-C Sterilisation", 97));
        list.add(new Equipment("EQ-1301", "Patient Vital Monitor", "Patient Monitoring",
                "MedRent Jaipur", 300, 7000, "Fair", "Maintenance", 4.2, "Jaipur",
                "20 Aug 2026, 02:00 PM", "S. Verma", "Chemical Disinfection", 88));
        list.add(new Equipment("EQ-1356", "Walker with Wheels", "Mobility Aids",
                "LifeCare Rentals", 40, 800, "Good", "Available", 4.7, "Jaipur",
                "31 Aug 2026, 08:20 AM", "A. Sharma", "UV-C Sterilisation + Chemical Wipe", 99));
        return list;
    }

    public static List<Booking> bookingList() {
        List<Booking> list = new ArrayList<>();
        list.add(new Booking("BK-20458", "Foldable Wheelchair (Deluxe)", "01 Sep 2026", "07 Sep 2026", "Delivered", 420));
        list.add(new Booking("BK-20461", "Electric Hospital Bed", "03 Sep 2026", "17 Sep 2026", "Confirmed", 3500));
        list.add(new Booking("BK-20392", "Oxygen Concentrator 5L", "18 Aug 2026", "25 Aug 2026", "Returned", 1260));
        return list;
    }

    public static List<String> categories() {
        List<String> c = new ArrayList<>();
        c.add("All Categories");
        c.add("Mobility Aids");
        c.add("Hospital Beds & Furniture");
        c.add("Respiratory Support");
        c.add("Patient Monitoring");
        c.add("Home-Care Accessories");
        return c;
    }

    public static List<String> cities() {
        List<String> c = new ArrayList<>();
        c.add("Jaipur");
        c.add("Delhi NCR");
        c.add("Mumbai");
        c.add("Bengaluru");
        return c;
    }
}
