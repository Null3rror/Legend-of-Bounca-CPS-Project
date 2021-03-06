package components;

import android.graphics.Canvas;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;

import com.example.SpriteType;

import coreModule.Constants;
import coreModule.GameObject;
import coreModule.Vector2;

import static com.example.SpriteType.Board;
import static com.example.SpriteType.Circle;

public class Sprite {
    private final SpriteType type;
    private final GameObject gameObject;

    public Sprite(GameObject gameObject, SpriteType spriteType) {
        this.gameObject = gameObject;
        this.type = spriteType;
    }

    public void Draw(Canvas canvas, Paint paint) {
        if (type == Circle) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Vector2 position = gameObject.transform.position;
                float radius     = gameObject.transform.size.x / 2;
                canvas.drawOval(position.x - radius,
                        position.y - radius,
                        position.x + radius,
                        position.y + radius,
                        paint);

                Paint.Style style = paint.getStyle();
                paint.setStyle(Paint.Style.STROKE);
                paint.setColor(Color.BLACK);
                canvas.drawRect(position.x - radius,
                        position.y - radius,
                        position.x + radius,
                        position.y + radius,
                        paint);
                paint.setStyle(style);
            }
        }else if(type == Board){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                RectF innerRectF = GetInnerRect();
                canvas.drawRoundRect(GetOuterRect(innerRectF), 0f, 0f, paint);
                paint.setColor(Color.LTGRAY);
                canvas.drawRoundRect(innerRectF, 0f, 0f, paint);


            }
        } else if(type == SpriteType.Button){

            paint.setColor(Color.LTGRAY);
            canvas.drawRoundRect(GetInnerRect(), 40f, 40f, paint);
            paint.setColor(Color.BLACK);
            paint.setTextSize(45);
            canvas.drawText("??????????", gameObject.transform.position.x / 3 , gameObject.transform.position.y * 1.5f, paint);
        }
    }

//    private Matrix SetPosition(float x, float y) {
//        Matrix btn_matrix = new Matrix();
//        btn_matrix.setTranslate(x, y);
//        btn_matrix.mapRect(GetInnerRect());
//
//        return btn_matrix;
//    }

    private RectF GetInnerRect() {
        Vector2 center = gameObject.transform.position;
        Vector2 size   = gameObject.transform.size;
        int left   = (int)center.x - (int)size.x / 2;
        int top    = (int)center.y - (int)size.y / 2;
        int right  = (int)center.x + (int)size.x / 2;
        int bottom = (int)center.y + (int)size.y / 2;
//        System.out.println(left + ", " + top + ", " + right + ", " + bottom);
//        System.out.println(center.x + ", " + center.y + "\n" + size.x + "| " + size.y);

        return new RectF(left, top, right, bottom);
    }

    private RectF GetOuterRect(RectF innerRectF) {
        int left   = (int)innerRectF.left   - Constants.borderThickness;
        int top    = (int)innerRectF.top    - Constants.borderThickness;
        int right  = (int)innerRectF.right  + Constants.borderThickness;
        int bottom = (int)innerRectF.bottom + Constants.borderThickness;
//        System.out.println(left + "| " + top + "| " + right + "| " + bottom);

        return new RectF(left, top, right, bottom);
    }
}
