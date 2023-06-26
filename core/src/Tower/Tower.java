package Tower;

import java.util.ArrayList;

import Enemies.Enemy;
import Enemies.Zombie;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;

public class Tower {
    public Sprite sprite;
    private SpriteBatch batch;
    private float cooldownTimer; // Cooldown timer for shots
    private float cooldownDuration = 0.5f; // Cooldown duration in seconds
    private Enemy zombie;
    private Sound shootingSound;

    public Tower() {
        batch = new SpriteBatch();
        Texture towerTexture = new Texture(Gdx.files.internal("Turret.png"));
        sprite = new Sprite(towerTexture);
        sprite.setOriginCenter();
        sprite.setScale(0.3f);
        sprite.setPosition(1400, 550);
        cooldownTimer = 0; // Initialize the cooldown timer
        zombie = new Zombie();
    }

    public void render() {
        batch.begin();
        if ((Gdx.input.isButtonPressed(Input.Buttons.LEFT) || Gdx.input.isKeyPressed(Input.Keys.SPACE)) && cooldownTimer <= 0) {
            playSound();
            attack();
            cooldownTimer = cooldownDuration; // Start the cooldown timer
        }


        float mouseX = Gdx.input.getX();
        float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

        float turretCenterX = sprite.getX() + sprite.getOriginX();
        float turretCenterY = sprite.getY() + sprite.getOriginY();

        float angle = MathUtils.atan2(mouseY - turretCenterY, mouseX - turretCenterX) * MathUtils.radiansToDegrees;
        sprite.setRotation(angle);

        sprite.draw(batch);
        batch.end();

        if (cooldownTimer > 0) {
            cooldownTimer -= Gdx.graphics.getDeltaTime(); // Update the cooldown timer
        }
    }

    public void attack() {
        float mouseX = Gdx.input.getX();
        float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

    }

    public void playSound() {
        shootingSound = Gdx.audio.newSound(Gdx.files.internal("Blaster_short.mp3"));
        shootingSound.setVolume(1L, (float) 0.1);
        shootingSound.play();
    }


}
