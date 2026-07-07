package org.codeberg.chromatic.fovchanger;

import net.fabricmc.api.ClientModInitializer;
import org.codeberg.chromatic.fovchanger.option.FOVChangerConfig;

public class FOVChanger implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        FOVChangerConfig.INSTANCE.preload();
    }
}
