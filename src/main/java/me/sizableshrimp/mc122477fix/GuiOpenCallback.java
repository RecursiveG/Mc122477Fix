package me.sizableshrimp.mc122477fix;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.ActionResult;

/**
 * Callback for opening a GUI.
 * Called before the GUI is opened.
 *
 * <p>Upon return:
 * <ul><li>SUCCESS cancels further listener processing and opens the GUI.
 * <li>PASS falls back to further processing.
 * <li>FAIL cancels further processing and does not open the GUI.</ul>
 */
public interface GuiOpenCallback {
    Event<GuiOpenCallback> EVENT = EventFactory.createArrayBacked(GuiOpenCallback.class,
            listeners -> screen -> {
                for (GuiOpenCallback listener : listeners) {
                    ActionResult result = listener.onGuiOpen(screen);

                    if (result != ActionResult.PASS)
                        return result;
                }

                return ActionResult.PASS;
            });

    ActionResult onGuiOpen(Screen screen);
}
