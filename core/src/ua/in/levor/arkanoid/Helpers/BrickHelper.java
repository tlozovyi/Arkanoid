package ua.in.levor.arkanoid.Helpers;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import ua.in.levor.arkanoid.Arkanoid;
import ua.in.levor.arkanoid.Sprites.Brick;

public class BrickHelper {
    private static BrickHelper instance;

    private Array<Brick> bricks = new Array<Brick>();

    private BrickHelper() {}

    public static BrickHelper getInstance() {
        if (instance == null) {
            instance = new BrickHelper();
        }
        return instance;
    }

    public void addBrick(Brick b) {
        bricks.add(b);
    }

    public Array<Brick> getAllBricks() {
        return bricks;
    }

    public Array<Brick> getAllBricksInRadius(float radius, Vector2 originPoint) {
        Array<Brick> inRadius = new Array<Brick>();
        float originX = originPoint.x;
        float originY = originPoint.y;
        float scaledRadius = Arkanoid.scale(radius);
        for (Brick brick : bricks) {
            float x = brick.getBody().getPosition().x;
            float y = brick.getBody().getPosition().y;
            float distance = (float) Math.sqrt(Math.pow(originX - x, 2) + Math.pow(originY - y, 2));
            if (distance < scaledRadius) {
                inRadius.add(brick);
            }
        }

        return inRadius;
    }
}