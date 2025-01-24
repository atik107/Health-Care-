package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import android.Manifest;
import android.content.DialogInterface;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class EmAmbActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;
    private static final int SEND_SMS_PERMISSION_REQUEST_CODE = 123;
    double latitude, longitude;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 100;
    private TextView locationTextView;
    Button callButton,generateLinkButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_em_amb);

        locationTextView = findViewById(R.id.locationTextView);
        Button getLocationButton = findViewById(R.id.getLocationButton);
        generateLinkButton = findViewById(R.id.generateLinkButton);
        callButton = findViewById(R.id.callButton);
        generateLinkButton.setEnabled(false);
        callButton.setEnabled(false);

        getLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation();
            }
        });

        generateLinkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmationDialog();
            }
        });

        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EmAmbActivity.this);
                builder.setTitle("Are you sure you want to make the call?");
                builder.setMessage("This will connect you to our Emergency Health Desk. Progress might be slow or unavailable. Agree to proceed");
                builder.setPositiveButton("Call", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String number = "+1234567890";
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:" + number));
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("Cancel", null);
                builder.show();
            }
        });
    }

    private void getLocation() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            // Permission granted, get the location
            getLocationAndDisplayAddress();
        }
    }

    private void getLocationAndDisplayAddress() {
        // Check if location services are enabled
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Fetching Current Address...\nIt may take a while.");
        progressDialog.setCancelable(true);
        progressDialog.show();
        if (locationManager != null && locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            try {
                locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        // Get latitude and longitude from the Location object
                        double latitude = location.getLatitude();
                        double longitude = location.getLongitude();

                        // Get the address from latitude and longitude
                        Geocoder geocoder = new Geocoder(EmAmbActivity.this, Locale.getDefault());
                        try {
                            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
                            if (addresses != null && addresses.size() > 0) {
                                Address fetchedAddress = addresses.get(0);
                                String addressText = fetchedAddress.getAddressLine(0);
                                // Display the address in locationTextView
                                locationTextView.setText("Current Address: " + addressText);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        progressDialog.dismiss();
                        generateLinkButton.setEnabled(true);
                        callButton.setEnabled(true);
                    }

                    // Other overridden methods of LocationListener (onProviderEnabled, onStatusChanged)...

                }, null);
            } catch (SecurityException e) {
                e.printStackTrace();
                progressDialog.dismiss();
            }

        } else {
            Toast.makeText(this, "Please enable GPS", Toast.LENGTH_SHORT).show();
        }
    }

    private void showConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Send Message for Emergency Ambulance?");
        builder.setMessage("The price is 5000/- to 10000/- depending on the location. If Accepted the Ambulance will be Dispatched Immediately");
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                generateGoogleMapsLink();
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    private void generateGoogleMapsLink() {
        // Check if location permissions are granted
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Fetching location...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            // Permission granted, get the location and generate the Google Maps link
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            if (locationManager != null) {
                try {
                    Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if (lastKnownLocation != null) {
                        latitude = lastKnownLocation.getLatitude();
                        longitude = lastKnownLocation.getLongitude();

                        // Generate Google Maps link using latitude and longitude
                        String googleMapsLink = "https://www.google.com/maps?q=" + latitude + "," + longitude;

                        // Send location via SMS
                        sendLocationViaSMS(latitude, longitude);
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(this, "Location not available", Toast.LENGTH_SHORT).show();
                    }
                } catch (SecurityException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
            }
        }
    }

    private void sendLocationViaSMS(double latitude, double longitude) {
        String phoneNumber = "+1234567890"; // Replace this with the recipient's phone number

        // Check if the SEND_SMS permission is granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            // If not granted, request the permission
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, SEND_SMS_PERMISSION_REQUEST_CODE);
        } else {
            String locationMessage = "https://www.google.com/maps?q=" + latitude + "," + longitude +
                    "\nDispatch to the location Immediately";

            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, locationMessage, null, null);

            progressDialog.dismiss();
            showSuccessDialog();
        }
    }

    private void showSuccessDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Message Sent");
        builder.setMessage("Location sent successfully!");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(EmAmbActivity.this, MenuActivity.class);
                startActivity(intent);
                finish();
            }
        });
        builder.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation();
            } else {
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == SEND_SMS_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, now send the SMS
                sendLocationViaSMS(latitude, longitude); // Replace with your actual latitude and longitude
            } else {
                Toast.makeText(this, "SMS permission denied. Cannot send SMS.", Toast.LENGTH_SHORT).show();
            }
        }
    }

}