package org.codeberg.chromatic.fovchanger.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
//? if >=26.1 {
import net.minecraft.client.Camera;
//?} elif >1.8.9 {
/*import net.minecraft.client.renderer.GameRenderer;
*///?} else {
/*import net.minecraft.client.render.GameRenderer;
*///?}
import org.codeberg.chromatic.fovchanger.option.FOVChangerConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

//? if >=26.1 {
@Mixin(Camera.class)
//?} else {
/*@Mixin(GameRenderer.class)
*///?}
public class CameraMixin {
    //? if >=1.21.4 {
    private static final String CONSTANT = "floatValue=0.85714287";
    //?} elif >1.8.9 {
    // private static final String CONSTANT = "doubleValue=0.8571428656578064";
    //?} else {
    // private static final String CONSTANT = "floatValue=60.0";
    //?}

    @ModifyExpressionValue(
            //? if >=26.1 {
            method = "modifyFovBasedOnDeathOrFluid",
            //?} else {
            // method = "getFov",
            //?}
            at = @At(value = "CONSTANT", args = CONSTANT)
    )
    //? if >=1.21.4 {
    private float fovChanger$scaleSubmergedFov(float vanilla) {
        return 1.0F - (1.0F - vanilla) * FOVChangerConfig.getSubmerged();
    }
    //?} elif >1.8.9 {
    //private double fovChanger$scaleSubmergedFov(double vanilla) {
    //    return 1.0 - (1.0 - vanilla) * FOVChangerConfig.getSubmerged();
    //}
    //?} else {
    //private float fovChanger$scaleSubmergedFov(float vanilla) {
    //    return 70.0F - (70.0F - vanilla) * FOVChangerConfig.getSubmerged();
    //}
    //?}
}
