package handlers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.ece454.gotl.Game;

import java.util.Stack;

import states.State;

public class GameStateManager
{
    private Game game;
    private Stack<State> states;

    public GameStateManager(Game game)
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

    public void render(SpriteBatch sb)
    {
        states.peek().render(sb);
    }

    public Game getGame() { return game; }
}
