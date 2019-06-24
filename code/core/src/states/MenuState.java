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
    private static final String TITLEOFGAMEP1 = "GOOSE";
    private static final String TITLEOFGAMEP2 = "ON THE LOOSE";
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


        // For now removing button skin
        // Skin skin = assetHandler.manager.get(assetHandler.BTN_SKIN_PATH, Skin.class);
        // skin.get(TextButton.TextButtonStyle.class).font = assetHandler.manager.get(assetHandler.FONT_PATH, BitmapFont.class);

        TextButton.TextButtonStyle btnStyle = new TextButton.TextButtonStyle();
        btnStyle.font= assetHandler.getManager().get(assetHandler.FONT_PATH, BitmapFont.class);
        Button startBtn = new TextButton(START, btnStyle);
        startBtn.setWidth(Gdx.graphics.getWidth() / 2);
        startBtn.setPosition(Gdx.graphics.getWidth() / 2.6f,Gdx.graphics.getHeight() / 8, Align.center);

        startBtn.setSize(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/4);
        // startBtn.setFontScale(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/4);
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
        int row_height = Gdx.graphics.getWidth() / 5;
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = assetHandler.getManager().get(assetHandler.FONT_PATH, BitmapFont.class);
        Label titlep1 = new Label(TITLEOFGAMEP1, labelStyle);
        titlep1.setSize(Gdx.graphics.getWidth(), row_height);
        titlep1.setAlignment(Align.center);
        titlep1.setPosition(0,Gdx.graphics.getHeight()-row_height*2);
        titlep1.setWidth(Gdx.graphics.getWidth());
        titlep1.setFontScale(2f);

        Label titlep2 = new Label(TITLEOFGAMEP2, labelStyle);
        titlep2.setSize(Gdx.graphics.getWidth(), row_height);
        titlep2.setAlignment(Align.center);
        titlep2.setPosition(0,Gdx.graphics.getHeight()-row_height*3);
        titlep2.setWidth(Gdx.graphics.getWidth());
        titlep2.setFontScale(2f);
        stage.addActor(titlep1);
        stage.addActor(titlep2);
    }
}
