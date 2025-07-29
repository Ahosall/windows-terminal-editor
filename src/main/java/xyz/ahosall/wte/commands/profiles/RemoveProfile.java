package xyz.ahosall.wte.commands.profiles;

import picocli.CommandLine.*;

import xyz.ahosall.wte.entities.TerminalProfile;
import xyz.ahosall.wte.repositories.WindowsTerminalRepository;
import xyz.ahosall.wte.services.WTProfileService;

@Command(name = "remove", description = "Remove a profile from the terminal")
public class RemoveProfile implements Runnable {

  @Option(names = "--guid", description = "Unique profile identifier", required = true)
  private String guid;

  @Override
  public void run() {
    try {
      WindowsTerminalRepository repo = new WindowsTerminalRepository();
      WTProfileService service = new WTProfileService(repo);
      TerminalProfile profile = service.getProfile(guid);
      service.removeProfile(profile.getGuid());
      System.out.println("Profile \"" + profile.getName() + "\" removido com sucesso");
    } catch (Exception e) {
      System.err.println("Failed to remove profile: " + e.getMessage());
    }
  }
}
