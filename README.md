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

## Compatibility

✅ **Paper Server** - Fully supported
✅ **Folia Server** - Fully supported with async-safe operations

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
gradle build
```

The compiled JAR will be located in `build/libs/`.

## License

See LICENSE file for details.

## Author

xoola

## Support

For issues or feature requests, please create an issue on GitHub.
