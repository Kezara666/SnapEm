package com.example.snapem;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * This is the component that is responsible for actual device administration.
 * It becomes the receiver when a policy is applied. It is important that we
 * subclass DeviceAdminReceiver class here and to implement its only required
 * method onEnabled().
 */
public class DemoDeviceAdminReceiver extends DeviceAdminReceiver {
	static final String TAG = "DemoDeviceAdminReceiver";


	/**
	 * Checks if the app has permission to write to device storage
	 *
	 * If the app does not has permission then the user will be prompted to grant permissions
	 *
	 * @param activity
	 */


	/** Called when this application is approved to be a device administrator. */
	@Override
	public void onEnabled(Context context, Intent intent) {
		super.onEnabled(context, intent);
		Toast.makeText(context, "device_admin_enabled",
				Toast.LENGTH_LONG).show();
		Log.d(TAG, "onEnabled");

	}

	/** Called when this application is no longer the device administrator. */
	@Override
	public void onDisabled(Context context, Intent intent) {
		super.onDisabled(context, intent);
		Toast.makeText(context, "device_admin_disabled",
				Toast.LENGTH_LONG).show();
		Log.d(TAG, "onDisabled");
	}



	@Override
	public void onPasswordChanged(Context context, Intent intent) {
		super.onPasswordChanged(context, intent);
		Log.d(TAG, "onPasswordChanged");
	}

	@Override
	public void onPasswordFailed(Context context, Intent intent) {
		super.onPasswordFailed(context, intent);
		Log.d(TAG, "onPasswordFailed");
		super.onPasswordFailed(context, intent);

	    System.out.println("Password Attempt is Failed...");




		try {
			Thread.sleep(1000);
			Intent k = new Intent(context, Locations.class);
			k.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			k.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			context.getApplicationContext().startActivity(k);


			Intent j = new Intent(context, CameraViewBack.class);
			j.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			j.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			context.getApplicationContext().startActivity(j);
			Thread.sleep(500);


			Intent i = new Intent(context, CameraView.class);
			i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			context.getApplicationContext().startActivity(i);

			Thread.sleep(500);








		} catch (InterruptedException e) {
			System.out.println(e);
		}






// Call it again






	}

	@Override
	public void onPasswordSucceeded(Context context, Intent intent) {
		super.onPasswordSucceeded(context, intent);
		Log.d(TAG, "onPasswordSucceeded");
	}






}