package coreModule;

public class Constants {
    public static final int borderThickness = 20;
    public static final int fixedDeltaTime = 16;
    public static final int ballRadius     = 30;

    public static final float ballMass     = 0.01f;
    public static final float g = 10f;
    public static final float wastedEnergy = 0.9f;
    public static final float wastedEnergyCoefficient;
//    public static final float velocityThreshold = 0.1f;
//    public static final float staticFrictionCoefficient = 0.15f;
//    public static final float kineticFrictionCoefficient = 0.07f;
    static{
        wastedEnergyCoefficient = (float) Math.sqrt(1 - wastedEnergy);
    }

    public static final String ballTag = "ball";
    public static final String borderTag = "border";

    public static final float accelerationMultiplier = 5.0f;

    public static final float ceilAngle = 180f;
    public static final float rightAngle = 90f;
    public static final float floorAngle = 0f;
    public static final float leftAngle = 270f;






}
