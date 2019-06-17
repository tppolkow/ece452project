package states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import handlers.GameStateManager;

public class PlayState extends State
{
    private World world;

    public PlayState(GameStateManager gsm)
    {
        super(gsm);
        world = new World(new Vector2(0, -9.81f), true);
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

    }

    @Override
    public void dispose()
    {

    }
}
