package com.coffee.corenex;

import com.coffee.corenex.client.KeyBindings;
import com.coffee.corenex.network.TeleportPacket;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.network.PacketDistributor;

// This class will not load on dedicated servers. Accessing client side code from here is safe.
@Mod(value = Corenex.MOD_ID, dist = Dist.CLIENT)
// You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
@EventBusSubscriber(modid = Corenex.MOD_ID, value = Dist.CLIENT)
public class CorenexClient {
    public CorenexClient(ModContainer container) {
        // Allows NeoForge to create a config screen for this mod's configs.
        // The config screen is accessed by going to the Mods screen > clicking on your mod > clicking on config.
        // Do not forget to add translations for your config options to the en_us.json file.
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {
        // Some client setup code
        Corenex.LOGGER.info("HELLO FROM CLIENT SETUP");
        Corenex.LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
    }

    // 修改按键输入监听
    @SubscribeEvent
    static void onKeyInput(net.neoforged.neoforge.client.event.InputEvent.Key event) {
        if (KeyBindings.TELEPORT_KEY.consumeClick()) {
            Minecraft minecraft = Minecraft.getInstance();
            if (minecraft.player != null) {
                // 获取玩家的移动输入
                double forward = 0;
                double sideways = 0;

                if (minecraft.options.keyUp.isDown()) forward += 1;
                if (minecraft.options.keyDown.isDown()) forward -= 1;
                if (minecraft.options.keyLeft.isDown()) sideways -= 1;
                if (minecraft.options.keyRight.isDown()) sideways += 1;

                // 发送数据包到服务器，包含移动方向信息
                PacketDistributor.sendToServer(new TeleportPacket(forward, sideways));
            }
        }
    }
}


