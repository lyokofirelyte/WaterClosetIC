package com.github.lyokofirelyte.WaterClosetIC;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.FireworkEffect.Type;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.lyokofirelyte.WaterClosetIC.Util.FireworkShenans;



public class WCCommands implements CommandExecutor {
  WCMain plugin;
  String WC = "§dWC §5// §d";

  public WCCommands(WCMain instance){
  
  
    plugin = instance;
  }
  
  public static boolean isInteger(String str) {
	    try {
	        Integer.parseInt(str);
	        return true;
	    } catch (NumberFormatException nfe) {
	        return false;
	    }
	}
  
  	public void spawnWorks(Location loc, Player p){
  		
  		int xBase = loc.getBlockX();
  		int yBase = loc.getBlockY();
  		int zBase = loc.getBlockZ();
  		World world = p.getWorld();
  		
  		List <Location> borderLocations = new ArrayList<Location>();
  		List <Location> borderLocations2 = new ArrayList<Location>();
  		List <Location> borderLocations3 = new ArrayList<Location>();
  		List <Location> borderLocations4 = new ArrayList<Location>();
  		int x = 0;
  		
  			while (x <= 10){
  				Location temp = new Location(world, xBase+x, yBase, zBase);
  				borderLocations.add(temp);
  				x++;
  			}
  			
  			x = 0;
  			
  			while (x <= 10){
  				Location temp = new Location(world, xBase-x, yBase, zBase);
  				borderLocations2.add(temp);
  				x++;
  			}
  			
  			x = 0;
  			
  			while (x <= 10){
  				Location temp = new Location(world, xBase, yBase, zBase+x);
  				borderLocations3.add(temp);
  				x++;
  			}
  			
 			x = 0;
  			
  			while (x <= 10){
  				Location temp = new Location(world, xBase, yBase, zBase-x);
  				borderLocations4.add(temp);
  				x++;
  			}

  			int h = 0;
  			long delay = 0L;
  			long delay2 = 0L;
  			
  			while (h <= 10){
  				
  				for (Location bleh : borderLocations){
  					int xBase2 = bleh.getBlockX();
  			  		int yBase2 = bleh.getBlockY();
  			  		int zBase2 = bleh.getBlockZ();
  			  		Location newLoc = new Location(world, xBase2, yBase2+h, zBase2);
  			  		delay = delay+10L;
  			  		spawnGO(newLoc, p, delay);
  				}
  				
  				delay = delay2;
  				
  				for (Location bleh : borderLocations2){
  					int xBase2 = bleh.getBlockX();
  			  		int yBase2 = bleh.getBlockY();
  			  		int zBase2 = bleh.getBlockZ();
  			  		Location newLoc = new Location(world, xBase2, yBase2+h, zBase2);
  			  		delay = delay+10L;
  			  		spawnGO(newLoc, p, delay); 
  				}
  				
  				delay = delay2;
  				
  				for (Location bleh : borderLocations3){
  					int xBase2 = bleh.getBlockX();
  			  		int yBase2 = bleh.getBlockY();
  			  		int zBase2 = bleh.getBlockZ();
  			  		Location newLoc = new Location(world, xBase2, yBase2+h, zBase2);
  			  		delay = delay+10L;
  			  		spawnGO(newLoc, p, delay);
  				}
  				
  				delay = delay2;
  				
  				for (Location bleh : borderLocations4){
  					int xBase2 = bleh.getBlockX();
  			  		int yBase2 = bleh.getBlockY();
  			  		int zBase2 = bleh.getBlockZ();
  			  		Location newLoc = new Location(world, xBase2, yBase2+h, zBase2);
  			  		delay = delay+10L;
  			  		spawnGO(newLoc, p, delay);
  				}
  				
  				delay2 = delay2+10L;
  				h++;
  			}
  		
  	}
  	
  	public void spawnGO(final Location l, final Player p, long delay){

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
  	
    public static List<Location> circle (Player player, Location loc, Integer r, Integer h, Boolean hollow, Boolean sphere, int plus_y) {
        List<Location> circleblocks = new ArrayList<Location>();
        int cx = loc.getBlockX();
        int cy = loc.getBlockY();
        int cz = loc.getBlockZ();
        for (int x = cx - r; x <= cx +r; x++)
            for (int z = cz - r; z <= cz +r; z++)
                for (int y = (sphere ? cy - r : cy); y < (sphere ? cy + r : cy + h); y++) {
                    double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? (cy - y) * (cy - y) : 0);
                    if (dist < r*r && !(hollow && dist < (r-1)*(r-1))) {
                        Location l = new Location(loc.getWorld(), x, y + plus_y, z);
                        circleblocks.add(l);
                        }
                    }
     
        return circleblocks;
    }
  	

	public void fireWorkCrazyAssShit2(Location loc, final Player p) {
		
	int w = 0;
		
	while (w <= 50){
      List<Location> circleblocks = FireworkShenans.circle(p, loc, 5, 1, true, false, w);
      long delay =  0L;
	
      
      	for (final Location l : circleblocks){
      		delay = delay + 2L;
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

	 
  public boolean onCommand(final CommandSender sender, Command cmd, String label, String[] args) {
	  
	  if (cmd.getName().equalsIgnoreCase("blame")){
		  
			Random rand = new Random();
			int randomNumber = rand.nextInt(Bukkit.getOnlinePlayers().length);
			int x = 0;
			
				for (Player bleh : Bukkit.getOnlinePlayers()){
					x++;
					
					if (x == randomNumber){
						Bukkit.broadcastMessage(WCMail.AS(((Player) sender).getDisplayName() + " &dblames " + bleh.getDisplayName() + "!"));
						break;
					}
				}
		
	  }
	  
    if (cmd.getName().equalsIgnoreCase("wc") || cmd.getName().equalsIgnoreCase("watercloset"))
    {


      if (args.length < 1)
      {
        sender.sendMessage(this.WC + "I'm not sure what you mean. Try /wc help or /wc ?");
        return true;
      }
      
      if (!(sender instanceof Player) && !args[0].equals("con"))
      {
        sender.sendMessage(this.WC + "Sorry console, these commands are for players only.");
        return true;
      }
      
      switch (args[0]){
      
      case "con":
    	  
    	  if (sender instanceof Player == false){
    		  for (Player bleh : Bukkit.getOnlinePlayers()){
    				
    				String globalColor = plugin.datacore.getString("Users." + bleh.getName() + ".GlobalColor");
    				
    					if (globalColor == null){
    						plugin.datacore.set("Users." + bleh.getName() + ".GlobalColor", "&f");
    					}
    					
    					globalColor = plugin.datacore.getString("Users." + bleh.getName() + ".GlobalColor");
    					String message = WCChannels.createString2(args, 1);
    					bleh.sendMessage(WCMail.AS("&4(づ｡◕‿‿◕｡)づ §f// &6Console§f: " + globalColor + message));   
    			}
    	  }
      
      break;
      
      case "setalliance":
    	  
    	  if (args.length != 3){
    		  sender.sendMessage(WCMail.WC + "/wc setalliance <player> <alliance> or /wc setalliance <player> none");
    		  break;
    	  }
    	  
    	  if (Bukkit.getPlayer(args[1]).hasPlayedBefore() == false){
    		  sender.sendMessage(WCMail.WC + "That player has never logged in before.");
    		  break;
    	  }
    	  
    	  if (sender.hasPermission("ic.staff") == false){
    		  sender.sendMessage(WCMail.WC + "You don't have perms.");
    		  break;
    	  }
    	  
    	  if (args[2].equalsIgnoreCase("none")){
    		  plugin.datacore.set("Users." + args[1] + ".AllianceName", null);
              plugin.datacore.set("Users." + args[1] + ".Alliance", null);
              plugin.datacore.set("Users." + args[1] + ".InAlliance", null);
              sender.sendMessage(WCMail.AS(WCMail.WC + "Cleared alliance data for " + args[2] + "."));
              break;
    	  }
    	  
    	  if (plugin.datacore.getBoolean("Alliances." + args[2] + ".Created") == false){
    		  sender.sendMessage(WCMail.WC + "That alliance does not exist. Make one with /wc addalliance <name> <color1> <color2>.");
    		  break;
    	  }
    	  
    	  String C1 = plugin.datacore.getString("Alliances." + args[2] + ".Color1");
    	  String C2 = plugin.datacore.getString("Alliances." + args[2] + ".Color2");
    	  String name = args[1];
    	  
    	  int midpoint = name.length() / 2;
          String firstHalf = name.substring(0, midpoint);
          String secondHalf = name.substring(midpoint);
          String newName = "&" + C1 + firstHalf + "&" + C2 + secondHalf;
          
          plugin.datacore.set("Users." + args[1] + ".AllianceName", newName);
          plugin.datacore.set("Users." + args[1] + ".Alliance", args[2]);
          plugin.datacore.set("Users." + args[1] + ".InAlliance", true);
          sender.sendMessage(WCMail.WC + "User updated with alliance and colors.");
          break;
          
      case "addalliance":
    	  
    	  if (args.length != 4){
    		  sender.sendMessage(WCMail.WC + "/wc addalliance <alliance> <color1> <color2>.");
    		  break;
    	  }
	  
    	  if (sender.hasPermission("ic.staff") == false){
    		  sender.sendMessage(WCMail.WC + "You don't have perms.");
    		  break;
    	  }
    	  
    	  if (args[2].contains("&") || args[3].contains("&")){
    		  sender.sendMessage(WCMail.WC + "Don't use ampersands here, just put the letter/number of the color.");
    		  break;
    	  }
    	  
    	  Boolean created = plugin.datacore.getBoolean("Alliances." + args[1] + ".Created");
    	
    		  if (created){
    			  sender.sendMessage(WCMail.WC + "That alliance has already been created!");
    			  break;
    		  }
    	  
          plugin.datacore.set("Alliances." + args[1] + ".Created", true);
          plugin.datacore.set("Alliances." + args[1] + ".Color1", args[2]);
          plugin.datacore.set("Alliances." + args[1] + ".Color2", args[3]);
          sender.sendMessage(WCMail.WC + "Alliance created.");
          break;
          
      case "globalcolor":
    	  
    	  if (args.length != 2){
    		  sender.sendMessage(WCMail.WC + "/wc globalcolor <color>.");
    		  break;
    	  }
    	  
    	  List <String> c1 = Arrays.asList("&1", "&2", "&3", "&4", "&5", "&6", "&7", "&8", "&9", "&0", "&a", "&b", "&c", "&d", "&e", "&f", "&k");
    	  
    	  if (!c1.contains(args[1])){
    		  sender.sendMessage(WCMail.WC + "That's not a color! Choose from " + c1 + ".");
    		  break;
    	  }
    	  
    	  sender.sendMessage(WCMail.AS(WCMail.WC + "You've changed your color to " + args[1] + "this."));
    	  plugin.datacore.set("Users." + sender.getName() + ".GlobalColor", args[1]);
    	  break;
      
      case "fork":
    	  
    	  sender.sendMessage(WCMail.WC + "LET'S DO THE FORK IN THE GARBAGE DISPOSAL!");
    	  final Location self = ((Player)sender).getLocation();
    	  final double x1 = self.getX();
    	  final double y1 = self.getY();
    	  final double z1 = self.getZ();
    	  int c = 100;
    	  long dl = 0L;
  	  	
  	  while (c > 0){	  
  	  
  		  c--;
  		  dl = dl+2L;
  		  
    	  Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable()
    	    {
    	      public void run()
    	      {

    	    		  ((Player)sender).sendMessage("DING");
    	    		  Random rand = new Random();
    	  			  int randomNumber = rand.nextInt(7);
    	  			  int randomNumber2 = rand.nextInt(7);
    	  			  int yawRandom = rand.nextInt(360);
    	  			  int pitchRandom = rand.nextInt(90);
    	  			  int plusMinus = rand.nextInt(1);
    	  			  ((Player)sender).getWorld().playSound(self, Sound.NOTE_BASS_DRUM, 3.0F, 0.5F);
    	  			  
    	  			  if (plusMinus == 0){
    	  				  Location current = new Location(((Player)sender).getWorld(), x1+randomNumber, y1, z1+randomNumber2, yawRandom, pitchRandom);
    	  				  ((Player)sender).teleport(current);
    	  			  }
    	  			  
    	  			if (plusMinus == 1){
  	  				  Location current = new Location(((Player)sender).getWorld(), x1-randomNumber, y1, z1-randomNumber2, yawRandom, (pitchRandom)-(pitchRandom*2));
  	  				  ((Player)sender).teleport(current);
  	  			  }
    	  			
    	  			if (randomNumber2 == 5){
    	  				FireworkShenans fplayer = new FireworkShenans();
          	        	try {
        			
    							fplayer.playFirework(((Player)sender).getWorld(), self,
    							FireworkEffect.builder().with(Type.BURST).withColor(Color.FUCHSIA).build());
    						} catch (IllegalArgumentException e) {
    							e.printStackTrace();
    						} catch (Exception e) {
    							e.printStackTrace();
    						}        	      }
    	  			}
    	      }
    	    
    	    , dl);
      
      }
  	  
  	  break;
	  
      case "pmcolor":
    	  
    	  if (args.length != 2){
    		  sender.sendMessage(WCMail.WC + "/wc pmcolor <color>. EX: /wc pmcolor &5.");
    		  break;
    	  }
    	  
    	  List <String> colorAvail = Arrays.asList("&1", "&2", "&3", "&4", "&5", "&6", "&7", "&8", "&9", "&0", "&a", "&b", "&c", "&d", "&e", "&f");
    	  
    	  if (colorAvail.contains(args[1]) == false){
    		  sender.sendMessage(WCMail.WC + "That's not a color! Choose from " + colorAvail + ".");
    		  break;
    	  }
    	  
    	  sender.sendMessage(WCMail.AS(WCMail.WC + "You've changed your color to " + args[1] + "this."));
    	  plugin.datacore.set("Users." + sender.getName() + ".CustomColor", args[1]);
    	  plugin.datacore.set("Users." + sender.getName() + ".HasCustomColor", true);
    	  break;
    	  
      
      case "spawnworks":

    	  	Player p7 = (Player) sender;
    	  	spawnWorks(p7.getLocation(), p7);
    	  	break;
    	  
        case "addobelisk":
        	
        	if (sender.hasPermission("wa.staff")){
        		
        		if (args.length != 2){
        			sender.sendMessage(WC + "Please use /wc addobelisk <name>");
        			break;
        		}
        		
		
        		List <String> names = plugin.config.getStringList("Obelisks.Names");
        		
        			if (names.contains(args[1])){
        				sender.sendMessage(WC + "That name already exists!");
        				break;
        			}
        			
        		plugin.datacore.set("Users." + sender.getName() + ".ObeliskPlaceMode", true);
        		names.add(args[1]);
        		plugin.config.set("Obelisks.Names", names);
        		plugin.datacore.set("Obelisks.Latest", args[1]);
        		sender.sendMessage(WCMail.AS(WC + "Place a GLOWSTONE into the spot where the landing location will be set."));
        		return true;
        	}
      
        break;
        
        case "remobelisk":
        	
        	if (sender.hasPermission("wa.staff")){
        		
        		if (args.length != 2){
        			sender.sendMessage(WC + "Please use /wc remobelisk <name>!");
        			break;
        		}
        		
        		List <String> names = plugin.config.getStringList("Obelisks.Names");
        		
        			if (!names.contains(args[1])){
        				sender.sendMessage(WC + "That name isn't on the list!");
        				break;
        			}

        		names.remove(args[1]);
        		plugin.config.set("Obelisks.Names", names);
        		sender.sendMessage(WC + "Obelisk removed.");
        		return true;
        	}
      
        break;
        
        case "placeholders":
        	
        	sender.sendMessage(WC + "%c = coords");
        	break;
        	
      	case "reload":
      		
      		if (sender.hasPermission("wa.staff")){
      		plugin.loadYamls();
      		plugin.saveYamls();
      		sender.sendMessage(WC + "Reloaded WC config.");
      		break;
      		} else {
      			sender.sendMessage(WC + "NO NO, YOU NO HAVE PERMISSIONS.");
      			break;
      		}
      		
      	case "save":
      		
      	if (sender.hasPermission("wa.staff")){
      		plugin.saveYamls();
      		sender.sendMessage(WC + "Saved WC config.");
      		break;
      	} else {
			sender.sendMessage(WC + "NO NO, YOU NO HAVE PERMISSIONS.");
			break;
		}
      		
      	case "backup":
      		
      		Boolean ok = plugin.config.getBoolean("OK");
      		
      		if (ok == false || ok == null){
      			sender.sendMessage("The main config is corrupted for some reason! Backup aborted! Contact Hugs!");
      			break;
      		}
      		
          	if (sender.hasPermission("wa.staff")){
          		plugin.backupYamls();
          		sender.sendMessage(WC + "Backup saved!");
          		break;
          	} else {
    			sender.sendMessage(WC + "NO NO, YOU NO HAVE PERMISSIONS.");
    			break;
    		}
          	
      	case "?": case "help": default:
      		
      	List <String> help = plugin.config.getStringList("Core.Help");
      	
      		for (String message : help){
      			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
      		}
      	break;
      }
    }
    return true;
  }
}
  