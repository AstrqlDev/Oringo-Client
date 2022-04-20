// 
// Decompiled by Procyon v0.5.36
// 

package me.oringo.oringoclient.qolfeatures.module.other;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import me.oringo.oringoclient.utils.Notifications;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.entity.player.EntityPlayer;
import me.oringo.oringoclient.utils.SkyblockUtils;
import me.oringo.oringoclient.OringoClient;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import com.google.gson.JsonObject;
import org.apache.commons.io.IOUtils;
import java.nio.charset.Charset;
import com.google.gson.JsonParser;
import java.net.URL;
import java.net.HttpURLConnection;
import java.util.UUID;
import net.minecraft.entity.Entity;
import java.util.ArrayList;
import me.oringo.oringoclient.qolfeatures.module.Module;

public class AntiNicker extends Module
{
    public static ArrayList<Entity> nicked;
    private ArrayList<UUID> checked;
    
    public AntiNicker() {
        super("Anti Nicker", 0, Category.OTHER);
        this.checked = new ArrayList<UUID>();
    }
    
    public static boolean isBot(final String name) {
        try {
            final HttpURLConnection connection = (HttpURLConnection)new URL(String.format("https://api.sk1er.club/levelheadv5/%s/LEVEL", name)).openConnection();
            connection.setRequestMethod("GET");
            connection.setUseCaches(true);
            connection.addRequestProperty("User-Agent", "Mozilla/4.76 (SK1ER LEVEL HEAD V7.0.2)");
            connection.setReadTimeout(150000);
            connection.setConnectTimeout(150000);
            connection.setDoOutput(true);
            final JsonObject jo = new JsonParser().parse(IOUtils.toString(connection.getInputStream(), Charset.defaultCharset())).getAsJsonObject();
            return jo.get("bot") != null;
        }
        catch (Exception exception) {
            return true;
        }
    }
    
    @SubscribeEvent
    public void onWorldJoin(final EntityJoinWorldEvent e) {
        if (e.entity.equals((Object)OringoClient.mc.field_71439_g)) {
            AntiNicker.nicked.clear();
        }
        if (!this.isToggled()) {
            return;
        }
        if (!this.checked.contains(e.entity.func_110124_au()) && (SkyblockUtils.onSkyblock || SkyblockUtils.isInOtherGame) && e.entity instanceof EntityPlayer && !e.entity.equals((Object)OringoClient.mc.field_71439_g) && !e.entity.func_145748_c_().func_150254_d().replaceAll(ChatFormatting.RED.toString(), "").replaceAll(ChatFormatting.RESET.toString(), "").equals(e.entity.func_70005_c_()) && !e.entity.func_145748_c_().func_150254_d().contains(ChatFormatting.RED.toString()) && !ChatFormatting.stripFormatting(e.entity.func_145748_c_().func_150260_c()).startsWith("[NPC]")) {
            this.checked.add(e.entity.func_110124_au());
            new Thread(() -> {
                try {
                    Thread.sleep(1000L);
                }
                catch (InterruptedException ex) {}
                if (isBot(e.entity.func_110124_au().toString()) && !AntiNicker.nicked.contains(e.entity)) {
                    AntiNicker.nicked.add(e.entity);
                    Notifications.showNotification("Oringo Client", (e.entity.func_145748_c_().func_150260_c().contains(ChatFormatting.OBFUSCATED.toString()) ? e.entity.func_70005_c_() : e.entity.func_145748_c_().func_150260_c()) + ChatFormatting.RESET + " is a nicker!", 4000);
                }
            }).start();
        }
    }
    
    static {
        AntiNicker.nicked = new ArrayList<Entity>();
    }
}
