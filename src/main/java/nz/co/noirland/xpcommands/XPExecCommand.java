package nz.co.noirland.xpcommands;

import com.google.common.base.Joiner;
import nz.co.noirland.zephcore.Util;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class XPExecCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length < 3) return false;

        int levels;
        try {
            levels = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            return false;
        }

        String name = args[1];
        OfflinePlayer offline = Util.player(name);
        if(!offline.isOnline()) return false;

        Player player = offline.getPlayer();

        if(!player.hasPermission(XPCommands.IGNORE_PERM)) {
            if(player.getLevel() < levels) {
                player.sendMessage(ChatColor.DARK_RED + "You need " + levels + " levels to do that!");
                return true;
            }

            player.sendMessage(ChatColor.GOLD + "Using " + levels + " levels...");
            player.setLevel(player.getLevel() - levels);
        }

        String c = Joiner.on(" ").join(Arrays.copyOfRange(args, 2, args.length));

        Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), c);

        return true;
    }
}
