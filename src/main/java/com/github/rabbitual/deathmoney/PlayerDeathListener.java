package com.github.rabbitual.deathmoney;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {

    private DeathMoneyPlugin plugin;

    public PlayerDeathListener(DeathMoneyPlugin plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        Economy eco = plugin.getEco();
        FileConfiguration config = plugin.getConfig();
        double cost = config.getDouble("death-cost");

        if (config.getBoolean("is-percent")) {
            double bal = eco.getBalance(player);
            cost = Math.min((bal / 100) * cost, bal);
        }

        eco.withdrawPlayer(player, cost);
        if (config.isString("death-message")) {
            String message = config.getString("death-message");
            player.sendMessage(String.format(ChatColor.translateAlternateColorCodes('&', message), cost));
        }
    }

}
