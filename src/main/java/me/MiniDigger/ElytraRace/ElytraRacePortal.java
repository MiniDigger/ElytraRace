package me.MiniDigger.ElytraRace;

import java.util.*;

import org.inventivetalent.particle.ParticleEffect;

import org.bukkit.*;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_9_R1.*;
import net.minecraft.server.v1_9_R1.PacketPlayOutTitle.EnumTitleAction;

public class ElytraRacePortal implements ConfigurationSerializable {
	
	private String race;
	private ElytraRacePortalType type;
	private List<Location> locs;
	
	public ElytraRacePortal(String race, ElytraRacePortalType type, List<Location> locs) {
		this.race = race;
		this.type = type;
		this.locs = locs;
	}
	
	public List<Location> getLocations() {
		return locs;
	}
	
	public String getRaceName() {
		return race;
	}
	
	public ElytraRacePortalType getType() {
		return type;
	}
	
	public ElytraRace getRace() {
		return ElytraRaceMain.getInstance().getRace(race);
	}
	
	public void createPortal(Location loc) {
		checkLoc(loc.getBlock());
	}
	
	private void checkLoc(Block b) {
		if (locs.contains(b.getLocation())) {
			return;
		}
		
		if (b.getType() != Material.STAINED_GLASS) {
			return;
		}
		
		b.setType(Material.AIR);
		locs.add(b.getLocation());
		
		checkLoc(b.getRelative(BlockFace.UP));
		checkLoc(b.getRelative(BlockFace.DOWN));
		checkLoc(b.getRelative(BlockFace.NORTH));
		checkLoc(b.getRelative(BlockFace.NORTH_EAST));
		checkLoc(b.getRelative(BlockFace.EAST));
		checkLoc(b.getRelative(BlockFace.SOUTH_EAST));
		checkLoc(b.getRelative(BlockFace.SOUTH));
		checkLoc(b.getRelative(BlockFace.SOUTH_WEST));
		checkLoc(b.getRelative(BlockFace.WEST));
		checkLoc(b.getRelative(BlockFace.NORTH_WEST));
	}
	
	public enum ElytraRacePortalType {
		NORMAL(Color.WHITE), FINISH(Color.BLACK), BOOST(Color.YELLOW), LOW(Color.GRAY);
		
		private Color color;
		
		private ElytraRacePortalType(Color color) {
			this.color = color;
		}
		
		public void setColor(Color color) {
			this.color = color;
		}
		
		public Color getColor() {
			return color;
		}
	}
	
	public void applyEffect(Player p) {
		Color color = Color.AQUA;
		switch (type) {
		case BOOST:
			p.getPlayer().setVelocity(p.getLocation().getDirection().multiply(ElytraRaceMain.BOOST_SPEED));
			actionbar(p, ChatColor.GOLD + "Boost!");
			break;
		case FINISH:
			// TODO Handle finish
			actionbar(p, ChatColor.GOLD + "Finish!");
			break;
		case LOW:
			p.getPlayer().setVelocity(p.getLocation().getDirection().multiply(ElytraRaceMain.SLOW_SPEED));
			actionbar(p, ChatColor.GOLD + "Slow!");
			break;
		case NORMAL:
			break;
		default:
			break;
		}
		
		effect(color);
		ElytraRace race = getRace();
		title(p, ChatColor.GOLD + "Portal passed", ChatColor.GOLD + "Only " + (race.getPortals().size() - race.getPortalNo(p)) + " Portals to go", 20, 2 * 20,
				20);
	}
	
	public void applyNegativEffect(Player player) {
		effect(Color.BLACK);
		actionbar(player, ChatColor.RED + "That was the wrong portal!");
		title(player, ChatColor.BLACK + "Wrong Portal", ChatColor.BLACK + "You need to pass " + (getRace().getPortalNo(player) + 1) + " First", 20, 2 * 20, 20);
	}
	
	@SuppressWarnings("unchecked")
	public static ElytraRacePortal deserialize(Map<String, Object> map) {
		return new ElytraRacePortal((String) map.get("race"), ElytraRacePortalType.valueOf((String) map.get("type")), (List<Location>) map.get("locs"));
	}
	
	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<>();
		map.put("race", race);
		map.put("type", type.name());
		map.put("locs", locs);
		return map;
	}
	
	private void effect(Color color) {
		List<Player> players = new ArrayList<>();
		for (UUID id : getRace().getPlayers()) {
			Player pp = Bukkit.getPlayer(id);
			if (pp != null) {
				players.add(pp);
			}
		}
		
		for (Location loc : locs) {
			ParticleEffect.CLOUD.sendColor(players, (double) loc.getBlockX(), (double) loc.getBlockY(), (double) loc.getBlockZ(), color);
		}
	}
	
	private void actionbar(Player p, String msg) {
		ChatComponentText comp = new ChatComponentText(msg);
		PacketPlayOutChat packet = new PacketPlayOutChat(comp, (byte) 2);
		EntityPlayer nmsp = ((CraftPlayer) p).getHandle();
		nmsp.playerConnection.sendPacket(packet);
	}
	
	private void title(Player p, String msg, String msg2, int fadeIn, int stay, int fadeOut) {
		ChatComponentText title = new ChatComponentText(msg);
		ChatComponentText subtitle = new ChatComponentText(msg2);
		PacketPlayOutTitle p1 = new PacketPlayOutTitle(EnumTitleAction.TITLE, title);
		PacketPlayOutTitle p2 = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, subtitle);
		PacketPlayOutTitle p3 = new PacketPlayOutTitle(fadeIn, stay, fadeOut);
		EntityPlayer nmsp = ((CraftPlayer) p).getHandle();
		if (!"".equals(msg)) {
			nmsp.playerConnection.sendPacket(p1);
		}
		if (!"".equals(msg2)) {
			nmsp.playerConnection.sendPacket(p2);
		}
		nmsp.playerConnection.sendPacket(p3);
	}
}
