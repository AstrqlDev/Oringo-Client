// 
// Decompiled by Procyon v0.5.36
// 

package me.oringo.oringoclient.qolfeatures.module.render;

import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.BooleanSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.NumberSetting;
import me.oringo.oringoclient.qolfeatures.module.Module;

public class Camera extends Module
{
    public NumberSetting cameraDistance;
    public BooleanSetting cameraClip;
    public BooleanSetting noHurtCam;
    
    public Camera() {
        super("Camera", Category.RENDER);
        this.cameraDistance = new NumberSetting("Camera Distance", 4.0, 2.0, 10.0, 0.1);
        this.cameraClip = new BooleanSetting("Camera Clip", true);
        this.noHurtCam = new BooleanSetting("No hurt cam", false);
        this.addSettings(this.cameraDistance, this.cameraClip, this.noHurtCam);
    }
}
