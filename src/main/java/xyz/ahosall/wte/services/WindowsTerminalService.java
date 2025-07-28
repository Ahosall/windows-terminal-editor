package xyz.ahosall.wte.services;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;

import xyz.ahosall.wte.entities.TerminalProfile;

public class WindowsTerminalService {
  private final String settingsPath;

  public WindowsTerminalService() throws Exception {
    String localAppData = System.getenv("LOCALAPPDATA");

    this.settingsPath = localAppData + "\\Packages\\Microsoft.WindowsTerminal_8wekyb3d8bbwe\\LocalState\\settings.json";
  }

  public List<TerminalProfile> listProfiles() {
    List<TerminalProfile> profiles = new ArrayList<>();

    JSONArray list = getSettingsJson().getJSONObject("profiles").getJSONArray("list");

    for (int i = 0; i < list.length(); i++) {
      JSONObject p = list.getJSONObject(i);

      TerminalProfile profile = new TerminalProfile();
      String guidStr = p.getString("guid");
      UUID guid = null;

      if (guidStr != null && guidStr.matches("\\{?[0-9a-fA-F\\-]{36}\\}?")) {
        guid = UUID.fromString(guidStr.replaceAll("[{}]", ""));
      }

      profile.setGuid(guid);
      profile.setName(p.optString("name", ""));
      profile.setCommandline(p.optString("commandline", ""));
      profile.setElevate(p.optBoolean("elevate", false));
      profile.setHidden(p.optBoolean("hidden", false));
      profile.setStartingDirectory(p.optString("startingDirectory", ""));
      profile.setColorScheme(p.optString("colorScheme", ""));
      profile.setFontFace(p.optString("fontFace", ""));
      profile.setFontSize(p.optInt("fontSize", 12));
      profile.setBackgroundImage(p.optString("backgroundImage", ""));

      profiles.add(profile);
    }

    return profiles;
  }

  public void createProfile(TerminalProfile profile) {
    try {
      JSONObject settingsJson = getSettingsJson();
      settingsJson.getJSONObject("profiles").getJSONArray("list").put(profile.toJson());

      FileWriter file = new FileWriter(settingsPath);
      file.write(settingsJson.toString(2));
      file.close();
    } catch (IOException e) {
      System.err.println("Error saving profiles: " + e.getMessage());
      e.printStackTrace();
    }
  }

  private JSONObject getSettingsJson() {
    try {
      String content = Files.readString(Paths.get(settingsPath));
      return new JSONObject(content);
    } catch (Exception e) {
      System.err.println("Error reading settings: " + e.getMessage());
      e.printStackTrace();
    }
    return null;
  }
}
