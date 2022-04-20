// 
// Decompiled by Procyon v0.5.36
// 

package me.oringo.oringoclient.utils;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.Entity;
import me.oringo.oringoclient.mixins.entity.PlayerSPAccessor;
import net.minecraft.util.MathHelper;
import me.oringo.oringoclient.OringoClient;
import net.minecraft.util.Vec3;

public class RotationUtils
{
    public static float lastReportedPitch;
    
    private RotationUtils() {
    }
    
    public static float[] getAngles(final Vec3 vec) {
        final double diffX = vec.field_72450_a - OringoClient.mc.field_71439_g.field_70165_t;
        final double diffY = vec.field_72448_b - (OringoClient.mc.field_71439_g.field_70163_u + OringoClient.mc.field_71439_g.func_70047_e());
        final double diffZ = vec.field_72449_c - OringoClient.mc.field_71439_g.field_70161_v;
        final double dist = MathHelper.func_76133_a(diffX * diffX + diffZ * diffZ);
        final float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0 / 3.141592653589793) - 90.0f;
        final float pitch = (float)(-(Math.atan2(diffY, dist) * 180.0 / 3.141592653589793));
        return new float[] { OringoClient.mc.field_71439_g.field_70177_z + MathHelper.func_76142_g(yaw - OringoClient.mc.field_71439_g.field_70177_z), OringoClient.mc.field_71439_g.field_70125_A + MathHelper.func_76142_g(pitch - OringoClient.mc.field_71439_g.field_70125_A) };
    }
    
    public static float[] getServerAngles(final Vec3 vec) {
        final double diffX = vec.field_72450_a - OringoClient.mc.field_71439_g.field_70165_t;
        final double diffY = vec.field_72448_b - (OringoClient.mc.field_71439_g.field_70163_u + OringoClient.mc.field_71439_g.func_70047_e());
        final double diffZ = vec.field_72449_c - OringoClient.mc.field_71439_g.field_70161_v;
        final float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0 / 3.141592653589793) - 90.0f;
        final double dist = MathHelper.func_76133_a(diffX * diffX + diffZ * diffZ);
        final float pitch = (float)(-(Math.atan2(diffY, dist) * 180.0 / 3.141592653589793));
        return new float[] { ((PlayerSPAccessor)OringoClient.mc.field_71439_g).getLastReportedYaw() + MathHelper.func_76142_g(yaw - ((PlayerSPAccessor)OringoClient.mc.field_71439_g).getLastReportedYaw()), ((PlayerSPAccessor)OringoClient.mc.field_71439_g).getLastReportedPitch() + MathHelper.func_76142_g(pitch - ((PlayerSPAccessor)OringoClient.mc.field_71439_g).getLastReportedPitch()) };
    }
    
    public static float[] getBowAngles(final Entity entity) {
        final double xDelta = (entity.field_70165_t - entity.field_70142_S) * 0.4;
        final double zDelta = (entity.field_70161_v - entity.field_70136_U) * 0.4;
        double d = OringoClient.mc.field_71439_g.func_70032_d(entity);
        d -= d % 0.8;
        final double xMulti = d / 0.8 * xDelta;
        final double zMulti = d / 0.8 * zDelta;
        final double x = entity.field_70165_t + xMulti - OringoClient.mc.field_71439_g.field_70165_t;
        final double z = entity.field_70161_v + zMulti - OringoClient.mc.field_71439_g.field_70161_v;
        final double y = OringoClient.mc.field_71439_g.field_70163_u + OringoClient.mc.field_71439_g.func_70047_e() - (entity.field_70163_u + entity.func_70047_e());
        final double dist = OringoClient.mc.field_71439_g.func_70032_d(entity);
        final float yaw = (float)Math.toDegrees(Math.atan2(z, x)) - 90.0f;
        final double d2 = MathHelper.func_76133_a(x * x + z * z);
        final float pitch = (float)(-(Math.atan2(y, d2) * 180.0 / 3.141592653589793)) + (float)dist * 0.11f;
        return new float[] { yaw, -pitch };
    }
    
    public static boolean isWithinFOV(final EntityLivingBase entity, final double fov) {
        final float yawDifference = Math.abs(getAngles((Entity)entity)[0] - OringoClient.mc.field_71439_g.field_70177_z);
        return yawDifference < fov && yawDifference > -fov;
    }
    
    public static float getYawDifference(final EntityLivingBase entity1, final EntityLivingBase entity2) {
        return Math.abs(getAngles((Entity)entity1)[0] - getAngles((Entity)entity2)[0]);
    }
    
    public static float getYawDifference(final EntityLivingBase entity1) {
        return Math.abs(OringoClient.mc.field_71439_g.field_70177_z - getAngles((Entity)entity1)[0]);
    }
    
    public static boolean isWithinPitch(final EntityLivingBase entity, final double pitch) {
        final float pitchDifference = Math.abs(getAngles((Entity)entity)[1] - OringoClient.mc.field_71439_g.field_70125_A);
        return pitchDifference < pitch && pitchDifference > -pitch;
    }
    
    public static float[] getAngles(final Entity en) {
        return getAngles(new Vec3(en.field_70165_t, en.field_70163_u + (en.func_70047_e() - en.field_70131_O / 1.5) + 0.5, en.field_70161_v));
    }
    
    public static float[] getServerAngles(final Entity en) {
        return getServerAngles(new Vec3(en.field_70165_t, en.field_70163_u + (en.func_70047_e() - en.field_70131_O / 1.5) + 0.5, en.field_70161_v));
    }
}
