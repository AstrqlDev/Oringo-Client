// 
// Decompiled by Procyon v0.5.36
// 

package me.oringo.oringoclient.commands;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.item.ItemStack;
import me.oringo.oringoclient.utils.Notifications;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.init.Items;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraft.util.BlockPos;
import net.minecraft.command.CommandException;
import java.util.Iterator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import me.oringo.oringoclient.OringoClient;
import net.minecraft.client.Minecraft;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.ICommand;

public class CheckNameCommand implements ICommand
{
    public static String profileView;
    
    public String func_71517_b() {
        return "checkname";
    }
    
    public String func_71518_a(final ICommandSender sender) {
        return "/checkname";
    }
    
    public List<String> func_71514_a() {
        return new ArrayList<String>();
    }
    
    public void func_71515_b(final ICommandSender sender, final String[] args) throws CommandException {
        final Minecraft mc = Minecraft.func_71410_x();
        if (args.length != 1) {
            OringoClient.sendMessageWithPrefix("/checkname [IGN]");
            return;
        }
        for (final EntityPlayer entity : mc.field_71441_e.field_73010_i) {
            if (entity.func_70005_c_().equalsIgnoreCase(args[0])) {
                if (entity.func_70032_d((Entity)mc.field_71439_g) > 6.0f) {
                    OringoClient.sendMessageWithPrefix("You are too far away!");
                    return;
                }
                if (mc.field_71439_g.func_70694_bm() != null) {
                    OringoClient.sendMessageWithPrefix("You can't hold anything in your hand!");
                    return;
                }
                mc.field_71442_b.func_78768_b((EntityPlayer)mc.field_71439_g, (Entity)entity);
                CheckNameCommand.profileView = args[0];
                return;
            }
        }
        OringoClient.sendMessageWithPrefix("That Player isn't loaded!");
    }
    
    public boolean func_71519_b(final ICommandSender sender) {
        return true;
    }
    
    public List<String> func_180525_a(final ICommandSender sender, final String[] args, final BlockPos pos) {
        final ArrayList<String> matching = new ArrayList<String>();
        for (final EntityPlayer playerEntity : Minecraft.func_71410_x().field_71441_e.field_73010_i) {
            if (playerEntity.func_70005_c_().toLowerCase().startsWith(args[0].toLowerCase())) {
                matching.add(playerEntity.func_70005_c_());
            }
        }
        return matching;
    }
    
    public boolean func_82358_a(final String[] args, final int index) {
        return false;
    }
    
    public int compareTo(final ICommand o) {
        return 0;
    }
    
    @SubscribeEvent
    public void onGui(final GuiOpenEvent event) {
        if (event.gui instanceof GuiChest && CheckNameCommand.profileView != null && ((ContainerChest)((GuiChest)event.gui).field_147002_h).func_85151_d().func_70005_c_().toLowerCase().startsWith(CheckNameCommand.profileView.toLowerCase())) {
            final ItemStack is;
            String name;
            new Thread(() -> {
                try {
                    Thread.sleep(100L);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                is = Minecraft.func_71410_x().field_71439_g.field_71070_bA.func_75139_a(22).func_75211_c();
                if (is != null && is.func_77973_b().equals(Items.field_151144_bL)) {
                    name = is.serializeNBT().func_74775_l("tag").func_74775_l("SkullOwner").func_74779_i("Name");
                    Minecraft.func_71410_x().field_71439_g.func_71053_j();
                    Notifications.showNotification("Oringo Client", "Real name: " + ChatFormatting.GOLD + name.replaceFirst("§", ""), 4000);
                }
                CheckNameCommand.profileView = null;
            }).start();
        }
    }
}
