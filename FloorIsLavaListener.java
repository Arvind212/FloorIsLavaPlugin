package me.arvind.FloorIsLava.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import me.arvind.FloorIsLava.Main;

public class FloorIsLavaListener implements Listener {

    private static final int LAVA_RISE_INTERVAL = 1200; // 60 seconds (20 ticks per second)
    private static final int LAVA_RISE_AMOUNT = 5;
    private static final int LAVA_MAX_HEIGHT = 256; // Maximum lava height allowed

    private int currentLavaHeight = 0;
    private World world;
    private int centreX;
    private int centreZ;
    private int borderSize;

    public FloorIsLavaListener(int centreX, int centreZ, int borderSize) {
        this.world = Bukkit.getWorlds().get(0); // Get the first loaded world, change accordingly if needed
        this.centreX = centreX;
        this.centreZ = centreZ;
        this.borderSize = borderSize;
    }

	public static void startGame(int centreX, int centreZ, int borderSize) {
        FloorIsLavaListener listener = new FloorIsLavaListener(centreX, centreZ, borderSize);

        // Schedule lava rising task
        new BukkitRunnable() {
            @Override
            public void run() {
                listener.riseLava();
            }
        }.runTaskTimer(Main.getInstance(), LAVA_RISE_INTERVAL, LAVA_RISE_INTERVAL);
    }

    @EventHandler
    private void riseLava() {
        if (currentLavaHeight >= LAVA_MAX_HEIGHT) {
            // Lava reached maximum height, handle game over or reset
            // You can add additional logic here, such as ending the game or resetting the lava level.
            return;
        }

        int minX = centreX - borderSize / 2;
        int minZ = centreZ - borderSize / 2;
        int maxX = centreX + borderSize / 2;
        int maxZ = centreZ + borderSize / 2;

        for (int x = minX; x <= maxX; x++) {
            for (int z = minZ; z <= maxZ; z++) {
                Block block = world.getBlockAt(x, currentLavaHeight, z);
                if (block.getType() == Material.AIR) {
                    block.setType(Material.LAVA);
                }
            }
        }
        
        // Notify players about the lava rise
        String message = "Lava is rising! Current height: " + currentLavaHeight;
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(message);
        }

        currentLavaHeight += LAVA_RISE_AMOUNT;
    }
}
