package com.example.upam.gps_geolocalizacion;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {
  private Button btnActualizar;
  private Button btnDesactivar;
  private TextView lblatitud;
  private TextView lbllongitud;
  private TextView lblpresicion;
  private TextView lblEstado;


  private LocationManager locManager;
  private LocationListener locListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnActualizar=(Button)findViewById(R.id.BtnActualizar);
        btnDesactivar=(Button)findViewById(R.id.BtnDesactivar);
        lblatitud=(TextView)findViewById(R.id.LblPosLatitud);
        lbllongitud =(TextView)findViewById(R.id.LblPosLongitud);
        lblpresicion =(TextView)findViewById(R.id.LblPosicion);
        lblEstado=(TextView)findViewById(R.id.LblEstado);

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comenzarLocalizacion();
            }
        });

        btnDesactivar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locManager.removeUpdates(locListener);
            }
        });
    }

    private void comenzarLocalizacion()
    {
        locManager =
                (LocationManager)getSystemService(Context.LOCATION_SERVICE);


        Location loc =
                locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);


        mostrarPosicion(loc);


        locListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                mostrarPosicion(location);
            }
            public void onProviderDisabled(String provider){
                lblEstado.setText("Provider OFF");
            }
            public void onProviderEnabled(String provider){
                lblEstado.setText("Provider ON ");
            }
            public void onStatusChanged(String provider, int status, Bundle extras){
                Log.i("", "Provider Status: " + status);
                lblEstado.setText("Provider Status: " + status);
            }
        };

        locManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 30000, 0, locListener);
    }

    private void mostrarPosicion(Location loc) {
        if(loc != null)
        {
            lblatitud.setText("Latitud: " + String.valueOf(loc.getLatitude()));
            lbllongitud.setText("Longitud: " + String.valueOf(loc.getLongitude()));
            lblpresicion.setText("Precision: " + String.valueOf(loc.getAccuracy()));
            Log.i("", String.valueOf(loc.getLatitude() + " - " + String.valueOf(loc.getLongitude())));
        }
        else
        {
            lblatitud.setText("Latitud: (sin_datos)");
            lbllongitud.setText("Longitud: (sin_datos)");
            lblpresicion.setText("Precision: (sin_datos)");
        }
    }


}
