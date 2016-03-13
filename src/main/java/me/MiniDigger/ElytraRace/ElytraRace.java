package me.MiniDigger.ElytraRace;

import java.util.*;

import org.bukkit.*;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;

public class ElytraRace implements ConfigurationSerializable {
	private String name;
	private Location spawn;
	private List<ElytraRacePortal> portals = new ArrayList<>();
	private Map<UUID, Double> scores = new HashMap<>();
	private Map<UUID, Integer> portalNos = new HashMap<>();
	
	public ElytraRace(String name, Location spawn) {
		this(name, spawn, new ArrayList<ElytraRacePortal>(), new HashMap<UUID, Double>());
	}
	
	public ElytraRace(String name, Location spawn, List<ElytraRacePortal> portals, Map<UUID, Double> scores) {
		super();
		this.name = name;
		this.spawn = spawn;
		this.portals = portals;
		this.scores = scores;
	}
	
	public void addPortal(ElytraRacePortal p, int no) {
		portals.add(no, p);
	}
	
	public void remPlayer(Player p) {
		portalNos.remove(p.getUniqueId());
	}
	
	public void addPlayer(Player p) {
		portalNos.put(p.getUniqueId(), 0);
	}
	
	/**
	 * @return if the score was changed
	 */
	public boolean addScore(Player p, double score) {
		double oldScore = getScore(p);
		if (oldScore != ElytraRaceMain.NO_SCORE && oldScore > score) {
			scores.put(p.getUniqueId(), score);
			return true;
		}
		return false;
	}
	
	public double getScore(Player p) {
		if (scores.containsKey(p.getUniqueId())) {
			return scores.get(p.getUniqueId());
		}
		return ElytraRaceMain.NO_SCORE;
	}
	
	public String getName() {
		return name;
	}
	
	public Location getSpawn() {
		return spawn;
	}
	
	public List<ElytraRacePortal> getPortals() {
		return portals;
	}
	
	public Map<UUID, Double> getScores() {
		return scores;
	}
	
	public Set<UUID> getPlayers() {
		return portalNos.keySet();
	}
	
	public int getPortalNo(Player p) {
		return portalNos.get(p.getUniqueId());
	}
	
	public void setPortalNo(Player p, int no) {
		portalNos.put(p.getUniqueId(), 0);
	}
	
	public static void deserialize(Map<String, Object> map) {
		// TODO DeSerialize ElytraRace
	}
	
	@Override
	public Map<String, Object> serialize() {
		// TODO Serialize ElytraRace
		return null;
	}
}
