// 
// Decompiled by Procyon v0.5.36
// 

package me.oringo.oringoclient.qolfeatures.module.settings;

import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.Expose;

public class Setting
{
    @Expose
    @SerializedName("name")
    public String name;
    private boolean hidden;
    
    public void setHidden(final boolean hidden) {
        this.hidden = hidden;
    }
    
    public boolean isHidden() {
        return this.hidden;
    }
}
