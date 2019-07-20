package handlers;

import com.ece454.gotl.GotlGame;

import java.util.Stack;

import states.State;

public class GameStateManager
{
    private static GameStateManager singleton = null;
    private GotlGame game;
    private Stack<State> states;
    private long playTime;
    private boolean isTimerSet;
    private int level;

    private GameStateManager(GotlGame game)
    {
        this.game = game;
        states = new Stack<State>();
    }

    public static GameStateManager getInstance() {
        if (singleton == null) {
            throw new AssertionError("Have to call init first");
        }

        return singleton;
    }

    public static GameStateManager init(GotlGame game) {
        if (singleton != null) {
            throw new AssertionError("Game State Manager already initialized");
        }

        singleton = new GameStateManager(game);
        return singleton;
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
        states.push(s);
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

    public void increaseLevel() {
        this.level++;
    }

    public int getLevel() {
        return level;
    }
}
