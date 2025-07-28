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
  private String colorScheme;
  private String fontFace;
  private int fontSize;
  private String backgroundImage;

  public TerminalProfile() {
  }

  public TerminalProfile(UUID guid, Boolean elevate, String name, String commandline, boolean hidden,
      String startingDirectory, String colorScheme, String fontFace,
      int fontSize, String backgroundImage) {
    this.guid = guid;
    this.elevate = elevate;
    this.name = name;
    this.commandline = commandline;
    this.hidden = hidden;
    this.startingDirectory = startingDirectory;
    this.colorScheme = colorScheme;
    this.fontFace = fontFace;
    this.fontSize = fontSize;
    this.backgroundImage = backgroundImage;
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

  public String getColorScheme() {
    return colorScheme;
  }

  public void setColorScheme(String colorScheme) {
    this.colorScheme = colorScheme;
  }

  public String getFontFace() {
    return fontFace;
  }

  public void setFontFace(String fontFace) {
    this.fontFace = fontFace;
  }

  public int getFontSize() {
    return fontSize;
  }

  public void setFontSize(int fontSize) {
    this.fontSize = fontSize;
  }

  public String getBackgroundImage() {
    return backgroundImage;
  }

  public void setBackgroundImage(String backgroundImage) {
    this.backgroundImage = backgroundImage;
  }

  public JSONObject toJson() {
    JSONObject json = new JSONObject();
    json.put("guid", "{" + getGuid().toString() + "}");
    json.put("name", getName());
    json.put("elevate", getElevate());
    json.put("commandline", getCommandline());
    json.put("hidden", isHidden());
    json.put("startingDirectory", getStartingDirectory());
    json.put("colorScheme", getColorScheme());
    json.put("fontFace", getFontFace());
    json.put("font", new JSONObject().put("size", getFontSize()));
    json.put("backgroundImage", getBackgroundImage());
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
        ",\n  colorScheme='" + colorScheme + "\'" +
        ",\n  fontFace='" + fontFace + "\'" +
        ",\n  fontSize=" + fontSize + "" +
        ",\n  backgroundImage='" + backgroundImage + "\'" +
        "\n}";
  }

}
