package states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;

import handlers.GameStateManager;

import static com.ece454.gotl.GotlGame.SCALE;

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
    public abstract void render();
    public abstract void update();
    public abstract void dispose();
}