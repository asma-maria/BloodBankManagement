package sample.model;

public class Donor {
    private int id;
    private String name;
    private String age;
    private String gender;
    private String blood;
    private String phone;
    private String lastDonatione;
    private String presentDonation;
    private String qtyBlood;
    private String address;
    private String email;

    public Donor() {
    }

    public Donor(int id, String name, String age, String gender, String blood, String phone, String lastDonatione, String presentDonation, String qtyBlood, String address, String email) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.blood = blood;
        this.phone = phone;
        this.lastDonatione = lastDonatione;
        this.presentDonation = presentDonation;
        this.qtyBlood = qtyBlood;
        this.address = address;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBlood() {
        return blood;
    }

    public void setBlood(String blood) {
        this.blood = blood;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLastDonatione() {
        return lastDonatione;
    }

    public void setLastDonatione(String lastDonatione) {
        this.lastDonatione = lastDonatione;
    }

    public String getPresentDonation() {
        return presentDonation;
    }

    public void setPresentDonation(String presentDonation) {
        this.presentDonation = presentDonation;
    }

    public String getQtyBlood() {
        return qtyBlood;
    }

    public void setQtyBlood(String qtyBlood) {
        this.qtyBlood = qtyBlood;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
