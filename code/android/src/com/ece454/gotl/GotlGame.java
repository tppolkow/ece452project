package com.ece454.gotl;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;

import handlers.AssetHandler;
import handlers.GameStateManager;
import states.MenuState;

public class GotlGame extends ApplicationAdapter {
	public static final float PIXEL_PER_METER = 32f;

	public static final float SCALE = 2.12f;
	public static final float TIME_STEP = 1 / 60f;
	public static final int VELOCITY_ITERATIONS = 6;
	public static final int POSITION_ITERATIONS = 2;
	private SpriteBatch batch;
	private GameStateManager gsm;
	private AssetHandler assetHandler;

	@Override
	public void create () {
		batch = new SpriteBatch();
		gsm = new GameStateManager(this);
		assetHandler = new AssetHandler();
		assetHandler.loadAssets();
		gsm.set(new MenuState(gsm));
	}

	@Override
	public void render()
	{
	    gsm.render();
	}

	@Override
	public void dispose()
	{
		batch.dispose();
	}

	@Override
	public void resize(int width, int height)
	{
	}

	public AssetHandler getAssetHandler() {
		return assetHandler;
	}

	public SpriteBatch getSpriteBatch() {
		return batch;
	}
}
