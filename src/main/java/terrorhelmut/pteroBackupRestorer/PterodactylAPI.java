package terrorhelmut.pteroBackupRestorer;

import okhttp3.*;
import org.slf4j.Logger;

import java.io.IOException;

public class PterodactylAPI {
    private final String baseUrl;
    private final String apiKey;
    private final Logger logger;

    public PterodactylAPI(String baseUrl, String apiKey, Logger logger) {
        this.baseUrl = baseUrl;
        this.apiKey = apiKey;
        this.logger = logger;
    }

    public boolean restoreBackup(String serverId, String backupId) {
        String url = baseUrl + "/api/client/servers/" + serverId + "/backups/" + backupId + "/restore";

        String json = "{\"truncate\":true}";
        RequestBody body = RequestBody.create(json, MediaType.get("application/json; charset=utf-8"));

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("Authorization", "Bearer " + apiKey)
                .addHeader("Accept", "application/json")
                .build();

        try (Response response = client.newCall(request).execute()) {
            logger.info("Requested URL: " + url);
            logger.info("Response Code: " + response.code());

            if (response.isSuccessful()) {
                logger.info("API response: " + response.body().string());
                return true;
            } else {
                logger.error("Failed to restore backup for server " + serverId + ": " + response.code() + " " + response.message());
                logger.error("API response: " + response.body().string());
            }
        } catch (IOException e) {
            logger.error("Error while restoring backup: " + e.getMessage());
        }

        return false;
    }
}
