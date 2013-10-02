package com.github.lyokofirelyte.WaterClosetIC;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;


public class WCChannels implements CommandExecutor, Listener {
	
  WCMain plugin;
  String waaprefix = ChatColor.WHITE + "waOS " + ChatColor.BLUE + "// " + ChatColor.AQUA;
  String waheader = ChatColor.GREEN + "| " + ChatColor.AQUA;
  String WC = "§dWC §5// §d";

  public WCChannels(WCMain instance)
  {
    this.plugin = instance;
  }
  
  
  @EventHandler(priority=EventPriority.HIGH)
  public void onPlayerChat(AsyncPlayerChatEvent event){
	  
	  String globalColor = plugin.datacore.getString("Users." + event.getPlayer().getName() + ".GlobalColor");
		
	  if (globalColor == null){
			plugin.datacore.set("Users." + event.getPlayer().getName() + ".GlobalColor", "&f");
	  }
		
	  globalColor = plugin.datacore.getString("Users." + event.getPlayer().getName() + ".GlobalColor");
	  
	  if (event.isCancelled()){
		  return;
	  }
	  
	  if (plugin.datacore.getBoolean("Users." + event.getPlayer().getName() + ".ObeliskTemp")){
		  event.setCancelled(true);
		  
		  if (plugin.config.getStringList("Obelisks.Names").contains(event.getMessage().toLowerCase())){
			  plugin.datacore.set("Users." + event.getPlayer().getName() + ".ObeliskSelection", true);
		  	  plugin.datacore.set("Users." + event.getPlayer().getName() + ".ObeliskLocation", event.getMessage().toLowerCase());
		  	  event.getPlayer().sendMessage(WCMail.WC + "Alright! Now just right click the glowstone and you're ready to go!");
		      plugin.datacore.set("Users." + event.getPlayer().getName() + ".ObeliskTemp", null);
		  } else if (event.getMessage().equals("##")){
			  plugin.datacore.set("Users." + event.getPlayer().getName() + ".ObeliskTemp", null);
		  		event.getPlayer().sendMessage(WCMail.WC + "Cancelled your teleport!");
		  } else {
			  event.getPlayer().sendMessage(WCMail.WC + "That location does not exist. Try again or type ## to cancle.");
		  }  
		  
		  return;
	  }
	  

	  
	  if (event.getMessage().contains("%c")) {
		  Location loc = event.getPlayer().getLocation();
		  int x = loc.getBlockX();
		  int y = loc.getBlockY(); 
		  int z = loc.getBlockZ(); 
		  event.setMessage(event.getMessage().replaceAll("%c", x + "," + y + "," + z)); 
		  } 
 
	  Player p = event.getPlayer(); 
	  String message = event.getMessage(); 
	  event.setCancelled(true);
	  globalChat(p, message);
 
  }
  


public void globalChat(Player p, String message){

    if (p.getWorld().getName().equals("Alliance")){
    	
    	String name = plugin.datacore.getString("Users." + p.getName() + ".AllianceName");
    	
    		if (name == null){
    			p.sendMessage(WCMail.WC + "You're not in an alliance. You can always join ForeverAlone by asking staff, however.");
    			return;
    		}
    		
    }
		
		for (Player bleh : Bukkit.getOnlinePlayers()){
			
			String finalMessage = message;
			
			String globalColor = plugin.datacore.getString("Users." + bleh.getName() + ".GlobalColor");
			
				if (globalColor == null){
					plugin.datacore.set("Users." + bleh.getName() + ".GlobalColor", "&7");
				}
				
				globalColor = plugin.datacore.getString("Users." + bleh.getName() + ".GlobalColor");
				  
				  if (finalMessage.contains("&r")){
					 finalMessage = finalMessage.replace("&r", globalColor);
				  }
				  
		    if (p.getWorld().getName().equals("Alliance") && bleh.getWorld().getName().equals("Alliance")){
		    	
		    	String name = plugin.datacore.getString("Users." + p.getName() + ".AllianceName");
		    	
		    	if (p.hasPermission("wa.staff") || p.hasPermission("ic.staff")){
					
					bleh.sendMessage(WCMail.AS("&7" + p.getWorld().getName() + WCVault.chat.getPlayerPrefix(p) + WCVault.chat.getPlayerSuffix(p) + name + "§f: " + globalColor + finalMessage));  
				
				} else {
					
					bleh.sendMessage(WCMail.AS("&7" + p.getWorld().getName() + WCVault.chat.getPlayerPrefix(p) + WCVault.chat.getPlayerSuffix(p) + name + "§f: " + globalColor) + finalMessage);  
				}

		    } else if (p.hasPermission("wa.staff") || p.hasPermission("ic.staff")){
				
					bleh.sendMessage(WCMail.AS("&7" + p.getWorld().getName() + WCVault.chat.getPlayerPrefix(p) + WCVault.chat.getPlayerSuffix(p) + p.getDisplayName() + "§f: " + globalColor + finalMessage));  
				
				} else {
					
					bleh.sendMessage(WCMail.AS("&7" + p.getWorld().getName() + WCVault.chat.getPlayerPrefix(p) + WCVault.chat.getPlayerSuffix(p) + p.getDisplayName() + "§f: " + globalColor) + finalMessage);  
				}
		}
		
	
		Bukkit.getServer().getConsoleSender().sendMessage(WCMail.AS("&7" + p.getWorld().getName() + WCVault.chat.getPlayerPrefix(p) + WCVault.chat.getPlayerSuffix(p) + p.getDisplayName() + "§f: " + message));

}

  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
  {
 
	  

    switch (cmd.getName()) { 
    
	
    case "msg": case "tell": case "t":
     
        if ((args.length == 0) || (args.length == 1))
        {
          sender.sendMessage(this.WC + "It seems you were trying to message someone. Try /msg <player> <message> instead...");
          return true;
        }

        String message2 = createString2(args, 1);

        if (sender.getName().toLowerCase().contains(args[0].toLowerCase()))
        {
          sender.sendMessage(this.WC + "Welp, you're forever alone. #sendingmessagestomyself");
          return true;
        }

        Player p = (Player)sender;
        
        Boolean hasCustomColor = plugin.datacore.getBoolean("Users." + p.getName() + ".HasCustomColor");
        
        	if (hasCustomColor)
        	{
        		plugin.datacore.set("Users." + p.getName() + ".CustomColorActive", plugin.datacore.getString("Users." + p.getName() + ".CustomColor"));
        	} else {
        		plugin.datacore.set("Users." + p.getName() + ".CustomColorActive", "&d");
        	}

        	
        	String customColor = plugin.datacore.getString("Users." + p.getName() + ".CustomColorActive");
        	
        for (Player currentPlayer : Bukkit.getServer().getOnlinePlayers())
        {
          if (currentPlayer.getName().toLowerCase().contains(args[0].toLowerCase()))
          {
          	
          	Boolean hasCustomColorOther = plugin.datacore.getBoolean("Users." + currentPlayer.getName() + ".HasCustomColor");
              
          	if (hasCustomColorOther)
          	{
          		plugin.datacore.set("Users." + currentPlayer.getName() + ".CustomColorActive", plugin.datacore.getString("Users." + currentPlayer.getName() + ".CustomColor"));
          	} else {
          		plugin.datacore.set("Users." + currentPlayer.getName() + ".CustomColorActive", "&d");
          	}
          	
          	String customColorOther = plugin.datacore.getString("Users." + currentPlayer.getName() + ".CustomColorActive");
          	
            this.plugin.datacore.set("Users." + Bukkit.getPlayer(args[0]).getName() + ".LastMessage", sender.getName());
            currentPlayer.sendMessage(WCMail.AS(customColorOther + "<- " + p.getDisplayName() + " §f// " + customColorOther + message2));
            sender.sendMessage(WCMail.AS(customColor + "-> " + currentPlayer.getDisplayName() + " §f// " + customColor + message2));
            break;
          }

        }

    break;
        
    case "r":

      p = (Player)sender;
      String message3 = createString(args, 0);
      String recent = this.plugin.datacore.getString("Users." + sender.getName() + ".LastMessage");
      
      hasCustomColor = plugin.datacore.getBoolean("Users." + p.getName() + ".HasCustomColor");
      
  	if (hasCustomColor)
  	{
  		plugin.datacore.set("Users." + p.getName() + ".CustomColorActive", plugin.datacore.getString("Users." + p.getName() + ".CustomColor"));
  	} else {
  		plugin.datacore.set("Users." + p.getName() + ".CustomColorActive", "&d");
  	}
  	
  	customColor = plugin.datacore.getString("Users." + p.getName() + ".CustomColorActive");

        for (Player currentPlayer : Bukkit.getServer().getOnlinePlayers())
        {
          if (currentPlayer.getName().toLowerCase().contains(((String)recent).toLowerCase()))
          {
        	  
        	  Boolean hasCustomColorOther = plugin.datacore.getBoolean("Users." + currentPlayer.getName() + ".HasCustomColor");
              
            	if (hasCustomColorOther)
            	{
            		plugin.datacore.set("Users." + currentPlayer.getName() + ".CustomColorActive", plugin.datacore.getString("Users." + currentPlayer.getName() + ".CustomColor"));
            	} else {
            		plugin.datacore.set("Users." + currentPlayer.getName() + ".CustomColorActive", "&d");
            	}
            	
            	String customColorOther = plugin.datacore.getString("Users." + currentPlayer.getName() + ".CustomColorActive");
            	
            this.plugin.datacore.set("Users." + currentPlayer.getName() + ".LastMessage", sender.getName());
            currentPlayer.sendMessage(WCMail.AS(customColorOther + "<- " + p.getDisplayName() + " §f// " + customColorOther + message3));
            sender.sendMessage(WCMail.AS(customColor + "-> " + currentPlayer.getDisplayName() + " §f// " + customColor + message3));
            break;
          }
        }
        
      break;
      
      }

    if ((cmd.getName().equalsIgnoreCase("o")) && ((sender instanceof Player)))
    {
      if (args.length <= 0)
      {
        sender.sendMessage(this.waaprefix + "Try /o -join, /o -leave, or /o -list");
        return true;
      }

      if (args[0].equalsIgnoreCase("-list"))
      {
        if (!sender.hasPermission("wa.staff"))
        {
          sender.sendMessage(this.waaprefix + "You are not staff!");
          return true;
        }

        List<String> chatUsers = this.plugin.datacore.getStringList("Alliances.StaffChat.chatUsers");

        sender.sendMessage(this.waaprefix + "Chat Users:");

        for (String p : chatUsers)
        {
          if (Bukkit.getOfflinePlayer(p).isOnline())
          {
            sender.sendMessage(ChatColor.AQUA + p);
          }

        }

        return true;
      }

      if (args[0].equalsIgnoreCase("-join"))
      {
        if (!sender.hasPermission("wa.staff"))
        {
          sender.sendMessage(this.waaprefix + "You are not staff!");
          return true;
        }

        if (this.plugin.datacore.getBoolean("Users." + sender.getName() + ".inOChat"))
        {
          sender.sendMessage(this.waaprefix + "You are already in o chat!");
          return true;
        }

        Player p2 = (Player)sender;
        List<String> chatUsers = this.plugin.datacore.getStringList("Alliances.StaffChat.chatUsers");
        chatUsers.add(p2.getName());
        this.plugin.datacore.set("Alliances.StaffChat.chatUsers", chatUsers);
        this.plugin.datacore.set("Users." + p2.getName() + ".inOChat", Boolean.valueOf(true));
        sender.sendMessage(this.waaprefix + "You can talk in o chat with /o <message> now!");

        for (String p : chatUsers){

          if (Bukkit.getOfflinePlayer(p).isOnline())
          {
            Bukkit.getPlayer(p).sendMessage("§c§oOh! §4// " + p2.getDisplayName() + " has joined o chat!");
          }

        }

        return true;
      }

      if (args[0].equalsIgnoreCase("-leave"))
      {
        if (!sender.hasPermission("wa.staff"))
        {
          sender.sendMessage(this.waaprefix + "You are not staff!");
          return true;
        }

        if (!this.plugin.datacore.getBoolean("Users." + sender.getName() + ".inOChat"))
        {
          sender.sendMessage(this.waaprefix + "You have already left o chat!");
          return true;
        }

        Player p2 = (Player)sender;
        List<String> chatUsers = this.plugin.datacore.getStringList("Alliances.StaffChat.chatUsers");
        chatUsers.remove(p2.getName());
        this.plugin.datacore.set("Alliances.StaffChat.chatUsers", chatUsers);
        this.plugin.datacore.set("Users." + p2.getName() + ".inOChat", Boolean.valueOf(false));
        sender.sendMessage(this.waaprefix + "You've left o chat.");

        for (String p : chatUsers){

          if (Bukkit.getOfflinePlayer(p).isOnline())
          {
            Bukkit.getPlayer(p).sendMessage("§c§oOh! §4// " + p2.getDisplayName() + " has left o chat!");
          }

        }

        return true;
      }

      if (this.plugin.datacore.getBoolean("Users." + sender.getName() + ".inOChat"))
      {
        Player p2 = (Player)sender;
        List<String> chatUsers = this.plugin.datacore.getStringList("Alliances.StaffChat.chatUsers");
        String message = createString(args, 0);

        for (String p : chatUsers){

          if (Bukkit.getOfflinePlayer(p).isOnline())
          {
            Bukkit.getPlayer(p).sendMessage("§c§oOh! §4// " + p2.getDisplayName() + ChatColor.WHITE + ": " + ChatColor.translateAlternateColorCodes('&', new StringBuilder().append(ChatColor.RED).append(message).toString()));
          }
        }

      }
      else
      {
        sender.sendMessage(this.waaprefix + "You are not in o chat. Try /o -join.");
      }

    }


    return true;
  }

public static String createString(String[] args, int i)
  {
    StringBuilder sb = new StringBuilder();
    for (i = 0; i < args.length; i++)
    {
      if ((i != args.length) && (i != 0))
      {
        sb.append(" ");
      }
      sb.append(args[i]);
    }
    String message = sb.toString();
    return message;
  }

  static String createString2(String[] args, int i)
  {
    StringBuilder sb = new StringBuilder();
    for (i = 1; i < args.length; i++)
    {
      if ((i != args.length) && (i != 1))
      {
        sb.append(" ");
      }
      sb.append(args[i]);
    }
    String message = sb.toString();
    return message;
  }
}