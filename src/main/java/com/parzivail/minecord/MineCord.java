package com.parzivail.minecord;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.Permission;
import net.minecraftforge.common.MinecraftForge;

import javax.security.auth.login.LoginException;
import java.util.LinkedList;
import java.util.Queue;

@Mod(modid = MineCord.MODID, version = MineCord.VERSION, acceptableRemoteVersions = "*")
public class MineCord
{
	static final String MODID = "minecord";
	static final String VERSION = "1.0";

	public static Config config;
	private static MineCordEventHandler eventHandler;

	@SideOnly(Side.SERVER)
	public static final Queue<MessageQueueEntry> messageQueue = new LinkedList<>();
	@SideOnly(Side.SERVER)
	public static JDA discordApi;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) throws Exception
	{
		config = new Config(event.getSuggestedConfigurationFile());
		if (config.getDiscordBotToken().equals(config.getDefaultToken()))
			throw new Exception("Discord bot token is required for MineCord.");
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent args)
	{
		eventHandler = new MineCordEventHandler();
		FMLCommonHandler.instance().bus().register(eventHandler);
		MinecraftForge.EVENT_BUS.register(eventHandler);
	}

	@Mod.EventHandler
	@SideOnly(Side.SERVER)
	public void init(FMLServerStartingEvent args) throws LoginException, InterruptedException
	{
		String token = config.getDiscordBotToken();
		discordApi = new JDABuilder(token).addEventListener(new MessageListener()).build();
		discordApi.awaitReady();
		String invite = discordApi.asBot().getInviteUrl(Permission.MESSAGE_READ, Permission.MESSAGE_WRITE);
		Lumberjack.info(String.format("MineCord invite link: %s", invite));
	}
}
