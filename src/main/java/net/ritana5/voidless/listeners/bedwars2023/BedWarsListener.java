package net.ritana5.voidless.listeners.bedwars2023;

import com.tomkeuper.bedwars.api.arena.GameState;
import com.tomkeuper.bedwars.api.arena.IArena;
import com.tomkeuper.bedwars.api.arena.team.TeamColor;
import com.tomkeuper.bedwars.api.events.gameplay.GameStateChangeEvent;
import net.ritana5.voidless.Voidless;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.material.Bed;
import org.bukkit.plugin.java.JavaPlugin;

public class BedWarsListener implements Listener {
    public BedWarsListener() {
    }

    @EventHandler
    public void onGameStart(GameStateChangeEvent event) {
        String[] split = Bukkit.getBukkitVersion().split("-")[0].split("\\.");
        String ver_num = split[1];
        int version = Integer.parseInt(ver_num);
        if (event.getNewState() == GameState.playing) {
            for (int e = 0; e < JavaPlugin.getPlugin(Voidless.class).getConfig().getStringList("arena-groups").size(); ++e) {
                if (event.getArena().getGroup().equals(JavaPlugin.getPlugin(Voidless.class).getConfig().getStringList("arena-groups").get(e))) {
                    for (int i = 0; i < event.getArena().getTeams().size(); ++i) {
                        if (!event.getArena().getTeams().get(i).isBedDestroyed()) {
                            BlockFace targetFace = ((Bed) event.getArena().getTeams().get(i).getBed().getBlock().getState().getData()).getFacing();
                            Block bed1;
                            TeamColor col1;
                            IArena ar1;
                            if (targetFace != BlockFace.SOUTH && targetFace != BlockFace.NORTH) {
                                if (targetFace == BlockFace.EAST || targetFace == BlockFace.WEST) {
                                    bed1 = event.getArena().getTeams().get(i).getBed().getBlock();
                                    col1 = event.getArena().getTeams().get(i).getColor();
                                    ar1 = event.getArena();
                                    if (version > 12) {
                                        new placerwe_1_13(bed1, col1, i, ar1);
                                    } else {
                                        new placerwe(bed1, col1, i, ar1);
                                    }
                                }
                            } else {
                                bed1 = event.getArena().getTeams().get(i).getBed().getBlock();
                                col1 = event.getArena().getTeams().get(i).getColor();
                                ar1 = event.getArena();
                                if (version > 12) {
                                    new placersn_1_13(bed1, col1, i, ar1);
                                } else {
                                    new placersn(bed1, col1, i, ar1);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
