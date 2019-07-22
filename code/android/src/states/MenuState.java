package states;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import handlers.AssetHandler;
import handlers.GameStateManager;

public class MenuState extends State {

    private Stage stage;
    private AssetHandler assetHandler;
    private boolean firstTimePlaying;

    public MenuState(GameStateManager gsm, boolean firstTimePlaying) {
        super(gsm);
        this.firstTimePlaying = firstTimePlaying;
        assetHandler = gsm.getGame().getAssetHandler();
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        createCrest();
        createButton();
        createSettingsBtn();
    }

    @Override
    public void render() {
        if (GameStateManager.resetInputProcessor)
        {
            Gdx.input.setInputProcessor(stage);
            GameStateManager.resetInputProcessor = false;
        }
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
        startBtn.setPosition(Gdx.graphics.getWidth() / 1.95f,Gdx.graphics.getHeight() / 4, Align.center);
        startBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.input.setInputProcessor(null);
                if (firstTimePlaying)
                {
                    gsm.push(new IntroStoryState(gsm));
                }
                else
                {
                    gsm.push(new PlayState(gsm));
                }
            }
        });
        stage.addActor(startBtn);
    }

    private void createSettingsBtn() {
        Texture texture = assetHandler.getManager().get(assetHandler.SETTINGS_BUTTON_PATH, Texture.class);
        Drawable drawable = new TextureRegionDrawable(new TextureRegion(texture));
        Button settingsBtn = new ImageButton(drawable);
        settingsBtn.setPosition(Gdx.graphics.getWidth()/0.75f ,Gdx.graphics.getHeight()/ 4.75f , Align.right);
        settingsBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gsm.push(new ChooseLevelState(gsm));
                gsm.render();
            }
        });
        settingsBtn.setTransform(true);
        settingsBtn.setScale(0.25f);
        stage.addActor(settingsBtn);
    }

    private void createCrest() {
        Image crest = new Image(assetHandler.getManager().get(assetHandler.GOTL_CREST_PATH, Texture.class));
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

