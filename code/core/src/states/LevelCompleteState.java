package states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
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

import handlers.AssetHandler;
import handlers.GameStateManager;

public class LevelCompleteState extends State {

    private static final String RESTART = "RESTART";
    private static final String LEVEL_COMPLETE = "Level Complete!!";
    private Stage stage;
    private AssetHandler assetHandler;

    public LevelCompleteState(GameStateManager gsm) {
        super(gsm);
        assetHandler = gsm.getGame().getAssetHandler();
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        createLabel();
        createButton();
    }

    @Override
    protected void handleInput() {

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
    public void dispose() {
        Gdx.input.setInputProcessor(null);
        stage.dispose();
    }

    private void createButton() {
        Skin skin = assetHandler.getManager().get(assetHandler.BTN_SKIN_PATH, Skin.class);
        Button restartBtn = new TextButton(RESTART, skin);
        restartBtn.setWidth(Gdx.graphics.getWidth() / 2);
        restartBtn.setPosition(Gdx.graphics.getWidth() / 2.6f,Gdx.graphics.getHeight() / 2, Align.center);
        restartBtn.setTransform(true);
        restartBtn.scaleBy(0.5f);
        restartBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gsm.pop();
                gsm.render();
            }
        });
        stage.addActor(restartBtn);
    }

    private void createLabel() {
        int row_height = Gdx.graphics.getWidth() / 12;
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = assetHandler.getManager().get(assetHandler.FONT_PATH, BitmapFont.class);
        Label title = new Label(LEVEL_COMPLETE, labelStyle);
        title.setSize(Gdx.graphics.getWidth(), row_height);
        title.setAlignment(Align.center);
        title.setPosition(0,Gdx.graphics.getHeight()-row_height*2);
        title.setWidth(Gdx.graphics.getWidth());
        stage.addActor(title);
    }
}
