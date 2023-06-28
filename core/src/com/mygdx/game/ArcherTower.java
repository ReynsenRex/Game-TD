package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class ArcherTower extends Tower{
    public Vector2 position;
    public Vector2 projectile_position;
    public Sprite sprite;
    public Sprite projectile_sprite;
    public float speed = 1000;
    public float projectile_speed = 10000;

    public ArcherTower() {
        Texture texture = new Texture(Gdx.files.internal("Archer_tower.png"));
        sprite = new Sprite(texture);

        Texture projectile_texture = new Texture(Gdx.files.internal("arrow.png"));
        projectile_sprite = new Sprite(projectile_texture);

        sprite.setScale((float) 0.8);
        projectile_sprite.setScale((float) 0.3);
        projectile_sprite.rotate(90);

        position = new Vector2(sprite.getScaleX() * sprite.getHeight() / 2, 800);
        projectile_position = new Vector2(position.x, position.y);
    }
    public void Update(float deltatime) {
        if (Gdx.input.isKeyPressed(Input.Keys.ENTER )) {
            projectile_position.x = position.x;
            projectile_position.y = position.y;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            position.x += deltatime * speed;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            position.x -= deltatime * speed;
        }

        projectile_position.y -= deltatime * projectile_speed;
    }

    public void Draw(SpriteBatch batch) {
        Update(Gdx.graphics.getDeltaTime());
        projectile_sprite.setPosition(projectile_position.x-50, projectile_position.y + 100);
        sprite.setPosition(position.x, position.y+100);
        batch.begin();
        sprite.draw(batch);
        projectile_sprite.draw(batch);
        batch.end();
    }
}
