package com.parzivail.minecord;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.event.ServerChatEvent;
import sx.blah.discord.handle.obj.IChannel;

public class MineCordEventHandler
{
	@SubscribeEvent
	@SideOnly(Side.SERVER)
	public void on(ServerChatEvent args)
	{
		IChannel channel = MineCord.discordApi.getChannelByID(MineCord.config.getDiscordPostChannel());
		if (channel != null)
		{
			channel.sendMessage(String.format("<%s> %s", args.username, args.message));
		}
		else
			Lumberjack.err("Configured discordPostChannelId does not exist!");
	}

	@SubscribeEvent
	@SideOnly(Side.SERVER)
	public void on(TickEvent.ServerTickEvent args)
	{
		if (args.phase != TickEvent.Phase.START)
			return;

		synchronized (MineCord.messageQueue)
		{
			while (!MineCord.messageQueue.isEmpty())
			{
				MessageQueueEntry message = MineCord.messageQueue.remove();
				MinecraftServer.getServer().getConfigurationManager().sendChatMsg(new ChatComponentText(String.format("<%s> %s", message.nickname, message.message)));
			}
		}
	}
}
