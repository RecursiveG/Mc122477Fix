package me.recursiveg;

import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.client.event.ScreenOpenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod("mc122477fix")
public class Mc122477Fix {
    public Mc122477Fix() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    long renderTicksSinceScreenOpen = 0;

    @SubscribeEvent
    public void onScreenOpen(ScreenOpenEvent ev) {
        if (ev.getScreen() instanceof ChatScreen || ev.getScreen() instanceof CreativeModeInventoryScreen) {
            renderTicksSinceScreenOpen = 0;
        }
    }

    @SubscribeEvent
    public void onCharTyped(ScreenEvent.KeyboardCharTypedEvent.Pre ev) {
        if (renderTicksSinceScreenOpen < 2) {
            ev.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onPostClientTick(TickEvent.RenderTickEvent ev) {
        if (ev.phase == TickEvent.Phase.END) renderTicksSinceScreenOpen++;
    }
}
