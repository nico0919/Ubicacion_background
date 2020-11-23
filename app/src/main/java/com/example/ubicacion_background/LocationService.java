package com.example.ubicacion_background;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Delayed;

public class LocationService extends Service {

    private final IBinder binder = new LocationBinder();
    private static FusedLocationProviderClient fusedLocationProviderClient;
    private static double latitud;
    private static double longitud;
    private static String localidad;
    private static String pais;
    private static String direccion;



    public class LocationBinder extends Binder{
        LocationService getLocationService(){
            return LocationService.this;

        }
    }


    public LocationService() {
    }

    public void onCreate(){

        Log.i("Location service","Created");
        getLocation();

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void onUnbind(){

    }

    public static double getLatitud() {
        return latitud;
    }

    public static double getLongitud() {
        return longitud;
    }

    public static String getLocalidad() {
        return localidad;
    }

    public static String getPais() {
        return pais;
    }

    public static String getDireccion() {
        return direccion;
    }

/*  if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(
                new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        Location location = task.getResult();
                        if (location != null) {
                            Geocoder geocoder = new Geocoder(MainActivity.this,
                                    Locale.getDefault());
                            try {
                                List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),
                                        location.getLongitude(), 1);

                                textView1.setText("Latitud" + addresses.get(0).getLatitude());
                                textView2.setText("Longitud" + addresses.get(0).getLongitude());
                                textView3.setText("Pais" + addresses.get(0).getCountryName());
                                textView4.setText("Localidad" + addresses.get(0).getLocality());
                                textView5.setText("Dirrecion" + addresses.get(0).getAddressLine(0));

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });



    }*/


    public void getLocation(){
        final Handler handler = new Handler();
        handler.post(new Runnable() {
                         @Override
                         public void run() {


                             if (ActivityCompat.checkSelfPermission(LocationService.this
                                     , Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ) {


                                 fusedLocationProviderClient.getLastLocation().addOnCompleteListener(
                                         new OnCompleteListener<Location>() {
                                             @Override
                                             public void onComplete(@NonNull Task<Location> task) {
                                                 Location location = task.getResult();
                                                 if (location != null) {
                                                     Geocoder geocoder = new Geocoder(LocationService.this,
                                                             Locale.getDefault());
                                                     try {
                                                         List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),
                                                                 location.getLongitude(), 1);


                                                         latitud = addresses.get(0).getLatitude();
                                                         longitud = addresses.get(0).getLongitude();
                                                         pais = addresses.get(0).getCountryName();
                                                         localidad = addresses.get(0).getLocality();
                                                         direccion = addresses.get(0).getAddressLine(0);

                                                         Log.i("Valor latitud", " " + latitud);
                                                     } catch (IOException e) {
                                                         e.printStackTrace();
                                                     }
                                                 }
                                             }
                                         });

                             }

                Log.i("Service getLocation", "post delayed...");
                handler.postDelayed(this,5000);
            }
        });
    }
}