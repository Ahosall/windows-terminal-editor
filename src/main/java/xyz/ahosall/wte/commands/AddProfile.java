package xyz.ahosall.wte.commands;

import java.util.UUID;

import picocli.CommandLine.*;

import xyz.ahosall.wte.entities.TerminalProfile;
import xyz.ahosall.wte.repositories.WindowsTerminalRepository;
import xyz.ahosall.wte.services.WTProfileService;

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

  @Override
  public void run() {
    try {
      WindowsTerminalRepository repo = new WindowsTerminalRepository();
      WTProfileService service = new WTProfileService(repo);

      TerminalProfile newProfile = new TerminalProfile();
      newProfile.setGuid(UUID.randomUUID());
      newProfile.setName(name);
      newProfile.setElevate(onlyAdmin);
      newProfile.setCommandline(cmdLine);
      newProfile.setHidden(isHidden);
      newProfile.setStartingDirectory(dir);

      service.createProfile(newProfile);

      System.out.println("New profile \"" + name + "\" added");
    } catch (Exception e) {
      System.err.println("Failed to add profile: " + e.getMessage());
    }
  }
}
