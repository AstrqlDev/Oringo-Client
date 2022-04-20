// 
// Decompiled by Procyon v0.5.36
// 

package me.oringo.oringoclient.commands;

import org.jetbrains.annotations.NotNull;
import net.minecraft.command.CommandException;
import me.oringo.oringoclient.utils.Notifications;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.command.ICommandSender;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraft.block.properties.IProperty;
import me.oringo.oringoclient.events.BlockChangeEvent;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.network.play.server.S02PacketChat;
import me.oringo.oringoclient.events.PacketReceivedEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import java.util.Iterator;
import net.minecraft.block.BlockNetherWart;
import net.minecraft.block.BlockCrops;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemHoe;
import net.minecraft.client.settings.KeyBinding;
import me.oringo.oringoclient.OringoClient;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraft.command.ICommand;

public class FarmingMacro implements ICommand
{
    private boolean toggled;
    private boolean left;
    private boolean direction;
    private boolean cage;
    private int ticksStanding;
    private int susPackets;
    private int pause;
    private int nukerCheck;
    
    public FarmingMacro() {
        this.ticksStanding = 0;
        this.susPackets = 0;
        this.pause = 0;
        this.nukerCheck = 0;
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent e) {
        if (OringoClient.mc.field_71439_g == null || OringoClient.mc.field_71441_e == null || !this.toggled || e.phase == TickEvent.Phase.END || this.cage) {
            return;
        }
        --this.pause;
        --this.nukerCheck;
        KeyBinding.func_74510_a(OringoClient.mc.field_71474_y.field_74351_w.func_151463_i(), false);
        KeyBinding.func_74510_a(OringoClient.mc.field_71474_y.field_74370_x.func_151463_i(), false);
        KeyBinding.func_74510_a(OringoClient.mc.field_71474_y.field_74366_z.func_151463_i(), false);
        KeyBinding.func_74510_a(OringoClient.mc.field_71474_y.field_151444_V.func_151463_i(), false);
        if (OringoClient.mc.field_71439_g.field_70173_aa % 600 == 0) {
            this.susPackets = 0;
            OringoClient.mc.field_71439_g.func_71165_d("/setspawn");
        }
        if (OringoClient.mc.field_71439_g.func_70694_bm() == null || !(OringoClient.mc.field_71439_g.func_70694_bm().func_77973_b() instanceof ItemHoe)) {
            if (this.pause < 5) {
                this.pause = 10;
            }
            else {
                if (OringoClient.mc.field_71439_g.func_70694_bm() != null && OringoClient.mc.field_71439_g.func_70694_bm().func_77973_b() instanceof ItemMap) {
                    return;
                }
                if (this.pause == 5) {
                    for (final Slot slot : OringoClient.mc.field_71439_g.field_71070_bA.field_75151_b) {
                        if (slot.func_75216_d() && slot.func_75211_c().func_77973_b() instanceof ItemHoe) {
                            this.numberClick(slot.field_75222_d, OringoClient.mc.field_71439_g.field_71071_by.field_70461_c);
                            OringoClient.mc.func_147114_u().func_147298_b().func_179290_a((Packet)new C08PacketPlayerBlockPlacement(OringoClient.mc.field_71439_g.func_70694_bm()));
                        }
                    }
                }
                return;
            }
        }
        if (OringoClient.mc.field_71441_e.func_180495_p(new BlockPos(OringoClient.mc.field_71439_g.field_70165_t, OringoClient.mc.field_71439_g.field_70163_u - 0.5, OringoClient.mc.field_71439_g.field_70161_v)).func_177230_c() == Blocks.field_150357_h && this.pause < 1) {
            this.pause = 600;
            OringoClient.mc.field_71439_g.func_71165_d("/setguestspawn");
            new Thread(() -> {
                try {
                    Thread.sleep(1800L);
                    OringoClient.mc.field_71439_g.func_71165_d("/ac wtf");
                }
                catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }).start();
            return;
        }
        if (this.pause > 0 || this.susPackets > 7 || OringoClient.mc.field_71462_r instanceof GuiContainer) {
            return;
        }
        if (Math.abs(OringoClient.mc.field_71439_g.field_70165_t - OringoClient.mc.field_71439_g.field_70142_S) < 0.15 && Math.abs(OringoClient.mc.field_71439_g.field_70161_v - OringoClient.mc.field_71439_g.field_70136_U) < 0.15) {
            ++this.ticksStanding;
            if (this.ticksStanding > 10) {
                this.left = !this.left;
                this.ticksStanding = 0;
            }
        }
        else if (this.ticksStanding > 0) {
            --this.ticksStanding;
        }
        if (OringoClient.mc.field_71476_x != null && OringoClient.mc.field_71476_x.func_178782_a() != null && (OringoClient.mc.field_71441_e.func_180495_p(OringoClient.mc.field_71476_x.func_178782_a()).func_177230_c() instanceof BlockCrops || OringoClient.mc.field_71441_e.func_180495_p(OringoClient.mc.field_71476_x.func_178782_a()).func_177230_c() instanceof BlockNetherWart)) {
            OringoClient.mc.field_71439_g.func_71038_i();
            OringoClient.mc.field_71442_b.func_180511_b(OringoClient.mc.field_71476_x.func_178782_a(), OringoClient.mc.field_71476_x.field_178784_b);
        }
        KeyBinding.func_74510_a(OringoClient.mc.field_71474_y.field_74351_w.func_151463_i(), true);
        KeyBinding.func_74510_a(OringoClient.mc.field_71474_y.field_74370_x.func_151463_i(), this.left);
        KeyBinding.func_74510_a(OringoClient.mc.field_71474_y.field_74366_z.func_151463_i(), !this.left);
        KeyBinding.func_74510_a(OringoClient.mc.field_71474_y.field_151444_V.func_151463_i(), true);
    }
    
    public void numberClick(final int slot, final int button) {
        OringoClient.mc.field_71442_b.func_78753_a(OringoClient.mc.field_71439_g.field_71069_bz.field_75152_c, slot, button, 2, (EntityPlayer)OringoClient.mc.field_71439_g);
    }
    
    @SubscribeEvent(receiveCanceled = true)
    public void onPacket(final PacketReceivedEvent event) {
        if (this.toggled) {
            if (event.packet instanceof S02PacketChat && ChatFormatting.stripFormatting(((S02PacketChat)event.packet).func_148915_c().func_150254_d()).startsWith("Warped from the ")) {
                this.direction = !this.direction;
            }
            if (event.packet instanceof S27PacketExplosion || (event.packet instanceof S12PacketEntityVelocity && OringoClient.mc.field_71441_e.func_73045_a(((S12PacketEntityVelocity)event.packet).func_149412_c()) == OringoClient.mc.field_71439_g)) {
                ++this.susPackets;
            }
            if (event.packet instanceof S08PacketPlayerPosLook) {
                this.pause = 20;
            }
        }
    }
    
    @SubscribeEvent
    public void onBlock(final BlockChangeEvent event) {
        if (this.toggled && this.pause < 1 && OringoClient.mc.field_71441_e.func_175726_f(event.pos) != null && !(OringoClient.mc.field_71441_e.func_180495_p(event.pos).func_177230_c() instanceof BlockCrops) && !(OringoClient.mc.field_71441_e.func_180495_p(event.pos).func_177230_c() instanceof BlockNetherWart) && OringoClient.mc.field_71439_g.func_70011_f((double)event.pos.func_177958_n(), (double)event.pos.func_177956_o(), (double)event.pos.func_177952_p()) < 7.0 && (event.state.func_177230_c() instanceof BlockCrops || event.state.func_177230_c() instanceof BlockNetherWart) && ((event.state.func_177230_c() instanceof BlockCrops) ? event.state.func_177229_b((IProperty)BlockCrops.field_176488_a) : ((Integer)event.state.func_177229_b((IProperty)BlockNetherWart.field_176486_a))) == 7 && this.nukerCheck++ > 20) {
            OringoClient.sendMessageWithPrefix("Nuker check");
            this.pause = 60;
        }
    }
    
    @SubscribeEvent
    public void onWorldLoad(final WorldEvent.Load e) {
        if (!this.toggled) {
            return;
        }
        this.toggled = false;
        KeyBinding.func_74510_a(OringoClient.mc.field_71474_y.field_74351_w.func_151463_i(), false);
        KeyBinding.func_74510_a(OringoClient.mc.field_71474_y.field_74370_x.func_151463_i(), false);
        KeyBinding.func_74510_a(OringoClient.mc.field_71474_y.field_74366_z.func_151463_i(), false);
        KeyBinding.func_74510_a(OringoClient.mc.field_71474_y.field_151444_V.func_151463_i(), false);
        int i;
        new Thread(() -> {
            try {
                for (i = 1; i < 4; ++i) {
                    Thread.sleep(5000L);
                    if (OringoClient.mc.field_71439_g != null) {
                        OringoClient.mc.field_71439_g.func_71165_d((i == 1) ? "/l" : ((i == 2) ? "/play skyblock" : "/is"));
                    }
                }
                Thread.sleep(10000L);
                this.toggled = true;
            }
            catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }).start();
    }
    
    public String func_71517_b() {
        return "farm";
    }
    
    public String func_71518_a(final ICommandSender sender) {
        return "/farm";
    }
    
    public List<String> func_71514_a() {
        return new ArrayList<String>();
    }
    
    public void func_71515_b(final ICommandSender sender, final String[] args) throws CommandException {
        this.toggled = !this.toggled;
        Notifications.showNotification("Oringo Client", this.toggled ? "Started farm!" : "Stopped farm!", 1500);
    }
    
    public boolean func_71519_b(final ICommandSender sender) {
        return true;
    }
    
    public List<String> func_180525_a(final ICommandSender sender, final String[] args, final BlockPos pos) {
        return new ArrayList<String>();
    }
    
    public boolean func_82358_a(final String[] args, final int index) {
        return false;
    }
    
    public int compareTo(@NotNull final ICommand o) {
        return 0;
    }
}
