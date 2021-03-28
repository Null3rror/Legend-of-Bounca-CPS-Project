package com.example.myapplication;

import org.junit.Test;

import components.collision.CircleCollider;
import coreModule.Vector2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CircleColliderUnitTest {
    @Test
    public void DetectCollision_WithItSelf_Collides() {
        Vector2 center1 = new Vector2(0.0f, 0.0f);
        float radius1 = 1.0f;
        CircleCollider collider = new CircleCollider(null, center1, radius1);
        assertTrue(collider.DetectCollision(collider));
    }

    @Test
    public void DetectCollision_OneContainsOther_Collides() {
        Vector2 center1 = new Vector2(0.0f, 0.0f);
        float radius1 = 1.0f;
        float radius2 = 2.0f;
        CircleCollider collider1 = new CircleCollider(null, center1, radius1);
        CircleCollider collider2 = new CircleCollider(null, center1, radius2);
        assertTrue(collider1.DetectCollision(collider2));
    }

    @Test
    public void DetectCollision_IsFunctionCommutative_True() {
        Vector2 center1 = new Vector2(0.0f, 0.0f);
        float radius1 = 1.0f;
        float radius2 = 2.0f;
        CircleCollider collider1 = new CircleCollider(null, center1, radius1);
        CircleCollider collider2 = new CircleCollider(null, center1, radius2);
        assertEquals(collider1.DetectCollision(collider2), collider2.DetectCollision(collider1));
    }

    @Test
    public void DetectCollision_OutsideOfEachOther_NoCollision() {
        Vector2 center1 = new Vector2(0.0f, 0.0f);
        Vector2 center2 = new Vector2(2.0f, 0.0f);
        float radius1 = 1.0f;
        float radius2 = 1.0f;
        CircleCollider collider1 = new CircleCollider(null, center1, radius1);
        CircleCollider collider2 = new CircleCollider(null, center2, radius2);
        assertFalse(collider1.DetectCollision(collider2));
    }
}
