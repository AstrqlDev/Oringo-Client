// 
// Decompiled by Procyon v0.5.36
// 

package me.oringo.oringoclient.commands;

import net.minecraft.util.BlockPos;
import net.minecraft.command.CommandException;
import java.util.Iterator;
import java.lang.reflect.Field;
import java.io.FileOutputStream;
import me.oringodevmode.Transformer;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ChatComponentText;
import com.mojang.realmsclient.gui.ChatFormatting;
import java.io.IOException;
import java.io.File;
import me.oringo.oringoclient.OringoClient;
import net.minecraft.world.IWorldAccess;
import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import java.util.List;
import net.minecraft.command.ICommandSender;
import java.util.ArrayList;
import net.minecraft.command.ICommand;

public class DevModeCommand implements ICommand
{
    public static ArrayList<String> ignoredPackets;
    
    public String func_71517_b() {
        return "oringodev";
    }
    
    public String func_71518_a(final ICommandSender sender) {
        return "/oringodev";
    }
    
    public List<String> func_71514_a() {
        return new ArrayList<String>();
    }
    
    public void func_71515_b(final ICommandSender sender, final String[] args) throws CommandException {
        try {
            final Field field_73021_x = World.class.getDeclaredField("field_73021_x");
            field_73021_x.setAccessible(true);
            final List<IWorldAccess> accesses = (List<IWorldAccess>)field_73021_x.get(Minecraft.func_71410_x().field_71441_e);
            for (final IWorldAccess access : accesses) {
                OringoClient.sendMessageWithPrefix(access.getClass().getSimpleName());
            }
        }
        catch (Exception ex) {}
        if (args.length == 1) {
            final File oringoDev = new File("OringoDev");
            OringoClient.sendMessage("§fPath: §a" + oringoDev.getAbsolutePath());
            if (args[0].equalsIgnoreCase("toggle")) {
                if (oringoDev.exists()) {
                    oringoDev.delete();
                }
                else {
                    try {
                        oringoDev.createNewFile();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                Minecraft.func_71410_x().field_71439_g.func_145747_a((IChatComponent)new ChatComponentText(ChatFormatting.RED + "Dev mod is: " + (oringoDev.exists() ? "enabled" : "disabled")));
                OringoClient.sendMessage("§cYou need to restart you game to apply the changes.");
            }
            else if (Transformer.classes.containsKey(args[0])) {
                final String[] split = args[0].split("[.]");
                final File file = new File(split[split.length - 1] + ".class");
                try {
                    final FileOutputStream fw = new FileOutputStream(file);
                    fw.write(Transformer.classes.get(args[0]));
                    fw.close();
                }
                catch (Exception e2) {
                    e2.printStackTrace();
                }
                OringoClient.sendMessage(String.format("§aSaving class as %s", file.getAbsolutePath()));
            }
            else {
                OringoClient.sendMessage("§cClass not found.");
            }
        }
        else {
            OringoClient.sendMessage("§c/oringodev <toggle/class name>");
        }
        if (args.length == 2 && args[0].equalsIgnoreCase("ignore")) {
            DevModeCommand.ignoredPackets.add(args[1]);
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
    
    public int compareTo(final ICommand o) {
        return 0;
    }
    
    static {
        DevModeCommand.ignoredPackets = new ArrayList<String>();
    }
}
