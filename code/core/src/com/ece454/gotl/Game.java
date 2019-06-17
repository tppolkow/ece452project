package com.ece454.gotl;

import com.badlogic.gdx.ApplicationAdapter;
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

public class Game extends ApplicationAdapter {
	static final float PIXEL_PER_METER = 32f;

	private static final String MAP_PATH = "map/desert_demo.tmx";
	private static final float SCALE = 2.0f;
	private static final float TIME_STEP = 1 / 60f;
	private static final int VELOCITY_ITERATIONS = 6;
	private static final int POSITION_ITERATIONS = 2;
	private SpriteBatch batch;
	private Texture img;
	private Box2DDebugRenderer box2DDebugRenderer;
	private World world;
	private Goose goose;
	private static final float VELOCITY_Y = -9.85f;
	private static final float VELOCITY_X = 0f;
	private OrthographicCamera orthographicCamera;
	private OrthogonalTiledMapRenderer tiledMapRenderer;
	private TiledMap tiledMap;

	@Override
	public void create () {
		orthographicCamera = new OrthographicCamera();
		orthographicCamera.setToOrtho(false, Gdx.graphics.getWidth() / SCALE, Gdx.graphics.getHeight() / SCALE);
		world = new World(new Vector2(VELOCITY_X, VELOCITY_Y), false);
		world.setContactListener(new WorldContactListener());
		goose = new Goose(world);
		batch = new SpriteBatch();
		img = goose.texture;
		box2DDebugRenderer = new Box2DDebugRenderer();
		tiledMap = new TmxMapLoader().load(MAP_PATH);
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
		MapParser.parseMapLayers(world, tiledMap);
	}

	@Override
	public void render () {
		update();
		Gdx.gl.glClearColor(0.5f, 0.8f, 1f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		tiledMapRenderer.render();
		batch.begin();
//		System.out.println("y " + goose.xPositionInTexture);
//		System.out.println("x " + goose.yPositionInTexture);
//		System.out.println("width " + goose.widthInTexture);
//		System.out.println("height " + goose.heightInTexture);

		batch.draw(img, goose.getBody().getPosition().x * PIXEL_PER_METER - (img.getWidth() / 2f),
				goose.getBody().getPosition().y * PIXEL_PER_METER - (img.getHeight() / 2f), goose.xPositionInTexture, goose.yPositionInTexture, goose.widthInTexture, goose.heightInTexture);
//		batch.draw(img, goose.getBody().getPosition().x * PIXEL_PER_METER - (img.getWidth() / 2f), goose.getBody().getPosition().y * PIXEL_PER_METER - (img.getHeight() /2f) );
		batch.end();

//		box2DDebugRenderer.render(world, orthographicCamera.combined.scl(PIXEL_PER_METER));

	}

	private void update(){
		world.step(TIME_STEP, VELOCITY_ITERATIONS, POSITION_ITERATIONS);

		inputUpdate();

		cameraUpdate();
		tiledMapRenderer.setView(orthographicCamera);
		batch.setProjectionMatrix(orthographicCamera.combined);
	}

	private void inputUpdate() {
		int horizontalForce = 0;
		boolean isJumping = false;
		if (Gdx.input.isTouched()) {
			Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
			touchPos = orthographicCamera.unproject(touchPos);
			if (touchPos.x / PIXEL_PER_METER > goose.getBody().getPosition().x) {
				System.out.println("move positive");
				horizontalForce += 1;
			}
			if (touchPos.x / PIXEL_PER_METER < goose.getBody().getPosition().x) {
				horizontalForce -= 1;
				System.out.println("move negtive");
			}
			if (touchPos.y / PIXEL_PER_METER > goose.getBody().getPosition().y && !goose.isJumping()) {
				isJumping = true;
				System.out.println("jump");
			}
		}
		gooseUpdate(horizontalForce, isJumping);
	}

	private void gooseUpdate(int horizontalForce, boolean isJumping) {
		if (goose.isDead()) {
			world.destroyBody(goose.getBody());
			goose = new Goose(world);
		}
		if(isJumping)
			goose.getBody().applyForceToCenter(0, goose.JUMP_FORCE, false);
		goose.getBody().setLinearVelocity(horizontalForce * goose.RUN_FORCE, goose.getBody().getLinearVelocity().y);
	}

	private void cameraUpdate(){
		Vector3 position = orthographicCamera.position;
		position.x = goose.getBody().getPosition().x * PIXEL_PER_METER;
		position.y = goose.getBody().getPosition().y * PIXEL_PER_METER;
		orthographicCamera.position.set(position);
		orthographicCamera.update();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
		box2DDebugRenderer.dispose();
		world.dispose();
		tiledMapRenderer.dispose();
		tiledMap.dispose();
	}

	@Override
	public void resize(int width, int height){
		orthographicCamera.setToOrtho(false, width/SCALE, height/SCALE);
	}
}
