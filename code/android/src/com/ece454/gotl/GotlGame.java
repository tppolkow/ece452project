package com.ece454.gotl;

import android.content.Context;
import android.content.SharedPreferences;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

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
	private SpriteBatch batch;
	private GameStateManager gsm;
	private AssetHandler assetHandler;
	public static final String PREFS_NAME = "shared_preferences";
	private final String firstTimePlayingStr = "firstTimePlaying";

	@Override
	public void create () {
		batch = new SpriteBatch();
		gsm = new GameStateManager(this);
		assetHandler = new AssetHandler();
		assetHandler.loadAssets();

		gsm.set(new MenuState(gsm,  getFirstTimePlaying()));
	}

	private boolean getFirstTimePlaying()
	{
		SharedPreferences settings = AndroidLauncher.getAppContext().getSharedPreferences(PREFS_NAME, 0);
		boolean firstTimePlaying = settings.getBoolean(firstTimePlayingStr, true);
		if (firstTimePlaying)
		{
			settings.edit().putBoolean(firstTimePlayingStr, false).commit();
		}
		return firstTimePlaying;
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
	}

	public AssetHandler getAssetHandler() {
		return assetHandler;
	}

	public SpriteBatch getSpriteBatch() {
		return batch;
	}
}
