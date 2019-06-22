package com.ece454.gotl;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

import handlers.GameStateManager;
import handlers.WorldManager;
import screens.LevelCompleteScreen;
import states.PlayState;

public class GotlGame extends Game {
	public static final float PIXEL_PER_METER = 32f;

	public static final float SCALE = 2.0f;
	public static final float TIME_STEP = 1 / 60f;
	public static final int VELOCITY_ITERATIONS = 6;
	public static final int POSITION_ITERATIONS = 2;
	public SpriteBatch batch;
	private GameStateManager gsm;
	private Box2DDebugRenderer box2DDebugRenderer;

	@Override
	public void create () {
		batch = new SpriteBatch();
		gsm = new GameStateManager(this);
		PlayState playScreen = new PlayState(gsm);
		gsm.push(playScreen);
		setScreen(playScreen);
	}

	@Override
	public void render ()
	{
		super.render();
//	    gsm.render(batch);
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
}
