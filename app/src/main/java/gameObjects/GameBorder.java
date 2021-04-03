package gameObjects;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.SpriteType;

import java.util.List;

import components.SensorReader;
import components.Sprite;
import components.Transform;
import components.collision.BoxCollider;
import coreModule.Constants;
import coreModule.GameObject;
import coreModule.Vector2;
import coreModule.Vector3;
import coreModule.Vector4;


public class GameBorder extends GameObject {
    private final SensorReader sensorReader;
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
        this.sensorReader = new SensorReader();
    }

    @Override
    public void Update() {
        Vector4 angles = sensorReader.GetAngles();
        transform.rotation = new Vector3((float) Math.toDegrees(angles.x), (float) Math.toDegrees(angles.y), (float) Math.toDegrees(angles.z));
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
        paint.setColor(Color.BLACK);
        float angle = 0;
        Vector2 normal;
        normal = collider.bounds.CalculateNormal(Constants.leftAngle + angle);
        canvas.drawLine(collider.bounds.min.x, collider.bounds.min.y + collider.bounds.size.y / 2,
                collider.bounds.min.x + normal.x * 100, collider.bounds.min.y + collider.bounds.size.y / 2 + normal.y * 100, paint);
        normal = collider.bounds.CalculateNormal(Constants.rightAngle + angle);
        canvas.drawLine(collider.bounds.max.x, collider.bounds.max.y - collider.bounds.size.y / 2,
                collider.bounds.max.x + normal.x * 100, collider.bounds.max.y - collider.bounds.size.y / 2 + normal.y * 100, paint);


        normal = collider.bounds.CalculateNormal(Constants.ceilAngle + angle);
        canvas.drawLine(collider.bounds.min.x + collider.bounds.size.x / 2, collider.bounds.min.y,
                collider.bounds.min.x + collider.bounds.size.x / 2 + normal.x * 100, collider.bounds.min.y + normal.y * 100, paint);
        normal = collider.bounds.CalculateNormal(Constants.floorAngle + angle);
        canvas.drawLine(collider.bounds.max.x - collider.bounds.size.x / 2, collider.bounds.max.y,
                collider.bounds.max.x - collider.bounds.size.x / 2 + normal.x * 100, collider.bounds.max.y + normal.y * 100, paint);
    }


}
