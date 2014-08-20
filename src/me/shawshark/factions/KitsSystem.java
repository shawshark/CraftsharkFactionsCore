package me.shawshark.factions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class KitsSystem implements CommandExecutor {
	
	public Factions instance;
	public Config config;
	
	public List<String> editing_kits = new ArrayList<String>();
	
	public Factions getInstance() {
		return instance;
	}
	
	public FileConfiguration getConfig() {
		return config.getConfig();
	}
	
	public KitsSystem(Factions instance, Config kits) {
		this.instance = instance;
		this.config = kits;
	}
	
	public HashMap<String, Integer> claimed = new HashMap<String, Integer>();
	
	public BukkitTask task;
	public void Runkitcooldownertask() {
		task = new BukkitRunnable() {
			@Override
			public void run() {
				
				if(claimed.size() < 1) {
					return;
				}
				
				Iterator<String> it1 = claimed.keySet().iterator();
				
				while(it1.hasNext()) {
					
					String key = it1.next();
					Integer value = claimed.get(key);
					boolean t = false;
					
					if(value == 0) {
				    	t = true;
				    	claimed.remove(key);
				    }
				    
				    if(!t) {
				    	int count;
					    count = claimed.get(key) -1;
					    claimed.put(key, count);
				    }
				    t = false;
				}
			}
		}.runTaskTimer(getInstance(), 20, 20);
	}
	
	public void saveKit(String name, ItemStack[] stack) {
		
		List<ItemStack> items = new ArrayList<ItemStack>();
		
		for ( ItemStack item : stack ) {
			if(item != null) {
				items.add(item);
			}
		}
		
		List<ItemStack> s = items;
		
		getConfig().set("kits." + name, s);
		getInstance().saveConfig();
	}
	
	public ItemStack[] loadKit(String name) {
		 @SuppressWarnings("unchecked")
		 List<ItemStack> items = (List<ItemStack>) getConfig().get("kits." + name);
         ItemStack[] content = items.toArray(new ItemStack[items.size()]);
		 return content;
	}
	
	public Boolean kitExists(String name) {
		if(getConfig().getString("kits." + name) != null) {
			return true;
		}
		return false;
	}

	@Override
	public boolean onCommand(CommandSender s, Command c, String l, String[] args) {
		
		Player player = (Player)s;
		
		if(c.getName().equalsIgnoreCase("createkit")) {
			
			if(!s.hasPermission("create.kit")) {
				s.sendMessage(ChatColor.RED + "You don't have permissions for this command!");
				return true;
			}
			
			if(!(args.length == 1)) {
				s.sendMessage(ChatColor.GOLD + "Invaild! /createkit [kit name]");
				return true;
			}
			
			String name = args[0];
			
			Player pl = (Player)s;
			
			editing_kits.add(player.getName());
			
			if(kitExists(name)) {
				Inventory inv = Bukkit.getServer().createInventory(player, 54, name);
				inv.setContents(loadKit(name));
				
				pl.openInventory(inv);
				return true;
			}
			
			Inventory inv = Bukkit.getServer().createInventory(player, 54, name);
			
			pl.openInventory(inv);
			return true;
		}
		
		if(c.getName().equalsIgnoreCase("kit")) {
			
			
			if(args[0].equalsIgnoreCase("Donor")) {
				
				if(!s.hasPermission("craftshark.kits.donor")) {
					s.sendMessage(ChatColor.GOLD + "You don't have permissions for the Donor kit!");
					return true;
				}
				
				if(claimed.containsKey(s.getName())) {
					
					int timer = claimed.get(s.getName());
					
					String message = ChatColor.GOLD + "Sorry you've already selected your kit, Please try again in {seconds} seconds!"
							.replace("{seconds}", ""+timer
					);
					
					s.sendMessage(message);
					return true;
				}
				
				claimed.put(s.getName(), 43200); // 12 hours.
				
				ItemStack[] donor = loadKit("donor");
				player.getInventory().addItem(donor);
				
				s.sendMessage(ChatColor.GREEN + "Enjoy your kit " + ChatColor.YELLOW + 
						s.getName()
				);
				
				return true;
			}
			if(args[0].equalsIgnoreCase("Supporter")) {
				
				if(!s.hasPermission("craftshark.kits.supporter")) {
					s.sendMessage(ChatColor.GOLD + "You don't have permissions for the Supporter kit!");
					return true;
				}
				
				if(claimed.containsKey(s.getName())) {
					
					int timer = claimed.get(s.getName());
					
					String message = ChatColor.GOLD + "Sorry you've already selected your kit, Please try again in {seconds} seconds!"
							.replace("{seconds}", ""+timer
					);
					
					s.sendMessage(message);
					return true;
				}
				
				claimed.put(s.getName(), 43200); // 12 hours.
				
				ItemStack[] supporter = loadKit("supporter");
				player.getInventory().addItem(supporter);
				
				s.sendMessage(ChatColor.GREEN + "Enjoy your kit " + ChatColor.YELLOW + 
						s.getName()
				);
				
				return true;
			}
			if(args[0].equalsIgnoreCase("VIP")) {
				
				if(!s.hasPermission("craftshark.kits.VIP")) {
					s.sendMessage(ChatColor.GOLD + "You don't have permissions for the VIP kit!");
					return true;
				}
				
				if(claimed.containsKey(s.getName())) {
					
					int timer = claimed.get(s.getName());
					
					String message = ChatColor.GOLD + "Sorry you've already selected your kit, Please try again in {seconds} seconds!"
							.replace("{seconds}", ""+timer
					);
					
					s.sendMessage(message);
					return true;
				}
				
				claimed.put(s.getName(), 43200); // 12 hours.
				
				ItemStack[] VIP = loadKit("vip");
				player.getInventory().addItem(VIP);
				
				s.sendMessage(ChatColor.GREEN + "Enjoy your kit " + ChatColor.YELLOW + 
						s.getName()
				);
				
				return true;
			}
			if(args[0].equalsIgnoreCase("Elite")) {
				
				if(!s.hasPermission("craftshark.kits.Elite")) {
					s.sendMessage(ChatColor.GOLD + "You don't have permissions for the Elite kit!");
					return true;
				}
				
				if(claimed.containsKey(s.getName())) {
					
					int timer = claimed.get(s.getName());
					
					String message = ChatColor.GOLD + "Sorry you've already selected your kit, Please try again in {seconds} seconds!"
							.replace("{seconds}", ""+timer
					);
					
					s.sendMessage(message);
					return true;
				}
				
				claimed.put(s.getName(), 43200); // 12 hours.
				
				ItemStack[] Elite = loadKit("elite");
				player.getInventory().addItem(Elite);
				
				s.sendMessage(ChatColor.GREEN + "Enjoy your kit " + ChatColor.YELLOW + 
						s.getName()
				);
				
				return true;
			}
			if(args[0].equalsIgnoreCase("Emperor")) {
				
				if(!s.hasPermission("craftshark.kits.Emperor")) {
					s.sendMessage(ChatColor.GOLD + "You don't have permissions for the Emperor kit!");
					return true;
				}
				
				if(claimed.containsKey(s.getName())) {
					
					int timer = claimed.get(s.getName());
					
					String message = ChatColor.GOLD + "Sorry you've already selected your kit, Please try again in {seconds} seconds!"
							.replace("{seconds}", ""+timer
					);
					
					s.sendMessage(message);
					return true;
				}
				
				claimed.put(s.getName(), 43200); // 12 hours.
				
				ItemStack[] Emperor = loadKit("emperor");
				player.getInventory().addItem(Emperor);
				
				s.sendMessage(ChatColor.GREEN + "Enjoy your kit " + ChatColor.YELLOW + 
						s.getName()
				);
				
				return true;
			}
			if(args[0].equalsIgnoreCase("God")) {
				
				if(!s.hasPermission("craftshark.kits.God")) {
					s.sendMessage(ChatColor.GOLD + "You don't have permissions for the God kit!");
					return true;
				}
				
				if(claimed.containsKey(s.getName())) {
					
					int timer = claimed.get(s.getName());
					
					String message = ChatColor.GOLD + "Sorry you've already selected your kit, Please try again in {seconds} seconds!"
							.replace("{seconds}", ""+timer
					);
					
					s.sendMessage(message);
					return true;
				}
				
				claimed.put(s.getName(), 43200); // 12 hours.
				
				ItemStack[] God = loadKit("god");
				player.getInventory().addItem(God);
				
				s.sendMessage(ChatColor.GREEN + "Enjoy your kit " + ChatColor.YELLOW + 
						s.getName()
				);
			
				return true;
			}
			
			if(args[0].equalsIgnoreCase("removetimer")) {
				
				if(!s.hasPermission("craftshark.kits.removetimer")) {
					s.sendMessage(ChatColor.RED + "You don't have permissions for this command!");
					return true;
				}
				
				if(!(args.length == 2)) {
					s.sendMessage(ChatColor.GOLD + "Correct format: /kit removetimer <name>");
					return true;
				}
				
				String name = args[1];
				if(!claimed.containsKey(name)) {
					s.sendMessage(ChatColor.RED + "Error: " + name + " hasn't got a timer!");
					return true;
				}
				
				claimed.remove(name);
				s.sendMessage(ChatColor.GOLD + "Removed " + name + "'s timer!");
				return true;
			}
			return true;
		}
		
		return true;
	}
}
