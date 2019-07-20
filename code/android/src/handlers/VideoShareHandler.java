package handlers;

import android.content.Context;
import android.content.Intent;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.MediaRecorder;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.net.Uri;
// import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.ToggleButton;

import com.badlogic.gdx.Gdx;
import com.ece454.gotl.AndroidLauncher;
import com.ece454.gotl.R;
import com.facebook.share.model.ShareVideo;
import com.facebook.share.model.ShareVideoContent;
import com.facebook.share.widget.ShareDialog;

import java.io.IOException;

import static com.ece454.gotl.AndroidLauncher.DISPLAY_HEIGHT;
import static com.ece454.gotl.AndroidLauncher.DISPLAY_WIDTH;
import static com.ece454.gotl.AndroidLauncher.PERMISSION_CODE;
import static com.ece454.gotl.AndroidLauncher.mScreenDensity;

public class VideoShareHandler
{
    private static final String VIDEO_COMMENT_STRING = "Hey fb friends! Look at this gameplay of GOTL, very epic :O";

    private ShareDialog shareDialog;
    private MediaProjectionManager projectionManager;
    private MediaProjection mediaProjection;
    private VirtualDisplay virtualDisplay;
    private MediaProjectionCallback mediaProjectionCallback;
    private ToggleButton toggleButton;
    private MediaRecorder mMediaRecorder;
    private AndroidLauncher mainActivity;

    public VideoShareHandler(AndroidLauncher mainActivity)
    {
        this.mainActivity = mainActivity;
        shareDialog = new ShareDialog(mainActivity);
        mediaProjectionCallback = new MediaProjectionCallback();
        mMediaRecorder = new MediaRecorder();
        projectionManager = (MediaProjectionManager)mainActivity.getSystemService(Context.MEDIA_PROJECTION_SERVICE);
//      initRecorder();
//      prepareRecorder();


        toggleButton = (ToggleButton)mainActivity.findViewById(R.id.toggle);
        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onToggleScreenShare(v);
            }
        });
    }

    private class MediaProjectionCallback extends MediaProjection.Callback {
        @Override
        public void onStop() {
            if (toggleButton.isChecked()) {
                toggleButton.setChecked(false);
                mMediaRecorder.stop();
                mMediaRecorder.reset();
                Log.v(AndroidLauncher.TAG, "Recording Stopped");
                initRecorder();
                prepareRecorder();
            }
            mediaProjection = null;
            stopScreenSharing();
            Log.i(AndroidLauncher.TAG, "MediaProjection Stopped");
        }
    }


    public void beginRecording(int resultCode, Intent data)
    {
        mediaProjection = projectionManager.getMediaProjection(resultCode, data);
        mediaProjection.registerCallback(mediaProjectionCallback, null);
        virtualDisplay = createVirtualDisplay();
        mMediaRecorder.start();
    }

    public void toggleOn()
    {
        toggleButton.setChecked(true);
    }

    public void toggleOff()
    {
        toggleButton.setChecked(false);

    }

    public void onToggleScreenShare(View view) {
        if (((ToggleButton) view).isChecked()) {
             initRecorder();
            Log.e(AndroidLauncher.TAG, "initialized recorder " );
             prepareRecorder();
            Log.e(AndroidLauncher.TAG, "prepared recorder " );
            startScreenSharing();
            Log.e(AndroidLauncher.TAG, "started sharing" );
        } else {
            mMediaRecorder.stop();
            mMediaRecorder.reset();
            Log.e(AndroidLauncher.TAG, "stopped and reset" );
            Log.v(AndroidLauncher.TAG, "Recording Stopped (toggle method)");
            stopScreenSharing();
            postVideo(Uri.parse(mainActivity.getFilesDir() + "/capture.mp4"));
        }
    }

    private void startScreenSharing() {
        if (mediaProjection == null) {
            mainActivity.startActivityForResult(projectionManager.createScreenCaptureIntent(), PERMISSION_CODE);
            return;
        }
        virtualDisplay = createVirtualDisplay();
        mMediaRecorder.start();
    }

    private void stopScreenSharing() {
        if (virtualDisplay == null) {
            return;
        }
        virtualDisplay.release();
        //mMediaRecorder.release();
    }

    private VirtualDisplay createVirtualDisplay() {
        return mediaProjection.createVirtualDisplay("MainActivity",
                DISPLAY_WIDTH, DISPLAY_HEIGHT, mScreenDensity,
                DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,
                mMediaRecorder.getSurface(), null /*Callbacks*/, null /*Handler*/);
    }


    private void prepareRecorder() {
        try {
            mMediaRecorder.prepare();
        } catch (IllegalStateException e) {
            e.printStackTrace();
            mainActivity.finish();
        } catch (IOException e) {
            e.printStackTrace();
            mainActivity.finish();
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
        mMediaRecorder.setOutputFile(mainActivity.getFilesDir() + "/capture.mp4");
        Log.v(AndroidLauncher.TAG, mainActivity.getFilesDir() + "/capture.mp4");
    }

    public void postVideo(Uri videoFileUri)
    {
        ShareVideo video = new ShareVideo.Builder()
                .setLocalUrl(videoFileUri)
                .build();
        final ShareVideoContent content = new ShareVideoContent.Builder()
                .setVideo(video)
                .setContentDescription(VIDEO_COMMENT_STRING)
                .build();
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