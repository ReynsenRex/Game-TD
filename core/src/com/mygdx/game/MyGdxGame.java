package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
public class MyGdxGame extends ApplicationAdapter {
    Animation<TextureRegion> zombieAnimation;
    private Texture texture, zombieTexture, towerTexture;
    private SpriteBatch batch;
    private Sprite sprite;
    private OrthographicCamera camera;
    private Array<Zombie> zombies;
    private Array<Tower> towers;
    private long nextSpawnTime = generateNextSpawnTime();
    private float screenWidth, screenHeight, stateTime;
    private Sprite spriteTower;

    private BitmapFont font;
    @Override
    public void create() {
        // buat Zombie
        zombieTexture = new Texture(Gdx.files.internal("Zombie.png"));
        TextureRegion[][] tmp = TextureRegion.split(zombieTexture, 124 / 3, 144 / 4);

        TextureRegion[] walkFrames = new TextureRegion[12];
        int index = 0;
        for (int i = 0; i < tmp.length; i++) {
            for (int j = 0; j < tmp[i].length; j++) {
                walkFrames[index++] = tmp[i][j];
            }
        }

        zombieAnimation = new Animation<TextureRegion>(0.3f, walkFrames);
        stateTime = 1f;

        batch = new SpriteBatch();

        // MAP
        texture = new Texture("Map.png");

        sprite = new Sprite(texture);
        sprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        sprite.setPosition(0, 0);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        zombies = new Array<>();

        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();

        font = new BitmapFont();
        // End of Zombie

        // buat Tower
        towerTexture = new Texture(Gdx.files.internal("towerTexture.png"));

        towers = new Array<>();
        towers.add(spawnTower(400,500)); // tower 1
        towers.add(spawnTower(50,510)); // tower 2
        towers.add(spawnTower(750,400)); // tower 3
        towers.add(spawnTower(1300,700)); // tower 4

    }

    private void spawnZombie() {
        Zombie zombie = new Zombie();
        zombie.texture = zombieTexture;
        zombie.sprite = new Sprite(zombie.texture);
        TextureRegion currentFrame = zombieAnimation.getKeyFrame(stateTime, true);
        float frameWidth = currentFrame.getRegionWidth();
        float frameHeight = currentFrame.getRegionHeight();
        float scale = 2f; // Set the scale factor to make the sprite appear larger
        float width = frameWidth * scale;
        float height = frameHeight * scale;
        zombie.sprite.setSize(width, height);
        zombie.x = 0;
        zombie.y = screenHeight * 0.416f;
        zombie.targetX = screenWidth * 0.132f;
        zombie.targetY = screenHeight * 0.416f;
        zombie.moving = true;
        zombies.add(zombie);
    }

    private Tower spawnTower(float x, float y) {
        Tower tower = new Tower();
        Tower.sprite = new Sprite(towerTexture);
        // Set the tower sprite's position and scale
        float towerScale = 0.6f; // Adjust the scale factor as desired

        Tower.sprite.setPosition(x, y);
        Tower.sprite.setScale(towerScale);

        spriteTower = Tower.sprite;

        return tower;
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

        stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time
        TextureRegion currentFrame = zombieAnimation.getKeyFrame(stateTime, true);
        // Render the map
        sprite.draw(batch);
        // Render and update the zombies
        for (Zombie zombie : zombies) {
            zombie.sprite.setPosition(zombie.x, zombie.y);
            // Draw the current frame of the zombie animation
            zombie.sprite.setRegion(currentFrame);
            zombie.sprite.draw(batch);

            // Render the zombie's health
            font.draw(batch, "Health: " + zombie.health, zombie.x, zombie.y + 90);
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

        // draw tower
        for (Tower tower : towers) {
            Tower.sprite.draw(batch);
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