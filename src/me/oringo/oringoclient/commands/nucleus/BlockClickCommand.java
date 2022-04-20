// 
// Decompiled by Procyon v0.5.36
// 

package me.oringo.oringoclient.commands.nucleus;

import org.jetbrains.annotations.NotNull;
import net.minecraft.util.EnumFacing;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.command.CommandException;
import net.minecraft.util.BlockPos;
import net.minecraft.network.Packet;
import me.oringo.oringoclient.utils.ReflectionUtils;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.C02PacketUseEntity;
import me.oringo.oringoclient.OringoClient;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.ICommand;

public class BlockClickCommand implements ICommand
{
    public String func_71517_b() {
        return "click";
    }
    
    public String func_71518_a(final ICommandSender iCommandSender) {
        return "/click";
    }
    
    public List<String> func_71514_a() {
        return new ArrayList<String>();
    }
    
    public void func_71515_b(final ICommandSender iCommandSender, final String[] strings) throws CommandException {
        if (strings.length < 3) {
            if (SaveCommand.idHashMap.containsKey(strings[0])) {
                final C02PacketUseEntity packet = new C02PacketUseEntity((Entity)OringoClient.mc.field_71439_g, C02PacketUseEntity.Action.INTERACT);
                ReflectionUtils.setFieldByIndex(packet, 0, SaveCommand.idHashMap.get(strings[0]));
                OringoClient.mc.func_147114_u().func_147298_b().func_179290_a((Packet)packet);
            }
            else if (SaveCommand.posHashMap.containsKey(strings[0])) {
                this.clickBlock(SaveCommand.posHashMap.get(strings[0]));
            }
            return;
        }
        this.clickBlock(new BlockPos(Integer.parseInt(strings[0]), Integer.parseInt(strings[1]), Integer.parseInt(strings[2])));
    }
    
    private void clickBlock(final BlockPos hitPos) {
        OringoClient.mc.func_147114_u().func_147298_b().func_179290_a((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, hitPos, EnumFacing.func_176733_a((double)OringoClient.mc.field_71439_g.field_70177_z)));
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
}
