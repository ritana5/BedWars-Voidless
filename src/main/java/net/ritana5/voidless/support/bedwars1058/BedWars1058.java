package net.ritana5.voidless.support.bedwars1058;

import com.andrei1058.bedwars.api.BedWars;
import com.andrei1058.bedwars.api.arena.IArena;
import net.ritana5.voidless.Voidless;
import net.ritana5.voidless.listeners.bedwars1058.BedWarsListener;
import net.ritana5.voidless.utils.Support;
import net.ritana5.voidless.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.stream.Collectors;

import static net.ritana5.voidless.Voidless.bw1058Api;
import static net.ritana5.voidless.Voidless.support;
import static net.ritana5.voidless.utils.Utils.color;

public class BedWars1058 {
    public BedWars1058() {
        start();
    }

    public void start() {
        support = Support.BEDWARS1058;
        bw1058Api = Bukkit.getServicesManager().getRegistration(BedWars.class).getProvider();

        loadListeners();
    }


    public void initConfig(File file) {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        JavaPlugin.getPlugin(Voidless.class).groups = config.getStringList("arena-groups").stream().map(String::toLowerCase).collect(Collectors.toList());
    }


    public static boolean isMode(IArena arena) {
        return JavaPlugin.getPlugin(Voidless.class).groups.contains(arena.getGroup().toLowerCase());
    }


    public void loadListeners() {
        Bukkit.getPluginManager().enablePlugin(JavaPlugin.getPlugin(Voidless.class));

        File folder = new File("plugins/BedWars1058/Addons/Voidless");
        if (!folder.exists()) {
            folder.mkdirs();
        }

        File configFile = new File(folder, "config.yml");
        if (!configFile.exists()) {
            try {
                Files.copy(JavaPlugin.getPlugin(Voidless.class).getResource("config.yml"), configFile.toPath());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        initConfig(configFile);

        Bukkit.getServicesManager().getRegistration(BedWars.class).getProvider();
        Voidless.bw1058Api = Bukkit.getServicesManager().getRegistration(com.andrei1058.bedwars.api.BedWars.class).getProvider();
        JavaPlugin.getPlugin(Voidless.class).placeSound = Voidless.bw1058Api.getForCurrentVersion("STEP_WOOL", "BLOCK_CLOTH_STEP", "BLOCK_CLOTH_STEP");
        JavaPlugin.getPlugin(Voidless.class).initConfig(configFile);
        JavaPlugin.getPlugin(Voidless.class).getServer().getPluginManager().registerEvents(new BedWarsListener(), JavaPlugin.getPlugin(Voidless.class));
        Bukkit.getConsoleSender().sendMessage("[BedWars1058] " + color("The &4B&ce&6d&eW&aa&9r&5s&d-&1V&2o&1i&2d&1l&2e&1s&2s addon was originally contributed to by Kiiya and Zuyte."));
        Utils.info("&4B&ce&6d&eW&aa&9r&5s&d-&1V&2o&1i&2d&1l&2e&1s&2s");
        Voidless.log.info("Plugin Version: " + JavaPlugin.getPlugin(Voidless.class).getDescription().getVersion());
        if (!(JavaPlugin.getPlugin(Voidless.class).getDescription().getVersion().equals("1.0-SNAPSHOT"))) {
            Voidless.log.info("WARNING: You are using an outdated version of the plugin! Please update at or (WIP)");
        } else {
            Voidless.log.info(("You are running on the latest release!"));
        }
        Voidless.log.info("Server Version: " + JavaPlugin.getPlugin(Voidless.class).getServer().getVersion() + "\n");
        Utils.info("Running on: &6BedWars&c1058");
    }
}
