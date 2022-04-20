// 
// Decompiled by Procyon v0.5.36
// 

package me.oringo.oringoclient.mixins;

import me.oringo.oringoclient.events.PacketReceivedEvent;
import java.io.IOException;
import net.minecraft.network.play.server.S40PacketDisconnect;
import net.minecraft.network.play.server.S3EPacketTeams;
import net.minecraft.network.play.server.S13PacketDestroyEntities;
import me.oringo.oringoclient.commands.PacketLoggerCommand;
import io.netty.channel.ChannelHandlerContext;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.minecraftforge.fml.common.eventhandler.Event;
import me.oringo.oringoclient.events.PacketSentEvent;
import net.minecraftforge.common.MinecraftForge;
import me.oringo.oringoclient.utils.PacketUtils;
import me.oringo.oringoclient.commands.DevModeCommand;
import net.minecraft.network.play.client.C03PacketPlayer;
import me.oringo.oringoclient.utils.OringoPacketLog;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.network.Packet;
import net.minecraft.network.NetworkManager;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ NetworkManager.class })
public abstract class Packets
{
    @Inject(method = { "sendPacket(Lnet/minecraft/network/Packet;)V" }, at = { @At("HEAD") }, cancellable = true)
    private void onSendPacket(final Packet<?> packet, final CallbackInfo callbackInfo) {
        if (OringoPacketLog.isEnabled() && !(packet instanceof C03PacketPlayer) && !DevModeCommand.ignoredPackets.contains(packet.getClass().getSimpleName())) {
            OringoPacketLog.logOut(PacketUtils.packetToString(packet));
        }
        if (!PacketUtils.noEvent.contains(packet) && MinecraftForge.EVENT_BUS.post((Event)new PacketSentEvent(packet))) {
            callbackInfo.cancel();
        }
    }
    
    @Inject(method = { "sendPacket(Lnet/minecraft/network/Packet;)V" }, at = { @At("RETURN") }, cancellable = true)
    private void onSendPacketPost(final Packet<?> packet, final CallbackInfo callbackInfo) {
        if (!PacketUtils.noEvent.contains(packet) && MinecraftForge.EVENT_BUS.post((Event)new PacketSentEvent.Post(packet))) {
            callbackInfo.cancel();
        }
        PacketUtils.noEvent.remove(packet);
    }
    
    @Inject(method = { "channelRead0" }, at = { @At("HEAD") }, cancellable = true)
    private void onChannelReadHead(final ChannelHandlerContext context, final Packet<?> packet, final CallbackInfo callbackInfo) {
        Label_0092: {
            if (PacketLoggerCommand.writer != null) {
                if (!(packet instanceof S13PacketDestroyEntities) && !(packet instanceof S3EPacketTeams)) {
                    if (!(packet instanceof S40PacketDisconnect)) {
                        break Label_0092;
                    }
                }
                try {
                    PacketLoggerCommand.writer.write(PacketUtils.packetToString(packet) + "\n");
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                if (packet instanceof S40PacketDisconnect) {
                    try {
                        PacketLoggerCommand.writer.close();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                    PacketLoggerCommand.writer = null;
                }
            }
        }
        if (!PacketUtils.noEvent.contains(packet) && MinecraftForge.EVENT_BUS.post((Event)new PacketReceivedEvent(packet, context))) {
            callbackInfo.cancel();
        }
    }
    
    @Inject(method = { "channelRead0" }, at = { @At("RETURN") }, cancellable = true)
    private void onPost(final ChannelHandlerContext context, final Packet<?> packet, final CallbackInfo callbackInfo) {
        if (!PacketUtils.noEvent.contains(packet) && MinecraftForge.EVENT_BUS.post((Event)new PacketReceivedEvent.Post(packet, context))) {
            callbackInfo.cancel();
        }
        PacketUtils.noEvent.remove(packet);
    }
}
