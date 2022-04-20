// 
// Decompiled by Procyon v0.5.36
// 

package me.oringo.oringoclient.commands.nucleus;

import org.jetbrains.annotations.NotNull;
import net.minecraft.command.CommandException;
import net.minecraft.init.Blocks;
import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.utils.Notifications;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import java.util.HashMap;
import net.minecraft.command.ICommand;

public class SaveCommand implements ICommand
{
    public static HashMap<String, BlockPos> posHashMap;
    public static HashMap<String, Integer> idHashMap;
    
    public String func_71517_b() {
        return "save";
    }
    
    public String func_71518_a(final ICommandSender iCommandSender) {
        return "/save";
    }
    
    public List<String> func_71514_a() {
        return new ArrayList<String>();
    }
    
    public void func_71515_b(final ICommandSender iCommandSender, final String[] args) throws CommandException {
        if (args.length < 1) {
            Notifications.showNotification("Oringo Client", "/save name", 2500);
            return;
        }
        if (args[0].toLowerCase().equals("list")) {
            SaveCommand.idHashMap.forEach((key, value) -> OringoClient.sendMessageWithPrefix(key + " " + value.toString()));
            SaveCommand.posHashMap.forEach((key, value) -> OringoClient.sendMessageWithPrefix(key + " " + value.toString()));
        }
        else if (OringoClient.mc.field_71476_x != null) {
            switch (OringoClient.mc.field_71476_x.field_72313_a) {
                case ENTITY: {
                    if (OringoClient.mc.field_71476_x.field_72308_g != null) {
                        if (!SaveCommand.posHashMap.containsKey(args[0])) {
                            SaveCommand.idHashMap.put(args[0], OringoClient.mc.field_71476_x.field_72308_g.func_145782_y());
                        }
                        else {
                            SaveCommand.idHashMap.replace(args[0], OringoClient.mc.field_71476_x.field_72308_g.func_145782_y());
                        }
                        Notifications.showNotification("Oringo Client", "Added " + OringoClient.mc.field_71476_x.field_72308_g.func_145782_y() + " to list", 2000);
                        break;
                    }
                    break;
                }
                case BLOCK: {
                    if (OringoClient.mc.field_71476_x.func_178782_a() != null && OringoClient.mc.field_71441_e.func_180495_p(OringoClient.mc.field_71476_x.func_178782_a()).func_177230_c() != Blocks.field_150350_a) {
                        if (!SaveCommand.posHashMap.containsKey(args[0])) {
                            SaveCommand.posHashMap.put(args[0], OringoClient.mc.field_71476_x.func_178782_a());
                        }
                        else {
                            SaveCommand.posHashMap.replace(args[0], OringoClient.mc.field_71476_x.func_178782_a());
                        }
                        Notifications.showNotification("Oringo Client", "Added " + OringoClient.mc.field_71476_x.func_178782_a() + " to list", 2000);
                        break;
                    }
                    break;
                }
            }
        }
    }
    
    public boolean func_71519_b(final ICommandSender iCommandSender) {
        return true;
    }
    
    public List<String> func_180525_a(final ICommandSender iCommandSender, final String[] strings, final BlockPos blockPos) {
        return new ArrayList<String>();
    }
    
    public boolean func_82358_a(final String[] strings, final int i) {
        return false;
    }
    
    public int compareTo(@NotNull final ICommand o) {
        return 0;
    }
    
    static {
        SaveCommand.posHashMap = new HashMap<String, BlockPos>();
        SaveCommand.idHashMap = new HashMap<String, Integer>();
    }
}
