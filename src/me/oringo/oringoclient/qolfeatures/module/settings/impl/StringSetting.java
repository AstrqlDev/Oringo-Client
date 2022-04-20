// 
// Decompiled by Procyon v0.5.36
// 

package me.oringo.oringoclient.qolfeatures.module.settings.impl;

import me.oringo.oringoclient.qolfeatures.module.settings.Setting;

public class StringSetting extends Setting
{
    private String value;
    
    public StringSetting(final String name) {
        this(name, "");
    }
    
    public StringSetting(final String name, final String defaultValue) {
        this.name = name;
        this.value = defaultValue;
    }
    
    public boolean is(final String string) {
        return this.value.equals(string);
    }
    
    public String getValue() {
        return this.value;
    }
    
    public void setValue(final String value) {
        this.value = value;
    }
}
