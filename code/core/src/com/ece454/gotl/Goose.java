package com.ece454.gotl;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Goose {
    public static final String PLAYER_IMG_PATH = "goose.png";
    public Texture texture = new Texture(Goose.PLAYER_IMG_PATH);
//    public int widthInTexture = texture.getWidth()/columns;
    public int widthInTexture = 60;
//    public int heightInTexture = 60;
    public int heightInTexture = texture.getHeight()/rows;
//    public int heightInTexture = 50;
    public int xPositionInTexture = texture.getWidth()/columns - widthInTexture;
    public int yPositionInTexture = texture.getHeight()/rows;


    private boolean isJumping = false;
    private boolean isDead = false;
    private static final int rows = 10;
    private static final int columns = 8;
    private static final int BOX_SIZE = 32;
    private static final float PLAYER_DENSITY = 1.0f;
    public static final float JUMP_FORCE = 250f;
    public static final float RUN_FORCE = 5f;
    private static final float PLAYER_START_X = 12f;
    private static final float PLAYER_START_Y = 12f;
    private Body body;

    public Goose(World world) {
        createBoxBody(world, PLAYER_START_X, PLAYER_START_Y);
    }

    private void createBoxBody(World world, float x, float y) {
        BodyDef bdef = new BodyDef();
        bdef.fixedRotation = true;
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.position.set(x, y);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(BOX_SIZE / Game.PIXEL_PER_METER / 2, BOX_SIZE / Game.PIXEL_PER_METER / 2);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = PLAYER_DENSITY;
        body = world.createBody(bdef);
        body.createFixture(fixtureDef).setUserData(this);
    }

    public Body getBody() {
        return body;
    }

    public void hit() {
        isDead = true;
    }
    public void setJumping(boolean jumping) {
        isJumping = jumping;
    }
    public boolean isJumping() {
        return isJumping;
    }
    public boolean isDead() {
        return isDead;
    }
}