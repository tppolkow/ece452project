package states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.ece454.gotl.Goose;

import handlers.AssetHandler;
import handlers.GameStateManager;
import handlers.WorldManager;

import static com.ece454.gotl.GotlGame.PIXEL_PER_METER;
import static com.ece454.gotl.GotlGame.POSITION_ITERATIONS;
import static com.ece454.gotl.GotlGame.TIME_STEP;
import static com.ece454.gotl.GotlGame.VELOCITY_ITERATIONS;

public class PlayState extends State {
    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer tiledMapRenderer;
    private Goose goose;
    private boolean isPressed = false;
    private Vector2 initialPressPos, finalPressPos;
    private Box2DDebugRenderer box2DDebugRenderer;
    private AssetHandler assetHandler;

    public PlayState(GameStateManager gsm)
    {
        super(gsm);
        initialPressPos = new Vector2();
        finalPressPos = new Vector2();
        assetHandler = gsm.getGame().getAssetHandler();
        tiledMap = assetHandler.getManager().get(assetHandler.MAP_PATH, TiledMap.class);
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        WorldManager.resetWorld();
        WorldManager.parseTiledMap(tiledMap);
        goose = new Goose();
        goose.createBoxBody(WorldManager.world);
        box2DDebugRenderer = new Box2DDebugRenderer();
    }

    @Override
    public void render() {
        update();
        SpriteBatch sb = gsm.getGame().getSpriteBatch();
        sb.setProjectionMatrix(cam.combined);
        Gdx.gl.glClearColor(0.5f, 0.8f, 1f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        tiledMapRenderer.render();
        sb.begin();
        sb.draw(goose.texture, goose.body.getPosition().x * PIXEL_PER_METER - (goose.getCurrentTextureYOffset()),
                goose.body.getPosition().y * PIXEL_PER_METER - (goose.getCurrentTextureXOffset()),
                goose.xPositionInTexture,
                goose.yPositionInTexture,
                goose.widthInTexture,
                goose.heightInTexture);
        sb.end();
    }

    @Override
    public void update()
    {
        WorldManager.world.step(TIME_STEP, VELOCITY_ITERATIONS, POSITION_ITERATIONS);

        handleInput();

        if (goose.isDead()) {
            disposeAndCreateNewGoose();
        } else if (goose.isLevelEnd()) {
            disposeAndCreateNewGoose();
            gsm.push(new LevelCompleteState(gsm));
            gsm.render();
        }

        updateCamera();
        tiledMapRenderer.setView(cam);
    }


    @Override
    protected void handleInput()
    {
        if (Gdx.input.isTouched()) {
            if (!isPressed)
            {
                isPressed = true;
                initialPressPos.x = -Gdx.input.getX();
                initialPressPos.y = Gdx.input.getY();
            }
        }
        else if (isPressed)
        {
            // User just released
            isPressed = false;
            finalPressPos.x = -Gdx.input.getX();
            finalPressPos.y = Gdx.input.getY();
            //System.out.println("JUMPING NOW");
            goose.jump(finalPressPos.sub(initialPressPos));
        }
    }

    private void updateCamera()
    {
        Vector3 position = cam.position;
        position.y = goose.body.getPosition().y * PIXEL_PER_METER;
        cam.position.set(position);
        cam.update();
    }

    private void disposeAndCreateNewGoose() {
        WorldManager.world.destroyBody(goose.body);
        goose.dispose();
        goose = new Goose();
        goose.createBoxBody(WorldManager.world);
    }

    @Override
    public void dispose()
    {

    }
}