package components;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;

import com.example.SpriteType;

import coreModule.Constants;
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
                Vector2 position = gameObject.transform.position;
                canvas.drawOval(position.x - 25, position.y + 25, position.x + 25,  position.y - 25, paint);
            }
        }else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                canvas.drawRoundRect(GetOuterRect(), 30f, 30f, paint);
                paint.setColor(Color.LTGRAY);
                canvas.drawRoundRect(GetInnerRect(), 30f, 30f, paint);

            }
        }
    }

    private RectF GetInnerRect(){
        System.out.println((gameObject.transform.position.y - gameObject.transform.size.y / 2) + " --- " +
                (gameObject.transform.position.x - gameObject.transform.size.x / 2) + " --- " +
                (gameObject.transform.position.y + gameObject.transform.size.y / 2) + " --- " +
                (gameObject.transform.position.x + gameObject.transform.size.x / 2));
        return new RectF( gameObject.transform.position.y - gameObject.transform.size.y / 2,
                gameObject.transform.position.x - gameObject.transform.size.x / 2,
                gameObject.transform.position.y + gameObject.transform.size.y / 2,
                gameObject.transform.position.x + gameObject.transform.size.x / 2);
    }

    private RectF GetOuterRect(){
//        RectF innerRectF = GetInnerRect();
        return new RectF( gameObject.transform.position.y - gameObject.transform.size.y / 2 - Constants.borderThickness,
                gameObject.transform.position.x - gameObject.transform.size.x / 2 - Constants.borderThickness,
                gameObject.transform.position.y + gameObject.transform.size.y / 2 + Constants.borderThickness,
                gameObject.transform.position.x + gameObject.transform.size.x / 2 + Constants.borderThickness);

    }
}
