package me.shawshark.factions.listeners;

import me.shawshark.factions.Factions;

import org.bukkit.event.Listener;

public class PlayerListener implements Listener {

	public Factions instance;
	public Factions getInstance() {
		return instance;
	}
	
	public PlayerListener(Factions instance) {
		this.instance = instance;
	}
}
