package me.sizableshrimp.mc122477fix.mixin;

import me.sizableshrimp.mc122477fix.KeyboardCharTypedCallback;
import me.sizableshrimp.mc122477fix.KeyboardKeyPressedCallback;
import net.minecraft.client.Keyboard;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Keyboard.class)
public class MixinKeyboard {
    @Inject(method = "onKey", at = @At(value = "FIELD", target = "Lnet/minecraft/client/Keyboard;debugCrashStartTime:J", ordinal = 0), cancellable = true)
    private void injectOnKey(long window, int key, int scancode, int i, int j, CallbackInfo ci) {
        ActionResult result = KeyboardKeyPressedCallback.EVENT.invoker().onKeyPressed(window, key, scancode, i, j);

        if (result == ActionResult.FAIL)
            ci.cancel();
    }

    @Inject(method = "onChar", at = @At(value = "FIELD", target = "Lnet/minecraft/client/Keyboard;client:Lnet/minecraft/client/MinecraftClient;", ordinal = 0), cancellable = true)
    private void injectOnChar(long window, int i, int j, CallbackInfo ci) {
        ActionResult result = KeyboardCharTypedCallback.EVENT.invoker().onCharTyped(window, i, j);

        if (result == ActionResult.FAIL)
            ci.cancel();
    }
}
