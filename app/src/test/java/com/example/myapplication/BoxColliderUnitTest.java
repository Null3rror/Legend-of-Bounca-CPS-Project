package com.example.myapplication;

import org.junit.Test;

import components.collision.BoxCollider;
import coreModule.Vector2;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BoxColliderUnitTest {
    @Test
    public void DetectCollision_CheckWithSelf_Collides() {
        Vector2 center1 = new Vector2(0.0f, 0.0f);
        Vector2 size1   = new Vector2(2.0f, 3.0f);
        BoxCollider collider1 = new BoxCollider(null, center1, size1);

        assertTrue(collider1.DetectCollision(collider1));
    }


    @Test
    public void DetectCollision_OneContainsOther_Collides() {
        Vector2 center1 = new Vector2(0.0f, 0.0f);
        Vector2 center2 = new Vector2(1.0f, 1.0f);
        Vector2 size1   = new Vector2(1.0f, 1.0f);
        Vector2 size2   = new Vector2(2.0f, 2.0f);
        BoxCollider collider1 = new BoxCollider(null, center1, size1);
        BoxCollider collider2 = new BoxCollider(null, center2, size2);

        assertTrue(collider1.DetectCollision(collider2));
    }

    @Test
    public void DetectCollision_RightSideHitLeftSide_Collides() {
        Vector2 center1 = new Vector2(0.0f, 0.0f);
        Vector2 center2 = new Vector2(1.0f, 0.0f);
        Vector2 size1   = new Vector2(2.0f, 1.0f);
        Vector2 size2   = new Vector2(1.0f, 1.0f);
        BoxCollider collider1 = new BoxCollider(null, center1, size1);
        BoxCollider collider2 = new BoxCollider(null, center2, size2);

        assertTrue(collider1.DetectCollision(collider2));
    }

    @Test
    public void DetectCollision_TopSideHitBottomSide_Collides() {
        Vector2 center1 = new Vector2(0.0f, 0.0f);
        Vector2 center2 = new Vector2(0.0f, 1.0f);
        Vector2 size1   = new Vector2(1.0f, 2.0f);
        Vector2 size2   = new Vector2(1.0f, 1.0f);
        BoxCollider collider1 = new BoxCollider(null, center1, size1);
        BoxCollider collider2 = new BoxCollider(null, center2, size2);

        assertTrue(collider1.DetectCollision(collider2));
    }

    @Test
    public void DetectCollision_NoCollision() {
        Vector2 center1 = new Vector2(0.0f, 0.0f);
        Vector2 center2 = new Vector2(10.0f, 10.0f);
        Vector2 size1   = new Vector2(5.0f, 2.0f);
        Vector2 size2   = new Vector2(1.0f, 1.0f);
        BoxCollider collider1 = new BoxCollider(null, center1, size1);
        BoxCollider collider2 = new BoxCollider(null, center2, size2);

        assertFalse(collider1.DetectCollision(collider2));
    }
}
