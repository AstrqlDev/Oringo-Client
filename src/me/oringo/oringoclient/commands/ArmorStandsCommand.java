// 
// Decompiled by Procyon v0.5.36
// 

package me.oringo.oringoclient.commands;

import net.minecraft.util.BlockPos;
import net.minecraft.util.IChatComponent;
import net.minecraft.event.ClickEvent;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.ChatComponentText;
import net.minecraft.command.CommandException;
import java.util.function.Consumer;
import java.util.Comparator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import me.oringo.oringoclient.utils.Notifications;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.client.Minecraft;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.ICommand;

public class ArmorStandsCommand implements ICommand
{
    public String func_71517_b() {
        return "armorstands";
    }
    
    public String func_71518_a(final ICommandSender sender) {
        return "/armorstands";
    }
    
    public List<String> func_71514_a() {
        return new ArrayList<String>();
    }
    
    public void func_71515_b(final ICommandSender sender, final String[] args) throws CommandException {
        if (args.length == 1) {
            try {
                final Entity entityByID = Minecraft.func_71410_x().field_71441_e.func_73045_a(Integer.parseInt(args[0]));
                Minecraft.func_71410_x().field_71442_b.func_78768_b((EntityPlayer)Minecraft.func_71410_x().field_71439_g, entityByID);
            }
            catch (Exception e) {
                Notifications.showNotification("Oringo Client", "This armor stand is too far away!", 1000);
            }
            return;
        }
        Minecraft.func_71410_x().field_71441_e.field_72996_f.stream().filter(entity -> entity instanceof EntityArmorStand).filter(entity -> entity.func_145818_k_()).filter(entity -> entity.func_145748_c_().func_150254_d().length() > 5).sorted(Comparator.comparingDouble(entity -> entity.func_70032_d((Entity)Minecraft.func_71410_x().field_71439_g))).forEach(ArmorStandsCommand::sendEntityInteract);
    }
    
    private static void sendEntityInteract(final Entity entity) {
        final ChatComponentText chatComponentText = new ChatComponentText("Name: " + entity.func_145748_c_().func_150254_d());
        final ChatStyle style = new ChatStyle();
        style.func_150241_a(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/armorstands " + entity.func_145782_y()));
        chatComponentText.func_150255_a(style);
        Minecraft.func_71410_x().field_71439_g.func_145747_a((IChatComponent)chatComponentText);
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
