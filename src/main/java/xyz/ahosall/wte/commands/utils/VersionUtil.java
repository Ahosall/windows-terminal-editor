package xyz.ahosall.wte.commands.utils;

import picocli.CommandLine.Command;

@Command(name = "version", description = "Show current version")
public class VersionUtil implements Runnable {
  @Override
  public void run() {
    Package pkg = VersionUtil.class.getPackage();
    String version = pkg.getImplementationVersion();

    System.out.println("Windows Terminal Editor v" + version + "");
  }
}
