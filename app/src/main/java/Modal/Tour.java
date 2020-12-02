package Modal;

public class Tour {
    private String name;
    private String price;
    private String desc;
    private String image;
    private String tourId;

    public Tour (){}

    public Tour (String tName, String tPrice, String tDesc, String tImage, String tourId){
        this.name = tName;
        this.price = tPrice;
        this.desc  = tDesc;
        this.image = tImage;
        this.tourId = tourId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = "https://wetouryou.herokuapp.com/img/tours/" + image;
    }

    public String getTourId() {
        return tourId;
    }

    public void setTourId(String tourId) {
        this.tourId = tourId;
    }
}
