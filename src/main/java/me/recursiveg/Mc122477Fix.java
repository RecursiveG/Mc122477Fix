package me.recursiveg;

import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.client.event.ScreenOpenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod("mc122477fix")
public class Mc122477Fix
{
    public Mc122477Fix() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    long lastScreenOpenTime = 0;
    @SubscribeEvent
    public void onScreenOpen(ScreenOpenEvent ev) {
        if (ev.getScreen() instanceof ChatScreen || ev.getScreen() instanceof CreativeModeInventoryScreen) lastScreenOpenTime = System.currentTimeMillis();
    }

    @SubscribeEvent
    public void onCharTyped(ScreenEvent.KeyboardCharTypedEvent.Pre ev) {
        if (System.currentTimeMillis() - lastScreenOpenTime < 50) ev.setCanceled(true);
    }
}
