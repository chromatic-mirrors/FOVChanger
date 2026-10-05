package org.codeberg.chromatic.fovchanger.option;

import org.polyfrost.oneconfig.api.config.v1.Config;
import org.polyfrost.oneconfig.api.config.v1.annotations.Slider;

public class FOVChangerConfig extends Config {

    public static final FOVChangerConfig INSTANCE = new FOVChangerConfig();

    @Slider(
            title = "Speed FOV Effects",
            step = 0.5F
    )
    public static float speed = 100.0F;

    public static float getSpeed() {
        return speed / 100;
    }

    @Slider(
            title = "Freezing FOV Effects",
            step = 0.5F
    )
    public static float freezing = 100.0F;

    public static float getFreezing() {
        return freezing / 100;
    }

    @Slider(
            title = "Sprint FOV Effects",
            step = 0.5F
    )
    public static float sprint = 100.0F;

    public static float getSprint() {
        return sprint / 100;
    }

    @Slider(
            title = "Aiming FOV Effects",
            step = 0.5F
    )
    public static float aiming = 100.0F;

    public static float getAiming() {
        return aiming / 100;
    }

    @Slider(
            title = "Flying FOV Effects",
            step = 0.5F
    )
    public static float flying = 100.0F;

    public static float getFlying() {
        return flying / 100;
    }

    @Slider(
            title = "Submerged FOV Effects",
            step = 0.5F
    )
    public static float submerged = 100.0F;

    public static float getSubmerged() {
        return submerged / 100;
    }

    private FOVChangerConfig() {
        super("fovchanger.json", "FOVChanger", Category.QOL);

        //? if =1.8.9
        // hideIf("freezing", () -> true);
    }
}
