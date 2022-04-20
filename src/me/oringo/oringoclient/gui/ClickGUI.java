// 
// Decompiled by Procyon v0.5.36
// 

package me.oringo.oringoclient.gui;

import java.util.Iterator;
import net.minecraftforge.common.MinecraftForge;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.RunnableSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.BooleanSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.ModeSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.utils.RenderUtils;
import java.awt.Color;
import java.util.Collections;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.Comparator;
import me.oringo.oringoclient.utils.font.Fonts;
import java.util.List;
import me.oringo.oringoclient.utils.shader.BlurUtils;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.opengl.GL11;
import java.io.IOException;
import net.minecraft.util.ChatAllowedCharacters;
import org.lwjgl.input.Keyboard;
import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.qolfeatures.module.keybinds.Keybind;
import me.oringo.oringoclient.config.ConfigManager;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.StringSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.NumberSetting;
import me.oringo.oringoclient.qolfeatures.module.Module;
import java.util.ArrayList;
import net.minecraft.client.gui.GuiScreen;

public class ClickGUI extends GuiScreen
{
    public static ArrayList<Panel> panels;
    private Module binding;
    private Panel draggingPanel;
    private NumberSetting draggingSlider;
    private StringSetting settingString;
    private float startX;
    private float startY;
    public int guiScale;
    private static int background;
    
    public ClickGUI() {
        this.binding = null;
        ClickGUI.panels = new ArrayList<Panel>();
        final int pwidth = 100;
        final int pheight = 20;
        int px = 100;
        final int py = 10;
        for (final Module.Category c : Module.Category.values()) {
            ClickGUI.panels.add(new Panel(c, (float)px, (float)py, (float)pwidth, (float)pheight));
            px += pwidth + 10;
        }
    }
    
    protected void func_73864_a(final int mouseX, final int mouseY, final int mouseButton) {
        this.draggingSlider = null;
        this.draggingPanel = null;
        this.settingString = null;
        this.binding = null;
        this.handle(mouseX, mouseY, mouseButton, 0.0f, Handle.CLICK);
    }
    
    protected void func_146286_b(final int mouseX, final int mouseY, final int state) {
        ConfigManager.saveConfig();
        Keybind.saveKeybinds();
        this.handle(mouseX, mouseY, state, 0.0f, Handle.RELEASE);
        super.func_146286_b(mouseX, mouseY, state);
    }
    
    public void func_73863_a(final int mouseX, final int mouseY, final float partialTicks) {
        this.handle(mouseX, mouseY, -1, partialTicks, Handle.DRAW);
        super.func_73863_a(mouseX, mouseY, partialTicks);
    }
    
    protected void func_73869_a(final char typedChar, final int keyCode) throws IOException {
        ConfigManager.saveConfig();
        Keybind.saveKeybinds();
        if (keyCode == 1 || keyCode == OringoClient.clickGui.getKeycode()) {
            if (this.binding != null) {
                this.binding.setKeycode(0);
                this.binding = null;
            }
            else if (this.settingString != null) {
                this.settingString = null;
            }
            else {
                this.draggingPanel = null;
                this.draggingSlider = null;
                super.func_73869_a(typedChar, keyCode);
            }
        }
        else if (this.binding != null) {
            this.binding.setKeycode(keyCode);
            this.binding = null;
        }
        else if (this.settingString != null) {
            if (keyCode == 28) {
                this.settingString = null;
            }
            else if (keyCode == 47 && (Keyboard.isKeyDown(157) || Keyboard.isKeyDown(29))) {
                this.settingString.setValue(ChatAllowedCharacters.func_71565_a(this.settingString.getValue() + func_146277_j()));
            }
            else if (keyCode != 14) {
                this.settingString.setValue(ChatAllowedCharacters.func_71565_a(this.settingString.getValue() + typedChar));
            }
            else {
                this.settingString.setValue(this.settingString.getValue().substring(0, Math.max(0, this.settingString.getValue().length() - 1)));
            }
        }
    }
    
    private void handle(int mouseX, int mouseY, final int key, final float partialTicks, final Handle handle) {
        final int toggled = OringoClient.clickGui.getColor().getRGB();
        final float scale = 2.0f / this.field_146297_k.field_71474_y.field_74335_Z;
        final int prevScale = this.field_146297_k.field_71474_y.field_74335_Z;
        GL11.glPushMatrix();
        if (this.field_146297_k.field_71474_y.field_74335_Z > 2 && !OringoClient.clickGui.scaleGui.isEnabled()) {
            this.field_146297_k.field_71474_y.field_74335_Z = 2;
            mouseX /= (int)scale;
            mouseY /= (int)scale;
            GL11.glScaled((double)scale, (double)scale, (double)scale);
        }
        if (handle == Handle.DRAW) {
            int blur = 0;
            final String selected = OringoClient.clickGui.blur.getSelected();
            switch (selected) {
                case "Low": {
                    blur = 4;
                    break;
                }
                case "High": {
                    blur = 7;
                    break;
                }
            }
            final ScaledResolution resolution = new ScaledResolution(this.field_146297_k);
            BlurUtils.renderBlurredBackground((float)blur, resolution.func_78326_a(), resolution.func_78328_b(), 0.0f, 0.0f, (float)resolution.func_78326_a(), (float)resolution.func_78328_b());
        }
        for (final Panel p : ClickGUI.panels) {
            switch (handle) {
                case DRAW: {
                    if (this.draggingPanel == p) {
                        p.x = this.startX + mouseX;
                        p.y = this.startY + mouseY;
                    }
                }
                case CLICK: {
                    if (!this.isHovered(mouseX, mouseY, p.x, p.y, p.height, p.width)) {
                        break;
                    }
                    if (key == 1) {
                        this.startX = p.x - mouseX;
                        this.startY = p.y - mouseY;
                        this.draggingPanel = p;
                        this.draggingSlider = null;
                        break;
                    }
                    if (key == 0) {
                        p.extended = !p.extended;
                        break;
                    }
                    break;
                }
                case RELEASE: {
                    this.draggingPanel = null;
                    this.draggingSlider = null;
                    break;
                }
            }
            float y = p.y + p.height + 3.0f;
            if (p.extended) {
                final int moduleHeight = 15;
                final List<Module> list = Module.getModulesByCategory(p.category).stream().sorted(Comparator.comparing(module -> Fonts.fontMediumBold.getStringWidth(module.getName()))).collect((Collector<? super Object, ?, List<Module>>)Collectors.toList());
                Collections.reverse(list);
                for (final Module module2 : list) {
                    if (module2.isDevOnly() && !OringoClient.devMode) {
                        continue;
                    }
                    Label_0834: {
                        switch (handle) {
                            case DRAW: {
                                RenderUtils.drawRect(p.x, y, p.x + p.width, y + moduleHeight, module2.isToggled() ? new Color(toggled).getRGB() : ClickGUI.background);
                                Fonts.fontMediumBold.drawSmoothCenteredString(module2.getName(), p.x + p.width / 2.0f, y + moduleHeight / 2.0f - Fonts.fontMediumBold.getHeight() / 2.0f, Color.white.getRGB());
                                break;
                            }
                            case CLICK: {
                                if (!this.isHovered(mouseX, mouseY, p.x, y, moduleHeight, p.width)) {
                                    break;
                                }
                                switch (key) {
                                    case 1: {
                                        module2.extended = !module2.extended;
                                        break Label_0834;
                                    }
                                    case 0: {
                                        module2.toggle();
                                        break Label_0834;
                                    }
                                }
                                break;
                            }
                        }
                    }
                    y += moduleHeight;
                    if (!module2.extended) {
                        continue;
                    }
                    for (final Setting setting : module2.settings) {
                        if (setting.isHidden()) {
                            continue;
                        }
                        if (setting instanceof ModeSetting) {
                            switch (handle) {
                                case DRAW: {
                                    RenderUtils.drawRect(p.x, y, p.x + p.width, y + moduleHeight, new Color(ClickGUI.background).brighter().getRGB());
                                    Fonts.fontMediumBold.drawSmoothString(setting.name, p.x + 2.0f, y + moduleHeight / 2.0f - Fonts.fontMediumBold.getHeight() / 2.0f, Color.white.getRGB());
                                    Fonts.fontMediumBold.drawSmoothString(((ModeSetting)setting).getSelected(), p.x + p.width - Fonts.fontMediumBold.getStringWidth(((ModeSetting)setting).getSelected()) - 2.0, y + moduleHeight / 2.0f - Fonts.fontMediumBold.getHeight() / 2.0f, new Color(143, 143, 143, 255).getRGB());
                                    break;
                                }
                                case CLICK: {
                                    if (!this.isHovered(mouseX, mouseY, p.x + p.width - Fonts.fontMediumBold.getStringWidth(((ModeSetting)setting).getSelected()) - 2.0, y, moduleHeight, Fonts.fontMediumBold.getStringWidth(((ModeSetting)setting).getSelected()))) {
                                        break;
                                    }
                                    ((ModeSetting)setting).cycle(key);
                                    break;
                                }
                            }
                        }
                        else if (setting instanceof NumberSetting) {
                            final float percent = (float)((((NumberSetting)setting).getValue() - ((NumberSetting)setting).getMin()) / (((NumberSetting)setting).getMax() - ((NumberSetting)setting).getMin()));
                            final float numberWidth = percent * p.width;
                            if (this.draggingSlider != null && this.draggingSlider == setting) {
                                final double mousePercent = Math.min(1.0f, Math.max(0.0f, (mouseX - p.x) / p.width));
                                final double newValue = mousePercent * (((NumberSetting)setting).getMax() - ((NumberSetting)setting).getMin()) + ((NumberSetting)setting).getMin();
                                ((NumberSetting)setting).setValue(newValue);
                            }
                            switch (handle) {
                                case DRAW: {
                                    RenderUtils.drawRect(p.x, y, p.x + p.width, y + moduleHeight, new Color(ClickGUI.background).brighter().brighter().getRGB());
                                    RenderUtils.drawRect(p.x, y, p.x + numberWidth, y + moduleHeight, new Color(toggled).brighter().getRGB());
                                    Fonts.fontMediumBold.drawSmoothString(setting.name, p.x + 2.0f, y + moduleHeight / 2.0f - Fonts.fontMediumBold.getHeight() / 2.0f, Color.white.getRGB());
                                    Fonts.fontMediumBold.drawSmoothString(String.valueOf(((NumberSetting)setting).getValue()), p.x + p.width - 2.0f - Fonts.fontMediumBold.getStringWidth(String.valueOf(((NumberSetting)setting).getValue())), y + moduleHeight / 2.0f - Fonts.fontBig.getHeight() / 2.0f, Color.white.getRGB());
                                    break;
                                }
                                case CLICK: {
                                    if (!this.isHovered(mouseX, mouseY, p.x, y, moduleHeight, p.width)) {
                                        break;
                                    }
                                    this.draggingSlider = (NumberSetting)setting;
                                    this.draggingPanel = null;
                                    break;
                                }
                            }
                        }
                        else if (setting instanceof BooleanSetting) {
                            switch (handle) {
                                case DRAW: {
                                    RenderUtils.drawRect(p.x, y, p.x + p.width, y + moduleHeight, new Color(ClickGUI.background).brighter().getRGB());
                                    RenderUtils.drawBorderedRoundedRect(p.x + p.width - 12.0f, y + moduleHeight / 2.0f - 4.0f, 8.0f, 8.0f, 3.0f, 3.0f, ((BooleanSetting)setting).isEnabled() ? new Color(toggled).brighter().getRGB() : new Color(ClickGUI.background).brighter().getRGB(), new Color(toggled).brighter().getRGB());
                                    Fonts.fontMediumBold.drawSmoothString(setting.name, p.x + 2.0f, y + moduleHeight / 2.0f - Fonts.fontMediumBold.getHeight() / 2.0f, Color.white.getRGB());
                                    break;
                                }
                                case CLICK: {
                                    if (!this.isHovered(mouseX, mouseY, p.x + p.width - 12.0f, y + moduleHeight / 2.0f - 4.0f, 8.0, 8.0)) {
                                        break;
                                    }
                                    ((BooleanSetting)setting).toggle();
                                    break;
                                }
                            }
                        }
                        else if (setting instanceof StringSetting) {
                            final String value = (this.settingString == setting) ? (((StringSetting)setting).getValue() + "_") : ((((StringSetting)setting).getValue() == null || ((StringSetting)setting).getValue().equals("")) ? (setting.name + "...") : Fonts.fontMediumBold.trimStringToWidth(((StringSetting)setting).getValue(), (int)p.width));
                            Label_2303: {
                                switch (handle) {
                                    case DRAW: {
                                        RenderUtils.drawRect(p.x, y, p.x + p.width, y + moduleHeight, new Color(ClickGUI.background).brighter().getRGB());
                                        Fonts.fontMediumBold.drawCenteredString(value, p.x + p.width / 2.0f, y + moduleHeight / 2.0f - Fonts.fontMediumBold.getHeight() / 2.0f, (((StringSetting)setting).getValue() == null || (((StringSetting)setting).getValue().equals("") && this.settingString != setting)) ? new Color(143, 143, 143, 255).getRGB() : Color.white.getRGB());
                                        break;
                                    }
                                    case CLICK: {
                                        if (!this.isHovered(mouseX, mouseY, p.x, y, moduleHeight, p.width)) {
                                            break;
                                        }
                                        switch (key) {
                                            case 0: {
                                                this.settingString = (StringSetting)setting;
                                                break Label_2303;
                                            }
                                            case 1: {
                                                ((StringSetting)setting).setValue("");
                                                break Label_2303;
                                            }
                                        }
                                        break;
                                    }
                                }
                            }
                        }
                        else if (setting instanceof RunnableSetting) {
                            switch (handle) {
                                case CLICK: {
                                    if (!this.isHovered(mouseX, mouseY, p.x, y, moduleHeight, p.width)) {
                                        break;
                                    }
                                    ((RunnableSetting)setting).execute();
                                    break;
                                }
                                case DRAW: {
                                    RenderUtils.drawRect(p.x, y, p.x + p.width, y + moduleHeight, new Color(ClickGUI.background).brighter().getRGB());
                                    Fonts.fontMediumBold.drawCenteredString(setting.name, p.x + p.width / 2.0f, y + moduleHeight / 2.0f - Fonts.fontMediumBold.getHeight() / 2.0f, Color.white.getRGB());
                                    break;
                                }
                            }
                        }
                        y += moduleHeight;
                    }
                    final String keyText = (this.binding == module2) ? "[...]" : ("[" + ((module2.getKeycode() >= 256) ? "  " : Keyboard.getKeyName(module2.getKeycode()).replaceAll("NONE", "  ")) + "]");
                    Label_2859: {
                        switch (handle) {
                            case DRAW: {
                                RenderUtils.drawRect(p.x, y, p.x + p.width, y + moduleHeight, new Color(ClickGUI.background).brighter().getRGB());
                                Fonts.fontMediumBold.drawSmoothString("Key", p.x + 2.0f, y + moduleHeight / 2.0f - Fonts.fontMediumBold.getHeight() / 2.0f, Color.white.getRGB());
                                Fonts.fontMediumBold.drawSmoothString(keyText, p.x + p.width - Fonts.fontMediumBold.getStringWidth(keyText) - 2.0, y + moduleHeight / 2.0f - Fonts.fontMediumBold.getHeight() / 2.0f, new Color(143, 143, 143, 255).getRGB());
                                break;
                            }
                            case CLICK: {
                                if (!this.isHovered(mouseX, mouseY, p.x + p.width - Fonts.fontMediumBold.getStringWidth(keyText) - 2.0, y, moduleHeight, Fonts.fontMediumBold.getStringWidth(keyText))) {
                                    break;
                                }
                                switch (key) {
                                    case 0: {
                                        this.binding = module2;
                                        break Label_2859;
                                    }
                                    case 1: {
                                        module2.setKeycode(0);
                                        break Label_2859;
                                    }
                                }
                                break;
                            }
                        }
                    }
                    y += moduleHeight;
                }
            }
            if (p.category == Module.Category.KEYBINDS && p.extended) {
                switch (handle) {
                    case CLICK: {
                        if (!this.isHovered(mouseX, mouseY, p.x, y, 15.0, p.width)) {
                            break;
                        }
                        final Keybind keybind = new Keybind("Keybind " + (Module.getModulesByCategory(Module.Category.KEYBINDS).size() + 1));
                        OringoClient.modules.add(keybind);
                        MinecraftForge.EVENT_BUS.register((Object)keybind);
                        keybind.setToggled(true);
                        break;
                    }
                    case DRAW: {
                        RenderUtils.drawRect(p.x, y, p.x + p.width, y + 15.0f, toggled);
                        Fonts.fontMediumBold.drawSmoothCenteredString("Add new keybind", p.x + p.width / 2.0f, y + 7.0f - Fonts.fontMediumBold.getHeight() / 2, Color.white.getRGB());
                        break;
                    }
                }
                y += 15.0f;
            }
            if (handle == Handle.DRAW) {
                if (p.extended) {
                    RenderUtils.drawRect(p.x, p.y + 3.0f, p.x + p.width, p.y + p.height + 3.0f, new Color(toggled).getRGB());
                    for (int i = 1; i < 4; ++i) {
                        RenderUtils.drawRect(p.x, p.y + i, p.x + p.width, p.y + p.height + i, new Color(0, 0, 0, 40).getRGB());
                    }
                }
                else {
                    y = p.y;
                }
                RenderUtils.drawRect(p.x, p.y, p.x + p.width, p.y + p.height, new Color(toggled).darker().getRGB());
                Fonts.fontBig.drawSmoothCenteredString(p.category.name, p.x + p.width / 2.0f, p.y + p.height / 2.0f - Fonts.fontBig.getHeight() / 2.0f, Color.white.getRGB());
                RenderUtils.drawRect(p.x, y, p.x + p.width, y + 5.0f, new Color(toggled).darker().getRGB());
            }
        }
        GL11.glPopMatrix();
        this.field_146297_k.field_71474_y.field_74335_Z = prevScale;
    }
    
    public void func_146281_b() {
        this.draggingPanel = null;
        this.draggingSlider = null;
        this.binding = null;
        this.settingString = null;
        super.func_146281_b();
    }
    
    public boolean isHovered(final int mouseX, final int mouseY, final double x, final double y, final double height, final double width) {
        return mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height;
    }
    
    public boolean func_73868_f() {
        return false;
    }
    
    static {
        ClickGUI.background = Color.getHSBColor(0.0f, 0.0f, 0.1f).getRGB();
    }
    
    enum Handle
    {
        DRAW, 
        CLICK, 
        RELEASE;
    }
    
    public class Panel
    {
        public Module.Category category;
        public float x;
        public float y;
        public float width;
        public float height;
        public boolean dragging;
        public boolean extended;
        
        public Panel(final Module.Category category, final float x, final float y, final float width, final float height) {
            this.category = category;
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.extended = true;
            this.dragging = false;
        }
    }
}
