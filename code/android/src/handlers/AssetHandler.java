package handlers;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * Class that handles the loading of all assets
 */
public class AssetHandler {

    private AssetManager manager = new AssetManager();
    public static final String BTN_SKIN_PATH = "skins/holo/skin/dark-mdpi/Holo-dark-mdpi.json";
    public static final String FONT_PATH = "fonts/amatic/AmaticSC-Regular.ttf";
    public static final String MAP_PATH = "map/desert_demo.tmx";
    public static final String ONE_STAR_PATH = "rating/1_star.png";
    public static final String TWO_STAR_PATH= "rating/2_star.png";
    public static final String THREE_STAR_PATH= "rating/3_star.png";
    public static final String LEVEL_1_PATH = "map/level_1.tmx";
    public static final String LEVEL_2_PATH = "map/level_2.tmx";
    public static final String LEVEL_3_PATH = "map/level_3.tmx";
    public static final String LEVEL_4_PATH = "map/level_4.tmx";
    public static final String LEVEL_5_PATH = "map/level_5.tmx";
    public static final String PLAYER_IMG_PATH = "goose.png";
    public static final String REVERSE_PLAYER_IMG_PATH = "goose_flipped.png";
    public static final String GOOSE_FACE_PATH = "goose_face.png";
    public static final String GOTL_CREST_PATH = "gotl_crest.png";
    public static final String MENU_BUTTON_PATH = "buttons/menu_button.png";
    public static final String NEXT_LEVEL_BUTTON_PATH = "buttons/next_level_button.png";
    public static final String RESTART_BUTTON_PATH = "buttons/restart_button.png";
    public static final String START_BUTTON_PATH = "buttons/start_button.png";

    public void loadAssets() {
        loadFonts();
        loadButtonImgs();
        loadMap();
        loadTextures();
        manager.finishLoading();
    }

    private void loadFonts() {
        FileHandleResolver resolver = new InternalFileHandleResolver();
        manager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        manager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));

        FreetypeFontLoader.FreeTypeFontLoaderParameter parameter = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        parameter.fontFileName = FONT_PATH;
        parameter.fontParameters.size = 120;
        parameter.fontParameters.color = Color.RED;
        manager.load(FONT_PATH, BitmapFont.class, parameter);
    }

    private void loadButtonImgs() {
        manager.load(MENU_BUTTON_PATH, Texture.class);
        manager.load(NEXT_LEVEL_BUTTON_PATH, Texture.class);
        manager.load(RESTART_BUTTON_PATH, Texture.class);
        manager.load(START_BUTTON_PATH, Texture.class);
    }

    private void loadMap() {
        manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        manager.load(LEVEL_1_PATH, TiledMap.class);
        manager.load(LEVEL_2_PATH, TiledMap.class);
        manager.load(LEVEL_3_PATH, TiledMap.class);
        manager.load(LEVEL_4_PATH, TiledMap.class);
        manager.load(LEVEL_5_PATH, TiledMap.class);
    }

    private void loadTextures(){
        manager.setLoader(Texture.class, new TextureLoader(new InternalFileHandleResolver()));
        manager.load(ONE_STAR_PATH, Texture.class);
        manager.load(TWO_STAR_PATH, Texture.class);
        manager.load(THREE_STAR_PATH, Texture.class);
        manager.load(PLAYER_IMG_PATH, Texture.class);
        manager.load(REVERSE_PLAYER_IMG_PATH, Texture.class);
        manager.load(GOOSE_FACE_PATH, Texture.class);
        manager.load(GOTL_CREST_PATH, Texture.class);
    }

    public AssetManager getManager() {
        return manager;
    }
}
