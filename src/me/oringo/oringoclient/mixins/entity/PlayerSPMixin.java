// 
// Decompiled by Procyon v0.5.36
// 

package me.oringo.oringoclient.mixins.entity;

import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.stats.AchievementList;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.DamageSource;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import me.oringo.oringoclient.events.MoveEvent;
import me.oringo.oringoclient.events.PlayerUpdateEvent;
import net.minecraft.item.EnumAction;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.minecraft.stats.StatList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.common.ForgeHooks;
import net.minecraft.util.MathHelper;
import me.oringo.oringoclient.qolfeatures.module.combat.KillAura;
import me.oringo.oringoclient.utils.MovementUtils;
import me.oringo.oringoclient.OringoClient;
import net.minecraft.potion.Potion;
import org.spongepowered.asm.mixin.Overwrite;
import me.oringo.oringoclient.utils.RotationUtils;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.common.MinecraftForge;
import me.oringo.oringoclient.events.MotionUpdateEvent;
import net.minecraft.stats.StatBase;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Final;
import net.minecraft.client.network.NetHandlerPlayClient;
import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.util.MovementInput;
import net.minecraft.client.entity.EntityPlayerSP;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = { EntityPlayerSP.class }, priority = 1)
public abstract class PlayerSPMixin extends AbstractClientPlayerMixin
{
    @Shadow
    public MovementInput field_71158_b;
    @Shadow
    @Final
    public NetHandlerPlayClient field_71174_a;
    @Shadow
    public float field_71086_bY;
    @Shadow
    public float field_71164_i;
    @Shadow
    public float field_71163_h;
    @Shadow
    public float field_71155_g;
    @Shadow
    public float field_71154_f;
    @Shadow
    private boolean field_175171_bO;
    @Shadow
    private float field_175165_bM;
    @Shadow
    private double field_175172_bI;
    @Shadow
    private double field_175166_bJ;
    @Shadow
    private double field_175167_bK;
    @Shadow
    private float field_175164_bL;
    @Shadow
    private int field_175168_bP;
    @Shadow
    private boolean field_175170_bN;
    
    @Shadow
    public abstract void func_70031_b(final boolean p0);
    
    @Shadow
    public abstract boolean func_70093_af();
    
    @Shadow
    public abstract void func_71009_b(final Entity p0);
    
    @Shadow
    public abstract void func_71047_c(final Entity p0);
    
    @Shadow
    public abstract void func_71064_a(final StatBase p0, final int p1);
    
    @Shadow
    protected abstract boolean func_175160_A();
    
    @Shadow
    public abstract void func_85030_a(final String p0, final float p1, final float p2);
    
    @Overwrite
    public void func_175161_p() {
        final MotionUpdateEvent event = new MotionUpdateEvent.Pre(this.field_70165_t, this.func_174813_aQ().field_72338_b, this.field_70161_v, this.field_70177_z, this.field_70125_A, this.field_70122_E, this.func_70051_ag(), this.func_70093_af());
        if (MinecraftForge.EVENT_BUS.post((Event)event)) {
            return;
        }
        final boolean flag = event.sprinting;
        if (flag != this.field_175171_bO) {
            if (flag) {
                this.field_71174_a.func_147297_a((Packet)new C0BPacketEntityAction((Entity)this, C0BPacketEntityAction.Action.START_SPRINTING));
            }
            else {
                this.field_71174_a.func_147297_a((Packet)new C0BPacketEntityAction((Entity)this, C0BPacketEntityAction.Action.STOP_SPRINTING));
            }
            this.field_175171_bO = flag;
        }
        final boolean flag2 = event.sneaking;
        if (flag2 != this.field_175170_bN) {
            if (flag2) {
                this.field_71174_a.func_147297_a((Packet)new C0BPacketEntityAction((Entity)this, C0BPacketEntityAction.Action.START_SNEAKING));
            }
            else {
                this.field_71174_a.func_147297_a((Packet)new C0BPacketEntityAction((Entity)this, C0BPacketEntityAction.Action.STOP_SNEAKING));
            }
            this.field_175170_bN = flag2;
        }
        if (this.func_175160_A()) {
            final double d0 = event.x - this.field_175172_bI;
            final double d2 = event.y - this.field_175166_bJ;
            final double d3 = event.z - this.field_175167_bK;
            final double d4 = event.yaw - this.field_175164_bL;
            final double d5 = event.pitch - this.field_175165_bM;
            boolean flag3 = d0 * d0 + d2 * d2 + d3 * d3 > 9.0E-4 || this.field_175168_bP >= 20;
            final boolean flag4 = d4 != 0.0 || d5 != 0.0;
            if (this.field_70154_o == null) {
                if (flag3 && flag4) {
                    this.field_71174_a.func_147297_a((Packet)new C03PacketPlayer.C06PacketPlayerPosLook(event.x, event.y, event.z, event.yaw, event.pitch, event.onGround));
                }
                else if (flag3) {
                    this.field_71174_a.func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(event.x, event.y, event.z, event.onGround));
                }
                else if (flag4) {
                    this.field_71174_a.func_147297_a((Packet)new C03PacketPlayer.C05PacketPlayerLook(event.yaw, event.pitch, event.onGround));
                }
                else {
                    this.field_71174_a.func_147297_a((Packet)new C03PacketPlayer(event.onGround));
                }
            }
            else {
                this.field_71174_a.func_147297_a((Packet)new C03PacketPlayer.C06PacketPlayerPosLook(this.field_70159_w, -999.0, this.field_70179_y, event.yaw, event.pitch, event.onGround));
                flag3 = false;
            }
            ++this.field_175168_bP;
            if (flag3) {
                this.field_175172_bI = event.x;
                this.field_175166_bJ = event.y;
                this.field_175167_bK = event.z;
                this.field_175168_bP = 0;
            }
            RotationUtils.lastReportedPitch = this.field_175165_bM;
            if (flag4) {
                this.field_175164_bL = event.yaw;
                this.field_175165_bM = event.pitch;
            }
        }
        MinecraftForge.EVENT_BUS.post((Event)new MotionUpdateEvent.Post(event));
    }
    
    public void func_70664_aZ() {
        this.field_70181_x = this.func_175134_bD();
        if (this.func_82165_m(Potion.field_76430_j.field_76415_H)) {
            this.field_70181_x += (this.func_70660_b(Potion.field_76430_j).func_76458_c() + 1) * 0.1f;
        }
        if (this.func_70051_ag()) {
            final float f = ((OringoClient.sprint.isToggled() && OringoClient.sprint.omni.isEnabled()) ? MovementUtils.getYaw() : ((OringoClient.killAura.isToggled() && KillAura.target != null && OringoClient.killAura.movementFix.isEnabled()) ? RotationUtils.getAngles((Entity)KillAura.target)[0] : this.field_70177_z)) * 0.017453292f;
            this.field_70159_w -= MathHelper.func_76126_a(f) * 0.2f;
            this.field_70179_y += MathHelper.func_76134_b(f) * 0.2f;
        }
        this.field_70160_al = true;
        ForgeHooks.onLivingJump((EntityLivingBase)this);
        this.func_71029_a(StatList.field_75953_u);
        if (this.func_70051_ag()) {
            this.func_71020_j(0.8f);
        }
        else {
            this.func_71020_j(0.2f);
        }
    }
    
    @Override
    public void func_70060_a(float strafe, float forward, final float friction) {
        float f = strafe * strafe + forward * forward;
        if (f >= 1.0E-4f) {
            f = MathHelper.func_76129_c(f);
            if (f < 1.0f) {
                f = 1.0f;
            }
            f = friction / f;
            strafe *= f;
            forward *= f;
            final float yaw = (OringoClient.killAura.isToggled() && KillAura.target != null && OringoClient.killAura.movementFix.isEnabled()) ? RotationUtils.getAngles((Entity)KillAura.target)[0] : this.field_70177_z;
            final float f2 = MathHelper.func_76126_a(yaw * 3.1415927f / 180.0f);
            final float f3 = MathHelper.func_76134_b(yaw * 3.1415927f / 180.0f);
            this.field_70159_w += strafe * f3 - forward * f2;
            this.field_70179_y += forward * f3 + strafe * f2;
        }
    }
    
    @Inject(method = { "pushOutOfBlocks" }, at = { @At("HEAD") }, cancellable = true)
    public void pushOutOfBlocks(final double d2, final double f, final double blockpos, final CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(false);
    }
    
    @Redirect(method = { "onLivingUpdate" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityPlayerSP;isUsingItem()Z"))
    public boolean isUsingItem(final EntityPlayerSP instance) {
        return (!OringoClient.noSlow.isToggled() || !instance.func_71039_bw()) && instance.func_71039_bw();
    }
    
    @Override
    public boolean func_70094_T() {
        return false;
    }
    
    @Inject(method = { "onLivingUpdate" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/AbstractClientPlayer;onLivingUpdate()V") }, cancellable = true)
    public void onLivingUpdate(final CallbackInfo ci) {
        if (OringoClient.sprint.omni.isEnabled() && OringoClient.sprint.isToggled()) {
            if (!MovementUtils.isMoving() || this.func_70093_af() || (this.func_71024_bL().func_75116_a() <= 6.0f && !this.field_71075_bZ.field_75101_c)) {
                if (this.func_70051_ag()) {
                    this.func_70031_b(false);
                }
                return;
            }
            if (!this.func_70051_ag()) {
                this.func_70031_b(true);
            }
        }
        if (OringoClient.noSlow.isToggled() && this.func_71039_bw()) {
            if (this.func_71011_bu().func_77973_b().func_77661_b(this.func_71011_bu()) == EnumAction.BLOCK) {
                final MovementInput field_71158_b = this.field_71158_b;
                field_71158_b.field_78900_b *= (float)OringoClient.noSlow.swordSlowdown.getValue();
                final MovementInput field_71158_b2 = this.field_71158_b;
                field_71158_b2.field_78902_a *= (float)OringoClient.noSlow.swordSlowdown.getValue();
            }
            else if (this.func_71011_bu().func_77973_b().func_77661_b(this.func_71011_bu()) == EnumAction.BOW) {
                final MovementInput field_71158_b3 = this.field_71158_b;
                field_71158_b3.field_78900_b *= (float)OringoClient.noSlow.bowSlowdown.getValue();
                final MovementInput field_71158_b4 = this.field_71158_b;
                field_71158_b4.field_78902_a *= (float)OringoClient.noSlow.bowSlowdown.getValue();
            }
            else if (this.func_71011_bu().func_77973_b().func_77661_b(this.func_71011_bu()) != EnumAction.NONE) {
                final MovementInput field_71158_b5 = this.field_71158_b;
                field_71158_b5.field_78900_b *= (float)OringoClient.noSlow.eatingSlowdown.getValue();
                final MovementInput field_71158_b6 = this.field_71158_b;
                field_71158_b6.field_78902_a *= (float)OringoClient.noSlow.eatingSlowdown.getValue();
            }
        }
        if (OringoClient.freeCam.isToggled()) {
            this.field_70145_X = true;
        }
        if (MinecraftForge.EVENT_BUS.post((Event)new PlayerUpdateEvent())) {
            ci.cancel();
        }
    }
    
    @Override
    public void func_70091_d(double x, double y, double z) {
        final MoveEvent event = new MoveEvent(x, y, z);
        if (MinecraftForge.EVENT_BUS.post((Event)event)) {
            return;
        }
        x = event.x;
        y = event.y;
        z = event.z;
        super.func_70091_d(x, y, z);
    }
    
    @Override
    public void func_71059_n(final Entity targetEntity) {
        if (ForgeHooks.onPlayerAttackTarget((EntityPlayer)this, targetEntity) && targetEntity.func_70075_an() && !targetEntity.func_85031_j((Entity)this)) {
            float f = (float)this.func_110148_a(SharedMonsterAttributes.field_111264_e).func_111126_e();
            int i = 0;
            float f2 = 0.0f;
            if (targetEntity instanceof EntityLivingBase) {
                f2 = EnchantmentHelper.func_152377_a(this.func_70694_bm(), ((EntityLivingBase)targetEntity).func_70668_bt());
            }
            else {
                f2 = EnchantmentHelper.func_152377_a(this.func_70694_bm(), EnumCreatureAttribute.UNDEFINED);
            }
            i += EnchantmentHelper.func_77501_a((EntityLivingBase)this);
            if (this.func_70051_ag()) {
                ++i;
            }
            if (f > 0.0f || f2 > 0.0f) {
                final boolean flag = this.field_70143_R > 0.0f && !this.field_70122_E && !this.func_70617_f_() && !this.func_70090_H() && !this.func_70644_a(Potion.field_76440_q) && this.field_70154_o == null && targetEntity instanceof EntityLivingBase;
                if (flag && f > 0.0f) {
                    f *= 1.5f;
                }
                f += f2;
                boolean flag2 = false;
                final int j = EnchantmentHelper.func_90036_a((EntityLivingBase)this);
                if (targetEntity instanceof EntityLivingBase && j > 0 && !targetEntity.func_70027_ad()) {
                    flag2 = true;
                    targetEntity.func_70015_d(1);
                }
                final double d0 = targetEntity.field_70159_w;
                final double d2 = targetEntity.field_70181_x;
                final double d3 = targetEntity.field_70179_y;
                final boolean flag3 = targetEntity.func_70097_a(DamageSource.func_76365_a((EntityPlayer)this), f);
                if (flag3) {
                    if (i > 0) {
                        targetEntity.func_70024_g((double)(-MathHelper.func_76126_a(this.field_70177_z * 3.1415927f / 180.0f) * i * 0.5f), 0.1, (double)(MathHelper.func_76134_b(this.field_70177_z * 3.1415927f / 180.0f) * i * 0.5f));
                        if (OringoClient.sprint.isToggled() && OringoClient.sprint.keep.isEnabled()) {
                            if (this.func_70051_ag()) {
                                OringoClient.mc.func_147114_u().func_147298_b().func_179290_a((Packet)new C0BPacketEntityAction((Entity)OringoClient.mc.field_71439_g, C0BPacketEntityAction.Action.STOP_SPRINTING));
                                OringoClient.mc.func_147114_u().func_147298_b().func_179290_a((Packet)new C0BPacketEntityAction((Entity)OringoClient.mc.field_71439_g, C0BPacketEntityAction.Action.START_SPRINTING));
                            }
                        }
                        else {
                            this.field_70159_w *= 0.6;
                            this.field_70179_y *= 0.6;
                            this.func_70031_b(false);
                        }
                    }
                    if (targetEntity instanceof EntityPlayerMP && targetEntity.field_70133_I) {
                        ((EntityPlayerMP)targetEntity).field_71135_a.func_147359_a((Packet)new S12PacketEntityVelocity(targetEntity));
                        targetEntity.field_70133_I = false;
                        targetEntity.field_70159_w = d0;
                        targetEntity.field_70181_x = d2;
                        targetEntity.field_70179_y = d3;
                    }
                    if (flag) {
                        this.func_71009_b(targetEntity);
                    }
                    if (f2 > 0.0f) {
                        this.func_71047_c(targetEntity);
                    }
                    if (f >= 18.0f) {
                        this.func_71029_a((StatBase)AchievementList.field_75999_E);
                    }
                    this.func_130011_c(targetEntity);
                    if (targetEntity instanceof EntityLivingBase) {
                        EnchantmentHelper.func_151384_a((EntityLivingBase)targetEntity, (Entity)this);
                    }
                    EnchantmentHelper.func_151385_b((EntityLivingBase)this, targetEntity);
                    final ItemStack itemstack = this.func_71045_bC();
                    Entity entity = targetEntity;
                    if (targetEntity instanceof EntityDragonPart) {
                        final IEntityMultiPart ientitymultipart = ((EntityDragonPart)targetEntity).field_70259_a;
                        if (ientitymultipart instanceof EntityLivingBase) {
                            entity = (Entity)ientitymultipart;
                        }
                    }
                    if (itemstack != null && entity instanceof EntityLivingBase) {
                        itemstack.func_77961_a((EntityLivingBase)entity, (EntityPlayer)this);
                        if (itemstack.field_77994_a <= 0) {
                            this.func_71028_bD();
                        }
                    }
                    if (targetEntity instanceof EntityLivingBase) {
                        this.func_71064_a(StatList.field_75951_w, Math.round(f * 10.0f));
                        if (j > 0) {
                            targetEntity.func_70015_d(j * 4);
                        }
                    }
                    this.func_71020_j(0.3f);
                }
                else if (flag2) {
                    targetEntity.func_70066_B();
                }
            }
        }
    }
}
