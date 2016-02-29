package nz.co.noirland.xpcommands;

import nz.co.noirland.zephcore.Debug;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class XPCommands extends JavaPlugin implements Listener {

    private static XPCommands inst;
    private static Debug debug;

    public static final String IGNORE_PERM = "xpcommands.bypass";

    public XPCommands() {
        inst = this;
    }

    public static XPCommands inst() {
        return inst;
    }

    public static Debug debug() { return debug; }

    @Override
    public void onEnable() {
        debug = new Debug(this);
        getServer().getPluginCommand("xpexec").setExecutor(new XPExecCommand());
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        String command = event.getMessage().toLowerCase();
        Player player = event.getPlayer();
        if(command.startsWith("/")) command = command.substring(1);
        command = command.split(" ")[0];

        if(player.hasPermission(IGNORE_PERM)) return;

        if(!XPConfig.inst().getCommands().containsKey(command)) return;

        int levels = XPConfig.inst().getCommands().get(command);

        if(player.getLevel() < levels) {
            player.sendMessage(ChatColor.DARK_RED + "You need " + levels + " levels to run this command!");
            event.setCancelled(true);
            return;
        }

        player.setLevel(player.getLevel() - levels);

        player.sendMessage(ChatColor.GOLD + "Using " + levels + " levels to run command...");
    }
}
