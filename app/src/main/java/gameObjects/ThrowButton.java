package gameObjects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import com.example.SpriteType;

import java.util.List;

import components.Sprite;
import components.Transform;
import coreModule.GameObject;

import static gameObjects.GameBorder.GetScreenHeight;
import static gameObjects.GameBorder.GetScreenWidth;

public class ThrowButton extends GameObject {
    private Sprite sprite;
    public RectF btn_rect;

    public ThrowButton(String tag, List<String> tagsToCheckCollisionWith) {
        super(tag, tagsToCheckCollisionWith);

        this.transform = new Transform();

        int width =  GetScreenWidth() / 20;
        int height =  GetScreenHeight() / 15;

        this.transform.size.Set(width, height);
        this.transform.position.Set(width >> 1, height >> 1);

        btn_rect = new RectF(0, 0, width, height);
        this.sprite = new Sprite(this, SpriteType.Button);
    }

    public boolean IsButtonPress(float x, float y){
//        System.out.println(btn_rect.bottom + " <-- d // " + btn_rect.left + " <-- l // " + btn_rect.top + " <-- t // " + btn_rect.right + " <-- r // ");
        if (btn_rect.contains(x, y)) {
            return true;
        }
        return false;
    }

    @Override
    public void Render(Canvas canvas, Paint paint) {
        paint.setColor(Color.BLUE);
        sprite.Draw(canvas, paint);
    }

    @Override
    public void Update() {
//        System.out.println(getScreenWidth() + " || " + getScreenHeight());

    }
}


