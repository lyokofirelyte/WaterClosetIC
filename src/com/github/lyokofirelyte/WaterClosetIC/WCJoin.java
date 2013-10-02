package com.github.lyokofirelyte.WaterClosetIC;

import java.util.List;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class WCJoin
  implements Listener
{
  WCMain plugin;

  public WCJoin(WCMain instance)
  {
    this.plugin = instance;
  }

@EventHandler(priority=EventPriority.HIGH)
  public boolean onPlayerJoin(final PlayerJoinEvent event)
  {
	  
	WCMail.mailLogin(event.getPlayer());
    List <String> joinMessages = this.plugin.config.getStringList("Core.JoinMessages");
    Random rand = new Random();
    int randomNumber = rand.nextInt(joinMessages.size());
    final String joinMessage = (String)joinMessages.get(randomNumber);

    event.setJoinMessage(null);

    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable()
    {
      public void run()
      {
        Player p = event.getPlayer();
        Bukkit.broadcastMessage("§e" + joinMessage.replace("%p", new StringBuilder("§7").append(p.getDisplayName()).append("§e").toString()).replace(" ", " §e"));
      }
    }
    , 5L);

    return true;
  }
}