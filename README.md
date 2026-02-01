<p align="center">
   <img alt="Chunky Offline Plugin Logo" src="https://cdn.modrinth.com/data/qwcFrO9b/images/b95641416f69ff27719fbaf3e6f6e255716a100a.png" />
</p>

<p align="center">
   <a alt="Supports Minecraft 1.21.11" title="Go to Minecraft's Server download site" href="https://www.minecraft.net/en-us/download/server" > <img alt="Supported Minecraft Version" src="https://img.shields.io/badge/Minecraft-1.21.11-69986a" /></a>
    <img alt="GitHub Release (latest)" src="https://img.shields.io/github/v/release/X00LA/ChunkyOfflinePlugin">
    <img alt="GitHub Downloads (all assets, all releases)" src="https://img.shields.io/github/downloads/X00LA/ChunkyOfflinePlugin/total" />
    <img alt="GitHub Downloads (latest)" src="https://img.shields.io/github/downloads/X00LA/ChunkyOfflinePlugin/latest/total">
    <a alt="SpigotMC" title="Go to SpigotMC's download site" href="https://hub.spigotmc.org/jenkins/" >
        <img alt="Supports SpigotMC" src="https://img.shields.io/badge/Supports-SpigotMC-gold?style=flat-square" /></a>
    <a alt="Supports PaperMC" title="Go to PaperMC's download site" href="https://papermc.io/downloads/paper" >
        <img alt="Supports PaperMC" src="https://img.shields.io/badge/Supports-PaperMC-blue" /></a>
    <a alt="Supports Folia" title="Go to Folia's download site" href="https://papermc.io/downloads/folia">
        <img alt="Supports Folia" src="https://img.shields.io/badge/Supports-Folia-green" /></a>
    <a alt="Supports PurPur" title="Go to Purpur's download site" href="https://purpurmc.org/download/purpur">
        <img alt="Supports Purpur" src="https://img.shields.io/badge/Supports-PurPur-a947ff" /></a>
</p>

<p align="center">
    <a alt="Ko-Fi Donation Link" title="Support me on Ko-Fi" href="https://ko-fi.com/Y8Y1RKLT1">
       <img alt="Ko-Fi Donation Banner" src="https://ko-fi.com/img/githubbutton_sm.svg" /></a>
</p>

# ChunkyOffline Plugin

A Paper plugin for offline chunk generation using the Chunky plugin. This plugin is fully compatible with Folia.

## Features

- **Automatic Offline Generation**: Automatically generates chunks when the server starts
- **Configurable**: Set custom radius and center coordinates
- **Full Control**: Start, pause, resume, and cancel chunk generation
- **Global Messages**: Optional broadcast messages for generation events
- **Folia Compatible**: Fully async-safe implementation
- **Permission Support**: Full LuckPerms and other permission plugin support

## Requirements

- Paper 1.21.11+
- Chunky plugin (1.3.152+)
- Java 21+

## Installation

1. Download the plugin JAR file
2. Place it in your `plugins/` directory
3. Restart your server

## Configuration

The plugin creates a `config.yml` file in the `plugins/ChunkyOffline/` directory.

### Default Configuration

```yaml
chunk-generation:
  radius: 10000          # Radius in blocks
  center:
    x: 0                 # Center X coordinate
    z: 0                 # Center Z coordinate
  global-messages: true  # Broadcast messages to all players
```

## Commands

All commands require the corresponding permission or OP level.

### Start Generation
```
/chunkyoffline start
/co start
```
Permission: `chunkyoffline.admin.start`

### Pause Generation
```
/chunkyoffline pause
/co pause
```
Permission: `chunkyoffline.admin.pause`

### Resume Generation
```
/chunkyoffline resume
/co resume
```
Permission: `chunkyoffline.admin.resume`

### Cancel Generation
```
/chunkyoffline cancel
/co cancel
```
Permission: `chunkyoffline.admin.cancel`

### Configure Settings
```
/chunkyoffline config set radius 10000
/chunkyoffline config set center-x 0
/chunkyoffline config set center-z 0
/chunkyoffline config set global-messages true

/chunkyoffline config get radius
/chunkyoffline config get center-x
/chunkyoffline config get center-z
/chunkyoffline config get global-messages
```
Permission: `chunkyoffline.admin.config`

## Permissions

| Permission | Default | Description |
|---|---|---|
| `chunkyoffline.admin.start` | OP | Start chunk generation |
| `chunkyoffline.admin.pause` | OP | Pause chunk generation |
| `chunkyoffline.admin.resume` | OP | Resume chunk generation |
| `chunkyoffline.admin.cancel` | OP | Cancel chunk generation |
| `chunkyoffline.admin.config` | OP | Configure plugin settings |

## Building

```bash
gradle clean build -nodaemon
```

The compiled JAR will be located in `build/libs/`.

## License

See LICENSE file for details.

## Support

For issues or feature requests, please create an issue on GitHub.
Want your language supported by this plugin? Feel free to use the en.json as template and create a new one with your language and open a request to merge it.
