package handlers;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.ece454.gotl.MapParser;
import com.ece454.gotl.WorldContactListener;

public class WorldManager
{
    private static final float VELOCITY_Y = -9.81f;
    private static final float VELOCITY_X = 0f;
    public static World world;

    private WorldManager() {}

    public static void parseTiledMap(TiledMap tm)
    {
        MapParser.parseMapLayers(WorldManager.world, tm);
    }

    public static boolean initialized()
    {
        return world != null;
    }

    public static void resetWorld()
    {
        if (initialized())
        {
            world.dispose();
        }

        world = new World(new Vector2(VELOCITY_X, VELOCITY_Y), false);
        world.setContactListener(new WorldContactListener());
    }
}
