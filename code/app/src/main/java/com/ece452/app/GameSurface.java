package com.ece452.app;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

public class GameSurface extends SurfaceView implements SurfaceHolder.Callback {

    private GameThread gameThread;

    private Goose goose;

    private DisplayMetrics displayMetrics;

    public GameSurface(Context context)  {
        super(context);

        displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);

        // Make Game Surface focusable so it can handle events. .
        this.setFocusable(true);

        // Sét callback.
        this.getHolder().addCallback(this);
    }

    public void update()  {
//        this.goose.update();
    }



    @Override
    public void draw(Canvas canvas)  {
        System.out.println("Gamesurface draw called");
        super.draw(canvas);

        this.goose.draw(canvas);
    }

    // Implements method of SurfaceHolder.Callback
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Bitmap gooseBitmap1 = BitmapFactory.decodeResource(this.getResources(),R.drawable.goose);
        System.out.println("DISPLAYMETRICS " + displayMetrics.widthPixels + " , " + displayMetrics.heightPixels);
        this.goose = new Goose(this,gooseBitmap1,0 ,displayMetrics.heightPixels);

        this.gameThread = new GameThread(this,holder);
        this.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.background));
        this.gameThread.setRunning(true);
        this.gameThread.start();
    }

    // Implements method of SurfaceHolder.Callback
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    // Implements method of SurfaceHolder.Callback
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry= true;
        while(retry) {
            try {
                this.gameThread.setRunning(false);

                // Parent thread must wait until the end of GameThread.
                this.gameThread.join();
            }catch(InterruptedException e)  {
                e.printStackTrace();
            }
            retry= true;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        System.out.println("touch event!");
        this.goose.jump();
        return true;
    }

}
