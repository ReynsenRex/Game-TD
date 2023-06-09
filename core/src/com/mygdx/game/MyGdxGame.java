package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

public class MyGdxGame extends ApplicationAdapter {

    private Texture zombieUp;
    private Texture zombieRight;
    private Texture zombieDown;
    private SpriteBatch batch;
    private Texture texture;
    private Sprite sprite;
    private OrthographicCamera camera;
    private Array<Zombie> zombies;
    private long nextSpawnTime = generateNextSpawnTime();

    private float screenWidth;
    private float screenHeight;

    @Override
    public void create() {

        zombieRight = new Texture(Gdx.files.internal("1 Zombie2.png"));
        zombieUp = new Texture(Gdx.files.internal("1 Zombie3.png"));
        zombieDown = new Texture(Gdx.files.internal("1 Zombie1.png"));

        batch = new SpriteBatch();

        texture = new Texture("Map.png");

        sprite = new Sprite(texture);
        sprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        sprite.setPosition(0, 0);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        zombies = new Array<>();

        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();
    }

    private void spawnZombie() {
        Zombie zombie = new Zombie();
        zombie.texture = zombieRight;
        zombie.sprite = new Sprite(zombie.texture);
        zombie.sprite.setSize(screenWidth * 0.052f, screenHeight * 0.093f);
        zombie.x = 0;
        zombie.y = screenHeight * 0.416f;
        zombie.targetX = screenWidth * 0.132f;
        zombie.targetY = screenHeight * 0.416f;
        zombie.moving = true;
        zombies.add(zombie);
    }

    private long generateNextSpawnTime() {
        return TimeUtils.nanoTime() + (long) (Math.random() * 800000000) + 100000000;
    }

    public void render() {
        // Clear the screen
        ScreenUtils.clear(0, 0, 0, 1);

        // Update the camera
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        // Spawn a new zombie if it's time
        if (TimeUtils.nanoTime() > nextSpawnTime) {
            spawnZombie();
            nextSpawnTime = generateNextSpawnTime();
        }

        // Begin the sprite batch
        batch.begin();

        // Render the map
        sprite.draw(batch);

        // Render and update the zombies
        for (Zombie zombie : zombies) {
            zombie.sprite.setPosition(zombie.x, zombie.y);
            zombie.sprite.draw(batch);

            // Check if the zombie has reached its current target position
            if (zombie.moving) {
                float speed = screenWidth * 0.003f; // Adjust the speed as desired

                // Calculate the direction and distance to the target position
                float deltaX = zombie.targetX - zombie.x;
                float deltaY = zombie.targetY - zombie.y;
                float distance = (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);

                // Check if the zombie has reached the target position
                if (distance <= speed) {
                    // Snap the zombie to the target position
                    zombie.x = zombie.targetX;
                    zombie.y = zombie.targetY;

                    // Determine the next target position
                    if (zombie.targetX == screenWidth * 0.132f && zombie.targetY == screenHeight * 0.416f) {
                        zombie.targetX = screenWidth * 0.132f;
                        zombie.targetY = screenHeight * 0.703f;
                    } else if (zombie.targetX == screenWidth * 0.132f && zombie.targetY == screenHeight * 0.703f) {
                        zombie.targetX = screenWidth * 0.338f;
                        zombie.targetY = screenHeight * 0.703f;
                    } else if (zombie.targetX == screenWidth * 0.338f && zombie.targetY == screenHeight * 0.703f) {
                        zombie.targetX = screenWidth * 0.338f;
                        zombie.targetY = screenHeight * 0.314f;
                    } else if (zombie.targetX == screenWidth * 0.338f && zombie.targetY == screenHeight * 0.314f) {
                        zombie.targetX = screenWidth * 0.594f;
                        zombie.targetY = screenHeight * 0.314f;
                    } else if (zombie.targetX == screenWidth * 0.594f && zombie.targetY == screenHeight * 0.314f) {
                        zombie.targetX = screenWidth * 0.6042f;
                        zombie.targetY = screenHeight * 0.5093f;
                    } else if (zombie.targetX == screenWidth * 0.6042f && zombie.targetY == screenHeight * 0.5093f) {
                    if (zombie.x >= screenWidth) {
                        // If the zombie is off-screen, set moving to false to stop rendering it
                        zombie.moving = false;
                    } else {
                        // Continue moving the zombie to the right
                        zombie.targetX = screenWidth;
                        zombie.targetY = screenHeight * 0.5093f;
                    }
                }
                } else {
                    // Calculate the interpolation factor based on the distance and speed
                    float interpolationFactor = speed / distance;

                    // Update the zombie's position based on the interpolation
                    zombie.x += deltaX * interpolationFactor;
                    zombie.y += deltaY * interpolationFactor;
                }
            }
        }

        // End the sprite batch
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        texture.dispose();
    }
}
