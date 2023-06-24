package Tower;

import java.util.ArrayList;

import Enemies.Enemy;
import Enemies.Zombie;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;

public class Tower {
    public Sprite sprite;
    private SpriteBatch batch;
    private ArrayList<Projectile> projectiles;
    private float cooldownTimer; // Cooldown timer for shots
    private float cooldownDuration = 0.5f; // Cooldown duration in seconds
    private Enemy zombie;

    public Tower() {
        projectiles = new ArrayList<>();
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

        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) && cooldownTimer <= 0) {
            attack();
            cooldownTimer = cooldownDuration; // Start the cooldown timer
        }

        for (Projectile projectile : projectiles) {
            projectile.update(Gdx.graphics.getDeltaTime(), (Zombie) zombie);
            projectile.render(batch);
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

        Projectile projectile = new Projectile();
        projectile.setPosition(1670, 770);
        projectile.setTarget(mouseX, mouseY);

        projectiles.add(projectile);
    }
}
