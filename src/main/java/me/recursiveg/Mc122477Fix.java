package me.recursiveg;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod("mc122477fix")
public class Mc122477Fix
{
    public Mc122477Fix() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    long lastChatOpenTime = 0;
    @SubscribeEvent
    public void onChatOpen(GuiOpenEvent ev) {
        if (ev.getGui() instanceof ChatScreen) lastChatOpenTime = System.currentTimeMillis();
    }

    @SubscribeEvent
    public void onChatTyped(GuiScreenEvent.KeyboardCharTypedEvent.Pre ev) {
        if (System.currentTimeMillis() < lastChatOpenTime + (long)(Minecraft.getInstance().getTickLength() * 100))
            ev.setCanceled(true);
    }
}
