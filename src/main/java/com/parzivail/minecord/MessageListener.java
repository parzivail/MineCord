package com.parzivail.minecord;

import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class MessageListener
{
	@EventSubscriber
	public void onMessageReceived(MessageReceivedEvent event)
	{
		if (event.getAuthor().isBot())
			return;

		if (event.getMessage().getFormattedContent().equalsIgnoreCase("~minecord-ping"))
			event.getChannel().sendMessage("Pong!");
		else
		{
			if (event.getChannel().getLongID() == MineCord.config.getDiscordListenChannel())
			{
				synchronized (MineCord.messageQueue)
				{
					MineCord.messageQueue.add(new MessageQueueEntry(event.getAuthor().getName(), event.getMessage().getFormattedContent()));
				}
			}
		}
	}
}
