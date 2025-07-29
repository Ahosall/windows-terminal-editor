package xyz.ahosall.wte.entities;

import java.util.UUID;

import org.json.JSONObject;

public class TerminalProfile {

  private UUID guid;
  private Boolean elevate;
  private String name;
  private String commandline;
  private boolean hidden;
  private String startingDirectory;

  public TerminalProfile() {
  }

  public TerminalProfile(UUID guid, Boolean elevate, String name, String commandline, boolean hidden,
      String startingDirectory) {
    this.guid = guid;
    this.elevate = elevate;
    this.name = name;
    this.commandline = commandline;
    this.hidden = hidden;
    this.startingDirectory = startingDirectory;
  }

  // Getters e Setters

  public UUID getGuid() {
    return guid;
  }

  public void setGuid(UUID guid) {
    this.guid = guid;
  }

  public Boolean getElevate() {
    return elevate;
  }

  public void setElevate(Boolean elevate) {
    this.elevate = elevate;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCommandline() {
    return commandline;
  }

  public void setCommandline(String commandline) {
    this.commandline = commandline;
  }

  public boolean isHidden() {
    return hidden;
  }

  public void setHidden(boolean hidden) {
    this.hidden = hidden;
  }

  public String getStartingDirectory() {
    return startingDirectory;
  }

  public void setStartingDirectory(String startingDirectory) {
    this.startingDirectory = startingDirectory;
  }

  public JSONObject toJson() {
    JSONObject json = new JSONObject();

    json.put("guid", "{" + getGuid().toString() + "}");
    json.put("name", getName());
    json.put("elevate", getElevate());
    json.put("commandline", getCommandline());
    json.put("hidden", isHidden());
    json.put("startingDirectory", getStartingDirectory());

    return json;
  }

  @Override
  public String toString() {
    return "TerminalProfile{" +
        "\n  guid='{" + getGuid().toString() + "}'" +
        ",\n  name='" + name + "\'" +
        ",\n  elevate='" + elevate + "\'" +
        ",\n  commandline='" + commandline + "\'" +
        ",\n  hidden=" + hidden + "" +
        ",\n  startingDirectory='" + startingDirectory + "\'" +
        "\n}";
  }

}
