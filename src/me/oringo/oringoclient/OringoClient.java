// 
// Decompiled by Procyon v0.5.36
// 

package me.oringo.oringoclient;

import net.minecraft.client.renderer.texture.DynamicTexture;
import java.nio.file.Files;
import java.nio.file.CopyOption;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ChatComponentText;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.nio.ByteBuffer;
import javax.net.ssl.SSLSession;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import java.security.KeyManagementException;
import javax.net.ssl.KeyManager;
import java.security.SecureRandom;
import java.security.NoSuchAlgorithmException;
import javax.net.ssl.SSLContext;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.X509TrustManager;
import javax.net.ssl.TrustManager;
import me.oringo.oringoclient.qolfeatures.module.keybinds.Keybind;
import java.io.InputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.lang.reflect.Field;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.DestroyBlockProgress;
import java.util.Map;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import java.util.Iterator;
import me.oringo.oringoclient.utils.font.Fonts;
import me.oringo.oringoclient.commands.PacketLoggerCommand;
import me.oringo.oringoclient.commands.BanCommand;
import me.oringo.oringoclient.utils.OringoPacketLog;
import me.oringo.oringoclient.config.ConfigManager;
import me.oringo.oringoclient.commands.FarmingMacro;
import me.oringo.oringoclient.utils.SkyblockUtils;
import me.oringo.oringoclient.qolfeatures.AttackQueue;
import me.oringo.oringoclient.commands.ClipCommand;
import me.oringo.oringoclient.commands.FireWork;
import me.oringo.oringoclient.commands.CheckNameCommand;
import me.oringo.oringoclient.commands.ArmorStandsCommand;
import me.oringo.oringoclient.commands.nucleus.SaveCommand;
import me.oringo.oringoclient.commands.nucleus.EntityClickCommand;
import me.oringo.oringoclient.commands.nucleus.BlockClickCommand;
import me.oringo.oringoclient.commands.nucleus.PlaceCrystalsCommand;
import me.oringo.oringoclient.qolfeatures.Updater;
import me.oringo.oringoclient.commands.StalkCommand;
import me.oringo.oringoclient.commands.DevModeCommand;
import me.oringo.oringoclient.commands.SettingsCommand;
import me.oringo.oringoclient.utils.Notifications;
import me.oringo.oringoclient.commands.AotvTestCommand;
import me.oringo.oringoclient.commands.WardrobeCommand;
import me.oringo.oringoclient.commands.CreateItem;
import net.minecraft.command.ICommand;
import net.minecraftforge.client.ClientCommandHandler;
import me.oringo.oringoclient.commands.JerryBoxMacro;
import net.minecraftforge.common.MinecraftForge;
import me.oringo.oringoclient.utils.shader.BlurUtils;
import me.oringo.oringoclient.qolfeatures.module.skyblock.BoneThrower;
import me.oringo.oringoclient.qolfeatures.module.other.Test;
import me.oringo.oringoclient.qolfeatures.module.render.ChestESP;
import me.oringo.oringoclient.qolfeatures.module.other.MurdererFinder;
import me.oringo.oringoclient.qolfeatures.module.skyblock.MetalDetector;
import me.oringo.oringoclient.qolfeatures.module.other.Blink;
import me.oringo.oringoclient.qolfeatures.module.player.FastPlace;
import me.oringo.oringoclient.qolfeatures.module.render.CustomInterfaces;
import me.oringo.oringoclient.qolfeatures.module.player.AutoTool;
import me.oringo.oringoclient.qolfeatures.module.combat.WTap;
import me.oringo.oringoclient.qolfeatures.module.render.TargetHUD;
import me.oringo.oringoclient.qolfeatures.module.render.ServerRotations;
import me.oringo.oringoclient.qolfeatures.module.skyblock.IceFillHelp;
import me.oringo.oringoclient.qolfeatures.module.skyblock.Snowballs;
import me.oringo.oringoclient.qolfeatures.module.other.GuessTheBuildAFK;
import me.oringo.oringoclient.qolfeatures.module.skyblock.AutoRogueSword;
import me.oringo.oringoclient.qolfeatures.module.other.TaraFly;
import me.oringo.oringoclient.qolfeatures.module.render.CustomESP;
import me.oringo.oringoclient.qolfeatures.module.render.InventoryDisplay;
import me.oringo.oringoclient.qolfeatures.module.render.RichPresenceModule;
import me.oringo.oringoclient.qolfeatures.module.render.ChinaHat;
import me.oringo.oringoclient.qolfeatures.module.render.PlayerESP;
import me.oringo.oringoclient.qolfeatures.module.player.ChestStealer;
import me.oringo.oringoclient.qolfeatures.module.player.InvManager;
import me.oringo.oringoclient.qolfeatures.module.skyblock.AutoS1;
import me.oringo.oringoclient.qolfeatures.module.other.TntRunPing;
import me.oringo.oringoclient.qolfeatures.module.combat.SumoFences;
import me.oringo.oringoclient.qolfeatures.module.skyblock.GhostBlocks;
import me.oringo.oringoclient.qolfeatures.module.skyblock.RemoveAnnoyingMobs;
import me.oringo.oringoclient.qolfeatures.module.player.SafeWalk;
import me.oringo.oringoclient.qolfeatures.module.render.DungeonESP;
import me.oringo.oringoclient.qolfeatures.module.skyblock.SecretAura;
import me.oringo.oringoclient.qolfeatures.module.skyblock.TerminatorAura;
import me.oringo.oringoclient.qolfeatures.module.other.AutoBypassRacism;
import me.oringo.oringoclient.qolfeatures.module.skyblock.TerminalAura;
import me.oringo.oringoclient.qolfeatures.module.other.AntiNicker;
import me.oringo.oringoclient.qolfeatures.module.player.AntiVoid;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import java.io.File;
import net.minecraft.util.ResourceLocation;
import java.util.HashMap;
import net.minecraft.util.BlockPos;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import me.oringo.oringoclient.qolfeatures.module.render.Giants;
import me.oringo.oringoclient.qolfeatures.module.render.FreeCam;
import me.oringo.oringoclient.qolfeatures.module.skyblock.Phase;
import me.oringo.oringoclient.qolfeatures.module.player.NoRotate;
import me.oringo.oringoclient.qolfeatures.module.combat.Hitboxes;
import me.oringo.oringoclient.qolfeatures.module.other.Derp;
import me.oringo.oringoclient.qolfeatures.module.macro.MithrilMacro;
import me.oringo.oringoclient.qolfeatures.module.render.Camera;
import me.oringo.oringoclient.qolfeatures.module.render.AnimationCreator;
import me.oringo.oringoclient.qolfeatures.module.render.Animations;
import me.oringo.oringoclient.qolfeatures.module.render.NickHider;
import me.oringo.oringoclient.qolfeatures.module.macro.AOTVReturn;
import me.oringo.oringoclient.qolfeatures.module.player.FastBreak;
import me.oringo.oringoclient.qolfeatures.module.macro.AutoSumoBot;
import me.oringo.oringoclient.qolfeatures.module.combat.Reach;
import me.oringo.oringoclient.qolfeatures.module.player.Sprint;
import me.oringo.oringoclient.qolfeatures.module.combat.NoSlow;
import me.oringo.oringoclient.qolfeatures.module.combat.NoHitDelay;
import me.oringo.oringoclient.qolfeatures.module.other.Modless;
import me.oringo.oringoclient.qolfeatures.module.skyblock.Aimbot;
import me.oringo.oringoclient.qolfeatures.module.player.Velocity;
import me.oringo.oringoclient.qolfeatures.module.combat.KillAura;
import me.oringo.oringoclient.qolfeatures.module.render.Gui;
import me.oringo.oringoclient.qolfeatures.module.Module;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraftforge.fml.common.Mod;

@Mod(modid = "examplemod", dependencies = "before:*", version = "1.7.1")
public class OringoClient
{
    public static CopyOnWriteArrayList<Module> modules;
    public static Gui clickGui;
    public static KillAura killAura;
    public static Velocity velocity;
    public static Aimbot bloodAimbot;
    public static Modless modless;
    public static NoHitDelay noHitDelay;
    public static NoSlow noSlow;
    public static Sprint sprint;
    public static Reach reach;
    public static AutoSumoBot autoSumo;
    public static FastBreak fastBreak;
    public static AOTVReturn aotvReturn;
    public static NickHider nickHider;
    public static Animations animations;
    public static AnimationCreator animationCreator;
    public static Camera camera;
    public static MithrilMacro mithrilMacro;
    public static Derp derp;
    public static Hitboxes hitboxes;
    public static NoRotate noRotate;
    public static Phase phase;
    public static FreeCam freeCam;
    public static Giants giants;
    public static boolean shouldUpdate;
    public static String[] vers;
    public static final Minecraft mc;
    public static ArrayList<BlockPos> stop;
    public static final String MODID = "examplemod";
    public static final String PREFIX = "§8[§bOringoClient§8] §7";
    public static final String VERSION = "1.7.1";
    public static boolean devMode;
    public static ArrayList<Runnable> scheduledTasks;
    public static HashMap<String, ResourceLocation> capes;
    public static HashMap<File, ResourceLocation> capesLoaded;
    
    @Mod.EventHandler
    public void onPre(final FMLPreInitializationEvent event) {
    }
    
    @Mod.EventHandler
    public void onInit(final FMLPreInitializationEvent event) {
        new File(OringoClient.mc.field_71412_D.getPath() + "/config/OringoClient").mkdir();
        new File(OringoClient.mc.field_71412_D.getPath() + "/config/OringoClient/capes").mkdir();
        OringoClient.modless.setToggled(true);
        OringoClient.modules.add(new AntiVoid());
        OringoClient.modules.add(OringoClient.clickGui);
        OringoClient.modules.add(OringoClient.killAura);
        OringoClient.modules.add(OringoClient.noRotate);
        OringoClient.modules.add(OringoClient.velocity);
        OringoClient.modules.add(OringoClient.bloodAimbot);
        OringoClient.modules.add(new AntiNicker());
        OringoClient.modules.add(new TerminalAura());
        OringoClient.modules.add(new AutoBypassRacism());
        OringoClient.modules.add(new TerminatorAura());
        OringoClient.modules.add(new SecretAura());
        OringoClient.modules.add(new DungeonESP());
        OringoClient.modules.add(new SafeWalk());
        OringoClient.modules.add(new RemoveAnnoyingMobs());
        OringoClient.modules.add(new GhostBlocks());
        OringoClient.modules.add(new SumoFences());
        OringoClient.modules.add(OringoClient.modless);
        OringoClient.modules.add(OringoClient.noHitDelay);
        OringoClient.modules.add(new TntRunPing());
        OringoClient.modules.add(OringoClient.noSlow);
        OringoClient.modules.add(OringoClient.sprint);
        OringoClient.modules.add(OringoClient.reach);
        OringoClient.modules.add(new AutoS1());
        OringoClient.modules.add(new InvManager());
        OringoClient.modules.add(new ChestStealer());
        OringoClient.modules.add(new PlayerESP());
        OringoClient.modules.add(OringoClient.autoSumo);
        OringoClient.modules.add(OringoClient.fastBreak);
        OringoClient.modules.add(OringoClient.nickHider);
        OringoClient.modules.add(new ChinaHat());
        OringoClient.modules.add(OringoClient.aotvReturn = new AOTVReturn());
        OringoClient.modules.add(OringoClient.mithrilMacro);
        final RichPresenceModule richPresence = new RichPresenceModule();
        OringoClient.modules.add(richPresence);
        OringoClient.modules.add(new InventoryDisplay());
        OringoClient.modules.add(new CustomESP());
        OringoClient.modules.add(new TaraFly());
        OringoClient.modules.add(new AutoRogueSword());
        OringoClient.modules.add(new GuessTheBuildAFK());
        OringoClient.modules.add(new Snowballs());
        OringoClient.modules.add(new IceFillHelp());
        OringoClient.modules.add(OringoClient.animations);
        OringoClient.modules.add(ServerRotations.getInstance());
        OringoClient.modules.add(TargetHUD.getInstance());
        OringoClient.modules.add(new WTap());
        OringoClient.modules.add(new AutoTool());
        OringoClient.modules.add(OringoClient.camera);
        OringoClient.modules.add(new CustomInterfaces());
        OringoClient.modules.add(FastPlace.getInstance());
        OringoClient.modules.add(OringoClient.derp);
        OringoClient.modules.add(new Blink());
        OringoClient.modules.add(OringoClient.freeCam);
        OringoClient.modules.add(OringoClient.hitboxes);
        OringoClient.modules.add(new MetalDetector());
        OringoClient.modules.add(new MurdererFinder());
        OringoClient.modules.add(new ChestESP());
        OringoClient.modules.add(new Test());
        OringoClient.modules.add(new BoneThrower());
        OringoClient.modules.add(OringoClient.phase);
        OringoClient.modules.add(OringoClient.giants);
        loadKeybinds();
        BlurUtils.registerListener();
        for (final Module m : OringoClient.modules) {
            MinecraftForge.EVENT_BUS.register((Object)m);
        }
        final JerryBoxMacro jerryBoxMacro = new JerryBoxMacro();
        ClientCommandHandler.instance.func_71560_a((ICommand)jerryBoxMacro);
        MinecraftForge.EVENT_BUS.register((Object)jerryBoxMacro);
        ClientCommandHandler.instance.func_71560_a((ICommand)new CreateItem());
        final WardrobeCommand wardrobeCommand = new WardrobeCommand();
        ClientCommandHandler.instance.func_71560_a((ICommand)wardrobeCommand);
        ClientCommandHandler.instance.func_71560_a((ICommand)new AotvTestCommand());
        MinecraftForge.EVENT_BUS.register((Object)wardrobeCommand);
        MinecraftForge.EVENT_BUS.register((Object)new Notifications());
        MinecraftForge.EVENT_BUS.register((Object)this);
        ClientCommandHandler.instance.func_71560_a((ICommand)new SettingsCommand());
        ClientCommandHandler.instance.func_71560_a((ICommand)new DevModeCommand());
        ClientCommandHandler.instance.func_71560_a((ICommand)new StalkCommand());
        MinecraftForge.EVENT_BUS.register((Object)new Updater());
        ClientCommandHandler.instance.func_71560_a((ICommand)new PlaceCrystalsCommand());
        ClientCommandHandler.instance.func_71560_a((ICommand)new BlockClickCommand());
        ClientCommandHandler.instance.func_71560_a((ICommand)new EntityClickCommand());
        ClientCommandHandler.instance.func_71560_a((ICommand)new SaveCommand());
        ClientCommandHandler.instance.func_71560_a((ICommand)new ArmorStandsCommand());
        final CheckNameCommand checkNameCommand = new CheckNameCommand();
        ClientCommandHandler.instance.func_71560_a((ICommand)checkNameCommand);
        ClientCommandHandler.instance.func_71560_a((ICommand)new FireWork());
        ClientCommandHandler.instance.func_71560_a((ICommand)new ClipCommand());
        MinecraftForge.EVENT_BUS.register((Object)new AttackQueue());
        MinecraftForge.EVENT_BUS.register((Object)checkNameCommand);
        MinecraftForge.EVENT_BUS.register((Object)new SkyblockUtils());
        final FarmingMacro farmingMacro;
        ClientCommandHandler.instance.func_71560_a((ICommand)(farmingMacro = new FarmingMacro()));
        MinecraftForge.EVENT_BUS.register((Object)farmingMacro);
        update();
        ConfigManager.loadConfig();
        if (richPresence.isToggled()) {
            richPresence.onEnable();
        }
        loadCustomNames();
        if (new File("OringoDev").exists()) {
            OringoPacketLog.start();
            OringoClient.devMode = true;
        }
        if (OringoClient.devMode) {
            ClientCommandHandler.instance.func_71560_a((ICommand)new BanCommand());
            ClientCommandHandler.instance.func_71560_a((ICommand)new PacketLoggerCommand());
            OringoClient.modules.add(OringoClient.animationCreator);
        }
        Fonts.bootstrap();
    }
    
    @Mod.EventHandler
    public void onPost(final FMLPostInitializationEvent event) {
        loadCapes();
    }
    
    public static Map<Integer, DestroyBlockProgress> getBlockBreakProgress() {
        try {
            final Field field_72738_e = RenderGlobal.class.getDeclaredField("field_72738_E");
            field_72738_e.setAccessible(true);
            return (Map<Integer, DestroyBlockProgress>)field_72738_e.get(Minecraft.func_71410_x().field_71438_f);
        }
        catch (Exception ex) {
            return new HashMap<Integer, DestroyBlockProgress>();
        }
    }
    
    public static void handleKeypress(final int key) {
        if (key == 0) {
            return;
        }
        for (final Module m : OringoClient.modules) {
            if (m.getKeycode() == key && !m.isKeybind()) {
                m.toggle();
                if (OringoClient.clickGui.disableNotifs.isEnabled()) {
                    continue;
                }
                Notifications.showNotification("Oringo Client", m.getName() + (m.isToggled() ? " enabled!" : " disabled!"), 1000);
            }
        }
    }
    
    private static void update() {
        try {
            OringoClient.vers = new BufferedReader(new InputStreamReader(new URL("http://niger.5v.pl/version").openStream())).readLine().split(" ");
            if (!"1.7.1".equals(OringoClient.vers[0])) {
                OringoClient.shouldUpdate = true;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Couldn't update");
        }
    }
    
    private static void loadCapes() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: dup            
        //     4: invokespecial   com/google/gson/Gson.<init>:()V
        //     7: new             Ljava/io/InputStreamReader;
        //    10: dup            
        //    11: new             Ljava/net/URL;
        //    14: dup            
        //    15: ldc_w           "http://niger.5v.pl/capes.txt"
        //    18: invokespecial   java/net/URL.<init>:(Ljava/lang/String;)V
        //    21: invokevirtual   java/net/URL.openStream:()Ljava/io/InputStream;
        //    24: invokespecial   java/io/InputStreamReader.<init>:(Ljava/io/InputStream;)V
        //    27: ldc_w           Ljava/util/HashMap;.class
        //    30: invokevirtual   com/google/gson/Gson.fromJson:(Ljava/io/Reader;Ljava/lang/Class;)Ljava/lang/Object;
        //    33: checkcast       Ljava/util/HashMap;
        //    36: astore_0        /* capeData */
        //    37: getstatic       me/oringo/oringoclient/OringoClient.capes:Ljava/util/HashMap;
        //    40: invokevirtual   java/util/HashMap.clear:()V
        //    43: aload_0         /* capeData */
        //    44: ifnull          88
        //    47: new             Ljava/util/HashMap;
        //    50: dup            
        //    51: invokespecial   java/util/HashMap.<init>:()V
        //    54: astore_1        /* cache */
        //    55: aload_0         /* capeData */
        //    56: aload_1         /* cache */
        //    57: invokedynamic   BootstrapMethod #0, accept:(Ljava/util/HashMap;)Ljava/util/function/BiConsumer;
        //    62: invokevirtual   java/util/HashMap.forEach:(Ljava/util/function/BiConsumer;)V
        //    65: getstatic       me/oringo/oringoclient/OringoClient.devMode:Z
        //    68: ifeq            88
        //    71: getstatic       java/lang/System.out:Ljava/io/PrintStream;
        //    74: new             Lcom/google/gson/Gson;
        //    77: dup            
        //    78: invokespecial   com/google/gson/Gson.<init>:()V
        //    81: aload_0         /* capeData */
        //    82: invokevirtual   com/google/gson/Gson.toJson:(Ljava/lang/Object;)Ljava/lang/String;
        //    85: invokevirtual   java/io/PrintStream.println:(Ljava/lang/String;)V
        //    88: goto            96
        //    91: astore_0        /* e */
        //    92: aload_0         /* e */
        //    93: invokevirtual   java/lang/Exception.printStackTrace:()V
        //    96: return         
        //    StackMapTable: 00 03 FB 00 58 42 07 01 F1 04
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                 
        //  -----  -----  -----  -----  ---------------------
        //  0      88     91     96     Ljava/lang/Exception;
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Could not infer any expression.
        //     at com.strobel.decompiler.ast.TypeAnalysis.runInference(TypeAnalysis.java:374)
        //     at com.strobel.decompiler.ast.TypeAnalysis.run(TypeAnalysis.java:96)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:344)
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
    
    private static void loadCustomNames() {
        final File killAuraNames = new File(OringoClient.mc.field_71412_D.getPath() + "/config/OringoClient/KillAura.cfg");
        final File invManagerDropList = new File(OringoClient.mc.field_71412_D.getPath() + "/config/OringoClient/InventoryManager.cfg");
        final File customESP = new File(OringoClient.mc.field_71412_D.getPath() + "config/OringoClient/CustomESP.cfg");
        try {
            if (!killAuraNames.exists()) {
                killAuraNames.createNewFile();
            }
            else {
                final DataInputStream dataInputStream = new DataInputStream(new FileInputStream(killAuraNames));
                for (int h = dataInputStream.readInt(), i = 0; i < h; ++i) {
                    KillAura.names.add(dataInputStream.readUTF());
                }
                dataInputStream.close();
            }
        }
        catch (Exception ex) {}
        try {
            if (!invManagerDropList.exists()) {
                invManagerDropList.createNewFile();
            }
            else {
                final DataInputStream dataInputStream = new DataInputStream(new FileInputStream(invManagerDropList));
                for (int h = dataInputStream.readInt(), i = 0; i < h; ++i) {
                    InvManager.dropCustom.add(dataInputStream.readUTF());
                }
                dataInputStream.close();
            }
        }
        catch (Exception ex2) {}
        try {
            if (!customESP.exists()) {
                customESP.createNewFile();
            }
            else {
                final DataInputStream dataInputStream = new DataInputStream(new FileInputStream(customESP));
                for (int h = dataInputStream.readInt(), i = 0; i < h; ++i) {
                    CustomESP.names.add(dataInputStream.readUTF());
                }
                dataInputStream.close();
            }
        }
        catch (Exception ex3) {}
    }
    
    private static void loadKeybinds() {
        try {
            final File oringoKeybinds = new File(OringoClient.mc.field_71412_D.getPath() + "/config/OringoClient/OringoKeybinds.cfg");
            if (!oringoKeybinds.exists()) {
                oringoKeybinds.createNewFile();
            }
            else {
                final DataInputStream dataInputStream = new DataInputStream(new FileInputStream(OringoClient.mc.field_71412_D.getPath() + "/config/OringoClient/OringoKeybinds.cfg"));
                for (int h = dataInputStream.readInt(), i = 0; i < h; ++i) {
                    final String name = dataInputStream.readUTF();
                    OringoClient.modules.add(new Keybind(name));
                }
                dataInputStream.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void disableSSLVerification() {
        final TrustManager[] trustAllCerts = { new X509TrustManager() {
                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
                
                @Override
                public void checkClientTrusted(final X509Certificate[] arg0, final String arg1) throws CertificateException {
                }
                
                @Override
                public void checkServerTrusted(final X509Certificate[] arg0, final String arg1) throws CertificateException {
                }
            } };
        SSLContext sc = null;
        try {
            sc = SSLContext.getInstance("SSL");
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            sc.init(null, trustAllCerts, new SecureRandom());
        }
        catch (KeyManagementException e2) {
            e2.printStackTrace();
        }
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        final HostnameVerifier validHosts = new HostnameVerifier() {
            @Override
            public boolean verify(final String arg0, final SSLSession arg1) {
                return true;
            }
        };
        HttpsURLConnection.setDefaultHostnameVerifier(validHosts);
    }
    
    private ByteBuffer readImageToBuffer(final InputStream imageStream) throws IOException {
        final BufferedImage bufferedimage = ImageIO.read(imageStream);
        final int[] aint = bufferedimage.getRGB(0, 0, bufferedimage.getWidth(), bufferedimage.getHeight(), null, 0, bufferedimage.getWidth());
        final ByteBuffer bytebuffer = ByteBuffer.allocate(4 * aint.length);
        for (final int i : aint) {
            bytebuffer.putInt(i << 8 | (i >> 24 & 0xFF));
        }
        bytebuffer.flip();
        return bytebuffer;
    }
    
    public static void sendMessage(final String message) {
        Minecraft.func_71410_x().field_71439_g.func_145747_a((IChatComponent)new ChatComponentText(message));
    }
    
    public static void sendMessageWithPrefix(final String message) {
        Minecraft.func_71410_x().field_71439_g.func_145747_a((IChatComponent)new ChatComponentText("§8[§bOringoClient§8] §7" + message));
    }
    
    static {
        OringoClient.modules = new CopyOnWriteArrayList<Module>();
        OringoClient.clickGui = new Gui();
        OringoClient.killAura = new KillAura();
        OringoClient.velocity = new Velocity();
        OringoClient.bloodAimbot = new Aimbot();
        OringoClient.modless = new Modless();
        OringoClient.noHitDelay = new NoHitDelay();
        OringoClient.noSlow = new NoSlow();
        OringoClient.sprint = new Sprint();
        OringoClient.reach = new Reach();
        OringoClient.autoSumo = new AutoSumoBot();
        OringoClient.fastBreak = new FastBreak();
        OringoClient.aotvReturn = new AOTVReturn();
        OringoClient.nickHider = new NickHider();
        OringoClient.animations = new Animations();
        OringoClient.animationCreator = new AnimationCreator();
        OringoClient.camera = new Camera();
        OringoClient.mithrilMacro = new MithrilMacro();
        OringoClient.derp = new Derp();
        OringoClient.hitboxes = new Hitboxes();
        OringoClient.noRotate = new NoRotate();
        OringoClient.phase = new Phase();
        OringoClient.freeCam = new FreeCam();
        OringoClient.giants = new Giants();
        mc = Minecraft.func_71410_x();
        OringoClient.stop = new ArrayList<BlockPos>();
        OringoClient.devMode = false;
        OringoClient.scheduledTasks = new ArrayList<Runnable>();
        OringoClient.capes = new HashMap<String, ResourceLocation>();
        OringoClient.capesLoaded = new HashMap<File, ResourceLocation>();
    }
}
