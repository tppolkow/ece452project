package com.ece452.app;

import android.graphics.Bitmap;

public abstract class GameObject {

    private static final int SCALE_FACTOR = 2;
    private static final int OFFSET = 5;

    protected Bitmap image;

    protected final int rowCount;
    protected final int colCount;

    protected final int WIDTH;
    protected final int HEIGHT;

    protected final int width;


    protected final int height;
    protected int x;
    protected int y;

    public GameObject(Bitmap image, int rowCount, int colCount, int x, int y)  {

        this.image = image;
        this.rowCount= rowCount;
        this.colCount= colCount;


        this.WIDTH = image.getWidth();
        this.HEIGHT = image.getHeight();

        this.width = this.WIDTH/ colCount;
        this.height= this.HEIGHT/ rowCount;

        this.x= x;
        this.y= y - (SCALE_FACTOR * height);
    }


    protected Bitmap createSubImageAt(int row, int col)  {
        // createBitmap(bitmap, x, y, width, height).
        Bitmap subImage = Bitmap.createBitmap(image, col* width, row* height + OFFSET ,width,height);
        System.out.println("Width " + width + " Height: " + height);
        Bitmap scaledImage = Bitmap.createScaledBitmap(subImage, SCALE_FACTOR*width, SCALE_FACTOR*height, false );
        return scaledImage;
    }

    public int getX()  {
        return this.x;
    }

    public int getY()  {
        return this.y;
    }


    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

}
