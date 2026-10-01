package medcare.model;

/**
 * Plain data holder mirroring the "Equipment" entity of the SRS
 * Entity-Relationship Diagram (Section 6.4) and database Table 6.3.
 */
public class Equipment {
    public String id;
    public String name;
    public String category;
    public String vendor;
    public double pricePerDay;
    public double deposit;
    public String condition;
    public String availability;
    public double vendorRating;
    public String city;

    // --- USP fields: Hygiene / Sanitization tracking ---
    public String lastSanitizedDate;
    public String sanitizationTechnician;
    public String sanitizationMethod;
    public int hygieneScore; // 0-100

    public Equipment(String id, String name, String category, String vendor, double pricePerDay,
                      double deposit, String condition, String availability, double vendorRating,
                      String city, String lastSanitizedDate, String sanitizationTechnician,
                      String sanitizationMethod, int hygieneScore) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.vendor = vendor;
        this.pricePerDay = pricePerDay;
        this.deposit = deposit;
        this.condition = condition;
        this.availability = availability;
        this.vendorRating = vendorRating;
        this.city = city;
        this.lastSanitizedDate = lastSanitizedDate;
        this.sanitizationTechnician = sanitizationTechnician;
        this.sanitizationMethod = sanitizationMethod;
        this.hygieneScore = hygieneScore;
    }

    @Override
    public String toString() { return name; }
}
