package com.ece454.gotl;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;

import handlers.AssetHandler;
import handlers.GameStateManager;
import states.MenuState;
import states.PlayState;

public class GotlGame extends ApplicationAdapter {
	public static final float PIXEL_PER_METER = 32f;

	public static final float SCALE = 2.0f;
	public static final float TIME_STEP = 1 / 60f;
	public static final int VELOCITY_ITERATIONS = 6;
	public static final int POSITION_ITERATIONS = 2;
	public static int levelCount = 0;
	private SpriteBatch batch;
	private GameStateManager gsm;
	private Box2DDebugRenderer box2DDebugRenderer;
	private AssetHandler assetHandler;
	private long playTime;

	@Override
	public void create () {
		batch = new SpriteBatch();
		gsm = new GameStateManager(this);
		assetHandler = new AssetHandler();
		assetHandler.loadAssets();
		levelCount = 0;
		gsm.push(new PlayState(gsm));
		gsm.push(new MenuState(gsm));
	}

	@Override
	public void render ()
	{
	    gsm.render();
	}

	@Override
	public void dispose ()
	{
		batch.dispose();
	}

	@Override
	public void resize(int width, int height)
	{
		//orthographicCamera.setToOrtho(false, width/SCALE, height/SCALE);
	}

	public AssetHandler getAssetHandler() {
		return assetHandler;
	}

	public SpriteBatch getSpriteBatch() {
		return batch;
	}

	public void setPlayTime(long playTime) {
		this.playTime = playTime;
	}

	public long getPlayTime() {
		return playTime;
	}
}
