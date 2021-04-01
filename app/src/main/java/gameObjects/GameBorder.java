package gameObjects;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.SpriteType;

import java.util.List;

import components.Sprite;
import components.Transform;
import components.collision.BoxCollider;
import coreModule.Constants;
import coreModule.GameObject;
import coreModule.Vector2;


public class GameBorder extends GameObject {
    private Sprite sprite;


    public GameBorder(String tag, List<String> tagsToCheckCollisionWith){
        super(tag, tagsToCheckCollisionWith);

        this.transform = new Transform();

        int offsetX = GetScreenWidth() / 5;
        int offsetY = GetScreenHeight() / 3;
        int sizeY = GetScreenHeight() - 2 * offsetY - 2 * Constants.borderThickness;
        int sizeX = GetScreenWidth() - 2 * offsetX - 2 * Constants.borderThickness;
        this.transform.size.Set(sizeX, sizeY);

        this.transform.position.Set(GetScreenWidth() >> 1, GetScreenHeight() >> 1);
        this.collider = new BoxCollider(this, transform.position, transform.size);
        this.sprite = new Sprite(this, SpriteType.Board);
    }

    @Override
    public void Update() {
//        System.out.println(getScreenWidth() + " || " + getScreenHeight());

    }


    public static int GetScreenWidth() {
        return Math.max(Resources.getSystem().getDisplayMetrics().widthPixels, Resources.getSystem().getDisplayMetrics().heightPixels);
    }

    public static int GetScreenHeight() {
        return Math.min(Resources.getSystem().getDisplayMetrics().widthPixels, Resources.getSystem().getDisplayMetrics().heightPixels);
    }
    @Override
    public void Render(Canvas canvas, Paint paint) {
//        System.out.println("-->" + getScreenWidth() + " --> " + getScreenHeight());
        paint.setColor(Color.argb(255, 255, 165, 0));
        sprite.Draw(canvas, paint);
    }


}
