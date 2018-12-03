package com.parzivail.minecord;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import java.io.File;

public class Config extends Configuration
{
	private static final String DEFAULT_TOKEN = "SECRET TOKEN";
	private Property discordBotToken;
	private Property discordListenChannel;
	private Property discordPostChannel;

	public Config(File file)
	{
		super(file);
		load();
	}

	public void load()
	{
		super.load();

		discordBotToken = get(Configuration.CATEGORY_GENERAL, "discordBotToken", DEFAULT_TOKEN, "The Discord bot's token");
		discordListenChannel = get(Configuration.CATEGORY_GENERAL, "discordListenChannelId", "000000000000000000", "The channel the bot listen for messages in");
		discordPostChannel = get(Configuration.CATEGORY_GENERAL, "discordPostChannelId", "000000000000000000", "The channel the bot will post Minecraft chats to");

		if (hasChanged())
			save();
	}

	public String getDiscordBotToken()
	{
		return discordBotToken.getString();
	}

	public long getDiscordListenChannel()
	{
		return Long.parseLong(discordListenChannel.getString());
	}

	public long getDiscordPostChannel()
	{
		return Long.parseLong(discordPostChannel.getString());
	}

	public String getDefaultToken()
	{
		return DEFAULT_TOKEN;
	}
}
