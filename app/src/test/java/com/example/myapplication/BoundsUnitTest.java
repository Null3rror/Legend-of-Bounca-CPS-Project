package com.example.myapplication;

import org.junit.Test;

import coreModule.Bounds;
import coreModule.Vector2;

import static org.junit.Assert.*;

public class BoundsUnitTest {
    @Test
    public void Intersect_WithSelf_Intersects() {
        Vector2 center1 = new Vector2(0.0f, 0.0f);
        Vector2 size1   = new Vector2(1.0f, 1.0f);
        Bounds bounds1 = new Bounds(center1, size1);

        assertTrue(bounds1.Intersects(bounds1));
    }

    @Test
    public void Intersect_OneContainsOther_Intersects() {
        Vector2 center1 = new Vector2(0.0f, 0.0f);
        Vector2 center2 = new Vector2(0.0f, 0.0f);
        Vector2 size1   = new Vector2(1.0f, 1.0f);
        Vector2 size2   = new Vector2(2.0f, 2.0f);
        Bounds bounds1 = new Bounds(center1, size1);
        Bounds bounds2 = new Bounds(center2, size2);

        assertTrue(bounds1.Intersects(bounds2));
    }

    @Test
    public void Intersect_RightSideHitLeftSide_Intersects() {
        Vector2 center1 = new Vector2(0.0f, 0.0f);
        Vector2 center2 = new Vector2(1.0f, 0.0f);
        Vector2 size1   = new Vector2(2.0f, 1.0f);
        Vector2 size2   = new Vector2(1.0f, 1.0f);
        Bounds bounds1 = new Bounds(center1, size1);
        Bounds bounds2 = new Bounds(center2, size2);

        assertTrue(bounds1.Intersects(bounds2));
    }

    @Test
    public void Intersect_TopSideHitBottomSide_Intersects() {
        Vector2 center1 = new Vector2(0.0f, 0.0f);
        Vector2 center2 = new Vector2(0.0f, 1.0f);
        Vector2 size1   = new Vector2(1.0f, 2.0f);
        Vector2 size2   = new Vector2(1.0f, 1.0f);
        Bounds bounds1 = new Bounds(center1, size1);
        Bounds bounds2 = new Bounds(center2, size2);

        assertTrue(bounds1.Intersects(bounds2));
    }

    @Test
    public void Intersect_NoIntersection() {
        Vector2 center1 = new Vector2(0.0f, 0.0f);
        Vector2 center2 = new Vector2(10.0f, 10.0f);
        Vector2 size1   = new Vector2(5.0f, 2.0f);
        Vector2 size2   = new Vector2(1.0f, 1.0f);
        Bounds bounds1 = new Bounds(center1, size1);
        Bounds bounds2 = new Bounds(center2, size2);

        assertFalse(bounds1.Intersects(bounds2));
    }
}
