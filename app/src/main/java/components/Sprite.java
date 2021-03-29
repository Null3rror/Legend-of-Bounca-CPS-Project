package components;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;

import com.example.SpriteType;

import coreModule.GameObject;
import coreModule.Vector2;

import static com.example.SpriteType.Circle;

public class Sprite {

    private SpriteType type;
    private GameObject gameObject;

    public Sprite(GameObject gameObject, SpriteType spriteType) {
        this.gameObject = gameObject;
        this.type = spriteType;
    }

    public void Draw(Canvas canvas, Paint paint) {
        if (type == Circle) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                canvas.drawOval(gameObject.transform.position.x - 25, gameObject.transform.position.y + 25, gameObject.transform.position.x + 25,  gameObject.transform.position.y - 25, paint);
            }
        }
    }
}
