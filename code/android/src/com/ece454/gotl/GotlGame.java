package com.ece454.gotl;

import android.net.Uri;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import handlers.AssetHandler;
import handlers.GameStateManager;
import handlers.SocialMediaHandler;
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
	private SocialMediaHandler smHandler;

	@Override
	public void create () {
		batch = new SpriteBatch();
		gsm = new GameStateManager(this);
		assetHandler = new AssetHandler();
		assetHandler.loadAssets();
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
	}

	public AssetHandler getAssetHandler() {
		return assetHandler;
	}

	public SpriteBatch getSpriteBatch() {
		return batch;
	}

	public void postVideo(Uri videoFileUri)
	{
		smHandler.postVideo(videoFileUri);
	}

	public void setSocialMediaHandler(SocialMediaHandler smh)
	{
		this.smHandler = smh;
	}
}
