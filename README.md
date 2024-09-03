---

# PteroBackupRestorer

**PteroBackupRestorer** is a Minecraft Velocity plugin that allows server administrators to restore backups for specific or all servers within their network using the Pterodactyl API. This plugin provides a streamlined and efficient way to manage server backups directly from the game or console.

### For further help contact me on discord my username is Terrorhelmut

## Features

- **Tested Minecraft Version**: 1.21
- **Restore Specific or All Servers**: Use the `/pbackuprestore <server-name/all>` command to restore backups for individual servers or all servers configured in the plugin.
- **Configurable Permissions**: Assign permissions to control which users can restore backups for different servers, ensuring secure and authorized operations.
- **Detailed Feedback**: Receive in-game or console messages confirming whether a backup was successfully restored, with clear success and error notifications.
- **Customizable Configuration**: Easily configure multiple server IDs, backup IDs, and associated permissions in the `config.yml` file to tailor the plugin to your network’s needs.
- **Pterodactyl API Integration**: Seamlessly integrates with the Pterodactyl panel, leveraging its API to manage and restore server backups efficiently.
- **Debug Mode**: Enable debug mode in the configuration to get detailed logs and information during command execution, helping in troubleshooting and monitoring.

## Configuration

The plugin automatically generates a `config.yml` file if it doesn't exist. This file is where you specify your Pterodactyl API key, base URL, and configure the servers and backups you want to manage.

```yaml
apiKey: your-api-key-here
baseUrl: https://your-pterodactyl-instance.com
debug: false
servers:
  example-server1:
    backupId: example-backup1-id
    permission: pbackuprestore.example-server1
    serverId: example-server1-id
  example-server2:
    backupId: example-backup2-id
    permission: pbackuprestore.example-server2
    serverId: example-server2-id

# Add more servers as needed
```

## Requirements

- **Java Version**: 17
- **Pterodactyl Panel**: A working Pterodactyl instance with API access.

## Installation

1. Download the latest release of the plugin.
2. Place the JAR file in your `plugins` directory.
3. Start your Velocity proxy to generate the `config.yml`.
4. Edit the `config.yml` to include your Pterodactyl API details and server configurations.
5. Reload or restart your proxy to apply the changes.

## Usage

- To restore a specific server, use the command: `/pbackuprestore <server-name>`
- To restore backups for all configured servers, use the command: `/pbackuprestore all`
