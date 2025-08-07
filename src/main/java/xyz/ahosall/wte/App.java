package xyz.ahosall.wte;

import picocli.CommandLine;
import picocli.CommandLine.Command;

import xyz.ahosall.wte.commands.profiles.*;

@Command(name = "wte", description = "Windows Terminal Editor", subcommands = {
    ListProfiles.class
})
public class App implements Runnable {

  @Override
  public void run() {
    System.out.println("Use 'wte help' to see all available commands");
  }

  public static void main(String[] args) {
    int exitCode = new CommandLine(new App()).execute(args);
    System.exit(exitCode);
  }
}
