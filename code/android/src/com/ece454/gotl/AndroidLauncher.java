package com.ece454.gotl;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.MediaRecorder;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.ece454.gotl.GotlGame;

import java.io.IOException;

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


	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LayoutInflater inflater = getLayoutInflater();
		RelativeLayout layout = new RelativeLayout(this);
		View myLayout = inflater.inflate(R.layout.wrapper, layout, false);


		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		mScreenDensity = metrics.densityDpi;

		mMediaRecorder = new MediaRecorder();
		initRecorder();
		prepareRecorder();

		mProjectionManager = (MediaProjectionManager) getSystemService
				(Context.MEDIA_PROJECTION_SERVICE);



		mMediaProjectionCallback = new MediaProjectionCallback();



		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useAccelerometer = false;
		config.useCompass = false;
		layout.addView(initializeForView(new GotlGame(), config));
		layout.addView(myLayout);
		setContentView(layout);

		mToggleButton = (ToggleButton) findViewById(R.id.toggle);
		mToggleButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onToggleScreenShare(v);
			}
		});
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (mMediaProjection != null) {
			mMediaProjection.stop();
			mMediaProjection = null;
		}
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
			mToggleButton.setChecked(false);
			return;
		}
		mMediaProjection = mProjectionManager.getMediaProjection(resultCode, data);
		mMediaProjection.registerCallback(mMediaProjectionCallback, null);
		mVirtualDisplay = createVirtualDisplay();
		mMediaRecorder.start();
	}

	public void onToggleScreenShare(View view) {
		if (((ToggleButton) view).isChecked()) {
			shareScreen();
		} else {
			mMediaRecorder.stop();
			mMediaRecorder.reset();
			Log.v(TAG, "Recording Stopped");
			stopScreenSharing();
			initRecorder();
			prepareRecorder();
		}
	}

	private void shareScreen() {
		if (mMediaProjection == null) {
			startActivityForResult(mProjectionManager.createScreenCaptureIntent(), PERMISSION_CODE);
			return;
		}
		mVirtualDisplay = createVirtualDisplay();
		mMediaRecorder.start();
	}

	private void stopScreenSharing() {
		if (mVirtualDisplay == null) {
			return;
		}
		mVirtualDisplay.release();
		//mMediaRecorder.release();
	}

	private VirtualDisplay createVirtualDisplay() {
		return mMediaProjection.createVirtualDisplay("MainActivity",
				DISPLAY_WIDTH, DISPLAY_HEIGHT, mScreenDensity,
				DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,
				mMediaRecorder.getSurface(), null /*Callbacks*/, null /*Handler*/);
	}

	private class MediaProjectionCallback extends MediaProjection.Callback {
		@Override
		public void onStop() {
			if (mToggleButton.isChecked()) {
				mToggleButton.setChecked(false);
				mMediaRecorder.stop();
				mMediaRecorder.reset();
				Log.v(TAG, "Recording Stopped");
				initRecorder();
				prepareRecorder();
			}
			mMediaProjection = null;
			stopScreenSharing();
			Log.i(TAG, "MediaProjection Stopped");
		}
	}

	private void prepareRecorder() {
		try {
			mMediaRecorder.prepare();
		} catch (IllegalStateException e) {
			e.printStackTrace();
			finish();
		} catch (IOException e) {
			e.printStackTrace();
			finish();
		}
	}

	private void initRecorder() {
		// mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.SURFACE);
		mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
		mMediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
		// mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		mMediaRecorder.setVideoEncodingBitRate(512 * 1000);
		mMediaRecorder.setVideoFrameRate(30);
		mMediaRecorder.setVideoSize(DISPLAY_WIDTH, DISPLAY_HEIGHT);
		mMediaRecorder.setOutputFile(getFilesDir() + "/capture.mp4");
		Log.v(TAG, getFilesDir() + "/capture.mp4");
	}
}
