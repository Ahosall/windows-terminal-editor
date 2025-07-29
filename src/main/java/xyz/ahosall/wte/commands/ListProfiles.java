package xyz.ahosall.wte.commands;

import java.util.List;

import picocli.CommandLine.Command;

import xyz.ahosall.wte.entities.TerminalProfile;
import xyz.ahosall.wte.repositories.WindowsTerminalRepository;
import xyz.ahosall.wte.services.WTProfileService;

@Command(name = "list-profiles", description = "List all profiles registered on the terminal")
public class ListProfiles implements Runnable {
  @Override
  public void run() {
    try {
      WindowsTerminalRepository repo = new WindowsTerminalRepository();
      WTProfileService service = new WTProfileService(repo);

      List<TerminalProfile> profiles = service.listProfiles();
      TerminalProfile defaultProfile = service.getDefaultProfile();

      System.out.println("Profiles found:");
      for (TerminalProfile profile : profiles) {
        String profileName = profile.getName();
        String profileGuid = profile.getGuid().toString();
        String isAdmin = profile.getElevate() ? " (admin mode)" : "";
        String isDefault = defaultProfile.getGuid().equals(profile.getGuid()) ? "(default)" : "";

        System.out.println(" - " + profileName + " (" + profileGuid + ")" + isAdmin + " " + isDefault);
      }
    } catch (Exception e) {
      System.err.println("Failed to list profiles: " + e.getMessage());
    }
  }
}
