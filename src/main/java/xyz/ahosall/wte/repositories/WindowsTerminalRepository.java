package xyz.ahosall.wte.repositories;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.*;

public class WindowsTerminalRepository {
  private final String settingsPath;
  private final JSONObject settingsJson;

  public WindowsTerminalRepository() throws Exception {
    String localAppData = System.getenv("LOCALAPPDATA");
    this.settingsPath = localAppData
        + "\\Packages\\Microsoft.WindowsTerminal_8wekyb3d8bbwe\\LocalState\\settings.json";

    String content = Files.readString(Paths.get(settingsPath));
    this.settingsJson = new JSONObject(content);
  }

  public JSONObject get() {
    return this.settingsJson;
  }

  public void save(String content) {
    try {
      FileWriter file = new FileWriter(settingsPath);
      file.write(content);
      file.close();
    } catch (IOException e) {
      System.err.println("Error saving settings: " + e.getMessage());
    }
  }
}
