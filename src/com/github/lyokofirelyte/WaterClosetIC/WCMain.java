package com.github.lyokofirelyte.WaterClosetIC;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.lyokofirelyte.WaterClosetIC.Util.ICPromotions;
import com.github.lyokofirelyte.WaterClosetIC.Util.StaticField;
import com.github.lyokofirelyte.WaterClosetIC.Util.TimeStampEX;
import com.github.lyokofirelyte.WaterClosetIC.Util.waOSReport;





public class WCMain extends JavaPlugin
{
  public WCVault vaultMgr = new WCVault(this);
  
  File configFile;
  File datacoreFile;
  
  File configFileBACKUP;
  File datacoreFileBACKUP;
  

  File mailFile;
  File helpFile;
  
  File mailFileBACKUP;
  File helpFileBACKUP;
  
  
  public FileConfiguration config;
  public FileConfiguration datacore;
  
  public FileConfiguration configBACKUP;
  public FileConfiguration datacoreBACKUP;
  
 
  public static FileConfiguration mail;
  public static FileConfiguration help;
  
  public static FileConfiguration mailBACKUP;
  public static FileConfiguration helpBACKUP;
  
  private String url;
  private String promoteUrl;
  private String username;
  private String password;
  private Connection conn;

  public void onEnable()
  {


    
    PluginManager pm = getServer().getPluginManager();
    pm.registerEvents(new WCJoin(this), this);
    pm.registerEvents(new WCQuit(this), this);
    pm.registerEvents(new StaticField(this), this);
    pm.registerEvents(new WCChannels(this), this);
    pm.registerEvents(new WCMobDrops(this), this);
    pm.registerEvents(new WCBlockPlace(this), this);
    pm.registerEvents(new WCDeath(this), this);

    this.configFile = new File(getDataFolder(), "config.yml");
    this.configFileBACKUP = new File(getDataFolder(), "configBACKUP.yml");
    
    this.datacoreFile =  new File(getDataFolder(), "datacore.yml");
    this.datacoreFileBACKUP =  new File(getDataFolder(), "datacoreBACKUP.yml");
    
    this.mailFile = new File(getDataFolder(), "mail.yml");
    this.mailFileBACKUP = new File(getDataFolder(), "mailBACKUP.yml");
    
    this.helpFile = new File(getDataFolder(), "help.yml");
    this.helpFileBACKUP = new File(getDataFolder(), "helpBACKUP.yml");


    this.vaultMgr.hookSetup();
    try
    {
      firstRun();
    } catch (Exception e) {
      e.printStackTrace();
    }

    
    this.config = new YamlConfiguration();
    this.configBACKUP = new YamlConfiguration();
    
    this.datacore = new YamlConfiguration();
    this.datacoreBACKUP = new YamlConfiguration();
    
    WCMain.mail = new YamlConfiguration();
    WCMain.mailBACKUP = new YamlConfiguration();
    
    WCMain.help = new YamlConfiguration();
    WCMain.helpBACKUP = new YamlConfiguration();
    
    loadYamls();
    int v = datacore.getInt("V");
    v++;
    datacore.set("V", v);
    Bukkit.broadcastMessage(WCMail.AS(WCMail.WC + "&o- watercloset for IC has updated (v2.1." + v + "&d&o) -"));

    url = config.getString("url");
    username = config.getString("username");
    password = config.getString("password");
    promoteUrl = config.getString("promoteUrl");

    if ((url == null) || (username == null) || (password == null) || (promoteUrl == null))
    {
      Bukkit.getServer().getLogger().info("You must provide a url, promoteURL, username, and password in the config.yml.");
      Bukkit.getServer().getPluginManager().disablePlugin(this);
    }

    
    registerCommands();
    
    
    getLogger().log(Level.INFO, "CORE HAS BEEN INITIALIZED.");

    List<String> pluginList = this.config.getStringList("Core.Plugins");

    for (String pluginMessage : pluginList)
    {
      getLogger().log(Level.INFO, pluginMessage.toUpperCase() + " IS READY.");
    }
    
  	}

  public void onDisable() {
	  
    getLogger().info("CORE AND ALL EXTENSIONS HAVE BEEN DEACTIVATED.");
    saveYamls();
    
    try
    {
      if (this.conn != null)
      {
        this.conn.close();
      }
    }
    catch (SQLException e) {
      e.printStackTrace();
    }
  }

  
  private void registerCommands()
  {
    getCommand("watercloset").setExecutor(new WCCommands(this));
    getCommand("wc").setExecutor(new WCCommands(this));

    getCommand("o").setExecutor(new WCChannels(this));

    getCommand("timestamp").setExecutor(new TimeStampEX(this));
    getCommand("getnick").setExecutor(new TimeStampEX(this));
    getCommand("stringbuilder").setExecutor(new TimeStampEX(this));
    getCommand("itemname").setExecutor(new TimeStampEX(this));

    getCommand("msg").setExecutor(new WCChannels(this));
    getCommand("tell").setExecutor(new WCChannels(this));
    getCommand("t").setExecutor(new WCChannels(this));
    getCommand("r").setExecutor(new WCChannels(this));

    getCommand("forcefield").setExecutor(new StaticField(this));
    getCommand("ff").setExecutor(new StaticField(this));
    
    getCommand("icpromote").setExecutor(new ICPromotions(this));
    getCommand("demote").setExecutor(new ICPromotions(this));
    getCommand("confirm").setExecutor(new ICPromotions(this));
    getCommand("deny").setExecutor(new ICPromotions(this));
    getCommand("check").setExecutor(new ICPromotions(this));
    getCommand("helpop").setExecutor(new ICPromotions(this));
    
    getCommand("report").setExecutor(new waOSReport(this));
    
    getCommand("mail").setExecutor(new WCMail(this));
    
    getCommand("search").setExecutor(new WCHelp(this));
    
    getCommand("blame").setExecutor(new WCCommands(this));
    
  }

  private void copy(InputStream in, File file)
  {
    try
    {
      OutputStream out = new FileOutputStream(file);
      byte[] buf = new byte[1024];
      int len;
      while ((len = in.read(buf)) > 0)
      {
        out.write(buf, 0, len);
      }
      out.close();
      in.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  public void backupYamls(){
	    
	  try
	    {
	      this.config.save(this.configFileBACKUP);
	      this.datacore.save(this.datacoreFileBACKUP);
	      WCMain.mail.save(this.mailFileBACKUP);
	      WCMain.help.save(this.helpFileBACKUP);
	    
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
  }

  public void saveYamls()
  {
    try
    {
      this.config.save(this.configFile);
      this.datacore.save(this.datacoreFile);
      WCMain.mail.save(this.mailFile);
      WCMain.help.save(this.helpFile);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void saveWC()
  {
    try {
      this.config.save(this.configFile);
      this.datacore.save(this.datacoreFile);
      WCMain.mail.save(this.mailFile);
      WCMain.help.save(this.helpFile);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void loadYamls()
  {
    try
    {
      this.config.load(this.configFile);
      this.datacore.load(this.datacoreFile);
      WCMain.mail.load(this.mailFile);
      WCMain.help.load(this.helpFile);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void loadWC() {
    try {
      this.config.load(this.configFile);
      this.datacore.load(this.datacoreFile);
      WCMain.mail.load(this.mailFile);
      WCMain.help.load(this.helpFile);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void firstRun() throws Exception {
    if (!this.configFile.exists()) {
    	this.configFile.getParentFile().mkdirs();
    	copy(getResource("config.yml"), this.configFile);
    }
    
    if (!this.mailFile.exists()){
        this.mailFile.getParentFile().mkdirs();
        copy(getResource("mail.yml"), this.mailFile);
    }
    
    if (!this.helpFile.exists()){
        this.helpFile.getParentFile().mkdirs();
        copy(getResource("help.yml"), this.helpFile);
    }
    
    
    if (!this.datacoreFile.exists()) {
    	this.datacoreFile.getParentFile().mkdirs();
        copy(getResource("datacore.yml"), this.datacoreFile);
    }
    
  }
  
  

}