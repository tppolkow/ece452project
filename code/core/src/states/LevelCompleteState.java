package states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import handlers.AssetHandler;
import handlers.GameStateManager;

public class LevelCompleteState extends State {

    private static final String NEXT_LEVEL = "NEXT LEVEL";
    private static final String LEVEL_COMPLETE = "Level %d Complete!!";
    private static final String TIMER = "Total Time: %.3fs";
    private static final String MAIN_MENU = "MAIN MENU";
    private static final float ONE_STAR_TIME = 30f;
    private static final float TWO_STAR_TIME = 20f;
    private Stage stage;
    private AssetHandler assetHandler;
    private int level = 0;
    private Table table = new Table();
    private float playTime = 0;
    private boolean layoutCreated = false;


    public LevelCompleteState(GameStateManager gsm) {
        super(gsm);
        assetHandler = gsm.getGame().getAssetHandler();
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    protected void handleInput() {

    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0.5f, 0.8f, 1f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        update();
        Batch batch = stage.getBatch();
        batch.begin();
        if (playTime >= ONE_STAR_TIME) {
            Texture one_star = assetHandler.getManager().get(assetHandler.ONE_STAR_PATH, Texture.class);
            batch.draw(one_star, Gdx.graphics.getWidth() / 2 - one_star.getWidth() / 2, Gdx.graphics.getHeight() / 1.95f);
        } else if (playTime >= TWO_STAR_TIME && playTime < ONE_STAR_TIME) {
            Texture two_star = assetHandler.getManager().get(assetHandler.TWO_STAR_PATH, Texture.class);
            batch.draw(two_star, Gdx.graphics.getWidth() / 2 - two_star.getWidth() / 2, Gdx.graphics.getHeight() / 1.95f);
        } else {
            Texture three_star = assetHandler.getManager().get(assetHandler.THREE_STAR_PATH, Texture.class);
            batch.draw(three_star, Gdx.graphics.getWidth() / 2 - three_star.getWidth() / 2, Gdx.graphics.getHeight() / 1.95f);
        }
        batch.end();
        stage.act();
        stage.draw();
    }

    @Override
    public void update() {
        playTime = gsm.getPlayTime() / 1000;
        level = gsm.getLvl();
        if (!layoutCreated) {
            layoutCreated = true;
            createLayout();
        }
    }

    @Override
    public void dispose() {
        Gdx.input.setInputProcessor(null);
        stage.dispose();
    }

    private Button createNextLvlBtn() {
        Texture texture = assetHandler.getManager().get(assetHandler.NEXT_LEVEL_BUTTON_PATH, Texture.class);
        Drawable drawable = new TextureRegionDrawable(new TextureRegion(texture));
        Button nextLvlBtn = new ImageButton(drawable);
        nextLvlBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gsm.pop();
                gsm.push(new PlayState(gsm, getLvlMap(gsm.getLvl() + 1)));
                gsm.render();
            }
        });

        return nextLvlBtn;
    }

    private Button createMainMenuBtn() {
        Texture texture = assetHandler.getManager().get(assetHandler.MENU_BUTTON_PATH, Texture.class);
        Drawable drawable = new TextureRegionDrawable(new TextureRegion(texture));
        Button mainMenuBtn = new ImageButton(drawable);
        mainMenuBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gsm.pop();
                gsm.resetLvl();
                gsm.push(new MenuState(gsm));
                gsm.render();
            }
        });

        return mainMenuBtn;
    }

    private Button createRestartBtn() {
        Texture texture = assetHandler.getManager().get(assetHandler.RESTART_BUTTON_PATH, Texture.class);
        Drawable drawable = new TextureRegionDrawable(new TextureRegion(texture));
        Button restartBtn = new ImageButton(drawable);
        restartBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gsm.pop();
                gsm.restartLvl(true);
                gsm.push(new PlayState(gsm, getLvlMap(gsm.getLvl())));
                gsm.render();
            }
        });


        return restartBtn;
    }

    private Label createTitle() {
        int row_height = Gdx.graphics.getWidth() / 12;
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = assetHandler.getManager().get(assetHandler.FONT_PATH, BitmapFont.class);
        Label title = new Label(String.format(LEVEL_COMPLETE, level), labelStyle);
        title.setSize(Gdx.graphics.getWidth(), row_height);
        title.setAlignment(Align.center);
        title.setWidth(Gdx.graphics.getWidth());

        return title;
    }

    private Label createTimeLabel() {
        int row_height = Gdx.graphics.getWidth() / 12;
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = assetHandler.getManager().get(assetHandler.FONT_PATH, BitmapFont.class);
        Label timeLabel = new Label(String.format(TIMER, playTime), labelStyle);
        timeLabel.setSize(Gdx.graphics.getWidth(), row_height);
        timeLabel.setAlignment(Align.center);
        timeLabel.setWidth(Gdx.graphics.getWidth());
        return timeLabel;
    }

    private void createLayout() {
        int span = 3;
        if (gsm.getLvl() == 5) {
            span = 2;
        }
        Label title = createTitle();
        Label timeLabel = createTimeLabel();
        Button nextLvlBtn = createNextLvlBtn();
        Button mainMenuBtn = createMainMenuBtn();
        Button restartBtn = createRestartBtn();
        table.add(title).colspan(span).align(Align.center).width(Gdx.graphics.getWidth()).padBottom(150);
        table.row();
        table.add(timeLabel).colspan(span).align(Align.center).width(Gdx.graphics.getWidth()).padBottom(100);
        table.row();
        table.add(mainMenuBtn).size(100, 100);
        table.add(restartBtn).size(100, 100);
        if (gsm.getLvl() != 5) {
            table.add(nextLvlBtn).size(100, 100);
        }
//        table.debug();
        table.setWidth(Gdx.graphics.getWidth());
        table.align(Align.center|Align.top);
        table.setPosition(0, Gdx.graphics.getHeight() / 1.5f);

        stage.addActor(table);
    }

    private TiledMap getLvlMap(int nextLvl) {
        TiledMap map;
        switch (nextLvl) {
            case 1: map = assetHandler.getManager().get(assetHandler.LEVEL_1_PATH, TiledMap.class);
                break;
            case 2: map = assetHandler.getManager().get(assetHandler.LEVEL_2_PATH, TiledMap.class);
                break;
            case 3: map = assetHandler.getManager().get(assetHandler.LEVEL_3_PATH, TiledMap.class);
                break;
            case 4: map = assetHandler.getManager().get(assetHandler.LEVEL_4_PATH, TiledMap.class);
                break;
            case 5: map = assetHandler.getManager().get(assetHandler.LEVEL_5_PATH, TiledMap.class);
                break;
            default: map = assetHandler.getManager().get(assetHandler.LEVEL_1_PATH, TiledMap.class);
                break;
        }

        return map;
    }
}
