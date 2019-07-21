package states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import handlers.GameStateManager;

import static com.ece454.gotl.GotlGame.SCALE;

public abstract class State
{
    protected OrthographicCamera cam;
    protected GameStateManager gsm;
    protected Viewport viewport;

    public State(GameStateManager gsm)
    {
        this.gsm = gsm;
        cam = new OrthographicCamera();
        cam.setToOrtho(false, Gdx.graphics.getWidth() / SCALE, Gdx.graphics.getHeight() / SCALE);
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), cam);
//        viewport = new FitViewport(16*32, 54 * 32, cam);
    }

    public Viewport getViewport(){
        return viewport;
    }

    protected abstract void handleInput();
    public abstract void render();
    public abstract void update();
    public abstract void dispose();
}