# MineCord
Minecraft Server <-> Discord interop.

# Setup

## Discord Bot Setup
1) Open the [Discord applicatons page](https://discordapp.com/developers/applications/).
2) Click "new application". Give it a name, picture and description.
3) Click "Create Bot User" and click "Yes, Do It!" when the dialog pops up.
4) Copy the token to your clipboard, we'll need it later.

## Mod Setup
Install as a normal Forge mod. You will also need to [download JDA](https://github.com/DV8FromTheWorld/JDA/releases/download/v3.8.0/JDA-3.8.0_423-withDependencies-no-opus.jar) and put it in `mods/`. Run the server once (which will crash, since the bot isn't configured) so that a config file will be generated. In the config file, supply the following values:

1) `discordBotToken`: The token for the bot you just created
2) `discordListenChannelId`: The Channel ID* of the Discord channel that MineCord will listen to and mirror in Minecraft
3) `discordPostChannelId`; The Channel ID of the Discord channel that Minecord will post all Minecraft chat messages to. Typically, this is the same as `discordListenChannelId`.

Launch the game again. Provided the values you entered were correct, an invite link will be generated and posted in the server console. Click it, and invite the bot to your server. Make sure the bot has the correct message sending and recieving permissions on the channels you selected.

\* You can get Channel IDs by going to Discord. First, enable Developer Mode: User Settings -> Appearance -> Enable Developer Mode. Then, in the desired server, right click the channel you want and click "Copy ID".
