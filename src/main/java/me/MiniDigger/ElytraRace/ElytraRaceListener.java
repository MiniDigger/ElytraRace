package me.MiniDigger.ElytraRace;

import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.entity.*;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.*;

public class ElytraRaceListener implements Listener {
	
	@EventHandler
	public void toggleElytra(EntityToggleGlideEvent e) {
		if (e.getEntityType() == EntityType.PLAYER && !e.isGliding()) {
			Player p = (Player) e.getEntity();
			if (ElytraRaceMain.getInstance().getRace(p) != null) {
				e.setCancelled(true);
				// TODO change speed based on angle, better boost and low handeling
				if (p.getVelocity().length() < p.getLocation().getDirection().multiply(ElytraRaceMain.FLY_SPEED).length()) {
					p.setVelocity(p.getLocation().getDirection().multiply(ElytraRaceMain.FLY_SPEED));
				}
			}
		}
	}
	
	@EventHandler
	public void enterPortal(PlayerMoveEvent e) {
		if (e.getFrom().getBlockX() == e.getTo().getBlockX() && e.getFrom().getBlockY() == e.getTo().getBlockY()
				&& e.getFrom().getBlockZ() == e.getTo().getBlockZ()) {
			return;
		}
		
		Player player = e.getPlayer();
		if (!player.isGliding()) {
			return;
		}
		
		ElytraRace race = ElytraRaceMain.getInstance().getRace(player);
		if (race == null) {
			return;
		}
		
		ElytraRacePortal portal = ElytraRaceMain.getInstance().getPortal(e.getTo());
		if (portal == null) {
			return;
		}
		
		if (portal.getRaceName().equals(race.getName())) {
			int no = race.getPortals().indexOf(portal);
			System.out.println("old no " + race.getPortalNo(player));
			System.out.println("portal no " + no);
			// TODO fix portal no (add a field to portal containing the no in the race)
			if (race.getPortalNo(player) == no - 1) {
				portal.applyEffect(player);
				race.setPortalNo(player, no);
			} else {
				portal.applyNegativEffect(player);
			}
		}
	}
	
	@EventHandler
	public void onCrash(EntityDamageEvent e) {
		if (e.getEntityType() != EntityType.PLAYER) {
			return;
		}
		
		Player p = (Player) e.getEntity();
		if (e.getCause() != DamageCause.FLY_INTO_WALL) {
			return;
		}
		
		ElytraRace race = ElytraRaceMain.getInstance().getRace(p);
		if (race == null) {
			return;
		}
		
		if (ElytraRaceMain.WHIP_MODE) {
			race.remPlayer(p);
			p.setGliding(false);
			// TODO handle score stuff
			p.setHealth(1.0);
			p.getLocation().getWorld().strikeLightning(p.getLocation());
			p.damage(9000);// gotta be sure
		} else {
			e.setCancelled(true);
		}
	}
}
