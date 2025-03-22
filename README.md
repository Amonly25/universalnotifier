# Universal Notifier Setup Guide
### Overview
Universal Notifier allows you to send notifications from your Minecraft server to Telegram, Discord, and Email. This guide will walk you through the setup process, including creating the necessary bots and configuring the system.
## Telegram Configuration
### Create a Telegram Bot
1. Open Telegram and search for BotFather.
2. Start a chat with BotFather and send the command:
```
/newbot
```
3. Follow the instructions to name your bot and receive the Bot Token.
4. Copy the generated token for later use.
### Get a Chat ID
1. Add the bot to a group or use it in a private chat.
2. Open the following URL in your browser:
```
https://api.telegram.org/botYOUR_BOT_TOKEN/getUpdates
```
3. Look for "chat":{"id":YOUR_CHAT_ID} in the JSON response.
4. Use this Chat ID in the configuration.
### Configure Telegram in config.yml
```
telegram:
  bot_token: "YOUR_TELEGRAM_BOT_TOKEN"
  chats_id:
    -123456789: #Public chat
      types:
      - STARTUP
      - SHUTDOWN
      - COMMAND_BROADCAST
    987654321: #Private chat
      types:
      - COMMAND_REPORT
      - LOW_TPS
```
## Discord Configuration
### Create a Discord Bot
1. Go to the Discord Developer Portal.
2. Click New Application and give it a name.
3. Navigate to Bot -> Add Bot.
4. Click Reset Token to generate a new bot token and copy it.
5. Under Privileged Gateway Intents, enable:
```
MESSAGE CONTENT INTENT
SERVER MEMBERS INTENT (if required)
```
### Invite the Bot to Your Server
1. Go to OAuth2 -> URL Generator.
2. Select Bot and Administrator permissions.
3. Copy the generated link and open it in your browser.
4. Select the server and invite the bot.
### Configure Telegram in config.yml
```
telegram:
  bot_token: "YOUR_TELEGRAM_BOT_TOKEN"
  chats_id:
    -123456789: #Public chat
      types:
      - STARTUP
      - SHUTDOWN
      - COMMAND_BROADCAST
    987654321: #Private chat
      types:
      - COMMAND_REPORT
      - LOW_TPS
```
## Email Configuration
### Get SMTP Details
1. Use your email provider’s SMTP settings. Common providers:
```
Gmail:
Host: smtp.gmail.com
Port: 465 (SSL) or 587 (TLS)
Outlook:
Host: smtp.office365.com
Port: 587
Custom Mail Server:
Contact your provider for SMTP details.
```
2. Ensure your email account allows SMTP access.
3. If using Gmail, enable Less Secure Apps or create an App Password.
### Configure Email in config.yml
```
email:
  host: "smtp.yourmail.com"
  port: "465"
  username: "your_email@yourmail.com"
  password: "your_password"
  ssl: "true" # Use a secure connection
  debug: "false"
  timeout: "10000" # 10 seconds
  subject: "Minecraft Server Notification"
  list:
    user@example*com:
      types:
      - STARTUP
      - SHUTDOWN
      - COMMAND_REPORT
```

## Developers
### Usage
```
<repositories>
	<repository>
		<id>jitpack.io</id>
		<url>https://jitpack.io</url>
	</repository>
</repositories>
<dependencies>
	<dependency>
		<groupId>com.github.Amonly25</groupId>
        	<artifactId>universalnotifier</artifactId>
        	<version>-SNAPSHOT</version>
	</dependency>
</dependencies>

if (plugin.getServer().getPluginManager().getPlugin("UniversalNotifier") != null) {
	UniversalNotifier notifier = UniversalNotifier.getInstance();
	String message = "Your custom message";
	notifier.getNotificationManager().broadcastToAll(Alert.CUSTOM, message);
}
```
