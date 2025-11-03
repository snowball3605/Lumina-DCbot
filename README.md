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

Lumina is a Discord bot that combines **plug-in functionality** and **hot reloading**.
allowing users to customize the features they want.

## Functions

### Server Manage

- Dynamic Voice - Dynamic voice channels, no manual operation required.
- Welcome Message - Automatic welcome message for new users

### Entertainment function
- Level System - You can gain experience simply by sending messages.
- Rank System - Experience-based leaderboards

## How to use？

### 1. Download latest Bot-core
![img_1.png](photo/img_1.png)

### 2. Enter your SETTING.yml
```
TOKEN: "xxxxxxxxx" # your discord token

lang: en_UK

Logger: true # true when you like to output log
```

### 3. Run

```
# must JDA 25+
java -jar Bot-core.jar
```

### 4. Reload config

Enter slash command /reload in any group when you edit config, but only owner can use /reload

## Slash Command Guild

- /info - Info about this bot
- /rank - Check how many exp you have

## console Command Guild
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

### v1.0.5

- Add plug-in functionality

## Problem Reporting

- You can use Issues to report

## Thank
Thank you for your use, if you like it, please star this project :)
