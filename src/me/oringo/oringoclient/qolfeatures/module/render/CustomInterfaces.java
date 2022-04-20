// 
// Decompiled by Procyon v0.5.36
// 

package me.oringo.oringoclient.qolfeatures.module.render;

import java.util.regex.Matcher;
import java.util.Iterator;
import java.util.List;
import java.util.Collection;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.client.renderer.GlStateManager;
import com.mojang.realmsclient.gui.ChatFormatting;
import java.util.regex.Pattern;
import me.oringo.oringoclient.utils.RenderUtils;
import java.awt.Color;
import me.oringo.oringoclient.utils.shader.BlurUtils;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.scoreboard.Team;
import net.minecraft.scoreboard.ScorePlayerTeam;
import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.utils.font.Fonts;
import com.google.common.collect.Lists;
import com.google.common.collect.Iterables;
import net.minecraft.scoreboard.Score;
import com.google.common.base.Predicate;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import me.oringo.oringoclient.events.ScoreboardRenderEvent;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.ModeSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.BooleanSetting;
import me.oringo.oringoclient.qolfeatures.module.Module;

public class CustomInterfaces extends Module
{
    public BooleanSetting customScoreboard;
    public BooleanSetting customFont;
    public BooleanSetting outline;
    public BooleanSetting hideLobby;
    public ModeSetting blurStrength;
    
    public CustomInterfaces() {
        super("Custom Interfaces", Category.RENDER);
        this.customScoreboard = new BooleanSetting("Custom Scoreboard", true);
        this.customFont = new BooleanSetting("Custom Font", true);
        this.outline = new BooleanSetting("Outline", false);
        this.hideLobby = new BooleanSetting("Hide lobby", true);
        this.blurStrength = new ModeSetting("Blur Strength", "Low", new String[] { "None", "Low", "High" });
        this.setToggled(true);
        this.addSettings(this.customScoreboard, this.customFont, this.outline, this.hideLobby, this.blurStrength);
    }
    
    @SubscribeEvent
    public void onDraw(final ScoreboardRenderEvent event) {
        if (!this.isToggled() || !this.customScoreboard.isEnabled()) {
            return;
        }
        event.setCanceled(true);
        this.renderScoreboard(event.objective, event.resolution, this.customFont.isEnabled());
    }
    
    private void renderScoreboard(final ScoreObjective p_180475_1_, final ScaledResolution p_180475_2_, final boolean customFont) {
        final Scoreboard scoreboard = p_180475_1_.func_96682_a();
        Collection<Score> collection = (Collection<Score>)scoreboard.func_96534_i(p_180475_1_);
        final List<Score> list = (List<Score>)Lists.newArrayList(Iterables.filter((Iterable)collection, (Predicate)new Predicate<Score>() {
            public boolean apply(final Score p_apply_1_) {
                return p_apply_1_.func_96653_e() != null && !p_apply_1_.func_96653_e().startsWith("#");
            }
        }));
        if (list.size() > 15) {
            collection = (Collection<Score>)Lists.newArrayList(Iterables.skip((Iterable)list, collection.size() - 15));
        }
        else {
            collection = list;
        }
        float width = this.getStringWidth(p_180475_1_.func_96678_d(), customFont);
        final int fontHeight = customFont ? (Fonts.fontMedium.getHeight() + 2) : OringoClient.mc.field_71466_p.field_78288_b;
        for (final Score score : collection) {
            final ScorePlayerTeam scoreplayerteam = scoreboard.func_96509_i(score.func_96653_e());
            final String s = ScorePlayerTeam.func_96667_a((Team)scoreplayerteam, score.func_96653_e()) + ": " + EnumChatFormatting.RED + score.func_96652_c();
            width = Math.max(width, this.getStringWidth(s, customFont));
        }
        final float i1 = (float)(collection.size() * fontHeight);
        final float arrayHeight = OringoClient.clickGui.getHeight();
        float j1 = p_180475_2_.func_78328_b() / 2.0f + i1 / 3.0f;
        if (OringoClient.clickGui.arrayList.isEnabled()) {
            j1 = Math.max(j1, arrayHeight + 40.0f + (collection.size() * fontHeight - fontHeight - 3));
        }
        final float k1 = 3.0f;
        final float l1 = p_180475_2_.func_78326_a() - width - k1;
        final float m = p_180475_2_.func_78326_a() - k1 + 2.0f;
        int blur = 0;
        final String selected = this.blurStrength.getSelected();
        switch (selected) {
            case "Low": {
                blur = 7;
                break;
            }
            case "High": {
                blur = 25;
                break;
            }
        }
        BlurUtils.renderBlurredBackground((float)blur, p_180475_2_.func_78326_a(), p_180475_2_.func_78328_b(), l1 - 2.0f, j1 - collection.size() * fontHeight - fontHeight - 3.0f, m - (l1 - 2.0f), (float)(fontHeight * (collection.size() + 1) + 4));
        if (this.outline.isEnabled()) {
            RenderUtils.drawBorderedRoundedRect(l1 - 2.0f, j1 - collection.size() * fontHeight - fontHeight - 3.0f, m - (l1 - 2.0f), (float)(fontHeight * (collection.size() + 1) + 4), 3.0f, 2.5f, new Color(21, 21, 21, 50).getRGB(), OringoClient.clickGui.getColor().getRGB());
        }
        else {
            RenderUtils.drawRoundRect2(l1 - 2.0f, j1 - collection.size() * fontHeight - fontHeight - 3.0f, m - (l1 - 2.0f), (float)(fontHeight * (collection.size() + 1) + 4), 3.0f, new Color(21, 21, 21, 50).getRGB());
        }
        int i2 = 0;
        for (final Score score2 : collection) {
            ++i2;
            final ScorePlayerTeam scoreplayerteam2 = scoreboard.func_96509_i(score2.func_96653_e());
            String s2 = ScorePlayerTeam.func_96667_a((Team)scoreplayerteam2, score2.func_96653_e());
            if (s2.contains("§ewww.hypixel.ne\ud83c\udf82§et")) {
                s2 = s2.replaceAll("§ewww.hypixel.ne\ud83c\udf82§et", "Oringo Client");
            }
            final float k2 = j1 - i2 * fontHeight;
            final Matcher matcher = Pattern.compile("[0-9][0-9]/[0-9][0-9]/[0-9][0-9]").matcher(s2);
            if (this.hideLobby.isEnabled() && matcher.find()) {
                s2 = ChatFormatting.GRAY + matcher.group();
            }
            final boolean flag = s2.equals("Oringo Client");
            if (flag) {
                if (customFont) {
                    Fonts.fontMediumBold.drawSmoothCenteredString(s2, l1 + width / 2.0f + 0.4f, k2 + 0.5f, new Color(20, 20, 20).getRGB());
                    Fonts.fontMediumBold.drawSmoothCenteredString(s2, l1 + width / 2.0f, k2, OringoClient.clickGui.getColor().brighter().getRGB());
                }
                else {
                    OringoClient.mc.field_71466_p.func_78276_b(s2, (int)(l1 + width / 2.0f - OringoClient.mc.field_71466_p.func_78256_a(s2) / 2), (int)k2, OringoClient.clickGui.getColor().brighter().brighter().getRGB());
                }
            }
            else {
                this.drawString(s2, l1, k2, 553648127, customFont);
            }
            if (i2 == collection.size()) {
                final String s3 = p_180475_1_.func_96678_d();
                this.drawString(s3, l1 + width / 2.0f - this.getStringWidth(s3, customFont) / 2.0f, k2 - fontHeight, Color.white.getRGB(), customFont);
            }
        }
        GlStateManager.func_179124_c(1.0f, 1.0f, 1.0f);
    }
    
    private void drawString(String s, final float x, final float y, final int color, final boolean customFont) {
        if (OringoClient.nickHider.isToggled() && s.contains(OringoClient.mc.func_110432_I().func_111285_a())) {
            s = s.replaceAll(OringoClient.mc.func_110432_I().func_111285_a(), OringoClient.nickHider.name.getValue());
        }
        if (customFont) {
            Fonts.fontMediumBold.drawSmoothString(s, x + 0.4f, y + 0.5f, new Color(20, 20, 20).getRGB(), true);
            Fonts.fontMediumBold.drawSmoothString(s, x, y, Color.white.getRGB());
        }
        else {
            OringoClient.mc.field_71466_p.func_78276_b(s, (int)x, (int)y, color);
        }
    }
    
    private float getStringWidth(final String s, final boolean customFont) {
        return customFont ? ((float)Fonts.fontMediumBold.getStringWidth(s)) : ((float)OringoClient.mc.field_71466_p.func_78256_a(s));
    }
}
