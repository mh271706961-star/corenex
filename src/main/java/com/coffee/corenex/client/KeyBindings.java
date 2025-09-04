package com.coffee.corenex.client;

import net.minecraft.client.KeyMapping;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.lwjgl.glfw.GLFW;

@OnlyIn(Dist.CLIENT)
public class KeyBindings {
    public static final KeyMapping TELEPORT_KEY = new KeyMapping(
            "key.corenex.teleport",
            GLFW.GLFW_KEY_C,
            "key.categories.movement"
    );
}
