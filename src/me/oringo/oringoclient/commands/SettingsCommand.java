// 
// Decompiled by Procyon v0.5.36
// 

package me.oringo.oringoclient.commands;

import net.minecraft.util.BlockPos;
import net.minecraft.command.CommandException;
import net.minecraft.scoreboard.ScorePlayerTeam;
import java.util.Iterator;
import net.minecraft.scoreboard.Scoreboard;
import java.util.Collection;
import net.minecraft.scoreboard.Score;
import net.minecraft.client.Minecraft;
import me.oringo.oringoclient.OringoClient;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.ICommand;

public class SettingsCommand implements ICommand
{
    public static boolean openSettings;
    
    public String func_71517_b() {
        return "oringo";
    }
    
    public String func_71518_a(final ICommandSender sender) {
        return "/oringo";
    }
    
    public List<String> func_71514_a() {
        return new ArrayList<String>();
    }
    
    public void func_71515_b(final ICommandSender sender, final String[] args) throws CommandException {
        if (args.length != 0 && args[0].equalsIgnoreCase("scoreboard") && OringoClient.mc.field_71439_g.func_96123_co() != null) {
            final StringBuilder builder = new StringBuilder();
            final Scoreboard sb = Minecraft.func_71410_x().field_71439_g.func_96123_co();
            final List<Score> list = new ArrayList<Score>(sb.func_96534_i(sb.func_96539_a(1)));
            for (final Score score : list) {
                final ScorePlayerTeam team = sb.func_96509_i(score.func_96653_e());
                final String s = team.func_96668_e() + score.func_96653_e() + team.func_96663_f();
                for (final char c : s.toCharArray()) {
                    if (c < '\u0100') {
                        builder.append(c);
                    }
                }
                builder.append("\n");
            }
            builder.append(OringoClient.mc.field_71439_g.func_96123_co().func_96539_a(1).func_96678_d());
            System.out.println(builder);
            return;
        }
        OringoClient.clickGui.toggle();
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
        SettingsCommand.openSettings = false;
    }
}
