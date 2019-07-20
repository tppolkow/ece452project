package handlers;

import android.app.Activity;
import android.net.Uri;

import com.facebook.share.model.ShareVideo;
import com.facebook.share.model.ShareVideoContent;
import com.facebook.share.widget.ShareDialog;


public class SocialMediaHandler
{
    private static final String VIDEO_COMMENT_STRING = "Hey fb friends! Look at this gameplay of GOTL, very epic :O";
    private ShareDialog shareDialog;

    public SocialMediaHandler(Activity mainActivity)
    {
        shareDialog = new ShareDialog(mainActivity);
    }

    public void postVideo(Uri videoFileUri)
    {
        ShareVideo video = new ShareVideo.Builder()
                .setLocalUrl(videoFileUri)
                .build();
        ShareVideoContent content = new ShareVideoContent.Builder()
                .setVideo(video)
                .setContentDescription(VIDEO_COMMENT_STRING)
                .build();
        if (ShareDialog.canShow(ShareVideoContent.class))
        {
            shareDialog.show(content);
        }
    }
}
