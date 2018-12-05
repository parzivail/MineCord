package com.parzivail.minecord;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraftforge.common.MinecraftForge;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventDispatcher;
import sx.blah.discord.util.DiscordException;

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
	public static IDiscordClient discordApi;

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
	public void init(FMLServerStartingEvent args)
	{
		String token = config.getDiscordBotToken();
		ClientBuilder clientBuilder = new ClientBuilder();
		clientBuilder.withToken(token);

		try
		{
			discordApi = clientBuilder.login(); // Creates the client instance and logs the client in
		}
		catch (DiscordException e)
		{
			Lumberjack.err("Unable to initialize Discord client!");
			throw e;
		}

		EventDispatcher dispatcher = discordApi.getDispatcher(); // Gets the EventDispatcher instance for this client instance
		dispatcher.registerListener(new MessageListener());

		Lumberjack.info("MineCord invite link: https://discordapp.com/api/oauth2/authorize?client_id=518948872442216454&permissions=67584&scope=bot");
	}
}
