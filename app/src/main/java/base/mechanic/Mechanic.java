package base.mechanic;

public class Mechanic {

    private String name;
    private String location;
    private String services;  // Add this field for services

    public Mechanic() {
        // Default constructor required for Firebase
    }

    public Mechanic(String name, String location, String services) {
        this.name = name;
        this.location = location;
        this.services = services;
    }

    public Mechanic(String userId, String shopName, String name, String email, String phone) {

    }

    // Getters and setters for each field

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }
}

