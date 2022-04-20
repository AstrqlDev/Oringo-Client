// 
// Decompiled by Procyon v0.5.36
// 

package me.oringo.oringoclient.utils.font;

import java.util.HashMap;
import java.io.InputStream;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.Minecraft;
import java.awt.Font;
import java.util.Map;

public class Fonts
{
    public static MinecraftFontRenderer fontMedium;
    public static MinecraftFontRenderer fontMediumBold;
    public static MinecraftFontRenderer fontBig;
    public static MinecraftFontRenderer fontSmall;
    public static MinecraftFontRenderer openSans;
    
    private Fonts() {
    }
    
    private static Font getFont(final Map<String, Font> locationMap, final String location, final int size) {
        Font font;
        try {
            if (locationMap.containsKey(location)) {
                font = locationMap.get(location).deriveFont(0, (float)size);
            }
            else {
                final InputStream is = Minecraft.func_71410_x().func_110442_L().func_110536_a(new ResourceLocation("oringoclient", "fonts/" + location)).func_110527_b();
                font = Font.createFont(0, is);
                locationMap.put(location, font);
                font = font.deriveFont(0, (float)size);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error loading font");
            font = new Font("default", 0, size);
        }
        return font;
    }
    
    public static void bootstrap() {
        final Map<String, Font> locationMap = new HashMap<String, Font>();
        Fonts.fontSmall = new MinecraftFontRenderer(getFont(locationMap, "roboto.ttf", 17), true, false);
        Fonts.fontMedium = new MinecraftFontRenderer(getFont(locationMap, "roboto.ttf", 19), true, false);
        Fonts.fontBig = new MinecraftFontRenderer(getFont(locationMap, "robotoMedium.ttf", 21), true, false);
        Fonts.fontMediumBold = new MinecraftFontRenderer(getFont(locationMap, "robotoMedium.ttf", 19), true, false);
        Fonts.openSans = new MinecraftFontRenderer(getFont(locationMap, "OpenSans-Regular.ttf", 18), true, false);
    }
}
