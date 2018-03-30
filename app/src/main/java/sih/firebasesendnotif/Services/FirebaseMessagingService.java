package sih.firebasesendnotif.Services;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.firebase.messaging.RemoteMessage;

import sih.firebasesendnotif.Fragments.DamLocationPicker;
import sih.firebasesendnotif.LocationPickerActivity;
import sih.firebasesendnotif.LoginActivity;
import sih.firebasesendnotif.NavbarActivity;
import sih.firebasesendnotif.R;
import sih.firebasesendnotif.SignupActivity;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
    private static final String TAG = "FirebaseService";
    double UserLat, UserLong;
    LocationManager locationManager;
    public FirebaseMessagingService() {
         locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
    }

    public void getLocation() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, CurrentlocationListener);
        //LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        double longitude = location.getLongitude();
        double latitude = location.getLatitude();
        String locLat = String.valueOf(latitude)+","+String.valueOf(longitude);
        Log.d("lat long",locLat);

    }


    LocationListener CurrentlocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {

            // TODO Auto-generated method stub
            if (location != null) {
                locationManager.removeUpdates(this);
                double lat = location.getLatitude();
                double lng = location.getLongitude();
                String strCurrentLatitude = String.valueOf(lat);
                String strCurrentLongitude = String.valueOf(lng);

                Log.e("Latitude FireMessage" ,strCurrentLatitude);
                Log.e("Longitude",strCurrentLongitude);
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if(remoteMessage.getData().size()>0){
            Log.d(TAG,"Message data : " +remoteMessage.getData());
        }

        if(remoteMessage.getNotification()!=null){
            String title=remoteMessage.getNotification().getTitle();
            String message = remoteMessage.getNotification().getBody();
            Log.d(TAG,"Title " + title);
            Log.d(TAG,"Body " + message);
            sendNotification(title,message);
        }
    }

    @Override
    public void onDeletedMessages() {

    }
    private void sendNotification(String title,String messageBody) {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        intent.putExtra("Current Location", true);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

//        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//        ft.replace(R.id.toPopulate, new DamLocationPicker());
//        ft.commit();

        String channelId = "8605+";
        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(title)
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }
        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}

