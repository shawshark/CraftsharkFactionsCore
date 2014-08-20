package me.shawshark.factions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import me.shawshark.factions.listeners.PlayerListener;
import me.shawshark.factions.listeners.WorldListener;

import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Factions extends JavaPlugin {

	public String[] worlds;
	public KitsSystem KitsSystem;
	
	//configs
	public Config kits;
	
	public void onEnable() {
		
		saveDefaultConfig();
		saveConfig();
		
		registerListeners();
		
		kits = new Config(this, "kits.yml");
		kits.saveConfig();
		
		//register classes
		KitsSystem = new KitsSystem(this, kits);
		
		if(getConfig().getString("Server.Worlds") != null) {
			worlds = getConfig().getString("worlds").split(",");
			for (String worldName : worlds) {
				getServer().createWorld(new WorldCreator(worldName));
			}
		}
		
		//load cooldowns.
		for(String line : getConfig().getStringList("cooldowns")) {	
			String[] cooldown = line.split(",");	
			KitsSystem.claimed.put(cooldown[0], Integer.parseInt(cooldown[1]));	
		}
				
		/* kits cooldown. */
		KitsSystem.Runkitcooldownertask();
	}
	
	public void onDisable() {
		
		List<String> claims = new ArrayList<String>();
		
		// get players name and time.
		for(Map.Entry<String, Integer> entry : KitsSystem.claimed.entrySet()) {
			String key = entry.getKey();
			Integer value = entry.getValue();
			claims.add(key + "," + value);
		}
				
		// set and save the config conig.yml
		kits.getConfig().set("cooldowns", claims);
		saveConfig();
		
		String worlds = null;
		for(World world : getServer().getWorlds()) {
			if(worlds == null) {
				worlds = world.getName();
			} else {
				worlds = "," + world.getName();
			}
		}
		
		getConfig().set("Server.Worlds", worlds);
		saveConfig();
	}
	
	public void registerListeners() {
		
		PluginManager pm = getServer().getPluginManager();
		
		pm.registerEvents(new PlayerListener(this), this);
		pm.registerEvents(new WorldListener(this), this);
		
	}
	
	public void registerCommands() {
		
		//kits system commands
		getCommand("createkit").setExecutor(new KitsSystem(this, kits));
		getCommand("kit").setExecutor(new KitsSystem(this, kits));
	}
}
