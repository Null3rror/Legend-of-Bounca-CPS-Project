package components.collision;

import java.util.List;

import components.RigidBody;
import coreModule.Bounds;
import coreModule.GameObject;

public abstract class Collider {
    public GameObject gameObject;
    public Bounds bounds;
    public abstract boolean DetectCollision(Collider other);

    public void Update() {
        CheckCollisionWithTags(gameObject.tagsToCheckCollisionWith);
    }

    public void CheckCollisionWithTags(List<String> tagsToCheckCollisionWith) {
        for (String tag: tagsToCheckCollisionWith) {
//            System.out.println("Looking for:" + tag);
            List<GameObject> gameObjects = GameObject.FindGameObjectsByTag(tag);
            for (GameObject gameObject: gameObjects) {
//                System.out.println("Found: " + gameObject.tag);
                Collider other = gameObject.collider;
                if (other != null) {
//                    System.out.println("Found other: " + gameObject.tag);
                    boolean doCollide = DetectCollision(other);
//                    System.out.println("Found other: " + gameObject.tag + " " + doCollide);
                    if (doCollide) {
                        RigidBody rigidBody = this.gameObject.rigidBody;
                        if (rigidBody != null) {
                            this.gameObject.rigidBody.HandleCollision(other);
                        }
                        this.gameObject.OnCollisionEnter(other);
                    }
                }

            }
        }
    }
}
