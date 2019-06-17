package states;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import handlers.GameStateManager;
import states.State;

public class MenuState extends State
{
    private BitmapFont font = new BitmapFont();
    public MenuState(GameStateManager gsm)
    {
        super(gsm);
    }
    @Override
    protected void handleInput()
    {

    }

    @Override
    public void update(float dt)
    {

    }

    @Override
    public void render(SpriteBatch sb)
    {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        font.draw(sb, "menu state", 100, 100);
        sb.end();
    }

    @Override
    public void dispose()
    {

    }
}
