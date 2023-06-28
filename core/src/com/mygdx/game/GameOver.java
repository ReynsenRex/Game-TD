package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameOver {
    private BitmapFont font;
    public void GOScreen(SpriteBatch batch) {
        font = new BitmapFont();
        font.getData().setScale(7);
        font.setColor(Color.RED);
        font.draw(batch, "GAME OVER", Gdx.graphics.getWidth()/2, Gdx.graphics.getWidth()/2);
    }
}
