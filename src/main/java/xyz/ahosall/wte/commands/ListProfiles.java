package xyz.ahosall.wte.commands;

import java.util.List;

import picocli.CommandLine.Command;

import xyz.ahosall.wte.entities.TerminalProfile;
import xyz.ahosall.wte.services.WindowsTerminalService;

@Command(name = "list-profiles", description = "List all profiles registered on the terminal")
public class ListProfiles implements Runnable {
  @Override
  public void run() {
    try {
      WindowsTerminalService service = new WindowsTerminalService();
      List<TerminalProfile> profiles = service.listProfiles();

      System.out.println("Profiles found:");
      for (TerminalProfile profile : profiles) {
        String profileName = profile.getName();
        String profileGuid = profile.getGuid().toString();
        System.out.println(" - " + profileName + " (" + profileGuid + ")");
      }
    } catch (Exception e) {
      System.err.println("Failed to list profiles: " + e.getMessage());
    }
  }
}
