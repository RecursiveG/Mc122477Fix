package me.sizableshrimp.mc122477fix.mixin;

import me.sizableshrimp.mc122477fix.GuiOpenCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.ActionResult;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MixinMinecraftClient {
    @Inject(method = "openScreen(Lnet/minecraft/client/gui/screen/Screen;)V", at = @At(value = "JUMP", opcode = Opcodes.IFNE, shift = At.Shift.BEFORE), cancellable = true)
    private void injectOpenScreen(Screen screen, CallbackInfo ci) {
        ActionResult result = GuiOpenCallback.EVENT.invoker().onGuiOpen(screen);

        if (result == ActionResult.FAIL)
            ci.cancel();
    }
}
