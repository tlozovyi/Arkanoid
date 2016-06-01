package ua.in.levor.arkanoid.PowerUps;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import java.util.Random;

import ua.in.levor.arkanoid.Arkanoid;

public class PowerUp extends Sprite {
    public static final int GRAVITY = 150;
    public static final int WIDTH = 24;
    public static final int NUMBER_OF_POWERUPS_AVAILABLE = 1;

    private World world;
    public Body b2body;

    private Type type;
    private Vector2 center;

    private boolean isReady = false;

    public PowerUp(World world, Vector2 centerPosition) {
        this.world = world;
        this.center = centerPosition;

        setBounds(0, 0, Arkanoid.scale(WIDTH), Arkanoid.scale(WIDTH));

        switch (new Random().nextInt(NUMBER_OF_POWERUPS_AVAILABLE)) {
            case 0:
                type = Type.BOMB;
                set(Arkanoid.adjustSize(new Sprite(new Texture(Gdx.files.internal("PowerUps/1bomb_powerup.png")))));
                break;
        }

        definePowerUp();
    }

    private void definePowerUp() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(center);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(Arkanoid.scale(WIDTH / 2), Arkanoid.scale(WIDTH / 2));

        fdef.shape = shape;
        fdef.isSensor = true;
        Fixture fixture = b2body.createFixture(fdef);
        fixture.setUserData(this);

        b2body.setLinearVelocity(0, -1.5f);
    }

    public void readyToUse() {
        isReady = true;
    }

    public boolean isReady() {
        return isReady;
    }

    public void update(float dt) {
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
    }

    public void dispose() {
        getTexture().dispose();
    }

    public enum Type {BOMB}
}