package com.parzivail.minecord;

class MessageQueueEntry
{
	public final String nickname;
	public final String message;

	public MessageQueueEntry(String nickname, String message)
	{
		this.nickname = nickname;
		this.message = message;
	}
}
