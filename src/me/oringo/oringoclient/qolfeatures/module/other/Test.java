// 
// Decompiled by Procyon v0.5.36
// 

package me.oringo.oringoclient.qolfeatures.module.other;

import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.NumberSetting;
import me.oringo.oringoclient.qolfeatures.module.Module;

public class Test extends Module
{
    public NumberSetting speed;
    
    public Test() {
        super("Test", Category.OTHER);
        this.speed = new NumberSetting("Speed", 1.0, 0.7, 2.0, 0.1);
        this.addSettings(this.speed);
    }
    
    @Override
    public boolean isDevOnly() {
        return true;
    }
}
