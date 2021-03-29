package coreModule;

import android.graphics.Canvas;
import android.graphics.Paint;

import components.Transform;

public abstract class GameObject {
    public Transform transform;

    public abstract void Update();

    public abstract void Render(Canvas canvas, Paint paint);
}
