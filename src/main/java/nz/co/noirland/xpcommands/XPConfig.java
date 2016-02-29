package nz.co.noirland.xpcommands;

import nz.co.noirland.zephcore.Config;
import nz.co.noirland.zephcore.Debug;
import org.bukkit.plugin.Plugin;

import java.util.Map;

public class XPConfig extends Config {

    private static XPConfig inst;

    public XPConfig() {
        super("config.yml");
    }

    public static XPConfig inst() {
        if(inst == null) {
            inst = new XPConfig();
        }
        return inst;
    }

    public void reload() {
        load();
    }

    @Override
    protected Plugin getPlugin() {
        return XPCommands.inst();
    }

    @Override
    protected Debug getDebug() {
        return XPCommands.debug();
    }

    @SuppressWarnings("unchecked")
    public Map<String, Integer> getCommands() {
        return (Map) config.getConfigurationSection("commands").getValues(false);
    }
}
