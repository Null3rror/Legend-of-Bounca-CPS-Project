package coreModule;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.myapplication.GameActivity;

import java.util.ArrayList;
import java.util.List;

import components.RigidBody;
import components.Transform;
import components.collision.Collider;

public abstract class GameObject {
    public Transform transform;
    public String tag;
    public List<String> tagsToCheckCollisionWith;

    public Collider collider;
    public RigidBody rigidBody;

    public static List<GameObject> gameObjects;

    static {
        gameObjects = new ArrayList<>();
    }


    public GameObject(String tag, List<String> tagsToCheckCollisionWith) {
        this.tag = tag;
        this.tagsToCheckCollisionWith = tagsToCheckCollisionWith;

        collider  = null;
        rigidBody = null;
        gameObjects.add(this);
    }

    public void Update() {

    }

    public void OnCollisionEnter(Collider other) {

    }

    public void Render(Canvas canvas, Paint paint) {

    }






    public static List<GameObject> FindGameObjectsByTag(String tag) {
        List<GameObject> foundGameObjects = new ArrayList<GameObject>();

        for (GameObject gameObject: gameObjects) {
            if (gameObject.tag.equals(tag))
                foundGameObjects.add(gameObject);
        }
        return foundGameObjects;
    }

    public static GameObject FindByTag(String tag) {
        for (GameObject gameObject: gameObjects) {
            if (gameObject.tag.equals(tag))
                return gameObject;
        }
        return null;
    }

}
