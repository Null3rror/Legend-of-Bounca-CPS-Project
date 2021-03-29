package gameObjects;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.DisplayMetrics;

import androidx.annotation.Dimension;

import com.example.SpriteType;

import components.RigidBody;
import components.Sprite;
import components.Transform;
import components.collision.BoxCollider;
import components.collision.Collider;
import coreModule.Constants;
import coreModule.GameObject;

import coreModule.Constants;


public class GameBorder extends GameObject {
    private Collider collider;
    private float angle;
    private Sprite sprite;
    public RectF outerRectF;
    public RectF innerRectF;

    public GameBorder(){
        angle = 0f;
        this.transform = new Transform();
        this.transform.Reset();
        outerRectF = new RectF();
        innerRectF = new RectF();

        int offsetX = getScreenWidth() / 5;
        int offsetY = getScreenHeight() / 5;
        int sizeY = getScreenHeight() - 2 * offsetY - 2 * Constants.borderThickness;
        int sizeX = getScreenWidth() - 2 * offsetX - 2 * Constants.borderThickness;
        Update();
        this.transform.position.Set(getScreenWidth() / 2 , getScreenHeight() / 2);
        this.transform.size.Set(sizeX, sizeY);
        this.collider = new BoxCollider(this, transform.position, transform.size);
        this.sprite = new Sprite(this, SpriteType.Board);
    }

    @Override
    public void Update() {
        innerRectF.set(transform.position.x - transform.size.x / 2, transform.position.y - transform.size.y / 2, transform.position.x + transform.size.x / 2, transform.position.y + transform.size.y / 2);
        outerRectF.set( innerRectF.left - Constants.borderThickness, innerRectF.top - Constants.borderThickness, innerRectF.right - Constants.borderThickness, innerRectF.bottom - Constants.borderThickness);
    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }
    @Override
    public void Render(Canvas canvas, Paint paint) {
//        System.out.println("-->" + getScreenWidth() + " --> " + getScreenHeight());
        paint.setColor(Color.argb(255, 255, 165, 0));
        sprite.Draw(canvas, paint);
    }

    public RectF GetOuterRect(){ return outerRectF; }
    public RectF GetInnerRect(){ return innerRectF; }
}
