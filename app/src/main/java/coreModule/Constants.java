package coreModule;

public class Constants {
    public static final int borderThickness = 20;
    public static final int fixedDeltaTime = 16;
    public static final int ballRadius     = 30;

    public static final float ballMass     = 0.01f;
    public static final float g = 9.81f;
    public static final float wastedEnergy = 0.6f;
    public static final float wastedEnergyCoefficient;

    public static final float velocityThreshold = 0.5f;

    public static final float staticFrictionCoefficient = 0.15f;
    public static final float kineticFrictionCoefficient = 0.07f;
    static{
        wastedEnergyCoefficient = (float) Math.sqrt(1 - wastedEnergy);
    }

    public static final String ballTag = "ball";
    public static final String borderTag = "border";
    public static final String buttonTag = "button";

    public static final float accelerationMultiplier = 20.0f;

    public static final float ceilAngle = 0f;
    public static final float rightAngle = 0f;
    public static final float floorAngle = 0f;
    public static final float leftAngle = 0f;






}
