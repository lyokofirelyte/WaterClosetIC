package com.github.lyokofirelyte.WaterClosetIC.Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.lyokofirelyte.WaterClosetIC.WCMail;
import com.github.lyokofirelyte.WaterClosetIC.WCMain;
import com.github.lyokofirelyte.WaterClosetIC.WCVault;



public class ICPromotions implements CommandExecutor {

	WCMain plugin;
	public ICPromotions(WCMain plugin)
	{
	    this.plugin = plugin;
	}
	
	private Connection conn;
	private PreparedStatement pst;
	private long unixTime;
	
	  public boolean onCommand(final CommandSender sender, Command cmd, String label, String[] args) throws IllegalArgumentException {
		  
		  switch (cmd.getName()){
		  
		  case "icpromote":
			 
			 if (args.length < 2){
				 sender.sendMessage(WCMail.WC + "/promote <player> <plot ID> <reason> or /promote <player> member.");
				 break;
			 }
			 
			 if (Bukkit.getPlayer(args[0]) == null){
				 sender.sendMessage(WCMail.WC + "That player has never logged in before or is offline.");
				 break;
			 }
			 
			 if (args[1].equalsIgnoreCase("member")){
				 
				 Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "pex promote " + args[1]);
				 Bukkit.broadcastMessage(WCMail.AS(WCMail.WC + ((Player) sender).getDisplayName() + " &dhas promoted " + Bukkit.getPlayer(args[0]).getDisplayName() + " &dto Member."));
				 log(sender.getName(), args[0], "0;0", "Sign-Up", ((Player) sender).getWorld().getName(), "Member", "1");
				 sender.sendMessage(WCMail.WC + "Promotion logged @ http://www.ohsototes.com/index.php?p=admin/promos");
				 plugin.datacore.set("Users." + sender.getName() + ".PromoteQue", null); 
				 break;
			 }
			 
			 plugin.datacore.set("Users." + sender.getName() + ".PromoteQue.Person", args[0]);
			 plugin.datacore.set("Users." + sender.getName() + ".PromoteQue.ID", args[1]);
			 plugin.datacore.set("Users." + sender.getName() + ".PromoteQue.Reason", TimeStampEX.createString(args, 2));
			 
			 String prefix = WCVault.chat.getPlayerPrefix(Bukkit.getPlayer(args[0]));
			 sender.sendMessage(WCMail.AS(WCMail.WC + "That player is rank " + prefix + ". &dType &b/confirm&d to approve the build, or &c/deny <reason>&d."));
			 break;
			 
		  case "confirm":
			  
			 final String person = plugin.datacore.getString("Users." + sender.getName() + ".PromoteQue.Person");
			 String ID = plugin.datacore.getString("Users." + sender.getName() + ".PromoteQue.ID");
			 String reason = plugin.datacore.getString("Users." + sender.getName() + ".PromoteQue.Reason");
			 
			 if (person == null){
				 sender.sendMessage(WCMail.WC + "You have no one to confirm!");
				 break;
			 }
			 
			 Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "pex promote " + person);
			 prefix = WCVault.chat.getPlayerPrefix(Bukkit.getPlayer(person));
			 Bukkit.broadcastMessage(WCMail.AS(WCMail.WC + ((Player) sender).getDisplayName() + " &dhas promoted " + Bukkit.getPlayer(person).getDisplayName() + " &dto " + prefix));
			 log(sender.getName(), person, ID, reason, ((Player) sender).getWorld().getName(), prefix, "1");
			 sender.sendMessage(WCMail.WC + "Promotion logged @ http://www.ohsototes.com/index.php?p=admin/promos");
			 plugin.datacore.set("Users." + sender.getName() + ".PromoteQue", null);
			 
			 List <String> plotPromos = plugin.datacore.getStringList("Plots." + ID + ".Promotions");
			 plotPromos.add(sender.getName() + " &aPROMOTED &f// " + person + " &f// " + reason);
			 plugin.datacore.set("Plots." + ID + ".Promotions", plotPromos);
			 
		        List<Location> circleblocks = FireworkShenans.circle(Bukkit.getPlayer(person), Bukkit.getPlayer(person).getLocation(), 5, 1, true, false, 1);
		        long delay =  0L;
		        	for (final Location l : circleblocks){
		        		delay = delay + 2L;
		        		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable()
		        	    {
		        	      public void run()
		        	      {
		        	        	FireworkShenans fplayer = new FireworkShenans();
		        	        	try {
									fplayer.playFirework(Bukkit.getPlayer(person).getWorld(), l,
									FireworkEffect.builder().with(Type.BURST).withColor(Color.BLUE).build());
								} catch (IllegalArgumentException e) {
									e.printStackTrace();
								} catch (Exception e) {
									e.printStackTrace();
								}        	      }
		        	    }
		        	    , delay);
		        	}
			 
		  break;
		  
		  case "demote":
			 
			 String person2 = args[0];
			 reason = TimeStampEX.createString(args, 1);
				 
				 if (Bukkit.getOfflinePlayer(args[0]).hasPlayedBefore() == false){
					 sender.sendMessage(WCMail.WC + "That player has never logged in before!");
					 break;
				 }
				 
			 Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "pex demote " + person2);
			 prefix = WCVault.chat.getPlayerPrefix(Bukkit.getPlayer(args[0]));
			 Bukkit.broadcastMessage(WCMail.AS(WCMail.WC + ((Player) sender).getDisplayName() + " &dhas demoted " + Bukkit.getPlayer(person2).getDisplayName() + " &dto " + prefix));
			 log(sender.getName(), person2, "0;0", reason, ((Player) sender).getWorld().getName(), prefix, "2");
			 sender.sendMessage(WCMail.WC + "Demotion logged @ http://www.ohsototes.com/index.php?p=admin/promos");	 
			
		  break;
		  
		  case "deny":
			  
			  if (args.length < 2){
				  sender.sendMessage(WCMail.WC + "/deny <player> <reason>");
				  break;
			  }
			  
			 person2 = args[0];
			 
			 reason = TimeStampEX.createString(args, 1);
				 
			 if (Bukkit.getOfflinePlayer(args[0]).hasPlayedBefore() == false){
					 sender.sendMessage(WCMail.WC + "That player has never logged in before!");
					 break;
			}
				 
			plugin.datacore.set("Users." + sender.getName() + ".PromoteQue", null);
			
			List <String> deniedPromotions = plugin.datacore.getStringList("Users." + person2 + ".DeniedPromotions");
			deniedPromotions.add(sender.getName() + " &f// " + reason);
			plugin.datacore.set("Users." + person2 + ".DeniedPromotions", deniedPromotions);
			sender.sendMessage(WCMail.WC + "Deny sent!");
			Bukkit.getPlayer(args[0]).sendMessage(WCMail.AS(WCMail.WC + "You were denied: &c" + reason));
			break;
		    
		  case "helpop":
			  
			  if (args.length == 0){
				  sender.sendMessage(WCMail.WC + "Please type a theme, for example: /helpop Quartz building with a tree in the middle.");
				  break;
			  }
			  
			  String theme = TimeStampEX.createString(args, 0);
			  	
			    if (plugin.datacore.getString("Promotions.Latest") != null){
			    	sender.sendMessage(WCMail.WC + "There is already someone in the build-check que, please try again once it has been judged.");
			    	break;
			    }
			    
			  plugin.datacore.set("Promotions.Latest", sender.getName());
			  
			  Bukkit.broadcastMessage(WCMail.AS(WCMail.WC + ((Player)sender).getDisplayName() + " &dhas requested a build check (&c" + theme + "&d)."));
			  
			    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable()
			    {
			      public void run()
			      {
			    	  String latest = plugin.datacore.getString("Promotions.Latest");
			    	  
			    	  	if (latest != null){
			    	  		Bukkit.broadcastMessage(WCMail.AS(WCMail.WC + "The check request for &c" + latest + " &dhas expired."));
			    	  		plugin.datacore.set("Promotions.Latest", null);
			    	  	}
			      }
			    }
			    , 3600L);
			    
			    
			  break;
		  
		  case "check":
			  
			  String latest = plugin.datacore.getString("Promotions.Latest");
			  	if (latest == null){
			  		sender.sendMessage(WCMail.WC + "There are no checks in the que!");
			  		break;
			  	}
			  Bukkit.getServer().dispatchCommand(sender, "tpo " + latest);
			  Bukkit.getServer().dispatchCommand(sender, "plot info");
			  Bukkit.broadcastMessage(WCMail.AS(WCMail.WC + ((Player)sender).getDisplayName() + " &dis checking a build from " + Bukkit.getPlayer(latest).getDisplayName() + "&5."));
			  plugin.datacore.set("Promotions.Latest", null);
			  break;		  
		  }
		  
		 return true;
	  }


	private void log(String name, String person, String PlotID, String reason, String world, String prefix, String type) {
		
	    String username = plugin.config.getString("username");
	    String password = plugin.config.getString("password");
	    String promoteUrl = plugin.config.getString("promoteUrl");
		
		try {
			try {
				if(conn != null)
				{
					conn.close();
				}
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
			try
			{
				conn = DriverManager.getConnection(promoteUrl, username, password);
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
				
		    pst = conn.prepareStatement("INSERT INTO promotions(timestamp, user, inspector, type, rank, world, plot, description, ss) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)");
			
		    String random = Long.toHexString(Double.doubleToLongBits(Math.random()));
		    unixTime = (System.currentTimeMillis() / 1000L);
			pst.setLong(1, unixTime);	
			pst.setString(2, person);
			pst.setString(3, name);
			pst.setString(4, type);
			pst.setString(5, prefix);
			pst.setString(6, world);
			pst.setString(7, PlotID);
			pst.setString(8, reason);
			pst.setString(9, random);
			
			pst.executeUpdate();
			pst.close();
			
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
	}
	
	
}
