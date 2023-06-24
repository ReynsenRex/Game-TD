package Enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class Zombie extends Enemy{
    private Animation<TextureRegion> zombieAnimationRight, zombieAnimationUp, zombieAnimationDown;
    private Texture zombieTextureMoveRight, zombieTextureMoveUp, zombieTextureMoveDown;
    private float screenWidth, screenHeight, stateTime; // Animation time for the zombie
    public float x, y;
    public int health;
    private Array<Enemy> zombies;
    private long nextSpawnTime = generateNextSpawnTime();
    private SpriteBatch batch;
    private BitmapFont font;
    private Rectangle hitbox;

    public Zombie() {
        super(new Texture(Gdx.files.internal("ZombieRight.png")),0 , 450);

        batch = new SpriteBatch();
        font = new BitmapFont();
        hitbox = new Rectangle(x, y, 70, 70); // Create the hitbox with initial position and size

        // Buat Zombie
        zombieTextureMoveRight = new Texture(Gdx.files.internal("ZombieRight.png"));

        TextureRegion[][] textureMoveRight = TextureRegion.split(zombieTextureMoveRight, 124 / 3, 36);
        TextureRegion[] walkFramesRight = new TextureRegion[textureMoveRight.length * textureMoveRight[0].length];
        int index = 0;
        for (int i = 0; i < textureMoveRight.length; i++) {
            for (int j = 0; j < textureMoveRight[i].length; j++) {
                walkFramesRight[index++] = textureMoveRight[i][j];
            }
        }

        zombieTextureMoveUp = new Texture(Gdx.files.internal("ZombieUp.png"));
        TextureRegion[][] textureMoveUp = TextureRegion.split(zombieTextureMoveUp, 124 / 3, 36);
        TextureRegion[] walkFramesUp = new TextureRegion[textureMoveUp.length * textureMoveUp[0].length];
        int indexUp = 0;
        for (int i = 0; i < textureMoveUp.length; i++) {
            for (int j = 0; j < textureMoveUp[i].length; j++) {
                walkFramesUp[indexUp++] = textureMoveUp[i][j];
            }
        }

        zombieTextureMoveDown = new Texture(Gdx.files.internal("ZombieDown.png"));
        TextureRegion[][] textureMoveDown = TextureRegion.split(zombieTextureMoveDown, 124 / 3, 36);
        TextureRegion[] walkFramesDown = new TextureRegion[textureMoveDown.length * textureMoveDown[0].length];
        int indexDown = 0;
        for (int i = 0; i < textureMoveDown.length; i++) {
            for (int j = 0; j < textureMoveDown[i].length; j++) {
                walkFramesDown[indexDown++] = textureMoveDown[i][j];
            }
        }

        zombieAnimationRight = new Animation<>(0.3f, walkFramesRight);
        zombieAnimationUp = new Animation<>(0.3f, walkFramesUp);
        zombieAnimationDown = new Animation<>(0.3f, walkFramesDown);

        zombies = new Array<>();

        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();
        this.health = 100;
    }
    @Override
    public long generateNextSpawnTime() {
        return TimeUtils.nanoTime() + (long) (Math.random() * 800000000) + 100000000;
    }
    @Override
    public void spawnZombie() {
        Enemy zombie = new Zombie();
        zombie.texture = zombieTextureMoveRight;
        zombie.sprite = new Sprite(zombie.texture);
        TextureRegion currentFrame = zombieAnimationRight.getKeyFrame(stateTime, true);
        float frameWidth = currentFrame.getRegionWidth();
        float frameHeight = currentFrame.getRegionHeight();
        float scale = 2f; // Set the scale factor to make the sprite appear larger
        float width = frameWidth * scale;
        float height = frameHeight * scale;
        zombie.sprite.setSize(width, height);
        zombie.x = 0;
        zombie.y = 450;
        zombie.targetX = 255;
        zombie.targetY = 450;
        zombie.moving = true;
        zombies.add(zombie);
    }
    @Override
    public void render() {
        batch.begin();

        // Spawn a new zombie if it's time
        if (TimeUtils.nanoTime() > nextSpawnTime) {
            spawnZombie();
            nextSpawnTime = generateNextSpawnTime();
        }
        stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time
        TextureRegion currentFrame = zombieAnimationRight.getKeyFrame(stateTime, true);

        // Render and update the zombies
        for (Enemy zombie : zombies) {
            zombie.sprite.setPosition(zombie.x, zombie.y);
            hitbox.setPosition(zombie.x, zombie.y);

            zombie.sprite.setRegion(currentFrame);
            zombie.sprite.draw(batch);

            // Draw the current frame of the zombie animation
            if (zombie.targetX == 255 && zombie.targetY == 760) {
                currentFrame = zombieAnimationUp.getKeyFrame(stateTime, true);
            } else if (zombie.targetX == 650 && zombie.targetY == 340) {
                currentFrame = zombieAnimationDown.getKeyFrame(stateTime, true);
            } else if (zombie.targetX == 1160 && zombie.targetY == 550) {
                currentFrame = zombieAnimationUp.getKeyFrame(stateTime, true);
            } else {
                currentFrame = zombieAnimationRight.getKeyFrame(stateTime, true);
            }

            // Draw the current frame of the zombie animation
            zombie.sprite.setRegion(currentFrame);
            zombie.sprite.draw(batch);
            // Render the zombie's health
            font.draw(batch, "      " + zombie.getHealth(), zombie.x, zombie.y + 90);
            // Check if the zombie has reached its current target position
            if (zombie.moving) {
                float speed  = 2; // Adjust the  as desired

                // Calculate the direction and distance to the target position
                float deltaX = zombie.targetX - zombie.x;
                float deltaY = zombie.targetY - zombie.y;
                float distance = (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);

                // Check if the zombie has reached the target position
                if (distance <= speed ) {
                    // Snap the zombie to the target position
                    zombie.x = zombie.targetX;
                    zombie.y = zombie.targetY;

                    // Determine the next target position
                    //Move Right
                    if (zombie.targetX == 255 && zombie.targetY == 450) {
                        zombie.targetX = 255;
                        zombie.targetY = 760;
                        //Move Up
                    } else if (zombie.targetX == 255 && zombie.targetY == 760) {
                        zombie.targetX = 650;
                        zombie.targetY = 760;
                        //Move Right
                    } else if (zombie.targetX == 650 && zombie.targetY == 760) {
                        zombie.targetX = 650;
                        zombie.targetY = 340;
                        //Move Down
                    } else if (zombie.targetX == 650 && zombie.targetY == 340) {
                        zombie.targetX = 1140;
                        zombie.targetY = 340;
                        //Move Right
                    } else if (zombie.targetX == 1140 && zombie.targetY == 340) {
                        zombie.targetX = 1160;
                        zombie.targetY = 550;
                        //Move Up
                    } else if (zombie.targetX == 1160 && zombie.targetY == 550) {
                        if (zombie.x >= screenWidth) {
                            // If the zombie is off-screen, set moving to false to stop rendering it
                            zombie.moving = false;
                        } else {
                            // Continue moving the zombie to the right
                            zombie.targetX = screenWidth;
                            zombie.targetY = 550;
                        }
                    }
                } else {
                    // Calculate the interpolation factor based on the distance and 
                    float interpolationFactor = speed  / distance;
                    // Update the zombie's position based on the interpolation
                    zombie.x += deltaX * interpolationFactor;
                    zombie.y += deltaY * interpolationFactor;
                }
            }
        }
        batch.end();
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    @Override
    public int setHealth() {
        return health = 50;
    }
    @Override
    public int getHealth() {
        return this.health;
    }

}

