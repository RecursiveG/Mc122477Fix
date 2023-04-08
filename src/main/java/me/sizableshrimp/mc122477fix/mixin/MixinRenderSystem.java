package me.sizableshrimp.mc122477fix.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import me.sizableshrimp.mc122477fix.GLFWPollCallback;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderSystem.class)
public class MixinRenderSystem {
    @Inject(method = "pollEvents",
            at = @At(value = "INVOKE", target = "Lorg/lwjgl/glfw/GLFW;glfwPollEvents()V", shift = At.Shift.AFTER),
            remap = false)
    private static void injectGlfwPoll(CallbackInfo ci) {
        GLFWPollCallback.EVENT.invoker().onPoll();
    }
}
