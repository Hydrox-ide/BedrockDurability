package me.Hydroxide.BedrockDurability;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;


public class Main extends JavaPlugin implements Listener{
	

	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(this, this);
		getConfig().addDefault("dropBedrock", true);
		saveDefaultConfig();
		this.getConfig().options().copyDefaults(true);
		saveConfig();
	}
	
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		if(event.getPlayer().isOp() || event.getPlayer().hasPermission("bedrockdurability.enablemsg")) {
			event.getPlayer().sendMessage("§8[§eBedrockDurability has been enabled.§8]");
		}
	}
	@EventHandler
	public void onBlockDamage(BlockDamageEvent event) {
		if(event.getBlock().getType() == Material.BEDROCK && event.getPlayer().getItemInHand().getType() == Material.DIAMOND_PICKAXE) {
			BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
	        scheduler.scheduleSyncDelayedTask(this, new Runnable() {
	            @Override
	            public void run() {
	            	if(getConfig().getBoolean("dropBedrock") == true) {
	              event.getBlock().breakNaturally();  
	            	} else {
	            		event.getBlock().setType(Material.AIR);
	            		event.getBlock().getWorld().playEffect(event.getBlock().getLocation(), Effect.STEP_SOUND, Material.BEDROCK);
	            	}
	            }
	        }, 150L);
		}
	}
}