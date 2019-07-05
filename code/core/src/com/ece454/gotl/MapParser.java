package com.ece454.gotl;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

public class MapParser {
    private static final String MAP_LAYER_NAME_GROUND = "ground";
    private static final String MAP_LAYER_NAME_BOUNDS = "bounds";
    private static final String MAP_LAYER_NAME_DANGERS = "danger";
    private static final String MAP_LAYER_NAME_CLOUD = "cloud";
    public static void parseMapLayers(World world, TiledMap tiledMap) {
        for (MapLayer layer : tiledMap.getLayers()) {
            for (MapObject object : layer.getObjects()) {
                Shape shape;
                if (object instanceof PolylineMapObject) {
                    shape = createPolyline((PolylineMapObject) object);
                } else {
                    continue;
                }
                if (layer.getName().equals(MAP_LAYER_NAME_GROUND))
                    new Ground(world, shape);
                if (layer.getName().equals(MAP_LAYER_NAME_BOUNDS))
                    new Bounds(world, shape);
                if (layer.getName().equals(MAP_LAYER_NAME_DANGERS))
                    new DangerZone(world, shape);
                if (layer.getName().equals(MAP_LAYER_NAME_CLOUD)) {
                    new Cloud(world, shape);
                }
            }
        }
    }
    private static ChainShape createPolyline(PolylineMapObject polyline) {
        float[] vertices = polyline.getPolyline().getTransformedVertices();
        Vector2[] worldVerticies = new Vector2[vertices.length / 2];
        for (int i = 0; i < worldVerticies.length; i++) {
            worldVerticies[i] = new Vector2(vertices[i * 2] / GotlGame.PIXEL_PER_METER,
                    vertices[i * 2 + 1] / GotlGame.PIXEL_PER_METER);
        }
        ChainShape cs = new ChainShape();
        cs.createChain(worldVerticies);
        return cs;
    }
}
