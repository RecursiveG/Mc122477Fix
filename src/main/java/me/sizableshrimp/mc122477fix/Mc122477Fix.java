package me.sizableshrimp.mc122477fix;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.ActionResult;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class Mc122477Fix implements ClientModInitializer {
    private long pollCount;
    private long prevKeyPoll;

    @Override
    public void onInitializeClient() {
        GLFWPollCallback.EVENT.register(() -> this.pollCount++);

        // Key press events are always processed before char type events
        KeyboardKeyPressedCallback.EVENT.register((window, key, scancode, action, modifiers) -> {
            MinecraftClient client = MinecraftClient.getInstance();
            // If this is a key release/repeat OR we're already in a screen (including chat screen), skip
            if (action != GLFW.GLFW_PRESS || client.currentScreen != null)
                return ActionResult.PASS;

            // If the chat or command key was pressed, store what poll count it happened on.
            if (client.options.keyChat.matchesKey(key, scancode) || client.options.keyCommand.matchesKey(key, scancode)) {
                this.prevKeyPoll = this.pollCount;
            } else {
                // Otherwise, set to -1
                this.prevKeyPoll = -1L;
            }

            return ActionResult.PASS;
        });
        KeyboardCharTypedCallback.EVENT.register((window, codepoint, modifiers) -> {
            // If the previous key poll is -1 or the poll count doesn't match up closely, skip
            if (this.prevKeyPoll == -1 || this.pollCount - this.prevKeyPoll > 5)
                return ActionResult.PASS;

            // If we are on a close poll count to when the key press event was polled,
            // then we should cancel the char type event to ensure it is not passed to the chat field.
            this.prevKeyPoll = -1;
            return ActionResult.FAIL;
        });
    }
}
