package org.codeberg.chromatic.fovchanger.option;

import net.minecraft.client.Minecraft;
import org.polyfrost.oneconfig.api.config.v1.Config;
import org.polyfrost.oneconfig.api.config.v1.annotations.MasterToggle;
import org.polyfrost.oneconfig.api.config.v1.annotations.Slider;
import org.polyfrost.oneconfig.api.config.v1.annotations.Switch;

public class FOVChangerConfig extends Config {

    @MasterToggle
    public static boolean enabled = true;

    @Switch(title = "Absolute FOV")
    public static boolean absolute = false;

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

    @Slider(title = "Speed FOV", min = 30.0F, max = 110.0F)
    public static float speedFov = 85.0F;

    @Slider(title = "Freezing FOV", min = 30.0F, max = 110.0F)
    public static float freezingFov = 60.0F;

    @Slider(title = "Sprint FOV", min = 30.0F, max = 110.0F)
    public static float sprintFov = 80.0F;

    @Slider(title = "Aiming FOV", min = 30.0F, max = 110.0F)
    public static float aimingFov = 60.0F;

    @Slider(title = "Flying FOV", min = 30.0F, max = 110.0F)
    public static float flyingFov = 77.0F;

    @Slider(title = "Submerged FOV", min = 30.0F, max = 110.0F)
    public static float submergedFov = 60.0F;

    public static float toModifier(float fov) {
        //? if >1.8.9 {
        return fov / Minecraft.getInstance().options.fov().get();
        //?} else {
        /*return fov / Minecraft.getInstance().options.fov;
        *///?}
    }

    public static float getAbsoluteModifier(boolean speedChanged, boolean freezing, boolean sprinting, boolean flying) {
        if (speedChanged) return toModifier(freezing ? freezingFov : speedFov);
        if (sprinting) return toModifier(sprintFov);
        if (flying) return toModifier(flyingFov);
        return 1.0F;
    }

    public static final FOVChangerConfig INSTANCE = new FOVChangerConfig();

    private FOVChangerConfig() {
        super("fovchanger.json", "FOVChanger", Category.QOL);

        addDependency("absolute", "enabled");
        for (String option : new String[]{"speed", "freezing", "sprint", "aiming", "flying", "submerged"}) {
            hideIf(option, () -> absolute);
            hideIf(option + "Fov", "absolute");
            addDependency(option, "enabled");
            addDependency(option + "Fov", "enabled");
        }

        //? if =1.8.9 {
        /*hideIf("freezing", () -> true);
        hideIf("freezingFov", () -> true);
        *///?}
    }
}
