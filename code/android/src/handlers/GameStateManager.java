package handlers;

import com.ece454.gotl.GotlGame;

import java.util.Stack;

import states.State;

public class GameStateManager
{
    private GotlGame game;
    private Stack<State> states;

    public GameStateManager(GotlGame game)
    {
        this.game = game;
        states = new Stack<>();
    }

    public void push(State s)
    {
        states.push(s);
    }

    public void pop()
    {
        states.pop().dispose();
    }

    public void render()
    {
        states.peek().render();
    }

    public GotlGame getGame() { return game; }
}
