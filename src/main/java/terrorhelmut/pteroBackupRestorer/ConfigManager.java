package terrorhelmut.pteroBackupRestorer;

import org.slf4j.Logger;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.representer.Representer;
import org.yaml.snakeyaml.introspector.BeanAccess;
import org.yaml.snakeyaml.nodes.Tag;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ConfigManager {
    private static final String CONFIG_FILE_NAME = "config.yml";
    private final Logger logger;
    private Map<String, ServerConfig> servers = new HashMap<>();
    private String apiKey;
    private String baseUrl;
    private boolean debug;

    public ConfigManager(Logger logger) {
        this.logger = logger;
        loadConfig();
    }

    private void loadConfig() {
        File configFile = new File("plugins/PteroBackupRestorer", CONFIG_FILE_NAME);
        if (!configFile.exists()) {
            generateDefaultConfig(configFile);
        }

        try (FileInputStream inputStream = new FileInputStream(configFile)) {
            Yaml yaml = new Yaml(new Constructor(ConfigData.class));
            ConfigData configData = yaml.load(inputStream);
            this.apiKey = configData.apiKey;
            this.baseUrl = configData.baseUrl;
            this.debug = configData.debug;
            this.servers = configData.servers;
        } catch (IOException e) {
            logger.error("Error loading config: " + e.getMessage());
        }
    }

    private void generateDefaultConfig(File configFile) {
        try {
            configFile.getParentFile().mkdirs();
            configFile.createNewFile();

            ConfigData defaultConfig = new ConfigData();
            defaultConfig.apiKey = "your-api-key-here";
            defaultConfig.baseUrl = "https://your-pterodactyl-instance.com";
            defaultConfig.debug = false;
            defaultConfig.servers = new HashMap<>();
            defaultConfig.servers.put("example-server1", new ServerConfig("example-server1-id", "example-backup1-id", "serverrestore.example-server1"));
            defaultConfig.servers.put("example-server2", new ServerConfig("example-server2-id", "example-backup2-id", "serverrestore.example-server2"));

            DumperOptions options = new DumperOptions();
            options.setIndent(2);
            options.setPrettyFlow(true);
            options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);

            Representer representer = new Representer(options);
            representer.getPropertyUtils().setSkipMissingProperties(true);
            representer.getPropertyUtils().setBeanAccess(BeanAccess.FIELD);
            representer.addClassTag(ConfigData.class, Tag.MAP);
            representer.addClassTag(ServerConfig.class, Tag.MAP);

            Yaml yaml = new Yaml(representer, options);
            try (FileWriter writer = new FileWriter(configFile)) {
                yaml.dump(defaultConfig, writer);
                writer.write("\n# Add more servers as needed"); // Füge den Kommentar am Ende der Datei hinzu
            }
        } catch (IOException e) {
            logger.error("Error generating default config: " + e.getMessage());
        }
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public boolean isDebug() {
        return debug;
    }

    public Map<String, ServerConfig> getServers() {
        return servers;
    }

    public ServerConfig getServerConfig(String serverName) {
        return servers.get(serverName);
    }

    public static class ServerConfig {
        public String serverId;
        public String backupId;
        public String permission;

        public ServerConfig() {}

        public ServerConfig(String serverId, String backupId, String permission) {
            this.serverId = serverId;
            this.backupId = backupId;
            this.permission = permission;
        }
    }

    private static class ConfigData {
        public String apiKey;
        public String baseUrl;
        public boolean debug;
        public Map<String, ServerConfig> servers;
    }
}
