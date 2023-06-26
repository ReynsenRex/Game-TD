package com.mygdx.game;

import Enemies.Zombie;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Tower {
    public Vector2 position;
    public Vector2 projectile_position;
    public Sprite sprite;
    public Sprite projectile_sprite;
    public float speed = 1000;
    public float projectile_speed = 10000;


    public Tower() {
        Texture texture = new Texture(Gdx.files.internal("Turret_fix.png"));
        sprite = new Sprite(texture);
        Texture projectile_texture = new Texture(Gdx.files.internal("fireBullet.png"));
        projectile_sprite = new Sprite(projectile_texture);
        sprite.setScale((float) 0.5);
        projectile_sprite.setScale((float) 0.2);
        position = new Vector2(1500,sprite.getScaleY()*sprite.getHeight()/2);
        projectile_position = new Vector2(0,1000);
    }

    public void Update(float deltatime){
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)){
            projectile_position.x = position.x;
            projectile_position.y = position.y;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)){
            position.y += deltatime * speed;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.S)){
            position.y -= deltatime * speed;
        }



        //projectile_position.x -= deltatime*projectile_speed;
    }

    public void Draw(SpriteBatch batch) {
        Update(Gdx.graphics.getDeltaTime());
        projectile_sprite.setPosition(projectile_position.x+250,projectile_position.y+100);
        sprite.setPosition(position.x, position.y);
        batch.begin();
        sprite.draw(batch);
        projectile_sprite.draw(batch);
        batch.end();
    }

    private boolean collidesWithZombie(Zombie zombie) {
        float zombieX = zombie.getX();
        float zombieY = zombie.getY();
        float zombieWidth = zombie.getSprite().getWidth();
        float zombieHeight = zombie.getSprite().getHeight();

        float projectileX = projectile_position.x;
        float projectileY = projectile_position.y;
        float projectileWidth = projectile_sprite.getWidth();
        float projectileHeight = projectile_sprite.getHeight();

        Rectangle zombieRectangle = new Rectangle(zombieX, zombieY, zombieWidth, zombieHeight);
        Rectangle projectileRectangle = new Rectangle(projectileX, projectileY, projectileWidth, projectileHeight);

        return zombieRectangle.overlaps(projectileRectangle);
    }

}
