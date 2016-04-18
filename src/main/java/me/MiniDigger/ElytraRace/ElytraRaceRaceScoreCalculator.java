package me.MiniDigger.ElytraRace;

import org.bukkit.entity.Player;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ElytraRaceRaceScoreCalculator extends ElytraRaceScoreCalculator {

    private Map<UUID, Integer> portalNo;
    private Map<UUID, Date> startTimes;

    public ElytraRaceRaceScoreCalculator() {
        super(ElytraRaceScoreCalculatorType.RACE);
        portalNo = new HashMap<>();
        startTimes = new HashMap<>();
    }

    @Override
    public void startRace(Player p) {
        portalNo.put(p.getUniqueId(), -1);
        startTimes.put(p.getUniqueId(), new Date());
    }

    @Override
    public void stopRace(Player p) {
        portalNo.remove(p.getUniqueId());
        startTimes.remove(p.getUniqueId());
    }

    @Override
    public void portal(Player p, ElytraRacePortal portal) {
        int newNo = portal.getNo();
        int oldNo = portalNo.get(p.getUniqueId());

        System.out.println("newNo " +newNo);
        System.out.println("oldNo " +oldNo);

        if(newNo != oldNo+1){
            portal.applyNegativEffect(p);
        }else{
            portal.applyEffect(p);
            portalNo.put(p.getUniqueId(),newNo);
        }
    }
}
