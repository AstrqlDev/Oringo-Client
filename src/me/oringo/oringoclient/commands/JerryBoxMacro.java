// 
// Decompiled by Procyon v0.5.36
// 

package me.oringo.oringoclient.commands;

import net.minecraft.client.settings.KeyBinding;
import me.oringo.oringoclient.utils.Notifications;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import me.oringo.oringoclient.utils.SkyblockUtils;
import net.minecraft.entity.player.EntityPlayer;
import me.oringo.oringoclient.OringoClient;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraft.util.BlockPos;
import net.minecraft.command.CommandException;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.ICommand;

public class JerryBoxMacro implements ICommand
{
    public static Thread jerrybox;
    
    public String func_71517_b() {
        return "jerrybox";
    }
    
    public String func_71518_a(final ICommandSender sender) {
        return "/jerrybox";
    }
    
    public List<String> func_71514_a() {
        return new ArrayList<String>();
    }
    
    public void func_71515_b(final ICommandSender sender, final String[] args) throws CommandException {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: ifnull          27
        //     6: getstatic       me/oringo/oringoclient/commands/JerryBoxMacro.jerrybox:Ljava/lang/Thread;
        //     9: invokevirtual   java/lang/Thread.stop:()V
        //    12: aconst_null    
        //    13: putstatic       me/oringo/oringoclient/commands/JerryBoxMacro.jerrybox:Ljava/lang/Thread;
        //    16: ldc             "Oringo Client"
        //    18: ldc             "Auto Jerry Box disabled!"
        //    20: sipush          1000
        //    23: invokestatic    me/oringo/oringoclient/utils/Notifications.showNotification:(Ljava/lang/String;Ljava/lang/String;I)V
        //    26: return         
        //    27: getstatic       me/oringo/oringoclient/OringoClient.mc:Lnet/minecraft/client/Minecraft;
        //    30: getfield        net/minecraft/client/Minecraft.field_71439_g:Lnet/minecraft/client/entity/EntityPlayerSP;
        //    33: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.func_70694_bm:()Lnet/minecraft/item/ItemStack;
        //    36: invokestatic    me/oringo/oringoclient/utils/SkyblockUtils.getDisplayName:(Lnet/minecraft/item/ItemStack;)Ljava/lang/String;
        //    39: ldc             " Jerry Box"
        //    41: invokevirtual   java/lang/String.endsWith:(Ljava/lang/String;)Z
        //    44: ifeq            69
        //    47: new             Ljava/lang/Thread;
        //    50: dup            
        //    51: invokedynamic   BootstrapMethod #0, run:()Ljava/lang/Runnable;
        //    56: invokespecial   java/lang/Thread.<init>:(Ljava/lang/Runnable;)V
        //    59: dup            
        //    60: putstatic       me/oringo/oringoclient/commands/JerryBoxMacro.jerrybox:Ljava/lang/Thread;
        //    63: invokevirtual   java/lang/Thread.start:()V
        //    66: goto            79
        //    69: ldc             "Oringo Client"
        //    71: ldc             "You need to hold a jerry box!"
        //    73: sipush          2000
        //    76: invokestatic    me/oringo/oringoclient/utils/Notifications.showNotification:(Ljava/lang/String;Ljava/lang/String;I)V
        //    79: return         
        //    Exceptions:
        //  throws net.minecraft.command.CommandException
        //    StackMapTable: 00 03 1B 29 09
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Unsupported node type: com.strobel.decompiler.ast.Lambda
        //     at com.strobel.decompiler.ast.Error.unsupportedNode(Error.java:32)
        //     at com.strobel.decompiler.ast.GotoRemoval.exit(GotoRemoval.java:612)
        //     at com.strobel.decompiler.ast.GotoRemoval.exit(GotoRemoval.java:586)
        //     at com.strobel.decompiler.ast.GotoRemoval.trySimplifyGoto(GotoRemoval.java:248)
        //     at com.strobel.decompiler.ast.GotoRemoval.removeGotosCore(GotoRemoval.java:66)
        //     at com.strobel.decompiler.ast.GotoRemoval.removeGotos(GotoRemoval.java:53)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:276)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:42)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:214)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at com.strobel.decompiler.DecompilerDriver.decompileType(DecompilerDriver.java:330)
        //     at com.strobel.decompiler.DecompilerDriver.decompileJar(DecompilerDriver.java:251)
        //     at com.strobel.decompiler.DecompilerDriver.main(DecompilerDriver.java:126)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public boolean func_71519_b(final ICommandSender sender) {
        return true;
    }
    
    public List<String> func_180525_a(final ICommandSender sender, final String[] args, final BlockPos pos) {
        return new ArrayList<String>();
    }
    
    public boolean func_82358_a(final String[] args, final int index) {
        return false;
    }
    
    public int compareTo(final ICommand o) {
        return 0;
    }
    
    @SubscribeEvent
    public void onGui(final GuiOpenEvent event) {
        if (event.gui instanceof GuiChest && ((ContainerChest)((GuiChest)event.gui).field_147002_h).func_85151_d().func_70005_c_().equalsIgnoreCase("open a jerry box") && JerryBoxMacro.jerrybox != null) {
            new Thread(() -> {
                try {
                    Thread.sleep(1L);
                    OringoClient.mc.field_71442_b.func_78753_a(OringoClient.mc.field_71439_g.field_71070_bA.field_75152_c, 22, 0, 3, (EntityPlayer)OringoClient.mc.field_71439_g);
                    Thread.sleep(1L);
                    SkyblockUtils.rightClick();
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
