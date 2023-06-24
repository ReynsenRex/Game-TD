package Tower;

import java.util.ArrayList;

import Enemies.Zombie;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

public class Tower {
    public Sprite sprite;
    public Texture towerTexture;
    private Sprite towerSprite;
    private SpriteBatch batch;

    public Tower() {
        batch = new SpriteBatch();
        towerTexture = new Texture(Gdx.files.internal("Turret.png"));
        towerSprite = new Sprite(towerTexture);
        sprite = towerSprite;
    }

    public void render() {

        batch.begin();

        float towerScale = 0.3f;
        towerSprite.setOriginCenter();
        towerSprite.setScale(towerScale);
        towerSprite.setPosition(1400, 550);
        float mouseX = Gdx.input.getX();
        float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

        float turretCenterX = sprite.getX() + sprite.getOriginX();
        float turretCenterY = sprite.getY() + sprite.getOriginY();

        float angle = MathUtils.atan2(mouseY - turretCenterY, mouseX - turretCenterX) * MathUtils.radiansToDegrees;
        sprite.setRotation(angle);

        sprite.draw(batch);
        towerSprite.draw(batch);
        batch.end();


    }

}