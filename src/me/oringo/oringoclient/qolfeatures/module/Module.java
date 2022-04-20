// 
// Decompiled by Procyon v0.5.36
// 

package me.oringo.oringoclient.qolfeatures.module;

import java.util.Iterator;
import me.oringo.oringoclient.OringoClient;
import org.lwjgl.input.Keyboard;
import java.util.ArrayList;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import java.util.List;
import me.oringo.oringoclient.utils.MilliTimer;
import me.oringo.oringoclient.config.ConfigManager;
import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.Expose;

public class Module
{
    @Expose
    @SerializedName("name")
    public String name;
    @Expose
    @SerializedName("toggled")
    private boolean toggled;
    @Expose
    @SerializedName("keyCode")
    private int keycode;
    private Category category;
    public boolean extended;
    @Expose
    @SerializedName("settings")
    public ConfigManager.ConfigSetting[] cfgSettings;
    private boolean devOnly;
    public MilliTimer toggledTime;
    public List<Setting> settings;
    
    public Module(final String name, final int keycode, final Category category) {
        this.toggledTime = new MilliTimer();
        this.settings = new ArrayList<Setting>();
        this.name = name;
        this.keycode = keycode;
        this.category = category;
    }
    
    public Module(final String name, final Category category) {
        this.toggledTime = new MilliTimer();
        this.settings = new ArrayList<Setting>();
        this.name = name;
        this.keycode = 0;
        this.category = category;
    }
    
    public boolean isToggled() {
        return this.toggled;
    }
    
    public void toggle() {
        this.toggled = !this.toggled;
        if (this.toggled) {
            this.onEnable();
        }
        else {
            this.onDisable();
        }
        this.toggledTime.updateTime();
    }
    
    public void onEnable() {
    }
    
    public void onSave() {
    }
    
    public boolean isKeybind() {
        return false;
    }
    
    public void addSetting(final Setting setting) {
        this.getSettings().add(setting);
    }
    
    public void addSettings(final Setting... settings) {
        for (final Setting setting : settings) {
            this.addSetting(setting);
        }
    }
    
    public Category getCategory() {
        return this.category;
    }
    
    public String getName() {
        return this.name;
    }
    
    public boolean isPressed() {
        return this.keycode != 0 && Keyboard.isKeyDown(this.keycode) && this.isKeybind();
    }
    
    public int getKeycode() {
        return this.keycode;
    }
    
    public void setKeycode(final int keycode) {
        this.keycode = keycode;
    }
    
    public List<Setting> getSettings() {
        return this.settings;
    }
    
    public static List<Module> getModulesByCategory(final Category c) {
        final List<Module> modules = new ArrayList<Module>();
        for (final Module m : OringoClient.modules) {
            if (m.getCategory() == c) {
                modules.add(m);
            }
        }
        return modules;
    }
    
    public static <T> T getModule(final Class<T> module) {
        for (final Module m : OringoClient.modules) {
            if (m.getClass().equals(module)) {
                return (T)m;
            }
        }
        return null;
    }
    
    public static Module getModuleByName(final String string) {
        for (final Module m : OringoClient.modules) {
            if (m.getName().equalsIgnoreCase(string)) {
                return m;
            }
        }
        return null;
    }
    
    public void setToggled(final boolean toggled) {
        this.toggled = toggled;
        this.toggledTime.updateTime();
    }
    
    public void onDisable() {
    }
    
    public void setDevOnly(final boolean devOnly) {
        this.devOnly = devOnly;
    }
    
    public boolean isDevOnly() {
        return this.devOnly;
    }
    
    public enum Category
    {
        COMBAT("Combat"), 
        SKYBLOCK("Skyblock"), 
        RENDER("Render"), 
        PLAYER("Player"), 
        MACRO("Macros"), 
        OTHER("Other"), 
        KEYBINDS("Keybinds");
        
        public String name;
        
        private Category(final String name) {
            this.name = name;
        }
    }
}
