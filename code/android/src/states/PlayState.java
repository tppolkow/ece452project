package states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.ece454.gotl.Goose;

import static com.ece454.gotl.GotlGame.PIXEL_PER_METER;
import static com.ece454.gotl.GotlGame.POSITION_ITERATIONS;
import static com.ece454.gotl.GotlGame.TIME_STEP;
import static com.ece454.gotl.GotlGame.VELOCITY_ITERATIONS;

import handlers.AssetHandler;
import handlers.GameStateManager;
import handlers.WorldManager;

public class PlayState extends State {
    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer tiledMapRenderer;
    private Goose goose;
    private boolean isPressed = false;
    private Vector2 initialPressPos, finalPressPos;
    private AssetHandler assetHandler;
    private Texture gooseForwardTexture;
    private Texture gooseReverseTexture;

    public PlayState(GameStateManager gsm)
    {
        super(gsm);
        initialPressPos = new Vector2();
        finalPressPos = new Vector2();
        tiledMap = gsm.getLvlMap();
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        assetHandler = gsm.getGame().getAssetHandler();
        WorldManager.resetWorld();
        WorldManager.parseTiledMap(tiledMap);
        gooseForwardTexture = assetHandler.getManager().get(assetHandler.PLAYER_IMG_PATH, Texture.class);
        gooseReverseTexture = assetHandler.getManager().get(assetHandler.REVERSE_PLAYER_IMG_PATH, Texture.class);
        goose = new Goose(gooseForwardTexture, gooseReverseTexture);
        goose.createBoxBody(WorldManager.world);
    }

    @Override
    public void render() {
        gsm.setPlayTime(System.currentTimeMillis());
        update();
        if (!goose.isLevelEnd()) {
            SpriteBatch sb = gsm.getGame().getSpriteBatch();
            sb.setProjectionMatrix(cam.combined);
            Gdx.gl.glClearColor(0.5f, 0.8f, 1f, 1f);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            tiledMapRenderer.render();
            sb.begin();
            goose.setCorrectTexture();
            sb.draw(goose.texture, goose.body.getPosition().x * PIXEL_PER_METER - (goose.getCurrentTextureYOffset()),
                    goose.body.getPosition().y * PIXEL_PER_METER - (goose.getCurrentTextureXOffset()),
                    goose.xPositionInTexture,
                    goose.yPositionInTexture,
                    goose.widthInTexture,
                    goose.heightInTexture);
            sb.end();
        }
    }

    @Override
    public void update()
    {
        WorldManager.world.step(TIME_STEP, VELOCITY_ITERATIONS, POSITION_ITERATIONS);

        handleInput();

        if (goose.isLevelFailed()){
            disposeAndCreateNewGoose();
        } else if (goose.isDead()) {
            gooseDeathAnimation();
        } else if (goose.isLevelEnd()) {
            goose.dispose();
            renderLvlComplete();
            return;
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

    private void gooseDeathAnimation(){
        goose.fall();
    }

    private void disposeAndCreateNewGoose() {
        WorldManager.world.destroyBody(goose.body);
        goose.dispose();
        System.out.println("GOose FORAWWRD " + gooseForwardTexture.toString());
        goose = new Goose(gooseForwardTexture, gooseReverseTexture);
        goose.createBoxBody(WorldManager.world);
    }

    private void renderLvlComplete() {
        gsm.setPlayTime(System.currentTimeMillis() - gsm.getPlayTime());
        gsm.set(new LevelCompleteState(gsm));
    }

    @Override
    public void dispose()
    {

    }
}
