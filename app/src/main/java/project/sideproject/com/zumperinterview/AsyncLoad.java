package project.sideproject.com.zumperinterview;

import project.sideproject.com.zumperinterview.model.RestaurantModel.RestaurantModel;
import project.sideproject.com.zumperinterview.model.item_search.Restaurant;
import project.sideproject.com.zumperinterview.model.search.Places;
import project.sideproject.com.zumperinterview.model.search.Result;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by Shishir on 2/4/2017.
 */
public class AsyncLoad {

    public static Observable<RestaurantModel> getObservable(final Places model, final String key){

        String status = model.getStatus();
        if(status == null) return getNullObservable();

        if (status.equalsIgnoreCase("OK")) {

            return Observable.create(new Observable.OnSubscribe<RestaurantModel>() {

                @Override
                public void call(Subscriber<? super RestaurantModel> subscriber) {
                    for (Result result : model.getResults()) {

                        RestaurantModel item = new RestaurantModel();

                        item.setName(result.getName());
                        item.setPlaceId(result.getPlaceId());
                        item.setIcon(result.getIcon());
                        item.setVicinity(result.getVicinity());

                        if (result.getRating() != null) {
                            item.setRating(result.getRating());
                        }

                        /*Inconsistency in the API
                        * Some response have no price level*/
                        if (result.getPriceLevel() != null) {
                            item.setPriceLevel(result.getPriceLevel());
                        }

                        Double lat = result.getGeometry().getLocation().getLat();
                        Double lon = result.getGeometry().getLocation().getLng();

                        if (lat != null && lon != null) {
                            item.setLatitude(result.getGeometry().getLocation().getLat());
                            item.setLongitude(result.getGeometry().getLocation().getLat());
                        }

                        setImageForItem(item, result, key);

                        subscriber.onNext(item);
                    }
                    subscriber.onCompleted();
                }
            });
        }else return getNullObservable();

    }

    public static Observable<RestaurantModel> getSingleRestaurant(final Restaurant model, final String key){

        if(model == null){
            return getNullObservable();
        }
        else {
            return Observable.create(new Observable.OnSubscribe<RestaurantModel>() {
                @Override
                public void call(Subscriber<? super RestaurantModel> subscriber) {

                    project.sideproject.com.zumperinterview.model.item_search.Result result = model.getResult();

                    RestaurantModel item = new RestaurantModel();
                    if(result.getName() != null){
                        item.setName(result.getName());
                    }

                    if(result.getFormattedAddress() != null){
                        item.setFormattedAddress(result.getFormattedAddress());
                    }

                    if(result.getFormattedPhoneNumber() != null){
                        item.setPhoneNumber(result.getFormattedPhoneNumber());
                    }

                    if (result.getRating() != null) {
                        item.setRating(result.getRating());
                    }

                    Double lat = result.getGeometry().getLocation().getLat();
                    Double lon = result.getGeometry().getLocation().getLng();

                    if (lat != null && lon != null) {
                        item.setLatitude(result.getGeometry().getLocation().getLat());
                        item.setLongitude(result.getGeometry().getLocation().getLat());
                    }

                    setImageForItem(item, result, key);

                    subscriber.onNext(item);
                    subscriber.onCompleted();
                }
            });
        }

    }

    private static void setImageForItem(RestaurantModel item, Result result, String currentKey) {

        String photoReference="";

        if(result.getPhotos() != null){
            photoReference = "&photoreference="+result.getPhotos().get(0).getPhotoReference();
        }

        setImage(item,currentKey,photoReference);
    }

    private static void setImageForItem(RestaurantModel item, project.sideproject.com.zumperinterview.model.item_search.Result result, String currentKey) {

        String photoReference="";

        if(result.getPhotos() != null){
            photoReference = "&photoreference="+result.getPhotos().get(0).getPhotoReference();
        }

        setImage(item,currentKey,photoReference);
    }

    private static void setImage(RestaurantModel item,String currentKey,String photoReference){
        String url = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=200&sensor=false";
        String key = "&key="+currentKey;
        item.setImage(url+key+photoReference);
    }

    // Method to return an observable for a null or a failed result

    public static Observable<RestaurantModel> getNullObservable(){

        return Observable.create(new Observable.OnSubscribe<RestaurantModel>(){

            @Override
            public void call(Subscriber<? super RestaurantModel> subscriber) {

                RestaurantModel item = new RestaurantModel();
                item.setName("No Item found, Please search again");

                subscriber.onNext(item);
                subscriber.onCompleted();
            }
        });
    }
}
