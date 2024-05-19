package me.recursiveg;

import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.ScreenEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.TickEvent;

@Mod("mc122477fix")
public class Mc122477Fix {
    public Mc122477Fix() {
        NeoForge.EVENT_BUS.register(this);
    }

    long renderTicksSinceScreenOpen = 0;

    @SubscribeEvent
    public void onScreenOpen(ScreenEvent.Opening ev) {
        if (ev.getNewScreen() instanceof ChatScreen || ev.getNewScreen() instanceof CreativeModeInventoryScreen) {
            renderTicksSinceScreenOpen = 0;
        }
    }

    @SubscribeEvent
    public void onKeyPressed(ScreenEvent.KeyPressed.Pre ev) {
        if (renderTicksSinceScreenOpen < 2) {
            ev.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onCharTyped(ScreenEvent.CharacterTyped.Pre ev) {
        if (renderTicksSinceScreenOpen < 2) {
            ev.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onPostClientTick(TickEvent.RenderTickEvent ev) {
        if (ev.phase == TickEvent.Phase.END) {
            renderTicksSinceScreenOpen++;
        }
    }
}
