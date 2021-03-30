package coreModule;

public class Constants {
    public static final int borderThickness = 20;
    public static final int fixedDeltaTime = 16;
    public static final int ballRadius     = 25;

    public static final float ballMass     = 0.01f;
    public static final float g = 10f;
    public static final float wastedEnergy = 0.1f;
    public static final float wastedEnergyCoefficient;
    public static final float velocityThreshold = 0.5f;
    static{
        wastedEnergyCoefficient = (float) Math.sqrt(1 - wastedEnergy);
    }

    public static final String ballTag = "ball";
    public static final String borderTag = "border";

}
