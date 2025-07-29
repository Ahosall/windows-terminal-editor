package xyz.ahosall.wte.services;

import java.util.*;

import org.json.JSONArray;
import org.json.JSONObject;

import xyz.ahosall.wte.entities.TerminalProfile;
import xyz.ahosall.wte.repositories.WindowsTerminalRepository;

public class WTProfileService {
  private final WindowsTerminalRepository repo;

  public WTProfileService(WindowsTerminalRepository repo) {
    this.repo = repo;
  }

  public TerminalProfile getProfile(String guidStr) {
    UUID profileGuid = normalizeGuid(guidStr);

    TerminalProfile profile = new TerminalProfile();
    JSONObject profileJson = getProfileByGuid(profileGuid);
    profile.setGuid(profileGuid);
    profile.setName(profileJson.optString("name", ""));
    profile.setElevate(profileJson.optBoolean("elevate", false));
    profile.setCommandline(profileJson.optString("commandline", ""));
    profile.setHidden(profileJson.optBoolean("hidden", false));
    profile.setStartingDirectory(profileJson.optString("startingDirectory", ""));

    return profile;
  }

  public TerminalProfile getDefaultProfile() {
    TerminalProfile profile = new TerminalProfile();
    String profileDefaultGuid = repo.get().getString("defaultProfile");

    UUID profileGuid = normalizeGuid(profileDefaultGuid);
    JSONObject profileJson = getProfileByGuid(profileGuid);
    profile.setGuid(profileGuid);
    profile.setName(profileJson.optString("name", ""));
    profile.setElevate(profileJson.optBoolean("elevate", false));
    profile.setCommandline(profileJson.optString("commandline", ""));
    profile.setHidden(profileJson.optBoolean("hidden", false));
    profile.setStartingDirectory(profileJson.optString("startingDirectory", ""));

    return profile;
  }

  public List<TerminalProfile> listProfiles() {
    List<TerminalProfile> profiles = new ArrayList<>();
    JSONArray profilesJson = repo.get().getJSONObject("profiles").getJSONArray("list");

    for (int i = 0; i < profilesJson.length(); i++) {
      JSONObject profileJson = profilesJson.getJSONObject(i);
      UUID profileGuid = normalizeGuid(profileJson.getString("guid"));

      TerminalProfile profile = new TerminalProfile();
      profile.setGuid(profileGuid);
      profile.setName(profileJson.optString("name", ""));
      profile.setElevate(profileJson.optBoolean("elevate", false));
      profile.setCommandline(profileJson.optString("commandline", ""));
      profile.setHidden(profileJson.optBoolean("hidden", false));
      profile.setStartingDirectory(profileJson.optString("startingDirectory", ""));

      profiles.add(profile);
    }

    return profiles;
  }

  public void createProfile(TerminalProfile terminal) {
    JSONObject terminalJson = terminal.toJson();
    JSONObject settingsJson = repo.get();
    settingsJson
        .getJSONObject("profiles")
        .getJSONArray("list")
        .put(terminalJson);

    repo.save(settingsJson.toString(2));
  }

  public void removeProfile(UUID guid) {
    JSONObject profileJson = getProfileByGuid(guid);
    JSONObject settingsJson = repo.get();
    JSONArray profilesJson = settingsJson.getJSONObject("profiles").getJSONArray("list");

    if (profileJson != null) {
      for (int i = 0; i < profilesJson.length(); i++) {
        JSONObject profileFound = profilesJson.getJSONObject(i);
        UUID profileGuid = normalizeGuid(profileFound.getString("guid"));

        if (profileGuid.equals(guid)) {
          settingsJson.getJSONObject("profiles").getJSONArray("list").remove(i);
          break;
        }
      }

      repo.save(settingsJson.toString(2));
      return;
    }

    throw new Error("Profile not found");
  }

  private JSONObject getProfileByGuid(UUID guid) {
    JSONObject profileFound = null;
    JSONArray profilesJson = repo.get().getJSONObject("profiles").getJSONArray("list");

    for (int i = 0; i < profilesJson.length(); i++) {
      JSONObject profileJson = profilesJson.getJSONObject(i);
      UUID profileGuid = normalizeGuid(profileJson.getString("guid"));

      if (profileGuid.equals(guid)) {
        profileFound = profileJson;
        break;
      }
    }

    return profileFound;
  }

  private UUID normalizeGuid(String guidStr) {
    UUID guid = null;

    if (guidStr == null) {
      guid = UUID.randomUUID();
    }

    if (guidStr != null && guidStr.matches("\\{?[0-9a-fA-F\\-]{36}\\}?")) {
      guid = UUID.fromString(guidStr.replaceAll("[{}]", ""));
    }

    return guid;
  }
}
