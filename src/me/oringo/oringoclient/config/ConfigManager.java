// 
// Decompiled by Procyon v0.5.36
// 

package me.oringo.oringoclient.config;

import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.Expose;
import java.util.List;
import java.io.IOException;
import java.nio.file.OpenOption;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import com.google.gson.Gson;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.StringSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.NumberSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.ModeSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.BooleanSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.Module;
import com.google.gson.GsonBuilder;
import java.nio.file.Files;
import java.io.File;
import me.oringo.oringoclient.OringoClient;

public class ConfigManager
{
    public static void loadConfig() {
        try {
            final String configString = new String(Files.readAllBytes(new File(OringoClient.mc.field_71412_D.getPath() + "/config/OringoClient/OringoClient.json").toPath()));
            final Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
            final Module[] modules = (Module[])gson.fromJson(configString, (Class)Module[].class);
            for (final Module module : OringoClient.modules) {
                for (final Module configModule : modules) {
                    if (module.getName().equals(configModule.getName())) {
                        try {
                            module.setToggled(configModule.isToggled());
                            module.setKeycode(configModule.getKeycode());
                            for (final Setting setting : module.settings) {
                                for (final ConfigSetting cfgSetting : configModule.cfgSettings) {
                                    if (setting.name.equals(cfgSetting.name)) {
                                        if (setting instanceof BooleanSetting) {
                                            ((BooleanSetting)setting).setEnabled((boolean)cfgSetting.value);
                                        }
                                        else if (setting instanceof ModeSetting) {
                                            ((ModeSetting)setting).setSelected((String)cfgSetting.value);
                                        }
                                        else if (setting instanceof NumberSetting) {
                                            ((NumberSetting)setting).setValue((double)cfgSetting.value);
                                        }
                                        else if (setting instanceof StringSetting) {
                                            ((StringSetting)setting).setValue((String)cfgSetting.value);
                                        }
                                    }
                                }
                            }
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                            System.out.println("Config Issue");
                        }
                    }
                }
            }
        }
        catch (Exception e2) {
            e2.printStackTrace();
        }
    }
    
    public static void saveConfig() {
        final Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
        for (final Module module : OringoClient.modules) {
            module.onSave();
            final List<ConfigSetting> settings = new ArrayList<ConfigSetting>();
            for (final Setting setting : module.settings) {
                final ConfigSetting cfgSetting = new ConfigSetting(null, null);
                cfgSetting.name = setting.name;
                if (setting instanceof BooleanSetting) {
                    cfgSetting.value = ((BooleanSetting)setting).isEnabled();
                }
                else if (setting instanceof ModeSetting) {
                    cfgSetting.value = ((ModeSetting)setting).getSelected();
                }
                else if (setting instanceof NumberSetting) {
                    cfgSetting.value = ((NumberSetting)setting).getValue();
                }
                else if (setting instanceof StringSetting) {
                    cfgSetting.value = ((StringSetting)setting).getValue();
                }
                settings.add(cfgSetting);
            }
            module.cfgSettings = settings.toArray(new ConfigSetting[0]);
        }
        try {
            Files.write(new File(OringoClient.mc.field_71412_D.getPath() + "/config/OringoClient/OringoClient.json").toPath(), gson.toJson((Object)OringoClient.modules).getBytes(StandardCharsets.UTF_8), new OpenOption[0]);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static class ConfigSetting
    {
        @Expose
        @SerializedName("name")
        public String name;
        @Expose
        @SerializedName("value")
        public Object value;
        
        public ConfigSetting(final String name, final Object value) {
            this.name = name;
            this.value = value;
        }
    }
}
