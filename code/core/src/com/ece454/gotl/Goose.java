package com.ece454.gotl;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import handlers.WorldManager;

public class Goose {
    public static final String PLAYER_IMG_PATH = "goose.png";
    public static final int MAX_JUMP_FORCE_Y = 1000;
    public static final int MAX_JUMP_FORCE_X = 130;
    public Texture texture;
    public int widthInTexture = 60;
    public int heightInTexture;
    public int xPositionInTexture;
    public int yPositionInTexture;


    private boolean isJumping = false;
    private boolean isDead = false;
    private boolean isLevelEnd = false;
    private static final int rows = 10;
    private static final int columns = 8;
    private static final int BOX_SIZE = 32;
    private static final float PLAYER_DENSITY = 1.0f;
    private static final float PLAYER_START_X = 1f;
    private static final float PLAYER_START_Y = 4.5f;

    private static final float TEXTURE_Y_IDLE_OFFSET = 22f;
    private static final float TEXTURE_X_OFFSET = 16f;

    public Body body;

    public Goose() {
        texture = new Texture(PLAYER_IMG_PATH);
        heightInTexture = texture.getHeight() / rows;
        xPositionInTexture = texture.getWidth() / columns - widthInTexture;
        yPositionInTexture = texture.getHeight() / rows;
    }

    public void createBoxBody(World world) {
        assert (world != null);

        BodyDef bdef = new BodyDef();
        bdef.fixedRotation = true;
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.position.set(PLAYER_START_X, PLAYER_START_Y);
        body = world.createBody(bdef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(BOX_SIZE / GotlGame.PIXEL_PER_METER / 2, BOX_SIZE / GotlGame.PIXEL_PER_METER / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = PLAYER_DENSITY;

        body.createFixture(fixtureDef).setUserData(this);

        shape.dispose();
    }

    public Body getBody() {
        return body;
    }


    public void hit() {
        isDead = true;
    }

    public float getCurrentTextureYOffset() {
        //TODO: return correct offsets for each sprite so the goose sprite matches its collision boundaries
        return TEXTURE_Y_IDLE_OFFSET;
    }

    public float getCurrentTextureXOffset() {
        return TEXTURE_X_OFFSET;
    }

    public void setJumping(boolean jumping) {
        isJumping = jumping;
    }

    public void setLevelEnd(boolean end) {
        this.isLevelEnd = end;
    }

    public void jump(Vector2 drag) {
        if (drag.y < 0) return;
        //System.out.println("BEFORE: x: " + drag.x + ", y: " + drag.y);
        drag.x = Math.max(-MAX_JUMP_FORCE_X, drag.x);
        drag.x = Math.min(MAX_JUMP_FORCE_X, drag.x);
        drag.y = Math.min(MAX_JUMP_FORCE_Y, drag.y);
        //System.out.println("AFTER:  x: " + drag.x + ", y: " + drag.y);
        body.applyForceToCenter(drag, false);
    }

    public boolean isJumping() {
        return isJumping;
    }

    public boolean isDead() {
        return isDead;
    }

    public boolean isLevelEnd() {
        return isLevelEnd;
    }

    public void dispose() {
        texture.dispose();
    }
}