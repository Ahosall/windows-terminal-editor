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
    UUID guid = normalizeGuid(guidStr);
    JSONObject profileJson = getProfileByGuid(guid, repo.get());

    if (profileJson == null) {
      throw new NoSuchElementException("Profile not found with GUID: " + guidStr);
    }

    return mapJsonToProfile(profileJson);
  }

  public TerminalProfile getDefaultProfile() {
    JSONObject settingsJson = repo.get();
    String guidStr = repo.get().getString("defaultProfile");

    if (guidStr == null) {
      throw new IllegalStateException("No default profile set in settings");
    }

    UUID guid = normalizeGuid(guidStr);
    JSONObject profileJson = getProfileByGuid(guid, settingsJson);

    if (profileJson == null) {
      throw new NoSuchElementException("Default profile not found with GUID: " + guidStr);
    }

    return mapJsonToProfile(profileJson);
  }

  public void setDefaultProfile(UUID guid) {
    JSONObject settingsJson = repo.get();
    JSONObject profile = getProfileByGuid(guid, settingsJson);

    if (profile == null) {
      throw new NoSuchElementException("Cannot set default profile. GUID not found: " + guid);
    }

    settingsJson.put("defaultProfile", "{" + guid.toString() + "}");
    repo.save(settingsJson.toString(2));
  }

  public List<TerminalProfile> listProfiles() {
    JSONObject settingsJson = repo.get();
    JSONArray profilesJson = settingsJson.getJSONObject("profiles").getJSONArray("list");

    List<TerminalProfile> profiles = new ArrayList<>();
    for (int i = 0; i < profilesJson.length(); i++) {
      JSONObject profileJson = profilesJson.getJSONObject(i);
      profiles.add(mapJsonToProfile(profileJson));
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

  public void editProfile(UUID guid, TerminalProfile terminal) {
    JSONObject settingsJson = repo.get();
    JSONObject terminalJson = terminal.toJson();

    JSONArray profilesJson = settingsJson
        .getJSONObject("profiles")
        .getJSONArray("list")
        .put(terminalJson);

    for (int i = 0; i < profilesJson.length(); i++) {
      JSONObject profileJson = profilesJson.getJSONObject(i);
      UUID profileGuid = normalizeGuid(profileJson.optString("guid", null));

      if (profileGuid != null && profileGuid.equals(guid)) {
        profilesJson.put(i, terminalJson);
        repo.save(settingsJson.toString(2));
        return;
      }
    }

    repo.save(settingsJson.toString(2));
  }

  public void removeProfile(UUID guid) {
    JSONObject settingsJson = repo.get();
    JSONArray profilesJson = settingsJson.getJSONObject("profiles").getJSONArray("list");

    for (int i = 0; i < profilesJson.length(); i++) {
      JSONObject profileJson = profilesJson.getJSONObject(i);
      UUID profileGuid = normalizeGuid(profileJson.optString("guid", null));

      if (profileGuid != null && profileGuid.equals(guid)) {
        profilesJson.remove(i);
        repo.save(settingsJson.toString(2));
        return;
      }
    }

    throw new NoSuchElementException("Porfile not found with GUID: " + guid);
  }

  private JSONObject getProfileByGuid(UUID guid, JSONObject settingsJson) {
    JSONArray profilesJson = settingsJson.getJSONObject("profiles").getJSONArray("list");

    for (int i = 0; i < profilesJson.length(); i++) {
      JSONObject profileJson = profilesJson.getJSONObject(i);
      UUID profileGuid = normalizeGuid(profileJson.optString("guid", null));

      if (profileGuid != null && profileGuid.equals(guid)) {
        return profileJson;
      }
    }

    return null;
  }

  private TerminalProfile mapJsonToProfile(JSONObject json) {
    TerminalProfile profile = new TerminalProfile();
    profile.setGuid(normalizeGuid(json.optString("guid", null)));
    profile.setName(json.optString("name", ""));
    profile.setElevate(json.optBoolean("elevate", false));
    profile.setCommandline(json.optString("commandline", ""));
    profile.setHidden(json.optBoolean("hidden", false));
    profile.setStartingDirectory(json.optString("startingDirectory", ""));
    return profile;
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
