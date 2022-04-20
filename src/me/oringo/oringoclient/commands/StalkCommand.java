// 
// Decompiled by Procyon v0.5.36
// 

package me.oringo.oringoclient.commands;

import net.minecraft.util.BlockPos;
import net.minecraft.command.CommandException;
import java.util.Iterator;
import me.oringo.oringoclient.utils.Notifications;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.client.Minecraft;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.command.ICommandSender;
import java.util.UUID;
import net.minecraft.command.ICommand;

public class StalkCommand implements ICommand
{
    public static UUID stalking;
    
    public String func_71517_b() {
        return "stalk";
    }
    
    public String func_71518_a(final ICommandSender sender) {
        return "/stalk";
    }
    
    public List<String> func_71514_a() {
        return new ArrayList<String>();
    }
    
    public void func_71515_b(final ICommandSender sender, final String[] args) throws CommandException {
        StalkCommand.stalking = null;
        if (args.length == 1) {
            for (final EntityPlayer player : Minecraft.func_71410_x().field_71441_e.field_73010_i) {
                if (player.func_70005_c_().equalsIgnoreCase(args[0])) {
                    StalkCommand.stalking = player.func_110124_au();
                    Notifications.showNotification("Oringo Client", "Enabled stalking!", 1000);
                    return;
                }
            }
            Notifications.showNotification("Oringo Client", "Player not found!", 1000);
            return;
        }
        Notifications.showNotification("Oringo Client", "Disabled Stalking!", 1000);
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
    
    public int compareTo(final ICommand o) {
        return 0;
    }
}
