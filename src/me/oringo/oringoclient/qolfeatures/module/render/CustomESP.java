// 
// Decompiled by Procyon v0.5.36
// 

package me.oringo.oringoclient.qolfeatures.module.render;

import java.util.ArrayList;
import java.io.OutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import me.oringo.oringoclient.utils.MobRenderUtils;
import net.minecraftforge.client.event.RenderLivingEvent;
import me.oringo.oringoclient.utils.OutlineUtils;
import me.oringo.oringoclient.events.RenderLayersEvent;
import java.util.Iterator;
import me.oringo.oringoclient.utils.RenderUtils;
import net.minecraft.entity.Entity;
import java.awt.Color;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import me.oringo.oringoclient.utils.Notifications;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.entity.item.EntityArmorStand;
import org.lwjgl.input.Mouse;
import me.oringo.oringoclient.OringoClient;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import net.minecraft.entity.EntityLivingBase;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.NumberSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.ModeSetting;
import java.util.regex.Pattern;
import java.util.List;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.BooleanSetting;
import me.oringo.oringoclient.qolfeatures.module.Module;

public class CustomESP extends Module
{
    public BooleanSetting middleClick;
    public static List<String> names;
    private boolean wasDown;
    private static Pattern start;
    private static Pattern end;
    public ModeSetting mode;
    public NumberSetting red;
    public NumberSetting green;
    public NumberSetting blue;
    private EntityLivingBase lastRendered;
    
    public CustomESP() {
        super("Custom ESP", Category.RENDER);
        this.middleClick = new BooleanSetting("Middle click to add", false);
        this.mode = new ModeSetting("Mode", "2D", new String[] { "Outline", "2D", "Chams", "Box", "Tracers" });
        this.red = new NumberSetting("Red", 1.0, 1.0, 255.0, 1.0);
        this.green = new NumberSetting("Green", 1.0, 1.0, 255.0, 1.0);
        this.blue = new NumberSetting("Blue", 1.0, 1.0, 255.0, 1.0);
        this.addSettings(this.mode, this.red, this.green, this.blue, this.middleClick);
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (OringoClient.mc.field_71439_g == null || OringoClient.mc.field_71441_e == null || !this.middleClick.isEnabled()) {
            return;
        }
        if (Mouse.isButtonDown(2) && OringoClient.mc.field_71462_r == null) {
            if (OringoClient.mc.field_147125_j != null && !this.wasDown && !(OringoClient.mc.field_147125_j instanceof EntityArmorStand) && OringoClient.mc.field_147125_j instanceof EntityLivingBase) {
                final String name = ChatFormatting.stripFormatting(OringoClient.mc.field_147125_j.func_145748_c_().func_150254_d());
                if (!CustomESP.names.contains(name)) {
                    CustomESP.names.add(name);
                    save();
                    Notifications.showNotification("Oringo Client", "Added " + ChatFormatting.AQUA + name + ChatFormatting.RESET + " to customESP", 2000);
                }
                else {
                    CustomESP.names.remove(name);
                    save();
                    Notifications.showNotification("Oringo Client", "Removed " + ChatFormatting.AQUA + name + ChatFormatting.RESET + " from customESP", 2000);
                }
            }
            this.wasDown = true;
        }
        else {
            this.wasDown = false;
        }
    }
    
    @SubscribeEvent
    public void onRender(final RenderWorldLastEvent event) {
        if (!this.isToggled() || (!this.mode.getSelected().equals("2D") && !this.mode.getSelected().equals("Box") && !this.mode.getSelected().equals("Tracers"))) {
            return;
        }
        final Color color = new Color((int)this.red.getValue(), (int)this.green.getValue(), (int)this.blue.getValue(), 255);
        for (final Entity entity : OringoClient.mc.field_71441_e.field_72996_f) {
            if (entity instanceof EntityLivingBase && entity != OringoClient.mc.field_71439_g && CustomESP.names.contains(ChatFormatting.stripFormatting(entity.func_145748_c_().func_150254_d()))) {
                final String selected = this.mode.getSelected();
                switch (selected) {
                    case "2D": {
                        RenderUtils.draw2D(entity, event.partialTicks, 1.5f, color);
                        continue;
                    }
                    case "Box": {
                        RenderUtils.entityESPBox(entity, event.partialTicks, color);
                        continue;
                    }
                    case "Tracers": {
                        RenderUtils.tracerLine(entity, event.partialTicks, 1.0f, color);
                        continue;
                    }
                }
            }
        }
    }
    
    @SubscribeEvent
    public void onRender(final RenderLayersEvent event) {
        final Color color = new Color((int)this.red.getValue(), (int)this.green.getValue(), (int)this.blue.getValue(), 255);
        if (this.isToggled() && CustomESP.names.contains(ChatFormatting.stripFormatting(event.entity.func_145748_c_().func_150254_d())) && event.entity != OringoClient.mc.field_71439_g && this.mode.getSelected().equals("Outline")) {
            OutlineUtils.outlineESP(event, color);
        }
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onRenderLiving(final RenderLivingEvent.Pre event) {
        if (this.lastRendered != null) {
            this.lastRendered = null;
            RenderUtils.disableChams();
            MobRenderUtils.unsetColor();
        }
        if (!this.mode.getSelected().equals("Chams") || !this.isToggled() || !CustomESP.names.contains(ChatFormatting.stripFormatting(event.entity.func_145748_c_().func_150254_d()))) {
            return;
        }
        final Color color = new Color((int)this.red.getValue(), (int)this.green.getValue(), (int)this.blue.getValue(), 255);
        RenderUtils.enableChams();
        MobRenderUtils.setColor(color);
        this.lastRendered = event.entity;
    }
    
    @SubscribeEvent
    public void onRenderLivingPost(final RenderLivingEvent.Specials.Pre event) {
        if (event.entity == this.lastRendered) {
            this.lastRendered = null;
            RenderUtils.disableChams();
            MobRenderUtils.unsetColor();
        }
    }
    
    private static void save() {
        try {
            final DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream("config/OringoClient/CustomESP.cfg"));
            dataOutputStream.writeInt(CustomESP.names.size());
            for (final String name : CustomESP.names) {
                dataOutputStream.writeUTF(name);
            }
            dataOutputStream.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    static {
        CustomESP.names = new ArrayList<String>();
        CustomESP.start = Pattern.compile("\\[Lv\\d*] ");
        CustomESP.end = Pattern.compile(" \\d*/\\d*$");
    }
}
