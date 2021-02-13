package me.sizableshrimp.mc122477fix;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.util.ActionResult;

@Environment(EnvType.CLIENT)
public class Mc122477Fix implements ClientModInitializer {
    private long chatOpenMillis;

    @Override
    public void onInitializeClient() {
        GuiOpenCallback.EVENT.register(screen -> {
            if (screen instanceof ChatScreen)
                this.chatOpenMillis = System.currentTimeMillis();

            return ActionResult.PASS;
        });
        GuiCharTypedCallback.EVENT.register((chr, modifiers) -> {
            if (System.currentTimeMillis() - this.chatOpenMillis < 50) {
                // If less than 50ms has occurred since the chat box has opened, cancel to fix GLFW/glfw#1794 on Unix-based systems.
                return ActionResult.FAIL;
            }

            return ActionResult.PASS;
        });
    }
}
