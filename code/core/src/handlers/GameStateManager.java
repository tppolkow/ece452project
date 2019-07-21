package handlers;

import com.ece454.gotl.GotlGame;

import java.util.Stack;

import states.State;

public class GameStateManager
{
    private GotlGame game;
    private Stack<State> states;
    private long playTime;
    private boolean isTimerSet;
    private int level;

    public GameStateManager(GotlGame game)
    {
        this.game = game;
        states = new Stack<State>();
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
