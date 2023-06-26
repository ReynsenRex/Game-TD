//package Tower;
//
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.audio.Sound;
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.Sprite;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.badlogic.gdx.math.Intersector;
//import com.badlogic.gdx.math.Rectangle;
//import com.badlogic.gdx.math.Vector2;
//import Enemies.Zombie;
//import com.badlogic.gdx.utils.Array;
//
//public class Projectile {
//    private static final float SPEED = 5000f;
//    private Sprite sprite;
//    private Vector2 position;
//    private Vector2 target;
//    private Vector2 direction;
//    private Rectangle hitbox; // Hitbox representation
////    private Sound hitSound;
//    Array<Sprite> sprites;
//
//    public Projectile() {
//        Texture bulletTexture = new Texture(Gdx.files.internal("fireBullet.png"));
//        sprite = new Sprite(bulletTexture);
//        sprite.setSize(50, 50);
//
//        position = new Vector2();
//        direction = new Vector2();
//        hitbox = new Rectangle();
////        hitSound = Gdx.audio.newSound(Gdx.files.internal("hitSound.wav"));
//    }
//
//    public void setPosition(float x, float y) {
//        position.set(x, y);
//        sprite.setPosition(x, y);
//        updateHitbox(); // Update the hitbox position when setting the position
//    }
//
//    public void setTarget(float targetX, float targetY) {
//        target = new Vector2(targetX, targetY);
//        direction = target.cpy().sub(position).nor();
//    }
//
//    public void update(float deltaTime, Zombie zombie) {
//        float dx = direction.x * SPEED * deltaTime;
//        float dy = direction.y * SPEED * deltaTime;
//        position.add(dx, dy);
//        sprite.setPosition(position.x, position.y);
//        updateHitbox();
//
////        hitSound = Gdx.audio.newSound(Gdx.files.internal("Zombie_attacked_SFX.mp3"));
////        hitSound.setVolume(1L, (float) 0.1);
//    }
//
//    public void render(SpriteBatch batch) {
//        sprite.draw(batch);
//    }
//
//    public boolean isOffScreen() {
//        return position.x < 0 || position.x > Gdx.graphics.getWidth() ||
//                position.y < 0 || position.y > Gdx.graphics.getHeight();
//    }
//
//    public boolean hasReachedTarget() {
//        return position.epsilonEquals(target, 1f);
//    }
//
//    public Rectangle getHitbox() {
//        return hitbox;
//    }
//
//    private void updateHitbox() {
//        float hitboxWidth = sprite.getWidth(); // Adjust hitbox size as needed
//        float hitboxHeight = sprite.getHeight();
//        hitbox.set(position.x, position.y, hitboxWidth, hitboxHeight);
//    }
//
//
//    private void destroy(Sprite sprite) {
////        sprites.removeValue(sprite, true);
//
//        // Dispose of the bullet sprite's texture
//        sprite.getTexture().dispose();
//    }
//}
