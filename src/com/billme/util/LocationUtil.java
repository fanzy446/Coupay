package com.billme.util;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

public class LocationUtil {
	LocationManager locationManager;

	public LocationUtil(Context context) {
		String serviceName = Context.LOCATION_SERVICE;
		locationManager = (LocationManager) context
				.getSystemService(serviceName);
	}

	public Location getLocationByGPS() {
		Log.i("error","使用GPS定位中");
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,
				0, locationListener);
		Location location = locationManager
				.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		return location;
	}

	public Location getLocationByNetwork() {
		locationManager.requestLocationUpdates(
				LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
		Location location = locationManager
				.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		return location;
	}

	public Location getLocation() {

		if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			return getLocationByGPS();
		} else if (locationManager
				.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
			return getLocationByNetwork();
		} else {
			Log.i("error","使用被动定位中");
			locationManager.requestLocationUpdates(
					LocationManager.PASSIVE_PROVIDER, 0, 0, locationListener);
			return locationManager
					.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
		}
	}

	private LocationListener locationListener = new LocationListener() {
		public void onLocationChanged(Location location) {

		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub

		}
	};
}
