package project.sideproject.com.zumperinterview.model;

/**
 * Created by Shishir on 2/4/2017.
 */
public class Data {

    private String name;
    private String rating;
    private String image;

    public Data(String name, String rating, String image){
        this.name = name;
        this.rating = rating;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getRating() {
        return rating;
    }

    public String getImage() {
        return image;
    }
}
