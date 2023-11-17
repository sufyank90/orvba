package base;

public class MyNote {

    private String id;
    private String shopName;
    private String mechanicName;
    private String number;
    private String address;
    private String note;

    public MyNote() {
    }

    public MyNote(String id, String shopName, String mechanicName, String number, String address, String note) {
        this.id = id;
        this.shopName = shopName;
        this.mechanicName = mechanicName;
        this.number = number;
        this.address = address;
        this.note = note;
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

    public String getMechanicName() {
        return mechanicName;
    }

    public void setMechanicName(String mechanicName) {
        this.mechanicName = mechanicName;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
