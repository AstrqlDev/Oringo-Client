// 
// Decompiled by Procyon v0.5.36
// 

package me.oringo.oringoclient.mixins.renderer;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import me.oringo.oringoclient.commands.StalkCommand;
import me.oringo.oringoclient.utils.RenderUtils;
import me.oringo.oringoclient.qolfeatures.module.other.AntiNicker;
import me.oringo.oringoclient.utils.MobRenderUtils;
import java.awt.Color;
import me.oringo.oringoclient.qolfeatures.module.macro.AutoSumoBot;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.minecraft.entity.Entity;
import net.minecraft.client.renderer.entity.RenderManager;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ RenderManager.class })
public abstract class RenderManagerMixin
{
    @Inject(method = { "doRenderEntity" }, at = { @At("HEAD") })
    public void doRenderEntityPre(final Entity entity, final double x, final double y, final double z, final float entityYaw, final float partialTicks, final boolean p_147939_10_, final CallbackInfoReturnable<Boolean> cir) {
        if (entity.equals((Object)AutoSumoBot.target)) {
            MobRenderUtils.setColor(new Color(255, 0, 0, 80));
        }
        if (AntiNicker.nicked.contains(entity)) {
            RenderUtils.enableChams();
            MobRenderUtils.setColor(new Color(255, 0, 0, 80));
        }
        if (entity.func_110124_au().equals(StalkCommand.stalking)) {
            entity.func_82142_c(false);
            RenderUtils.enableChams();
            MobRenderUtils.setColor(new Color(64, 0, 255, 80));
        }
    }
    
    @Inject(method = { "doRenderEntity" }, at = { @At("RETURN") })
    public void doRenderEntityPost(final Entity entity, final double x, final double y, final double z, final float entityYaw, final float partialTicks, final boolean p_147939_10_, final CallbackInfoReturnable<Boolean> cir) {
        if (entity.equals((Object)AutoSumoBot.target)) {
            MobRenderUtils.unsetColor();
        }
        if (AntiNicker.nicked.contains(entity)) {
            RenderUtils.disableChams();
            MobRenderUtils.unsetColor();
        }
        if (entity.func_110124_au().equals(StalkCommand.stalking)) {
            RenderUtils.disableChams();
            MobRenderUtils.unsetColor();
        }
    }
}
