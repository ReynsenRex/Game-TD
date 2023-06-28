package Interface;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class Timer implements TimerInterface{

    private float timer;
    private int points;
    private BitmapFont font;
    @Override
    public void startTimer(SpriteBatch batch) {

        timer += Gdx.graphics.getDeltaTime();

        if (timer >= 1.0f) {
            points += 1;
            timer -= 1.0f;
        }

        font = new BitmapFont();  // Instantiate the BitmapFont class
        font.getData().setScale(5);
        font.setColor(Color.RED);
        font.draw(batch, "Timer: " + points, 100, 200);
    }
}
