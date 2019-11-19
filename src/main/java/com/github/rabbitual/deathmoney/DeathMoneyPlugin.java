package com.github.rabbitual.deathmoney;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class DeathMoneyPlugin extends JavaPlugin {

    private Economy eco;

    @Override
    public void onEnable() {
        if (!setupEconomy()) {
            getLogger().severe("Could not detect an economy plugin - disabling plugin..");
            Bukkit.getPluginManager().disablePlugin(this);
        }

        saveDefaultConfig();
        new PlayerDeathListener(this);
    }

    public Economy getEco() {
        return eco;
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        eco = rsp.getProvider();
        return eco != null;
    }

}
