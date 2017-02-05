package project.sideproject.com.zumperinterview.model.RestaurantModel;

/**
 * Created by Shishir on 2/4/2017.
 */
public class RestaurantModel {
    private String name;
    private double rating;
    private String placeId;
    private double latitude;
    private double longitude;
    private Integer priceLevel = null;
    private String icon;
    private String vicinity;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String id) {
        this.placeId = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Integer getPriceLevel() {
        return priceLevel;
    }

    public void setPriceLevel(Integer priceLevel) {
        this.priceLevel = priceLevel;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getVicinity() {
        return vicinity;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }
}
