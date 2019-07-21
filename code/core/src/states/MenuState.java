package states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ece454.gotl.GotlGame;

import handlers.AssetHandler;
import handlers.GameStateManager;

public class MenuState extends State {

    private Stage stage;
    private AssetHandler assetHandler;

    public MenuState(GameStateManager gsm) {
        super(gsm);
        assetHandler = gsm.getGame().getAssetHandler();
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        createCrest();
        createButton();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0.5f, 0.8f, 1f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    @Override
    public void update() {

    }

    @Override
    protected void handleInput() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    private void createButton() {
        Texture texture = assetHandler.getManager().get(assetHandler.START_BUTTON_PATH, Texture.class);
        Drawable drawable = new TextureRegionDrawable(new TextureRegion(texture));
        Button startBtn = new ImageButton(drawable);
        startBtn.setPosition(Gdx.graphics.getWidth() / 1.95f,Gdx.graphics.getHeight() / 5, Align.center);
        startBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gsm.pop();
                gsm.push(new PlayState(gsm, assetHandler.getManager().get(assetHandler.LEVEL_1_PATH, TiledMap.class)));
                gsm.render();
            }
        });
        stage.addActor(startBtn);
    }

    private void createCrest() {
        Image crest = new Image(assetHandler.getManager().get(assetHandler.GOTL_CREST, Texture.class));
        Image goose = new Image(assetHandler.getManager().get(assetHandler.GOOSE_FACE_PATH, Texture.class));
        crest.addAction(Actions.repeat(RepeatAction.FOREVER, Actions.rotateBy(20, 1)));
        crest.setPosition(Gdx.graphics.getWidth() / 2.02f - crest.getWidth() / 2, Gdx.graphics.getHeight() / 1.5f - crest.getWidth() / 2);
        crest.setOrigin(crest.getWidth() / 2, crest.getHeight() / 2);
        crest.setAlign(Align.center);
        goose.setOrigin(crest.getWidth() / 2, crest.getHeight() / 2);
        goose.setPosition(Gdx.graphics.getWidth() / 2.25f - goose.getWidth() / 2, Gdx.graphics.getHeight() / 1.56f - goose.getWidth() / 2);
        stage.addActor(crest);
        stage.addActor(goose);
    }
}
