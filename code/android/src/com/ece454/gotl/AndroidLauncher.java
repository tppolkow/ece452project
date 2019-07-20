package com.ece454.gotl;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import handlers.VideoShareHandler;

public class AndroidLauncher extends AndroidApplication {

	private static final String TAG = "MainActivity";
	private static final int PERMISSION_CODE = 1;
	private int mScreenDensity;
	private MediaProjectionManager mProjectionManager;
	private static final int DISPLAY_WIDTH = 480;
	private static final int DISPLAY_HEIGHT = 640;
	private MediaProjection mMediaProjection;
	private VirtualDisplay mVirtualDisplay;
	private MediaProjectionCallback mMediaProjectionCallback;
	private ToggleButton mToggleButton;
	private MediaRecorder mMediaRecorder;

	private GotlGame game;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LayoutInflater inflater = getLayoutInflater();
		RelativeLayout layout = new RelativeLayout(this);
		View myLayout = inflater.inflate(R.layout.wrapper, layout, false);

		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		mScreenDensity = metrics.densityDpi;

		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useAccelerometer = false;
		config.useCompass = false;

		layout.addView(initializeForView(new GotlGame(), config));
		layout.addView(myLayout);
		setContentView(layout);

		videoShareHandler = new VideoShareHandler(this);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode != PERMISSION_CODE) {
			Log.e(TAG, "Unknown request code: " + requestCode);
			return;
		}
		if (resultCode != RESULT_OK) {
			Toast.makeText(this,
					"Screen Cast Permission Denied", Toast.LENGTH_SHORT).show();

			videoShareHandler.toggleOff();
			return;
		}
		videoShareHandler.beginRecording(resultCode, data);
	}
}
