package base.mechanic;

public class Mechanic {
    private String id;
    private String name;
    private String shopName;
    private String email;
    private String phone;
    private String lat;
    private String lng;
    private String services;

    private String address;
    private String imgUrl;
    private String addNote;

    public Mechanic() {
        // Default constructor required for Firebase
    }


    public Mechanic(String id, String name, String shopName, String email, String phone, String lat, String lng, String services, String address, String imgUrl, String addNote) {
        this.id = id;
        this.name = name;
        this.shopName = shopName;
        this.email = email;
        this.phone = phone;
        this.lat = lat;
        this.lng = lng;
        this.services = services;
        this.address = address;
        this.imgUrl = imgUrl;
        this.addNote = addNote;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getAddress() {
        return address;
    }

    public void getAddress(String address) {
        this.address = address;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getAddNote() {
        return addNote;
    }

    public void setAddNote(String addNote) {
        this.imgUrl = addNote;
    }


}

