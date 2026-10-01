package medcare.model;

/** Mirrors the "Booking" entity (SRS Table 6.5). */
public class Booking {
    public String bookingId;
    public String equipmentName;
    public String startDate;
    public String endDate;
    public String status; // Pending, Confirmed, Delivered, Returned, Cancelled
    public double totalAmount;

    public Booking(String bookingId, String equipmentName, String startDate, String endDate,
                    String status, double totalAmount) {
        this.bookingId = bookingId;
        this.equipmentName = equipmentName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.totalAmount = totalAmount;
    }
}
