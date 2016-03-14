package me.MiniDigger.ElytraRace;

import java.util.*;

import me.MiniDigger.ElytraRace.ElytraRaceScoreCalculator.ElytraRaceScoreCalculatorType;

import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;

public class ElytraRace implements ConfigurationSerializable {
	private String name;
	private Location spawn;
	private List<ElytraRacePortal> portals = new ArrayList<>();
	private Map<UUID, Double> scores = new HashMap<>();
	private Map<UUID, Integer> portalNos = new HashMap<>();
	private ElytraRaceScoreCalculator scoreCalc;
	
	public ElytraRace(String name, Location spawn, ElytraRaceScoreCalculatorType type) {
		this(name, spawn, new ArrayList<ElytraRacePortal>(), new HashMap<UUID, Double>(), type);
	}
	
	public ElytraRace(String name, Location spawn, List<ElytraRacePortal> portals, Map<UUID, Double> scores, ElytraRaceScoreCalculatorType type) {
		super();
		this.name = name;
		this.spawn = spawn;
		this.portals = portals;
		this.scores = scores;
		this.scoreCalc = new ElytraRaceScoreCalculator(type);
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
	
	public void setSpawn(Location spawn) {
		this.spawn = spawn;
	}
	
	@SuppressWarnings("unchecked")
	public static ElytraRace deserialize(Map<String, Object> map) {
		return new ElytraRace((String) map.get("name"), (Location) map.get("spawn"), (List<ElytraRacePortal>) map.get("portals"),
				(Map<UUID, Double>) map.get("scores"), ElytraRaceScoreCalculatorType.valueOf((String) map.get("type")));
	}
	
	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<>();
		map.put("name", name);
		map.put("spawn", spawn);
		map.put("portals", portals);
		map.put("scores", scores);
		map.put("type", scoreCalc.getType().name());
		return map;
	}
	
	public Map<UUID, Double> getHighScores() {
		return sortByValue(scores);
	}
	
	private Map<UUID, Double> sortByValue(Map<UUID, Double> map) {
		List<Map.Entry<UUID, Double>> list = new LinkedList<>(map.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<UUID, Double>>() {
			@Override
			public int compare(Map.Entry<UUID, Double> o1, Map.Entry<UUID, Double> o2) {
				return -1 * (o1.getValue()).compareTo(o2.getValue());
			}
		});
		Map<UUID, Double> result = new LinkedHashMap<>();
		for (Map.Entry<UUID, Double> entry : list) {
			result.put(entry.getKey(), entry.getValue());
		}
		return result;
	}
}
