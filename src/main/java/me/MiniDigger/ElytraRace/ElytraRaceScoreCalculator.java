package me.MiniDigger.ElytraRace;

import org.bukkit.entity.Player;

public abstract class ElytraRaceScoreCalculator {
	
	private ElytraRaceScoreCalculatorType type;
	
	public ElytraRaceScoreCalculator(ElytraRaceScoreCalculatorType type) {
		this.type = type;
	}
	
	public abstract void startRace(Player p);
	
	public abstract void stopRace(Player p) ;
	
	public abstract void portal(Player p, ElytraRacePortal portal) ;
	
	public ElytraRaceScoreCalculatorType getType() {
		return type;
	}
	
	public enum ElytraRaceScoreCalculatorType {
		/**
		 * Time based, need to enter all portals
		 */
		RACE(ElytraRaceRaceScoreCalculator.class),
		/**
		 * Get points for flying though portals
		 */
		POINTS(ElytraRacePointsScoreCalculator.class);

		private Class clazz;

		ElytraRaceScoreCalculatorType(Class clazz){
			this.clazz = clazz;
		}

		public ElytraRaceScoreCalculator newInstance() {
			try {
				return (ElytraRaceScoreCalculator) clazz.newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				ElytraRaceMain.getInstance().getLogger().warning("Could not instantiate a new ScoreCalculator from class " + clazz);
				e.printStackTrace();
			}
            return null;
		}
	}
}
