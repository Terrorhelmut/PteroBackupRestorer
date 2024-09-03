package terrorhelmut.pteroBackupRestorer;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import net.kyori.adventure.text.Component;
import org.slf4j.Logger;

import java.util.List;

public class RestoreCommand implements SimpleCommand {
    private final ConfigManager configManager;
    private final Logger logger;

    public RestoreCommand(ConfigManager configManager, Logger logger) {
        this.configManager = configManager;
        this.logger = logger;
    }

    @Override
    public void execute(Invocation invocation) {
        CommandSource source = invocation.source();
        String[] args = invocation.arguments();

        if (args.length < 1) {
            source.sendMessage(Component.text("Usage: /pbackuprestore <server-name/all>"));
            return;
        }

        String targetServer = args[0];

        if (targetServer.equalsIgnoreCase("all")) {
            configManager.getServers().forEach((serverName, serverConfig) -> {
                if (source.hasPermission(serverConfig.permission)) {
                    restoreBackup(source, serverConfig);
                } else {
                    source.sendMessage(Component.text("You don't have permission to restore " + serverName));
                }
            });
        } else {
            ConfigManager.ServerConfig serverConfig = configManager.getServerConfig(targetServer);
            if (serverConfig != null) {
                if (source.hasPermission(serverConfig.permission)) {
                    restoreBackup(source, serverConfig);
                } else {
                    source.sendMessage(Component.text("You don't have permission to restore this server."));
                }
            } else {
                source.sendMessage(Component.text("Server not found in config."));
            }
        }
    }

    private void restoreBackup(CommandSource source, ConfigManager.ServerConfig serverConfig) {
        PterodactylAPI api = new PterodactylAPI(configManager.getBaseUrl(), configManager.getApiKey(), logger);
        boolean success = api.restoreBackup(serverConfig.serverId, serverConfig.backupId);

        if (success) {
            source.sendMessage(Component.text("Successfully restored backup for server " + serverConfig.serverId));
        } else {
            source.sendMessage(Component.text("Failed to restore backup for server " + serverConfig.serverId));
        }
    }

    @Override
    public List<String> suggest(Invocation invocation) {
        return List.of(); // No suggestions for this command
    }
}
