package net.ritana5.voidless.support.bedwars2023;

import com.tomkeuper.bedwars.api.BedWars;
import net.ritana5.voidless.Voidless;
import net.ritana5.voidless.listeners.bedwars2023.BedWarsListener;
import net.ritana5.voidless.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.stream.Collectors;

import static net.ritana5.voidless.utils.Utils.color;


public class Addon extends com.tomkeuper.bedwars.api.addon.Addon {

    public Addon() {
    }

    @Override
    public String getAuthor() {
        return Voidless.instance.getDescription().getAuthors().get(0);
    }

    @Override
    public Plugin getPlugin() {
        JavaPlugin.getPlugin(Voidless.class);
        return Voidless.getInstance();
    }

    @Override
    public String getVersion() {
        return Voidless.instance.getDescription().getVersion();
    }

    @Override
    public String getDescription() {
        return Voidless.instance.getDescription().getDescription();
    }

    @Override
    public String getName() {
        return Voidless.instance.getDescription().getName();
    }

    public void initConfig(File file) {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        JavaPlugin.getPlugin(Voidless.class).groups = config.getStringList("arena-groups").stream().map(String::toLowerCase).collect(Collectors.toList());
    }

    public void load() {
        Bukkit.getPluginManager().enablePlugin(JavaPlugin.getPlugin(Voidless.class));

        File folder = new File("plugins/BedWars2023/Addons/Voidless");
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
        Voidless.bw2023Api = Bukkit.getServicesManager().getRegistration(BedWars.class).getProvider();
        JavaPlugin.getPlugin(Voidless.class).placeSound = Voidless.bw2023Api.getForCurrentVersion("STEP_WOOL", "BLOCK_CLOTH_STEP", "BLOCK_CLOTH_STEP");
        JavaPlugin.getPlugin(Voidless.class).initConfig(configFile);
        JavaPlugin.getPlugin(Voidless.class).getServer().getPluginManager().registerEvents(new BedWarsListener(), JavaPlugin.getPlugin(Voidless.class));
        Bukkit.getConsoleSender().sendMessage("[BedWars2023] " + color("The BedWars-Voidless addon was originally contributed to by Kiiya and Zuyte."));
        Utils.info("&4B&ce&6d&eW&aa&9r&5s&d-&1V&2o&1i&2d&1l&2e&1s&2s");
        Voidless.log.info("Plugin Version: " + JavaPlugin.getPlugin(Voidless.class).getDescription().getVersion());
        if (!(JavaPlugin.getPlugin(Voidless.class).getDescription().getVersion().equals("1.0-SNAPSHOT"))) {
            Voidless.log.info("WARNING: You are using an outdated version of the plugin! Please update at or (WIP)");
        } else {
            Voidless.log.info(("You are running on the latest release!"));
        }
        Voidless.log.info("Server Version: " + JavaPlugin.getPlugin(Voidless.class).getServer().getVersion() + "\n");
        Utils.info("Running on: &fBedWars&c2023&r");
    }

    public void unload() {
        Bukkit.getPluginManager().disablePlugin(JavaPlugin.getPlugin(Voidless.class));
    }
}
