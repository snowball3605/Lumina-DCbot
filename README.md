# Lumina Discord bot

<div style="text-align:center">

<img src="https://img.shields.io/badge/Kotlin-2.2.20-purple?logo=kotlin" />
<img src="https://img.shields.io/badge/JDA-6.1.0-red?logo=" />
<img src="https://img.shields.io/badge/Version-1.0.4-green?logo=github" /> <br>
<img src="https://img.shields.io/badge/Kotlin-JVM-purple?logo=kotlin" />
<img src="https://img.shields.io/badge/BUILD-Maven-blue?logo=maven" />
<img src="https://img.shields.io/badge/JDK-25-orange?logo=openjdk" />

</div>

## Introduction

Lumina is a **multi-functional** Discord bot that aims to make server management something everyone can do.
Lumina is developed based on Kotlin, JDA, **Responsive Programming**. It **supports 10 languages**, including common ones like Chinese and English, and allows users to **translate text as needed**.

## Functions

### Server Manage

- Dynamic Voice - Dynamic voice channels, no manual operation required.
- Welcome Message - Automatic welcome message for new users

### Entertainment function
- Level System - You can gain experience simply by sending messages.
- Rank System - Experience-based leaderboards

## How to use？

### 1. Download latest Lumina-x.x.x.zip and upzip
![img.png](photo/img.png)

### 2. Enter your SETTING.yml
```
TOKEN: DISCORD_BOT_TOKEN  # NOT NULL, your discord bot token
JOIN_CHANNEL: CHANNEL_ID_LONG # NULL when you don't use it, [Text Channel id]  Send welcome message when new user join

Dynamic_Voice_ID:
- "CHANNEL_ID_LONG-CATEGORY_ID_LONG" # NULL when you don't use it, [Voice channel id] create a new voice channel in category and move user to new voice channel when user join this voice channel

Dynamic_Voice_Name: "%Voice_Name% - VC" # NOT NULL if you use this function, name when create a new voice channel, %Voice_Name% meaning this user name

Owner_Discord_ID:
- OWNER_USER_ID_LONG" # NOT NULL, Enter owner discord id

Owner_Discord_Server_ID: "OWNER_DISCORD_SERVER_ID" # NOT NULL, Enter this bot's main discord group id
Upgrade_Message_Channel: "CHANNEL_ID_LONG" # NULL when you don't use it, Send level up when user send message get the exp

lang: en_UK # Please enter your preferred language in this bot.
like: xx_XX
Please check /lang/xx_XX.yml

Logger_Output: true ## true if you want this bot send log in contro
```

### 3. Run

```
# must JDA 25+
java -jar Lumina_x.x.x.jar
```

### 4. Reload config

Enter slash command /reload in any group when you edit config, but only owner can use /reload

## Slash Command Guild

- /info - Info about this bot
- /rank - Check how many exp you have
- /reload - Reload config setting

## Update Log

### v1.0.1

- Change the programming language from Java to Kotlin
- Slash Command /info
- Send message when new user join

### v1.0.2

- Added log output
- fix some problem
- Dynamic Voice

### v1.0.3

- Added get exp when you send message
- Slash Command /reload
- Connect database

### v1.0.4

- Added README.md
- Slash Command /rank
- more language

## Problem Reporting

- You can use Issues to report

## Thank
Thank you for your use, if you like it, please star this project :)