// 
// Decompiled by Procyon v0.5.36
// 

package me.oringo.oringoclient.qolfeatures.module.render;

import net.minecraftforge.event.world.WorldEvent;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import me.oringo.oringoclient.utils.MobRenderUtils;
import net.minecraftforge.client.event.RenderLivingEvent;
import me.oringo.oringoclient.utils.OutlineUtils;
import me.oringo.oringoclient.events.RenderLayersEvent;
import java.util.Iterator;
import java.awt.Color;
import net.minecraft.entity.Entity;
import me.oringo.oringoclient.utils.RenderUtils;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import me.oringo.oringoclient.OringoClient;
import net.minecraftforge.event.entity.living.LivingEvent;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.NumberSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.ModeSetting;
import net.minecraft.entity.player.EntityPlayer;
import java.util.HashMap;
import me.oringo.oringoclient.qolfeatures.module.Module;

public class PlayerESP extends Module
{
    public HashMap<EntityPlayer, Integer> ticksInvisible;
    public ModeSetting mode;
    public NumberSetting opacity;
    private EntityPlayer lastRendered;
    
    public PlayerESP() {
        super("PlayerESP", 0, Category.RENDER);
        this.ticksInvisible = new HashMap<EntityPlayer, Integer>();
        this.mode = new ModeSetting("Mode", "2D", new String[] { "Outline", "2D", "Chams", "Box", "Tracers" });
        this.opacity = new NumberSetting("Opacity", 255.0, 0.0, 255.0, 1.0) {
            @Override
            public boolean isHidden() {
                return !PlayerESP.this.mode.is("Chams");
            }
        };
        this.addSettings(this.mode, this.opacity);
    }
    
    @SubscribeEvent
    public void onUpdateEntity(final LivingEvent.LivingUpdateEvent event) {
        if (event.entity instanceof EntityPlayer && event.entity != OringoClient.mc.field_71439_g && this.isToggled()) {
            if (this.ticksInvisible.containsKey(event.entity)) {
                if (event.entity.func_82150_aj()) {
                    this.ticksInvisible.replace((EntityPlayer)event.entity, this.ticksInvisible.get(event.entity) + 1);
                }
            }
            else {
                this.ticksInvisible.put((EntityPlayer)event.entity, 0);
            }
        }
    }
    
    @SubscribeEvent
    public void onRender(final RenderWorldLastEvent event) {
        if (!this.isToggled() || (!this.mode.getSelected().equals("2D") && !this.mode.getSelected().equals("Box") && !this.mode.getSelected().equals("Tracers"))) {
            return;
        }
        final Color color = OringoClient.clickGui.getColor();
        for (final EntityPlayer entityPlayer : OringoClient.mc.field_71441_e.field_73010_i) {
            if (this.isValidEntity(entityPlayer) && entityPlayer != OringoClient.mc.field_71439_g) {
                final String selected = this.mode.getSelected();
                switch (selected) {
                    case "2D": {
                        RenderUtils.draw2D((Entity)entityPlayer, event.partialTicks, 1.5f, color);
                        continue;
                    }
                    case "Box": {
                        RenderUtils.entityESPBox((Entity)entityPlayer, event.partialTicks, color);
                        continue;
                    }
                    case "Tracers": {
                        RenderUtils.tracerLine((Entity)entityPlayer, event.partialTicks, 1.0f, color);
                        continue;
                    }
                }
            }
        }
    }
    
    @SubscribeEvent
    public void onRender(final RenderLayersEvent event) {
        final Color color = OringoClient.clickGui.getColor();
        if (this.isToggled() && event.entity instanceof EntityPlayer && this.isValidEntity((EntityPlayer)event.entity) && event.entity != OringoClient.mc.field_71439_g && this.mode.getSelected().equals("Outline")) {
            OutlineUtils.outlineESP(event, color);
        }
    }
    
    @SubscribeEvent
    public void onRenderLiving(final RenderLivingEvent.Pre event) {
        if (this.lastRendered != null) {
            this.lastRendered = null;
            RenderUtils.disableChams();
            MobRenderUtils.unsetColor();
        }
        if (!(event.entity instanceof EntityOtherPlayerMP) || !this.mode.getSelected().equals("Chams") || !this.isToggled()) {
            return;
        }
        final Color color = RenderUtils.applyOpacity(OringoClient.clickGui.getColor(), (int)this.opacity.getValue());
        RenderUtils.enableChams();
        MobRenderUtils.setColor(color);
        this.lastRendered = (EntityPlayer)event.entity;
    }
    
    @SubscribeEvent
    public void onRenderLivingPost(final RenderLivingEvent.Specials.Pre event) {
        if (event.entity == this.lastRendered) {
            this.lastRendered = null;
            RenderUtils.disableChams();
            MobRenderUtils.unsetColor();
        }
    }
    
    @SubscribeEvent
    public void onWorldLoad(final WorldEvent.Load event) {
        this.ticksInvisible.clear();
    }
    
    private boolean isValidEntity(final EntityPlayer player) {
        return this.ticksInvisible.containsKey(player) && player.field_70173_aa - this.ticksInvisible.get(player) > 20 && !player.field_70128_L && player.func_110143_aJ() > 0.0f;
    }
}
