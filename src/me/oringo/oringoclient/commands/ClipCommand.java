// 
// Decompiled by Procyon v0.5.36
// 

package me.oringo.oringoclient.commands;

import org.jetbrains.annotations.NotNull;
import net.minecraft.util.BlockPos;
import net.minecraft.command.CommandException;
import me.oringo.oringoclient.utils.Notifications;
import net.minecraft.util.MathHelper;
import me.oringo.oringoclient.OringoClient;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.ICommand;

public class ClipCommand implements ICommand
{
    public String func_71517_b() {
        return "clip";
    }
    
    public String func_71518_a(final ICommandSender sender) {
        return "/clip";
    }
    
    public List<String> func_71514_a() {
        return new ArrayList<String>();
    }
    
    public void func_71515_b(final ICommandSender sender, final String[] args) throws CommandException {
        if (args.length == 1) {
            OringoClient.mc.field_71439_g.func_70107_b(MathHelper.func_76128_c(OringoClient.mc.field_71439_g.field_70165_t) + 0.5, OringoClient.mc.field_71439_g.field_70163_u + Double.parseDouble(args[0]), MathHelper.func_76128_c(OringoClient.mc.field_71439_g.field_70161_v) + 0.5);
        }
        else {
            Notifications.showNotification("Oringo Client", "/clip distance", 1500);
        }
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
