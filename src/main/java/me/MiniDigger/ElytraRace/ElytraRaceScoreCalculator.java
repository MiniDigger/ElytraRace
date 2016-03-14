package me.MiniDigger.ElytraRace;

import org.bukkit.entity.Player;

public class ElytraRaceScoreCalculator {
	
	private ElytraRaceScoreCalculatorType type;
	
	public ElytraRaceScoreCalculator(ElytraRaceScoreCalculatorType type) {
		this.type = type;
	}
	
	public void startRace(Player p) {
	
	}
	
	public void stopRace(Player p) {
	
	}
	
	public void portal(Player p, ElytraRacePortal portal) {
	
	}
	
	public ElytraRaceScoreCalculatorType getType() {
		return type;
	}
	
	public enum ElytraRaceScoreCalculatorType {
		/**
		 * Time based, need to enter all portals
		 */
		RACE,
		/**
		 * Get points for flying though portals
		 */
		POINTS;
	}
}
