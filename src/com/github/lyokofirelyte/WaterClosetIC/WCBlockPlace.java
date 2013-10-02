package com.github.lyokofirelyte.WaterClosetIC;


import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class WCBlockPlace implements Listener{
	
	WCMain plugin;
	
	public WCBlockPlace(WCMain instance){
    plugin = instance;
    } 


	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBlockPlace(BlockPlaceEvent e){

	if (plugin.datacore.getBoolean("Users." + e.getPlayer().getName() + ".ObeliskPlaceMode")){
		
		plugin.datacore.set("Users." + e.getPlayer().getName() + ".ObeliskPlaceMode", false);
		double x = e.getBlock().getLocation().getX();
		double y = e.getBlock().getLocation().getY();
		double z = e.getBlock().getLocation().getZ();
		
		String xyz = x + "," + y + "," + z;
		String latest = plugin.datacore.getString("Obelisks.Latest");
		String world = e.getPlayer().getWorld().getName();
		
		plugin.config.set("Obelisks.Locations." + xyz + ".X", x);
		plugin.config.set("Obelisks.Locations." + xyz + ".Y", y);
		plugin.config.set("Obelisks.Locations." + xyz + ".Z", z);
		plugin.config.set("Obelisks.Locations." + xyz + ".Name", latest);
		plugin.config.set("Obelisks.Locations." + xyz + ".World", world);
		plugin.config.set("Obelisks.ListGrab." + latest, xyz);
		
		e.getPlayer().sendMessage(WCMail.AS(WCMail.WC + "Location set for " + latest));
		return;
	}
	
			 
			
	}

}

