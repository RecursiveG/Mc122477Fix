package me.sizableshrimp.mc122477fix.mixin;

import me.sizableshrimp.mc122477fix.GLFWPollCallback;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MixinMinecraftClient {
    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/GLX;pollEvents()V", shift = At.Shift.AFTER, ordinal = 0))
    private void injectGlfwPoll(CallbackInfo ci) {
        GLFWPollCallback.EVENT.invoker().onPoll();
    }
}
