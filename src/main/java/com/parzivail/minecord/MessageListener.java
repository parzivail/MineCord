package com.parzivail.minecord;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class MessageListener extends ListenerAdapter
{
	@Override
	public void onMessageReceived(MessageReceivedEvent event)
	{
		if (event.getAuthor().isBot())
			return;

		if (event.getMessage().getContentDisplay().equalsIgnoreCase("~minecord-ping"))
			event.getChannel().sendMessage("Pong!").queue();
		else
		{
			if (event.getChannel().getIdLong() == MineCord.config.getDiscordListenChannel())
			{
				synchronized (MineCord.messageQueue)
				{
					MineCord.messageQueue.add(new MessageQueueEntry(event.getAuthor().getName(), event.getMessage().getContentStripped()));
				}
			}
		}
	}
}
