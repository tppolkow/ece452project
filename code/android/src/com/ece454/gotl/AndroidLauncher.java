package com.ece454.gotl;

import android.content.Context;
import android.content.Intent;
import android.hardware.display.VirtualDisplay;
import android.media.MediaRecorder;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.internal.CallbackManagerImpl;
import com.facebook.login.LoginResult;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.ShareVideo;
import com.facebook.share.model.ShareVideoContent;
import com.facebook.share.widget.ShareDialog;

import handlers.VideoShareHandler;
import java.io.IOException;

public class AndroidLauncher extends AndroidApplication {

	public static final String TAG = "MainActivity";
	public static final int PERMISSION_CODE = 1;
	public static int mScreenDensity;
	public static final int DISPLAY_WIDTH = 480;
	public static final int DISPLAY_HEIGHT = 640;
	private VideoShareHandler videoShareHandler;
	private static Context context;
	private CallbackManager callbackManager;
	private ShareDialog shareDialog;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = getApplicationContext();
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
		callbackManager = CallbackManager.Factory.create();
		videoShareHandler = new VideoShareHandler(this);
		shareDialog = new ShareDialog(this);
		shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
			@Override
			public void onSuccess(Sharer.Result loginResult) {
				System.out.println("SUCCCESS");
				// App code
			}

			@Override
			public void onCancel() {
				System.out.println("CANCEL");
				// App code
			}

			@Override
			public void onError(FacebookException exception) {
				System.out.println("ERROR");
				exception.printStackTrace();
				// App code
			}
		});
	}

	public static Context getAppContext()
	{
		return context;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (!FacebookSdk.isFacebookRequestCode(requestCode)) {
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
		} else {
//			if (requestCode == CallbackManagerImpl.RequestCodeOffset.Login.toRequestCode()) {
//				//login
//				startActivity(Intent.createChooser(data, "Share via"));
//			}
//			else if (requestCode == CallbackManagerImpl.RequestCodeOffset.Share.toRequestCode()){
//				//share
//				startActivity(Intent.createChooser(data, "Share via"));
//			}
			callbackManager.onActivityResult(requestCode, resultCode, data);
			super.onActivityResult(requestCode, resultCode, data);
		}
	}

	public void postVideo(Uri videoFileUri)
	{
		ShareVideo video = new ShareVideo.Builder()
				.setLocalUrl(videoFileUri)
				.build();
        final ShareVideoContent content = new ShareVideoContent.Builder()
                .setVideo(video)
                .setContentDescription(VideoShareHandler.VIDEO_COMMENT_STRING)
                .build();
//		final ShareLinkContent content = new ShareLinkContent.Builder()
//				.setContentUrl(Uri.parse("https://developers.facebook.com"))
//				.build();
		if (shareDialog.canShow(content))
		{
			Gdx.app.postRunnable(new Runnable() {
				@Override
				public void run() {
					shareDialog.show(content);
				}
			});
		}
		else
		{
            /*Snackbar.make(mainActivity.findViewById(R.id.parent_layout), "YEET", Snackbar.LENGTH_LONG)
                    .setAction("CLOSE", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) { }
                    }).show();
                    */
		}
	}
}
