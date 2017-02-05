package project.sideproject.com.zumperinterview;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Shishir on 2/5/2017.
 */
public class GetLocation extends Service implements LocationListener{

    private Context mContext;
    private Location location;
    private double latitude, longitude;
    private LocationManager manager ;
    private boolean canGetLocation = false;
    private boolean isGPSEnabled = false, isNetworkEnabled = false;
    private long Min_Time_Between_Updates = 1000*60*1;
    private float Min_Distance_For_Updates = 10;

    public GetLocation(Context context){
        this.mContext = context;
        setLocation();

    }

    public Location getLocation(){

        Location currentLocation = null;

        if(location != null){
            currentLocation = location;
        }

        return currentLocation;
    }

    public boolean canGetLocation(){
        return this.canGetLocation;
    }

    private void setLocation() {
        try {

            manager = (LocationManager) mContext.getSystemService(mContext.LOCATION_SERVICE);
            isGPSEnabled = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNetworkEnabled = manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if(!isGPSEnabled && !isNetworkEnabled){
                Log.i("Provider", "No provider available");
            }
            else{
                this.canGetLocation = true;
                Log.i("Network ",String.valueOf(isNetworkEnabled));
                Log.i("GPS ",String.valueOf(isGPSEnabled));

                if(isNetworkEnabled){
                    manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, Min_Time_Between_Updates, Min_Distance_For_Updates, this);

                    if(manager!= null){
                        location = manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    }

                }
                if (isGPSEnabled) {

                    if(location == null){
                        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, Min_Time_Between_Updates, Min_Distance_For_Updates, this);

                        if(manager!= null){
                            location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        }
                    }
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void showSettings(){

        AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);

        dialog.setTitle("GPS is disabled");
        dialog.setMessage("Please enable GPS in order for this application to work");
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mContext.startActivity(intent);
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }

        });

        dialog.show();

    }

    @Override
    public void onLocationChanged(Location location) {
        this.location = location;
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
