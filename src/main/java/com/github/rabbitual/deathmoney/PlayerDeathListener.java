package com.github.rabbitual.deathmoney;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {

    private DeathMoneyPlugin plugin;

    public PlayerDeathListener(DeathMoneyPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        Economy eco = plugin.getEco();
        FileConfiguration config = plugin.getConfig();
        double cost = config.getDouble("death-cost");

        if (config.getBoolean("is-percent")) {
            cost = (cost/eco.getBalance(player));
        }

        eco.withdrawPlayer(player, Math.min(eco.getBalance(player), cost));
    }

}
