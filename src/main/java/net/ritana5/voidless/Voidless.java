package net.ritana5.voidless;

import net.ritana5.voidless.support.bedwars1058.BedWars1058;
import net.ritana5.voidless.support.bedwars2023.BedWars2023;
import net.ritana5.voidless.utils.Support;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;


public final class Voidless extends JavaPlugin {
    public static com.andrei1058.bedwars.api.BedWars bw1058Api;
    public static com.tomkeuper.bedwars.api.BedWars bw2023Api;
    public static Support support;
    public static Voidless instance;
    public String placeSound;
    public List groups;

    public Voidless() {
    }

    public static Logger log = Bukkit.getLogger();

    public void onEnable() {
        loadSupport();
        log = this.getLogger();
        instance = this;
    }

    private void loadSupport() {
        if (Bukkit.getPluginManager().getPlugin("BedWars1058") != null && Bukkit.getPluginManager().getPlugin("BedWars2023") == null) {
            new BedWars1058();
        } else if (Bukkit.getPluginManager().getPlugin("BedWars2023") != null && Bukkit.getPluginManager().getPlugin("BedWars1058") == null) {
            new BedWars2023();
        } else if (Bukkit.getPluginManager().getPlugin("BedWars2023") != null && Bukkit.getPluginManager().getPlugin("BedWars1058") != null) {
            Bukkit.getLogger().severe("WARNING: BedWars1058 and BedWars2023 were both detected! To use this addon, choose one (preferably BedWars2023).");
            Bukkit.getPluginManager().disablePlugin(this);
        } else if (Bukkit.getPluginManager().getPlugin("BedWars2023") == null && Bukkit.getPluginManager().getPlugin("BedWars1058") == null) {
            Bukkit.getLogger().severe("WARNING: No supported BedWars plugin detected! To use this addon, choose between BedWars1058 and BedWars2023 (preferably BedWars2023).");
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }

    public void onDisable() {
    }

    public void initConfig(File file) {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        config.addDefault("arena-groups", new ArrayList<>(Collections.singletonList("voidless_solo")));
    }


    public static Voidless getInstance() {
        return instance;
    }

}