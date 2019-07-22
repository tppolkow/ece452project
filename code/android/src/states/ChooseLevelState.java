package states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import handlers.AssetHandler;
import handlers.GameStateManager;

public class ChooseLevelState extends State {


    private Stage stage;
    private AssetHandler assetHandler;

    private Table table = new Table();

    public ChooseLevelState(GameStateManager gsm) {
        super(gsm);
        assetHandler = gsm.getGame().getAssetHandler();
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        createLayout();
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

    private Button createLvlButton(String text, final int level ) {
        TextButton.TextButtonStyle btnStyle = new TextButton.TextButtonStyle();
        btnStyle.font= assetHandler.getManager().get(assetHandler.FONT_PATH, BitmapFont.class);
        Button lvlButton = new TextButton(text, btnStyle);
        //lvlButton.setWidth(Gdx.graphics.getWidth() / 2);
        // lvlButton.setPosition(Gdx.graphics.getWidth() / 2.6f,Gdx.graphics.getHeight() / 8, Align.center);

        //lvlButton.setSize(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/4);
        // lvlButton.setFontScale(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/4);
        //lvlButton.setTransform(true);
        //lvlButton.scaleBy(0.5f);

        lvlButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gsm.setLevel(level);
                gsm.set(new PlayState(gsm));
            }
        });
        return lvlButton;
        // stage.addActor(lvlButton);
    }


    private void createLayout() {

        Button lvl1Btn =  createLvlButton("LEVEL 1", 1);
        Button lvl2Btn =  createLvlButton("LEVEL 2", 2);
        Button lvl3Btn =  createLvlButton("LEVEL 3", 3);
        Button lvl4Btn =  createLvlButton("LEVEL 4", 4);
        Button lvl5Btn =  createLvlButton("LEVEL 5", 5);

        table.add(lvl1Btn).align(Align.center).width(Gdx.graphics.getWidth()).padBottom(100);
        table.row();
        table.add(lvl2Btn).align(Align.center).width(Gdx.graphics.getWidth()).padBottom(100);
        table.row();
        table.add(lvl3Btn).align(Align.center).width(Gdx.graphics.getWidth()).padBottom(100);
        table.row();
        table.add(lvl4Btn).align(Align.center).width(Gdx.graphics.getWidth()).padBottom(100);
        table.row();
        table.add(lvl5Btn).align(Align.center).width(Gdx.graphics.getWidth()).padBottom(100);
        table.setWidth(Gdx.graphics.getWidth());
        table.align(Align.center|Align.top);
        table.setPosition(0, Gdx.graphics.getHeight() / 1.1f);

        stage.addActor(table);
        stage.addActor(createMainMenuBtn());
    }

    private Button createMainMenuBtn() {
        float scale = 0.5f;
        Texture texture = assetHandler.getManager().get(assetHandler.MENU_BUTTON_PATH, Texture.class);
        Drawable drawable = new TextureRegionDrawable(new TextureRegion(texture));
        Button mainMenuBtn = new ImageButton(drawable);
        mainMenuBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gsm.resetInputProcessor = true;
                gsm.pop();
            }
        });
        mainMenuBtn.setTransform(true);
        mainMenuBtn.setScale(scale);
        mainMenuBtn.setPosition(Gdx.graphics.getWidth() - texture.getWidth() * scale - 40, texture.getHeight() * scale - 40);
        return mainMenuBtn;
    }
}