// 
// Decompiled by Procyon v0.5.36
// 

package me.oringo.oringoclient.qolfeatures.module.render;

import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.NumberSetting;
import me.oringo.oringoclient.qolfeatures.module.Module;

public class Giants extends Module
{
    public NumberSetting scale;
    
    public Giants() {
        super("Giants", Category.RENDER);
        this.scale = new NumberSetting("Scale", 1.0, 0.1, 5.0, 0.1);
        this.addSettings(this.scale);
    }
}
