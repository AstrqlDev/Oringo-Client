// 
// Decompiled by Procyon v0.5.36
// 

package me.oringo.oringoclient.qolfeatures.module.settings.impl;

import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.Expose;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;

public class BooleanSetting extends Setting
{
    @Expose
    @SerializedName("value")
    private boolean enabled;
    
    public BooleanSetting(final String name, final boolean enabled) {
        this.name = name;
        this.enabled = enabled;
    }
    
    public boolean isEnabled() {
        return this.enabled;
    }
    
    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }
    
    public void toggle() {
        this.setEnabled(!this.isEnabled());
    }
}
