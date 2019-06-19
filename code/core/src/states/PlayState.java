package states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.ece454.gotl.Game;
import com.ece454.gotl.Goose;

import handlers.GameStateManager;
import handlers.WorldManager;

import static com.ece454.gotl.Game.PIXEL_PER_METER;
import static com.ece454.gotl.Game.POSITION_ITERATIONS;
import static com.ece454.gotl.Game.SCALE;
import static com.ece454.gotl.Game.TIME_STEP;
import static com.ece454.gotl.Game.VELOCITY_ITERATIONS;

public class PlayState extends State
{
    private static final String MAP_PATH = "map/desert_demo.tmx";
    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer tiledMapRenderer;
    private Goose goose;
    private boolean isPressed = false;
    private Vector2 initialPressPos, finalPressPos;

    public PlayState(GameStateManager gsm)
    {
        super(gsm);
        initialPressPos = new Vector2();
        finalPressPos = new Vector2();
        tiledMap = new TmxMapLoader().load(MAP_PATH);
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        WorldManager.resetWorld();
        WorldManager.parseTiledMap(tiledMap);
        goose = new Goose();
        goose.createBoxBody(WorldManager.world);
    }


    @Override
    public void render(SpriteBatch sb)
    {
        update();
        sb.setProjectionMatrix(cam.combined);
        Gdx.gl.glClearColor(0.5f, 0.8f, 1f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        tiledMapRenderer.render();
        sb.begin();
        sb.draw(goose.texture, goose.body.getPosition().x * PIXEL_PER_METER - (goose.texture.getWidth() / 2f),
                goose.body.getPosition().y * PIXEL_PER_METER - (goose.texture.getHeight() / 2f),
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

        if (goose.isDead())
        {
            WorldManager.world.destroyBody(goose.body);
            goose.dispose();
            goose = new Goose();
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

    @Override
    public void dispose()
    {

    }
}