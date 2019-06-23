package screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ece454.gotl.GotlGame;

import handlers.AssetHandler;
import handlers.GameStateManager;
import states.PlayState;

public class MenuScreen extends ScreenAdapter {

    private static final String START = "START";
    private static final String TITLEOFGAME = "GOOSE ON THE LOOSE";
    private GotlGame game;
    private Stage stage;
    private AssetHandler assetHandler;

    // needs a lot more design
    public MenuScreen(GotlGame game) {
        this.game = game;
        this.assetHandler = game.assetHandler;
        this.stage = new Stage(new ScreenViewport());
        createLabel();
        createButton();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.5f, 0.8f, 1f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    private void createButton() {
        Skin skin = assetHandler.manager.get(assetHandler.BTN_SKIN_PATH, Skin.class);
        Button startBtn = new TextButton(START, skin);
        startBtn.setWidth(Gdx.graphics.getWidth() / 2);
        startBtn.setPosition(Gdx.graphics.getWidth() / 2.6f,Gdx.graphics.getHeight() / 2, Align.center);
        startBtn.setTransform(true);
        startBtn.scaleBy(0.5f);
        startBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new PlayState(new GameStateManager(game)));
            }
        });
        stage.addActor(startBtn);
    }

    private void createLabel() {
        int row_height = Gdx.graphics.getWidth() / 12;
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = assetHandler.manager.get(assetHandler.FONT_PATH, BitmapFont.class);
        Label title = new Label(TITLEOFGAME, labelStyle);
        title.setSize(Gdx.graphics.getWidth(), row_height);
        title.setAlignment(Align.center);
        title.setPosition(0,Gdx.graphics.getHeight()-row_height*2);
        title.setWidth(Gdx.graphics.getWidth());
        stage.addActor(title);
    }
}
