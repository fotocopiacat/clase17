package com.example.android.clase16

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

//se necesitau un listener, en este caso LOCATIONLISTENER.
// Se debe sobreescribir si o si 4 metodos
class MainActivity : AppCompatActivity(), LocationListener {

    //necesitamos un locationmanager (asi como los sensormanager)

    var lm : LocationManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lm = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        //el gps es un permiso importante entonces debe salir un popup que le indique al usuario
        //sobre el uso del GPS

        val permisos = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION)
        var granted = true
        for (permiso in permisos)
        {
            granted = granted and (ActivityCompat.checkSelfPermission(this, permiso) == PackageManager.PERMISSION_GRANTED)
        }
        if (!granted)
        {
            ActivityCompat.requestPermissions(this,permisos,1)
        }
        else {
            lm?.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000,1f,this)
        }
    }

    //estos son los 4 metodos a implementar obligatoriamente
    override fun onLocationChanged(location: Location?) {
        //se asigna textos a los labels del view activity_main
        lblLatitud.text = location?.latitude.toString()
        lblLongitud.text = location?.longitude.toString()
        lblAltitud.text = location?.altitude.toString()
    }

    //se debe hacer ctrol o  para sobrescribir este metodo
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode)
        {
            1->
            {
                lm = getSystemService(Context.LOCATION_SERVICE) as LocationManager
                var granted = true
                for (permiso in permissions) {
                    granted = granted and (ActivityCompat.checkSelfPermission(
                        this, permiso)== PackageManager.PERMISSION_GRANTED)
                }
                if (grantResults.size > 0 && granted)
                {
                    lm?.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000,1f,this)
                } else
                {
                    Toast.makeText(this, " permiso de gps requerido",Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        //se borra los To DO de los ultimos 3
    }

    override fun onProviderEnabled(provider: String?) {
        //se borra
    }

    override fun onProviderDisabled(provider: String?) {
        //se borra
    }

}
