package terrorhelmut.pteroBackupRestorer;

import com.google.inject.Inject;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import org.slf4j.Logger;

@Plugin(id = "pterobackuprestorer", name = "PteroBackupRestorer", version = "1.0-SNAPSHOT")
public class PteroBackupRestorer {

    private final ProxyServer server;
    private final Logger logger;
    private ConfigManager configManager;

    @Inject
    public PteroBackupRestorer(ProxyServer server, Logger logger) {
        this.server = server;
        this.logger = logger;
    }

    @com.velocitypowered.api.event.Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        this.configManager = new ConfigManager(this.logger);
        CommandManager commandManager = this.server.getCommandManager();
        commandManager.register("pbackuprestore", new RestoreCommand(this.configManager, this.logger));
    }
}
