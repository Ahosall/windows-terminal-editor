package xyz.ahosall.wte.commands.profiles;

import java.util.UUID;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import xyz.ahosall.wte.entities.TerminalProfile;
import xyz.ahosall.wte.repositories.WindowsTerminalRepository;
import xyz.ahosall.wte.services.WTProfileService;

@Command(name = "new", description = "Add a new profile in the terminal")
public class NewProfile implements Runnable {

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
  @Option(names = "--default", description = "Set default profile", required = false, defaultValue = "false")
  private Boolean isDefault;

  @Override
  public void run() {
    try {
      WindowsTerminalRepository repo = new WindowsTerminalRepository();
      WTProfileService service = new WTProfileService(repo);

      TerminalProfile profile = new TerminalProfile();
      profile.setGuid(UUID.randomUUID());
      profile.setName(name);
      profile.setElevate(onlyAdmin);
      profile.setCommandline(cmdLine);
      profile.setHidden(isHidden);
      profile.setStartingDirectory(dir);

      service.createProfile(profile);

      if (isDefault) {
        service.setDefaultProfile(profile.getGuid());
      }

      System.out.println("New profile \"" + name + "\" added");
    } catch (Exception e) {
      System.err.println("Failed to add profile: " + e.getMessage());
    }
  }
}
