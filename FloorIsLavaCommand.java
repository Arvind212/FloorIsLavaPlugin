package me.arvind.FloorIsLava.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.arvind.FloorIsLava.listeners.FloorIsLavaListener;

public class FloorIsLavaCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be executed by a player.");
            return true;
        }

        Player player = (Player) sender;
        if (!player.hasPermission("floorislava.start")) {
            player.sendMessage("You don't have permission to start the game.");
            return true;
        }
        
        if (args.length < 3) {
            player.sendMessage("Usage: /fil <centreX> <centreZ> <borderSize>");
            return true;
        }
        
        int centreX, centreZ, borderSize;
        try {
            centreX = Integer.parseInt(args[0]);
            centreZ = Integer.parseInt(args[1]);
            borderSize = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            player.sendMessage("Invalid arguments. Please provide valid integers.");
            return true;
        }
        
        // Start the game with the provided parameters
        FloorIsLavaListener.startGame(centreX, centreZ, borderSize);
        player.sendMessage("The Floor is Lava game started with center at (" + centreX + "," + centreZ + ") and border size " + borderSize + "!");
        return true;
    }
}
