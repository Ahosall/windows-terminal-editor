package xyz.ahosall.wte.commands;

import java.util.UUID;

import picocli.CommandLine.*;
import xyz.ahosall.wte.entities.TerminalProfile;
import xyz.ahosall.wte.services.WindowsTerminalService;

@Command(name = "add-profile", description = "Add a new profile in the terminal")
public class AddProfile implements Runnable {

  @Option(names = "--name", description = "Profile name", required = true)
  private String name;
  @Option(names = "--cmd", description = "Executable file", required = true)
  private String cmdLine;
  @Option(names = "--admin", description = "Admin mode", required = false, defaultValue = "false")
  private Boolean onlyAdmin;
  @Option(names = "--hidden", description = "Hidden profile", required = false, defaultValue = "false")
  private Boolean isHidden;
  @Option(names = "--dir", description = "Directory where the profile is loaded", required = false)
  private String dir;

  @Option(names = "--color", description = "Color scheme", required = false)
  private String color;
  @Option(names = "--font", description = "Font face", required = false)
  private String font;
  @Option(names = "--font-size", description = "Font size", required = false, defaultValue = "12")
  private int size;
  @Option(names = "--bg", description = "Background image", required = false)
  private String background;

  @Override
  public void run() {
    try {

      WindowsTerminalService service = new WindowsTerminalService();
      TerminalProfile newProfile = new TerminalProfile();
      newProfile.setGuid(UUID.randomUUID());
      newProfile.setName(name);
      newProfile.setElevate(onlyAdmin);
      newProfile.setCommandline(cmdLine);
      newProfile.setHidden(isHidden);
      newProfile.setStartingDirectory(dir);
      newProfile.setColorScheme(color);
      newProfile.setFontFace(font);
      newProfile.setFontSize(size);
      newProfile.setBackgroundImage(background);

      service.createProfile(newProfile);
    } catch (Exception e) {
      System.err.println("Failed to add profile: " + e.getMessage());
    }
  }
}
