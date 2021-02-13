package me.sizableshrimp.mc122477fix;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.util.ActionResult;

/**
 * Callback for typing a character in a GUI.
 * Called before the character is processed.
 *
 * <p>Upon return:
 * <ul><li>SUCCESS cancels further listener processing and forwards the character to be processed by the client.
 * <li>PASS falls back to further processing.
 * <li>FAIL cancels further processing and does not forward the character.</ul>
 */
public interface GuiCharTypedCallback {
    Event<GuiCharTypedCallback> EVENT = EventFactory.createArrayBacked(GuiCharTypedCallback.class,
            listeners -> (chr, modifiers) -> {
                for (GuiCharTypedCallback listener : listeners) {
                    ActionResult result = listener.onCharTyped(chr, modifiers);

                    if (result != ActionResult.PASS)
                        return result;
                }

                return ActionResult.PASS;
            });

    ActionResult onCharTyped(char chr, int modifiers);
}
