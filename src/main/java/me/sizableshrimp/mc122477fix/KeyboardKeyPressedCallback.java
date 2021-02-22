package me.sizableshrimp.mc122477fix;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.util.ActionResult;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallbackI;

/**
 * Callback for pressing a key on the keyboard.
 * This event happens before char typed events in {@link KeyboardCharTypedCallback} are processed, if at all.
 * Called before the key press is processed.
 *
 * <p>Upon return:
 * <ul><li>SUCCESS cancels further listener processing and forwards the key press to be processed by the client.
 * <li>PASS falls back to further processing.
 * <li>FAIL cancels further processing and does not forward the key press.</ul>
 */
public interface KeyboardKeyPressedCallback {
    Event<KeyboardKeyPressedCallback> EVENT = EventFactory.createArrayBacked(KeyboardKeyPressedCallback.class,
            listeners -> (window, key, scancode, i, modifiers) -> {
                for (KeyboardKeyPressedCallback listener : listeners) {
                    ActionResult result = listener.onKeyPressed(window, key, scancode, i, modifiers);

                    if (result != ActionResult.PASS)
                        return result;
                }

                return ActionResult.PASS;
            });

    /**
     * Will be called when a key is pressed, repeated or released.
     *
     * @param window the window that received the event
     * @param key the keyboard key that was pressed or released
     * @param scancode the system-specific scancode of the key
     * @param action the key action. One of: {@link GLFW#GLFW_PRESS PRESS}, {@link GLFW#GLFW_RELEASE RELEASE}, {@link GLFW#GLFW_REPEAT REPEAT}
     * @param modifiers bitfield describing which modifiers keys were held down
     * @see GLFWKeyCallbackI#invoke(long, int, int, int, int)
     */
    ActionResult onKeyPressed(long window, int key, int scancode, int action, int modifiers);
}
