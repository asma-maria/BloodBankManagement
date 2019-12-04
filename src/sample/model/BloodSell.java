package sample.model;

public class BloodSell {
    private int id;
    private String blood;
    private String price;
    private String presentDate;
    private String qtyBlood;
    private String result;

    public BloodSell(){

    }
    public BloodSell(int id,String presentDate, String blood,String qtyBlood,String price, String result){
        this.id = id;
        this.price = price;
        this.blood = blood;
        this.result=result;
        this.presentDate = presentDate;
        this.qtyBlood = qtyBlood;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBlood() {
        return blood;
    }

    public void setBlood(String blood) {
        this.blood = blood;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPresentDate() {
        return presentDate;
    }

    public void setPresentDate(String presentDate) {
        this.presentDate = presentDate;
    }

    public String getQtyBlood() {
        return qtyBlood;
    }

    public void setQtyBlood(String qtyBlood) {
        this.qtyBlood = qtyBlood;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}

