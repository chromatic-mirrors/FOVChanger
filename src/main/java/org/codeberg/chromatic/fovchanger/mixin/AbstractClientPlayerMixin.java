package org.codeberg.chromatic.fovchanger.mixin;

//? if >1.8.9 {
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
//?} else {
/*import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.client.entity.living.player.ClientPlayerEntity;
import net.minecraft.entity.living.attribute.EntityAttributes;
import net.minecraft.item.Items;
*///?}
import org.codeberg.chromatic.fovchanger.option.FOVChangerConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
//? if >1.8.9
import org.spongepowered.asm.mixin.injection.ModifyArg;

//? if >1.8.9 {
@Mixin(AbstractClientPlayer.class)
public class AbstractClientPlayerMixin {
    @Unique
    private static final float DEFAULT_SPEED = 0.30000001192092896F;

    @ModifyArg(
            method = "getFieldOfViewModifier",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;lerp(FFF)F"),
            index = 2
    )
    private float fovChanger$applyCustomFovModifier(float baseFov) {
        Minecraft minecraft = Minecraft.getInstance();
        Player player = minecraft.player;

        if (player == null) return baseFov;

        float modifier = 1.0F;

        if (player.getAbilities().flying) {
            modifier *= 1.0F + 0.1F * FOVChangerConfig.getFlying();
        }

        AttributeInstance movementAttr = player.getAttribute(Attributes.MOVEMENT_SPEED);
        if (movementAttr != null) {
            float currentSpeed = (float) movementAttr.getValue();
            float walkingSpeed = player.getAbilities().getWalkingSpeed();

            if (currentSpeed != walkingSpeed) {
                boolean isFreezing = player.getTicksFrozen() > 0;
                double speedConfig = isFreezing ? FOVChangerConfig.getFreezing() : FOVChangerConfig.getSpeed();

                if (player.isSprinting()) {
                    float sprintEffects = (currentSpeed / (1 + DEFAULT_SPEED)) - walkingSpeed;
                    sprintEffects *= (float) speedConfig;

                    float sprintBonus = DEFAULT_SPEED * FOVChangerConfig.getSprint();
                    double modifiedSpeed = (walkingSpeed + sprintEffects) * (1.0F + sprintBonus);

                    modifier *= (float) ((modifiedSpeed / walkingSpeed + 1.0F) / 2.0F);
                } else {
                    float effects = (float) ((currentSpeed - walkingSpeed) * speedConfig);
                    modifier *= ((effects + walkingSpeed) / walkingSpeed + 1.0F) / 2.0F;
                }
            }
        }

        if (player.getAbilities().getWalkingSpeed() == 0.0F ||
                Float.isNaN(modifier) ||
                Float.isInfinite(modifier)) {
            modifier = 1.0F;
        }

        ItemStack usedItem = player.getUseItem();
        if (player.isUsingItem()) {

            if (usedItem.getItem() instanceof BowItem) {
                int ticks = player.getTicksUsingItem();
                float drawProgress = Math.min((float) ticks / 20.0F, 1.0F);
                drawProgress *= drawProgress;
                modifier *= 1.0F - (drawProgress * 0.15F) * FOVChangerConfig.getAiming();
            } else if (minecraft.options.getCameraType().isFirstPerson() &&
                    player.isScoping()) {
                modifier *= 0.1F;
            }
        }

        return modifier;
    }
}
//?} else {
/*@Mixin(ClientPlayerEntity.class)
public class AbstractClientPlayerMixin {
    @Unique
    private static final float DEFAULT_SPEED = 0.30000001192092896F;

    @ModifyReturnValue(method = "getFovModifier", at = @At("RETURN"))
    private float fovChanger$applyCustomFovModifier(float vanilla) {
        ClientPlayerEntity player = (ClientPlayerEntity) (Object) this;

        float modifier = 1.0F;

        if (player.abilities.flying) {
            modifier *= 1.0F + 0.1F * FOVChangerConfig.getFlying();
        }

        float currentSpeed = (float) player.getAttribute(EntityAttributes.MOVEMENT_SPEED).get();
        float walkingSpeed = player.abilities.getWalkSpeed();

        if (currentSpeed != walkingSpeed) {
            float speedConfig = FOVChangerConfig.getSpeed();

            if (player.isSprinting()) {
                float sprintEffects = (currentSpeed / (1 + DEFAULT_SPEED)) - walkingSpeed;
                sprintEffects *= speedConfig;

                float sprintBonus = DEFAULT_SPEED * FOVChangerConfig.getSprint();
                double modifiedSpeed = (walkingSpeed + sprintEffects) * (1.0F + sprintBonus);

                modifier *= (float) ((modifiedSpeed / walkingSpeed + 1.0F) / 2.0F);
            } else {
                float effects = (currentSpeed - walkingSpeed) * speedConfig;
                modifier *= ((effects + walkingSpeed) / walkingSpeed + 1.0F) / 2.0F;
            }
        }

        if (walkingSpeed == 0.0F ||
                Float.isNaN(modifier) ||
                Float.isInfinite(modifier)) {
            modifier = 1.0F;
        }

        if (player.hasItemInUse() && player.getItemInUse().getItem() == Items.BOW) {
            int ticks = player.getRemainingItemUseDuration();
            float drawProgress = Math.min((float) ticks / 20.0F, 1.0F);
            drawProgress *= drawProgress;
            modifier *= 1.0F - (drawProgress * 0.15F) * FOVChangerConfig.getAiming();
        }

        return modifier;
    }
}
*///?}
