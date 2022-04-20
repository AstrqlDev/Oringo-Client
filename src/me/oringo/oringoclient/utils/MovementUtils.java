// 
// Decompiled by Procyon v0.5.36
// 

package me.oringo.oringoclient.utils;

import me.oringo.oringoclient.OringoClient;

public class MovementUtils
{
    public static MilliTimer strafeTimer;
    
    public static float getSpeed() {
        return (float)Math.sqrt(OringoClient.mc.field_71439_g.field_70159_w * OringoClient.mc.field_71439_g.field_70159_w + OringoClient.mc.field_71439_g.field_70179_y * OringoClient.mc.field_71439_g.field_70179_y);
    }
    
    public static float getSpeed(final double x, final double z) {
        return (float)Math.sqrt(x * x + z * z);
    }
    
    public static void strafe(final boolean ignoreDelay) {
        strafe(getSpeed(), ignoreDelay);
    }
    
    public static boolean isMoving() {
        return OringoClient.mc.field_71439_g != null && (OringoClient.mc.field_71439_g.field_70701_bs != 0.0f || OringoClient.mc.field_71439_g.field_70702_br != 0.0f);
    }
    
    public static boolean hasMotion() {
        return OringoClient.mc.field_71439_g.field_70159_w != 0.0 && OringoClient.mc.field_71439_g.field_70179_y != 0.0 && OringoClient.mc.field_71439_g.field_70181_x != 0.0;
    }
    
    public static void strafe(final float speed, final boolean ignoreDelay) {
        if (!isMoving() || (!MovementUtils.strafeTimer.hasTimePassed(200L) && !ignoreDelay)) {
            return;
        }
        final double yaw = getDirection();
        OringoClient.mc.field_71439_g.field_70159_w = -Math.sin(yaw) * speed;
        OringoClient.mc.field_71439_g.field_70179_y = Math.cos(yaw) * speed;
        MovementUtils.strafeTimer.updateTime();
    }
    
    public static void strafe(final float speed, final float yaw) {
        if (!isMoving() || !MovementUtils.strafeTimer.hasTimePassed(150L)) {
            return;
        }
        OringoClient.mc.field_71439_g.field_70159_w = -Math.sin(Math.toRadians(yaw)) * speed;
        OringoClient.mc.field_71439_g.field_70179_y = Math.cos(Math.toRadians(yaw)) * speed;
        MovementUtils.strafeTimer.updateTime();
    }
    
    public static void forward(final double length) {
        final double yaw = Math.toRadians(OringoClient.mc.field_71439_g.field_70177_z);
        OringoClient.mc.field_71439_g.func_70107_b(OringoClient.mc.field_71439_g.field_70165_t + -Math.sin(yaw) * length, OringoClient.mc.field_71439_g.field_70163_u, OringoClient.mc.field_71439_g.field_70161_v + Math.cos(yaw) * length);
    }
    
    public static double getDirection() {
        return Math.toRadians(getYaw());
    }
    
    public static float getYaw() {
        float rotationYaw = OringoClient.mc.field_71439_g.field_70177_z;
        if (OringoClient.mc.field_71439_g.field_70701_bs < 0.0f) {
            rotationYaw += 180.0f;
        }
        float forward = 1.0f;
        if (OringoClient.mc.field_71439_g.field_70701_bs < 0.0f) {
            forward = -0.5f;
        }
        else if (OringoClient.mc.field_71439_g.field_70701_bs > 0.0f) {
            forward = 0.5f;
        }
        if (OringoClient.mc.field_71439_g.field_70702_br > 0.0f) {
            rotationYaw -= 90.0f * forward;
        }
        if (OringoClient.mc.field_71439_g.field_70702_br < 0.0f) {
            rotationYaw += 90.0f * forward;
        }
        return rotationYaw;
    }
    
    static {
        MovementUtils.strafeTimer = new MilliTimer();
    }
}
