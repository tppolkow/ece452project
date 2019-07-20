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

import handlers.SocialMediaHandler;

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

		mMediaRecorder = new MediaRecorder();
		initRecorder();
		prepareRecorder();

		mProjectionManager = (MediaProjectionManager) getSystemService
				(Context.MEDIA_PROJECTION_SERVICE);



		mMediaProjectionCallback = new MediaProjectionCallback();



		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useAccelerometer = false;
		config.useCompass = false;
		game = new GotlGame();
		game.setSocialMediaHandler(new SocialMediaHandler(this));
		initialize(game, config);
	}
}
