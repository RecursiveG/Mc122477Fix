package me.sizableshrimp.mc122477fix.mixin;

import me.sizableshrimp.mc122477fix.GuiCharTypedCallback;
import net.minecraft.client.Keyboard;
import net.minecraft.client.gui.Element;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Keyboard.class)
public class MixinKeyboard {
    @Inject(method = "method_1458(Lnet/minecraft/client/gui/Element;II)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Element;charTyped(CI)Z"), cancellable = true)
    private static void injectOnCharLambda1(Element element, int i, int j, CallbackInfo ci) {
        internal((char) i, j, ci);
    }

    @Inject(method = "method_1473(Lnet/minecraft/client/gui/Element;CI)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Element;charTyped(CI)Z"), cancellable = true)
    private static void injectOnCharLambda2(Element element, char c, int j, CallbackInfo ci) {
        internal(c, j, ci);
    }

    private static void internal(char chr, int modifiers, CallbackInfo ci) {
        ActionResult result = GuiCharTypedCallback.EVENT.invoker().onCharTyped(chr, modifiers);

        if (result == ActionResult.FAIL)
            ci.cancel();
    }
}
