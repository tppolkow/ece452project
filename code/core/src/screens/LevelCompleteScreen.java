package screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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

import states.PlayState;

public class LevelCompleteScreen extends ScreenAdapter {

    private static final String BTN_SKIN_PATH = "skins/holo/skin/dark-mdpi/Holo-dark-mdpi.json";
    private static final String LABEL_SKIN_PATH = "skins/glassy/skin/glassy-ui.json";
    private static final String RESTART = "RESTART";
    private GotlGame game;
    private Stage stage;

    public LevelCompleteScreen(GotlGame game) {
        this.game = game;
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
        Skin skin = new Skin(Gdx.files.internal(BTN_SKIN_PATH));
        Button restartbtn = new TextButton(RESTART, skin);
        restartbtn.setWidth(Gdx.graphics.getWidth()/2);
        restartbtn.setPosition(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2, Align.center);
        restartbtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

            }
        });
        stage.addActor(restartbtn);
    }

    private void createLabel() {
        int row_height = Gdx.graphics.getWidth() / 12;
        Skin labelSkin = new Skin(Gdx.files.internal(LABEL_SKIN_PATH));
        Label title = new Label("Level Complete!!", labelSkin);
        title.setSize(Gdx.graphics.getWidth(), row_height);
        title.setFontScale(5);
        title.setAlignment(Align.center);
        title.setPosition(0,Gdx.graphics.getHeight()-row_height*2);
        title.setWidth(Gdx.graphics.getWidth());
        stage.addActor(title);
    }
}
