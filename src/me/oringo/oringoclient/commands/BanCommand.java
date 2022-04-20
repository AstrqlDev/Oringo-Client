// 
// Decompiled by Procyon v0.5.36
// 

package me.oringo.oringoclient.commands;

import org.jetbrains.annotations.NotNull;
import net.minecraft.command.CommandException;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import java.util.Random;
import me.oringo.oringoclient.OringoClient;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.ICommand;

public class BanCommand implements ICommand
{
    public String func_71517_b() {
        return "selfban";
    }
    
    public String func_71518_a(final ICommandSender iCommandSender) {
        return "/selfban";
    }
    
    public List<String> func_71514_a() {
        return new ArrayList<String>();
    }
    
    public void func_71515_b(final ICommandSender iCommandSender, final String[] strings) throws CommandException {
        if (strings.length == 1 && strings[0].equals("confirm")) {
            OringoClient.sendMessageWithPrefix("You will get banned in ~3 seconds!");
            for (int i = 0; i < 10; ++i) {
                OringoClient.mc.func_147114_u().func_147298_b().func_179290_a((Packet)new C08PacketPlayerBlockPlacement(new BlockPos(new Random().nextInt(), new Random().nextInt(), new Random().nextInt()), 1, OringoClient.mc.field_71439_g.field_71071_by.func_70448_g(), 0.0f, 0.0f, 0.0f));
            }
        }
        else {
            OringoClient.sendMessageWithPrefix("/selfban confirm");
        }
    }
    
    public boolean func_71519_b(final ICommandSender iCommandSender) {
        return true;
    }
    
    public List<String> func_180525_a(final ICommandSender iCommandSender, final String[] strings, final BlockPos blockPos) {
        return new ArrayList<String>();
    }
    
    public boolean func_82358_a(final String[] strings, final int i) {
        return true;
    }
    
    public int compareTo(@NotNull final ICommand o) {
        return 0;
    }
}
