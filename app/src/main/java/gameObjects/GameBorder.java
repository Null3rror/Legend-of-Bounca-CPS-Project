package gameObjects;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.DisplayMetrics;

import androidx.annotation.Dimension;

import com.example.SpriteType;

import java.util.List;

import components.RigidBody;
import components.Sprite;
import components.Transform;
import components.collision.BoxCollider;
import components.collision.Collider;
import coreModule.Constants;
import coreModule.GameObject;

import coreModule.Constants;


public class GameBorder extends GameObject {
    private float angle;
    private Sprite sprite;


    public GameBorder(String tag, List<String> tagsToCheckCollisionWith){
        super(tag, tagsToCheckCollisionWith);
        angle = 0f;
        this.transform = new Transform();

        int offsetX = getScreenWidth() / 5;
        int offsetY = getScreenHeight() / 5;
        int sizeY = getScreenHeight() - 2 * offsetY - 2 * Constants.borderThickness;
        int sizeX = getScreenWidth() - 2 * offsetX - 2 * Constants.borderThickness;
        this.transform.size.Set(sizeX, sizeY);

        this.transform.position.Set(getScreenWidth() >> 1, getScreenHeight() >> 1);

        this.collider = new BoxCollider(this, transform.position, transform.size);
        this.sprite = new Sprite(this, SpriteType.Board);
    }

    @Override
    public void Update() {
//        System.out.println(getScreenWidth() + " || " + getScreenHeight());

    }

    public static int getScreenWidth() {
        return Math.max(Resources.getSystem().getDisplayMetrics().widthPixels, Resources.getSystem().getDisplayMetrics().heightPixels);
    }

    public static int getScreenHeight() {
        return Math.min(Resources.getSystem().getDisplayMetrics().widthPixels, Resources.getSystem().getDisplayMetrics().heightPixels);
    }
    @Override
    public void Render(Canvas canvas, Paint paint) {
//        System.out.println("-->" + getScreenWidth() + " --> " + getScreenHeight());
        paint.setColor(Color.argb(255, 255, 165, 0));
        sprite.Draw(canvas, paint);
    }


}
