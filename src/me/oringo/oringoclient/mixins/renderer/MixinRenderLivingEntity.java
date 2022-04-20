// 
// Decompiled by Procyon v0.5.36
// 

package me.oringo.oringoclient.mixins.renderer;

import me.oringo.oringoclient.events.RenderLayersEvent;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.minecraftforge.fml.common.eventhandler.Event;
import me.oringo.oringoclient.events.RenderModelEvent;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import org.spongepowered.asm.mixin.Final;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ RendererLivingEntity.class })
public abstract class MixinRenderLivingEntity extends RenderMixin
{
    @Shadow
    protected ModelBase field_77045_g;
    @Shadow
    protected boolean field_177098_i;
    @Shadow
    @Final
    private static Logger field_147923_a;
    
    @Shadow
    protected abstract <T extends EntityLivingBase> float func_77040_d(final T p0, final float p1);
    
    @Shadow
    protected abstract float func_77034_a(final float p0, final float p1, final float p2);
    
    @Shadow
    protected abstract <T extends EntityLivingBase> void func_77039_a(final T p0, final double p1, final double p2, final double p3);
    
    @Shadow
    protected abstract <T extends EntityLivingBase> float func_77044_a(final T p0, final float p1);
    
    @Shadow
    protected abstract <T extends EntityLivingBase> void func_77043_a(final T p0, final float p1, final float p2, final float p3);
    
    @Shadow
    protected abstract <T extends EntityLivingBase> void func_77041_b(final T p0, final float p1);
    
    @Shadow
    protected abstract <T extends EntityLivingBase> boolean func_177088_c(final T p0);
    
    @Shadow
    protected abstract void func_180565_e();
    
    @Shadow
    protected abstract <T extends EntityLivingBase> boolean func_177090_c(final T p0, final float p1);
    
    @Shadow
    @Override
    public abstract void func_76986_a(final Entity p0, final double p1, final double p2, final double p3, final float p4, final float p5);
    
    @Shadow
    protected abstract void func_177091_f();
    
    @Shadow
    protected abstract <T extends EntityLivingBase> void func_77036_a(final T p0, final float p1, final float p2, final float p3, final float p4, final float p5, final float p6);
    
    @Shadow
    protected abstract <T extends EntityLivingBase> void func_177093_a(final T p0, final float p1, final float p2, final float p3, final float p4, final float p5, final float p6, final float p7);
    
    @Inject(method = { "renderModel" }, at = { @At("HEAD") }, cancellable = true)
    private <T extends EntityLivingBase> void renderModelPre(final T entity, final float p_77036_2_, final float p_77036_3_, final float p_77036_4_, final float p_77036_5_, final float p_77036_6_, final float scaleFactor, final CallbackInfo ci) {
        if (MinecraftForge.EVENT_BUS.post((Event)new RenderModelEvent(entity, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, scaleFactor, this.field_77045_g))) {
            ci.cancel();
        }
    }
    
    @Inject(method = { "renderLayers" }, at = { @At("RETURN") }, cancellable = true)
    protected void renderLayersPost(final EntityLivingBase entitylivingbaseIn, final float p_177093_2_, final float p_177093_3_, final float partialTicks, final float p_177093_5_, final float p_177093_6_, final float p_177093_7_, final float p_177093_8_, final CallbackInfo ci) {
        if (MinecraftForge.EVENT_BUS.post((Event)new RenderLayersEvent(entitylivingbaseIn, p_177093_2_, p_177093_3_, p_177093_5_, p_177093_6_, p_177093_7_, p_177093_8_, this.field_77045_g))) {
            ci.cancel();
        }
    }
    
    @Inject(method = { "renderModel" }, at = { @At("RETURN") }, cancellable = true)
    private <T extends EntityLivingBase> void renderModelPost(final T entity, final float p_77036_2_, final float p_77036_3_, final float p_77036_4_, final float p_77036_5_, final float p_77036_6_, final float scaleFactor, final CallbackInfo ci) {
        if (MinecraftForge.EVENT_BUS.post((Event)new RenderModelEvent.Post(entity, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, scaleFactor, this.field_77045_g))) {
            ci.cancel();
        }
    }
}
