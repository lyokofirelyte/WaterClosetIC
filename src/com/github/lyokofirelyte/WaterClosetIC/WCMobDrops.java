package com.github.lyokofirelyte.WaterClosetIC;

import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import com.github.lyokofirelyte.WaterClosetIC.Util.FireworkShenans;




public class WCMobDrops implements Listener {

	WCMain plugin;
	
	public WCMobDrops(WCMain instance){
	   plugin = instance;
    } 
	
	
	 @EventHandler(priority = EventPriority.NORMAL)
	  public void onPlayerBadTouch(PlayerInteractEvent event){
		 		 
	      if ((event.getAction() == Action.RIGHT_CLICK_AIR) || (event.getAction() == Action.RIGHT_CLICK_BLOCK)){

	    	  	if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getType() == Material.GLOWSTONE){
	    	  		obeliskCheck(event, event.getPlayer());
	    	  	}
	        }
	      }
	    
	private void obeliskCheck(PlayerInteractEvent e, Player p) {
		
		double x = e.getClickedBlock().getX();
		double y = e.getClickedBlock().getY();
		double z = e.getClickedBlock().getZ();
		String xyz = x + "," + y + "," + z;
		
		Boolean clickedAlready = plugin.datacore.getBoolean("Users." + p.getName() + ".ObeliskSelection");
			if (clickedAlready){
				
				String latest = plugin.datacore.getString("Users." + p.getName() + ".ObeliskLocation");
				String latestCoords = plugin.config.getString("Obelisks.ListGrab." + latest);
				
				double xTo = plugin.config.getInt("Obelisks.Locations." + latestCoords + ".X");
				double yTo = plugin.config.getInt("Obelisks.Locations." + latestCoords + ".Y");
				double zTo = plugin.config.getInt("Obelisks.Locations." + latestCoords + ".Z");
				String w = plugin.config.getString("Obelisks.Locations." + latestCoords + ".World");
						
				String location = plugin.datacore.getString("Users." + p.getName() + ".ObeliskLocation");
				obeliskTeleport(e, p, location, x, y, z, xTo, yTo, zTo, w);
				return;
			}
	
		
		String obeliskCoords = plugin.config.getString("Obelisks.Locations." + xyz + ".Name");
		
		if (obeliskCoords == null){
			return;
		} else if(p.getFoodLevel() != 20){
			p.sendMessage(WCMail.WC + "You must have full energy to use this!");
			return;
		} else {
			plugin.datacore.set("Users." + p.getName() + ".ObeliskTemp", true);
			List <String> obeliskLocations = plugin.config.getStringList("Obelisks.Names");
			p.sendMessage(WCMail.WC + "Please type the location you would like to visit or type ## to cancle!");
		    p.sendMessage(obeliskLocations.toString());
		}
	}




	private void obeliskTeleport(PlayerInteractEvent e, final Player p, String location, final double x, final double y, final double z, final double xTo, final double yTo, final double zTo, String w) {

		plugin.datacore.set("Users." + p.getName() + ".ObeliskSelection", null);
		
		final World world = p.getWorld();
		Location loc = new Location(world, x, y, z);
		
		final Location tp = new Location(world, x, y+1, z, 0, 180);
		Location loc2 = new Location(world, x, y+1, z);
		final Location finalLoc = new Location(Bukkit.getWorld(w), xTo, yTo+50, zTo, 0, 180);
		final Location finalLocLanding = new Location(Bukkit.getWorld(w), xTo, yTo, zTo, 0, 180);
        p.teleport(tp);
		world.playEffect(loc, Effect.ENDER_SIGNAL, 0);
		world.playEffect(loc2, Effect.ENDER_SIGNAL, 0);
		world.playEffect(loc, Effect.BLAZE_SHOOT, 0);
		world.playEffect(loc2, Effect.MOBSPAWNER_FLAMES, 0);
		world.playEffect(loc, Effect.SMOKE, 0);
		world.playEffect(loc2, Effect.SMOKE, 0);
		
		fireWorkCrazyAssShit(loc, p);
		
		 Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable()
		    {
		      public void run()
		      {
		        p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 999999999, 0));
		        p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 60, 0));
		        p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 9999999, 5));
		        plugin.datacore.set("Users." + p.getName() + ".NoDamage", true);
		      }
		    }
		    , 10L);
		 
		 Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable()
		    {
		      public void run()
		      {
		    	p.setVelocity(new Vector(0, 3, 0));
		      }
		    }
		    , 15L);
		 
		 Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable()
		    {
		      public void run()
		      {
		    	  p.setVelocity(new Vector(0, 5, 0));
		    	  world.playEffect(p.getLocation(), Effect.GHAST_SHOOT, 0);
		      }
		    }
		    , 55L);
		 
		 Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable()
		    {
		      public void run()
		      {
		    	p.teleport(finalLoc);
		    	fireWorkCrazyAssShit(finalLocLanding, p);
		      }
		    }
		    , 70L);	 
			 
		 Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable()
		    {
		      public void run()
		      {
		    	  p.setVelocity(new Vector(0, -5, 0));
		    	  world.playEffect(p.getLocation(), Effect.GHAST_SHOOT, 0);
		      }
		    }
		    , 83L);
		 
		 Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable()
		    {
		      public void run()
		      {
		    	world.playEffect(finalLocLanding, Effect.ENDER_SIGNAL, 0);
		  		world.playEffect(finalLocLanding, Effect.ENDER_SIGNAL, 0);
		  		world.playEffect(finalLocLanding, Effect.BLAZE_SHOOT, 0);
		  		world.playEffect(finalLocLanding, Effect.MOBSPAWNER_FLAMES, 0);
		  		world.playEffect(finalLocLanding, Effect.SMOKE, 0);
		  		p.removePotionEffect(PotionEffectType.CONFUSION);
		  		p.removePotionEffect(PotionEffectType.NIGHT_VISION);
		  		plugin.datacore.set("Users." + p.getName() + ".NoDamage", null);
		      }
		    }
		    , 105L);
		 
		 Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable()
		    {
		      public void run()
		      {
		    	  p.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
		      }
		    }
		    , 175L);
	}




	public void fireWorkCrazyAssShit(Location loc, final Player p) {
		
	int w = 0;
		
	while (w <= 50){
        List<Location> circleblocks = FireworkShenans.circle(p, loc, 5, 1, true, false, w);
        long delay =  0L;
        
        	for (final Location l : circleblocks){
        		delay = delay + 3L;
        		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable()
        	    {
        	      public void run()
        	      {
        	        	FireworkShenans fplayer = new FireworkShenans();
        	        	try {
							fplayer.playFirework(p.getWorld(), l,
							FireworkEffect.builder().with(Type.BURST).withColor(Color.WHITE).build());
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (Exception e) {
							e.printStackTrace();
						}        	      }
        	    }
        	    , delay);
        	}
        	
        w++;

	}
		
	}


	
}

