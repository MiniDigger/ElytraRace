package me.MiniDigger.ElytraRace;

import java.util.*;

import com.torchmind.minecraft.annotation.Plugin;

import me.MiniDigger.ElytraRace.ElytraRacePortal.ElytraRacePortalType;
import me.MiniDigger.ElytraRace.config.*;
import me.MiniDigger.ElytraRace.lib.com.minnymin.command.CommandFramework;

import org.bukkit.*;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import mkremins.fanciful.FancyMessage;

@Plugin(name = "ElytraRace", version = "1.0", description = "Use your new fancy Elytra to fly through portals and compare your score to your friends", author = "MiniDigger", website = "http://www.example.org")
public class ElytraRaceMain extends JavaPlugin {
	
	public static final FancyMessage PREFIX = new FancyMessage("[").color(ChatColor.BLACK).then("Elytra").color(ChatColor.RED).then("Race")
			.color(ChatColor.BLUE).then("]").color(ChatColor.BLACK);
	private static ElytraRaceMain INSTANCE;
	
	public static final int NO_SCORE = -9000;
	
	public static double FLY_SPEED = 2.0;
	public static int SHOW_HIGHSCORE = 5;
	public static double BOOST_SPEED = 4.0;
	public static double SLOW_SPEED = 0.6;
	
	private List<ElytraRace> races = new ArrayList<>();
	private List<ElytraRacePortal> portals = new ArrayList<>();
	
	private CommandFramework command;
	private SimpleConfigManager configManager;
	private SimpleConfig mainConfig;
	
	@Override
	public void onLoad() {
		INSTANCE = this;
		super.onLoad();
	}
	
	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(new ElytraRaceListener(), this);
		
		command = new CommandFramework(this);
		command.registerCommands(new ElytraRaceCommands());
		command.registerCommands(new ElytraRaceCompleter());
		command.registerHelp();
		
		ConfigurationSerialization.registerClass(ElytraRace.class);
		ConfigurationSerialization.registerClass(ElytraRacePortal.class);
		
		config();
	}
	
	private void config() {
		configManager = new SimpleConfigManager(this);
		// TODO add plugin page url
		mainConfig = configManager.getNewConfig("ElytraRaceConfig.yml",
				new String[] { "This is the config file for ElytraRace", "Find infos at <url to follow>" });
				
		// load portal colors
		for (ElytraRacePortalType type : ElytraRacePortalType.values()) {
			try {
				// TODO check if this is working
				Color color = (Color) mainConfig.get("portal." + type.name());
				type.setColor(color);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		FLY_SPEED = mainConfig.getDouble("flySpeed", 2);
		SHOW_HIGHSCORE = mainConfig.getInt("showHighscore", 5);
	}
	
	public ElytraRacePortal getPortal(Location loc) {
		for (ElytraRacePortal p : portals) {
			for (Location l : p.getLocations()) {
				if (l.getWorld().getName().equals(loc.getWorld().getName()) && l.getBlockX() == loc.getBlockX() && l.getBlockY() == loc.getBlockY()
						&& loc.getBlockZ() == l.getBlockZ()) {
					return p;
				}
			}
		}
		return null;
	}
	
	public static ElytraRaceMain getInstance() {
		return INSTANCE;
	}
	
	public ElytraRace getRace(Player player) {
		for (ElytraRace race : races) {
			if (race.getPlayers().contains(player.getUniqueId())) {
				return race;
			}
		}
		return null;
	}
	
	public ElytraRace getRace(String name) {
		for (ElytraRace race : races) {
			if (race.getName().equals(name)) {
				return race;
			}
		}
		return null;
	}
}
