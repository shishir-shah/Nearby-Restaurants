package project.sideproject.com.zumperinterview;

import project.sideproject.com.zumperinterview.model.RestaurantModel.RestaurantModel;
import project.sideproject.com.zumperinterview.model.search.Places;
import project.sideproject.com.zumperinterview.model.search.Result;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by Shishir on 2/4/2017.
 */
public class AsyncLoad {

    public static Observable<RestaurantModel> getObservable(final Places model){

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
                        item.setRating(result.getRating());

                        /*Inconsistency in the API
                        * Some response have no price level*/
                        if (result.getPriceLevel() != null) {
                            item.setPriceLevel(result.getPriceLevel());
                        }

                        item.setVicinity(result.getVicinity());

                        item.setLatitude(result.getGeometry().getLocation().getLat());
                        item.setLongitude(result.getGeometry().getLocation().getLat());
                        subscriber.onNext(item);
                    }
                    subscriber.onCompleted();
                }
            });
        }else return getNullObservable();

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
