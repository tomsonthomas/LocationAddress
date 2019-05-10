package com.example.myapplication;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    /** Called when the activity is first created. */
    String lat = "", lon = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnLocation = (Button) findViewById(R.id.btnLocation);
        btnLocation.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                // Acquire a reference to the system Location Manager
                LocationManager locationManager = (LocationManager) MainActivity.this.getSystemService(Context.LOCATION_SERVICE);
                locationManager.getAllProviders();
                // Define a listener that responds to location updates
                LocationListener locationListener = new LocationListener() {
                    public void onLocationChanged(Location location) {
                        // Called when a new location is found by the network location provider.
                        lat = Double.toString(location.getLatitude());
                        lon = Double.toString(location.getLongitude());
                        TextView tv = (TextView) findViewById(R.id.txtLoc);
                        tv.setText("Your Location is:" + lat + "--" + lon);
                    }

                    public void onStatusChanged(String provider, int status, Bundle extras) {
                    }

                    public void onProviderEnabled(String provider) {
                    }

                    public void onProviderDisabled(String provider) {
                    }
                };
                // Register the listener with the Location Manager to receive location updates
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
            }
        });

        Button btnSend = (Button)findViewById(R.id.btnSend);
        btnSend.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               // postData(lat, lon);
            }
        });

        Button btnAdd = (Button)findViewById(R.id.btnAddress);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TextView tv = (TextView)findViewById(R.id.txtAddress);
                tv.setText(GetAddress(lat, lon));
            }
        });

    }

  /*  public void postData(String la, String lo) {
        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet  htget = new HttpGet("http://myappurl.com/Home/Book/"+la+"/"+lo);

        try {
            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(htget);
            String resp = response.getStatusLine().toString();
            Toast.makeText(this, resp, 5000).show();


        } catch (ClientProtocolException e) {
            Toast.makeText(this, "Error", 5000).show();
        } catch (IOException e) {
            Toast.makeText(this, "Error", 5000).show();
        }
    }*/
    public String GetAddress(String lat, String lon)
    {
        Geocoder geocoder = new Geocoder(this, Locale.ENGLISH);
        String ret = "";
        try {
            List<Address> addresses = geocoder.getFromLocation(Double.parseDouble(lat), Double.parseDouble(lon), 1);


            if(addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("Address:\n");
                strReturnedAddress.append(returnedAddress.getAddressLine(0)).append("\n");
                strReturnedAddress.append("Latitude:"+returnedAddress.getLatitude()).append("\n");
                strReturnedAddress.append("Longitude:"+returnedAddress.getLongitude()).append("\n");
                ret = strReturnedAddress.toString();
            }
            else{
                ret = "No Address returned!";
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            ret = "Can't get Address!";
        }
        return ret;
    }
}
