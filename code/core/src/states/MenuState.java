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

public class MenuState extends State {

    private static final String START = "START";
    private static final String TITLEOFGAME = "GOOSE ON THE LOOSE";
    private Stage stage;
    private AssetHandler assetHandler;

    // needs a lot more design
    public MenuState(GameStateManager gsm) {
        super(gsm);
        assetHandler = gsm.getGame().getAssetHandler();
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        createLabel();
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
        Skin skin = assetHandler.getManager().get(assetHandler.BTN_SKIN_PATH, Skin.class);
        Button startBtn = new TextButton(START, skin);
        startBtn.setWidth(Gdx.graphics.getWidth() / 2);
        startBtn.setPosition(Gdx.graphics.getWidth() / 2.6f,Gdx.graphics.getHeight() / 2, Align.center);
        startBtn.setTransform(true);
        startBtn.scaleBy(0.5f);
        startBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gsm.pop();
                gsm.render();
            }
        });
        stage.addActor(startBtn);
    }

    private void createLabel() {
        int row_height = Gdx.graphics.getWidth() / 12;
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = assetHandler.getManager().get(assetHandler.FONT_PATH, BitmapFont.class);
        Label title = new Label(TITLEOFGAME, labelStyle);
        title.setSize(Gdx.graphics.getWidth(), row_height);
        title.setAlignment(Align.center);
        title.setPosition(0,Gdx.graphics.getHeight()-row_height*2);
        title.setWidth(Gdx.graphics.getWidth());
        stage.addActor(title);
    }
}
