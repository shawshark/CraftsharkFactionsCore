package me.shawshark.factions;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class Config {
	
	private Plugin plugin;		
	private FileConfiguration conf = null;
	private File file = null;
	private String fname = null;

	public Config(Plugin plugin, String filename) {		
		this.plugin = plugin;
		fname = filename;
	}
	
	public void ReloadConfig() {
		if(file == null) {
			file = new File(plugin.getDataFolder(), fname);
		}
		
		conf = YamlConfiguration.loadConfiguration(file);

		InputStream isDefaults = plugin.getResource(fname);
		if(isDefaults!=null) {
			@SuppressWarnings("deprecation")
			YamlConfiguration confDefault = YamlConfiguration.loadConfiguration(isDefaults);
			conf.setDefaults(confDefault);
		}
	}

	public FileConfiguration getConfig() {
		if(conf == null ) {
			ReloadConfig();
		}
		return conf;
	}

	public void saveConfig() {
		if(conf == null || file == null) {
			return;
		}

		try{
			conf.save(file);
		} catch(IOException ex) {
			plugin.getLogger().log(Level.SEVERE, "IOException: Error saving configuration file '" + fname + "'!");
			plugin.getLogger().log(Level.SEVERE, ex.toString());
		}
	}
}
