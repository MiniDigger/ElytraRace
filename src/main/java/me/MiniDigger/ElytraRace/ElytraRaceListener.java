package me.MiniDigger.ElytraRace;

import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.event.player.*;

public class ElytraRaceListener implements Listener {
	
	@EventHandler
	public void toggleElytra(EntityToggleGlideEvent e) {
		if (e.getEntityType() == EntityType.PLAYER && !e.isGliding()) {
			Player p = (Player) e.getEntity();
			if (ElytraRaceMain.getInstance().getRace(p) != null) {
				e.setCancelled(true);
				p.setVelocity(p.getLocation().getDirection().multiply(ElytraRaceMain.FLY_SPEED));
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
			if (race.getPortalNo(player) == no - 1) {
				portal.applyEffect(player);
				race.setPortalNo(player, no);
			} else {
				portal.applyNegativEffect(player);
			}
		}
	}
}
