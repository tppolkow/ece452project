package handlers;

import android.content.SharedPreferences;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.ece454.gotl.AndroidLauncher;
import com.ece454.gotl.GotlGame;

import java.util.Stack;

import states.State;

import static com.ece454.gotl.GotlGame.PREFS_NAME;

public class GameStateManager
{
    private GotlGame game;
    private Stack<State> states;
    private long playTime;
    private boolean isTimerSet;
    private int level;
    public static final int NUM_LEVELS = 5;
    private static final String currentLevelStr = "currentLevel";
    public static boolean resetInputProcessor = false;

    public GameStateManager(GotlGame game)
    {
        this.game = game;
        states = new Stack<State>();
        isTimerSet = false;
        getLevelFromSettings();
    }

    public void push(State s)
    {
        states.push(s);
    }

    public void pop()
    {
        states.pop().dispose();
    }

    public void set(State s)
    {
        if (!states.empty())
        {
            pop();
        }
        push(s);
    }

    public void update(float dt)
    {
        states.peek().update();
    }

    public void render()
    {
        states.peek().render();
    }

    public GotlGame getGame() { return game; }

    public void setPlayTime(long playTime) {
        if (!isTimerSet) {
            isTimerSet = true;
            this.playTime = playTime;
        }
    }

    public long getPlayTime() {
        isTimerSet = false;
        return playTime;
    }

    private void getLevelFromSettings()
    {
        SharedPreferences settings = AndroidLauncher.getAppContext().getSharedPreferences(PREFS_NAME, 0);
        this.level = settings.getInt(currentLevelStr, 1);
    }

    public void incrementLevel() {
        this.level++;
        saveLevel();
    }

    private void saveLevel() {
        AndroidLauncher.getAppContext().getSharedPreferences(PREFS_NAME, 0)
                .edit()
                .putInt(currentLevelStr, this.level)
                .commit();
    }


    public int getLvl() {
        return level;
    }

    public void resetLvl() {
        this.level = 1;
        saveLevel();
    }

    public TiledMap getLvlMap() {
        AssetHandler assetHandler = game.getAssetHandler();
        switch (level) {
            case 1:
                return assetHandler.getManager().get(assetHandler.LEVEL_1_PATH, TiledMap.class);
            case 2:
                return assetHandler.getManager().get(assetHandler.LEVEL_2_PATH, TiledMap.class);
            case 3:
                return assetHandler.getManager().get(assetHandler.LEVEL_3_PATH, TiledMap.class);
            case 4:
                return assetHandler.getManager().get(assetHandler.LEVEL_4_PATH, TiledMap.class);
            case 5:
                return assetHandler.getManager().get(assetHandler.LEVEL_5_PATH, TiledMap.class);
            default:
                return null;
        }
    }
}
