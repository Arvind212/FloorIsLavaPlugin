package me.arvind.FloorIsLava;

import org.bukkit.plugin.java.JavaPlugin;

import me.arvind.FloorIsLava.commands.FloorIsLavaCommand;

public class Main extends JavaPlugin {

    private static Main instance;

    @Override
    public void onEnable() {
        instance = this;

        // Register commands
        getCommand("fil").setExecutor(new FloorIsLavaCommand());

    }

    public static Main getInstance() {
        return instance;
    }
}
