package states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.ece454.gotl.Game;

import handlers.GameStateManager;

import static com.ece454.gotl.Game.SCALE;

public abstract class State
{
    protected OrthographicCamera cam;
    protected GameStateManager gsm;

    public State(GameStateManager gsm)
    {
        this.gsm = gsm;
        cam = new OrthographicCamera();
        cam.setToOrtho(false, Gdx.graphics.getWidth() / SCALE, Gdx.graphics.getHeight() / SCALE);
    }

    protected abstract void handleInput();
    public abstract void render(SpriteBatch sb);
    public abstract void update();
    public abstract void dispose();
}