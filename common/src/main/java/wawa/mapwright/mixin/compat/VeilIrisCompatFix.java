package wawa.mapwright.mixin.compat;

import foundry.veil.impl.client.render.shader.program.ShaderProgramImpl;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wawa.mapwright.Rendering;

@Mixin(value = ShaderProgramImpl.Wrapper.class, priority = 2000)
public class VeilIrisCompatFix {

    /**
     * Veil's Iris compat ({@code ShaderWrapperMixin}) breaks map rendering by redireecting
     * every draw made with a Veil shader into an internal Iris offscreen framebuffer whenever
     * a shaderpack is active, and the geometry ends up rendered into a buffer that's never
     * displayed.
     * <p>
     * Re-binds the correct render target immediately after Veil's redirect.
     */
    @Inject(method = "apply", at = @At("TAIL"))
    private void beginBeatingVeilWithASteelPipe(CallbackInfo ci) {
        if (Rendering.inGuiShaderDraw) {
            Minecraft.getInstance().getMainRenderTarget().bindWrite(true);
        }
    }
}