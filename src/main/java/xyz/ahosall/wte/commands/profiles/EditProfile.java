package xyz.ahosall.wte.commands.profiles;

import picocli.CommandLine.*;

import xyz.ahosall.wte.entities.TerminalProfile;
import xyz.ahosall.wte.repositories.WindowsTerminalRepository;
import xyz.ahosall.wte.services.WTProfileService;

@Command(name = "edit", description = "Edit a profile in the terminal")
public class EditProfile implements Runnable {

  @Parameters(index = "0", description = "Unique profile identifier")
  private String guid;

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
      TerminalProfile profile = service.getProfile(guid);

      profile.setName(name);
      profile.setElevate(onlyAdmin);
      profile.setCommandline(cmdLine);
      profile.setHidden(isHidden);
      profile.setStartingDirectory(dir);

      service.editProfile(profile.getGuid(), profile);

      if (isDefault) {
        service.setDefaultProfile(profile.getGuid());
      }

      System.out.println("Profile \"" + profile.getName() + "\" removido com sucesso");
    } catch (Exception e) {
      System.err.println("Failed to remove profile: " + e.getMessage());
    }
  }
}
