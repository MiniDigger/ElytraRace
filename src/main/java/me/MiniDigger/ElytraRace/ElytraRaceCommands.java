package me.MiniDigger.ElytraRace;

import java.util.*;

import me.MiniDigger.ElytraRace.ElytraRacePortal.ElytraRacePortalType;
import me.MiniDigger.ElytraRace.lib.com.minnymin.command.*;

import org.bukkit.*;

public class ElytraRaceCommands {
	
	@Command(name = "elytrarace", permission = "", aliases = "er")
	public void elytrarace(CommandArgs args) throws CloneNotSupportedException {
		ElytraRaceMain.PREFIX.clone().then("===== ElytraRace =====").send(args.getSender());
		ElytraRaceMain.PREFIX.clone().then("You are running v" + ElytraRaceMain.getInstance().getDescription().getVersion() + " by MiniDigger")
				.send(args.getSender());
		if (args.getSender().hasPermission("elytrarace.user")) {
			ElytraRaceMain.PREFIX.clone().then("User Commands: ").style(ChatColor.BOLD).send(args.getSender());
			ElytraRaceMain.PREFIX.clone().then("Use ").then("'/er start <race>'").color(ChatColor.YELLOW).then(" to start a race!").send(args.getSender());
			ElytraRaceMain.PREFIX.clone().then("Use ").then("'/er restart <race>'").color(ChatColor.YELLOW).then(" to restart a race!").send(args.getSender());
			ElytraRaceMain.PREFIX.clone().then("Use ").then("'/er end <race>'").color(ChatColor.YELLOW).then(" to leave a race!").send(args.getSender());
			ElytraRaceMain.PREFIX.clone().then("Use ").then("'/er highscore <race>'").color(ChatColor.YELLOW).then(" to show the highscores for this race!")
					.send(args.getSender());
			ElytraRaceMain.PREFIX.clone().then("Use ").then("'/er score <race>'").color(ChatColor.YELLOW).then(" to show your for this race!")
					.send(args.getSender());
		}
		
		if (args.getSender().hasPermission("elytrarace.admin")) {
			ElytraRaceMain.PREFIX.clone().then("Admin Commands: ").style(ChatColor.BOLD).send(args.getSender());
			ElytraRaceMain.PREFIX.clone().then("Use ").then("'/er create <race>'").color(ChatColor.YELLOW).then(" to create a new race!")
					.send(args.getSender());
			ElytraRaceMain.PREFIX.clone().then("Use ").then("'/er spawn <race>'").color(ChatColor.YELLOW).then(" to set the spawn of a race!")
					.send(args.getSender());
			ElytraRaceMain.PREFIX.clone().then("Use ").then("'/er portal <no> <type> <race>'").color(ChatColor.YELLOW).then(" to set a portal!")
					.send(args.getSender());
			ElytraRaceMain.PREFIX.clone().then("Use ").then("'/er delete <race>'").color(ChatColor.YELLOW).then(" to delete a race!").send(args.getSender());
		}
	}
	
	@Command(name = "elytrarace.start", permission = "elytrarace.user", aliases = "er.start", usage = "elytrarace start <race>", inGameOnly = true)
	public void start(CommandArgs args) throws CloneNotSupportedException {
		if (args.length() != 1) {
			ElytraRaceMain.PREFIX.clone().then("Wrong Arguments entered: " + args.getCommand().getUsage()).color(ChatColor.RED).send(args.getSender());
			return;
		}
		
		String name = args.getArgs()[0];
		ElytraRace race = ElytraRaceMain.getInstance().getRace(name);
		if (race == null) {
			ElytraRaceMain.PREFIX.clone().then("Unknown Race: " + name).color(ChatColor.RED).send(args.getSender());
			return;
		}
		
		//TODO add the score stuff
		
		race.addPlayer(args.getPlayer());
		args.getPlayer().teleport(race.getSpawn());
		args.getPlayer().setGliding(true);
	}
	
	@Command(name = "elytrarace.restart", permission = "elytrarace.user", aliases = "er.restart", usage = "elytrarace restart", inGameOnly = true)
	public void restart(CommandArgs args) throws CloneNotSupportedException {
		// TODO implement missing command
		if (args.length() != 1) {
			ElytraRaceMain.PREFIX.clone().then("Wrong Arguments entered: " + args.getCommand().getUsage()).color(ChatColor.RED).send(args.getSender());
			return;
		}
		
		String name = args.getArgs()[0];
		ElytraRace race = ElytraRaceMain.getInstance().getRace(name);
		if (race == null) {
			ElytraRaceMain.PREFIX.clone().then("Unknown Race: " + name).color(ChatColor.RED).send(args.getSender());
			return;
		}
		
		//TODO add the score stuff
		args.getPlayer().teleport(race.getSpawn());
	}
	
	@Command(name = "elytrarace.end", permission = "elytrarace.user", aliases = "er.end", usage = "elytrarace end", inGameOnly = true)
	public void end(CommandArgs args) throws CloneNotSupportedException {
		ElytraRace race = ElytraRaceMain.getInstance().getRace(args.getPlayer());
		race.remPlayer(args.getPlayer());
		//TODO teleport the player somewhere maybe? ^^
		//TODO handle score stuff
		args.getPlayer().setGliding(false);
	}
	
	@Command(name = "elytrarace.highscore", permission = "elytrarace.user", aliases = "er.highscore", usage = "elytrarace highscore <race>")
	public void highscore(CommandArgs args) throws CloneNotSupportedException {
		if (args.length() != 1) {
			ElytraRaceMain.PREFIX.clone().then("Wrong Arguments entered: " + args.getCommand().getUsage()).color(ChatColor.RED).send(args.getSender());
			return;
		}
		
		String name = args.getArgs()[0];
		ElytraRace race = ElytraRaceMain.getInstance().getRace(name);
		if (race == null) {
			ElytraRaceMain.PREFIX.clone().then("Unknown Race: " + name).color(ChatColor.RED).send(args.getSender());
			return;
		}
		
		ElytraRaceMain.PREFIX.clone().then("==== Highscores for Race " + name + " ====").send(args.getSender());
		
		Map<UUID, Double> highscores = race.getHighScores();
		int c = 1;
		for (UUID id : highscores.keySet()) {
			if (c < ElytraRaceMain.SHOW_HIGHSCORE + 1) {
				OfflinePlayer p = Bukkit.getOfflinePlayer(id);
				ElytraRaceMain.PREFIX.clone().then("#" + c + " " + p.getName() + ": " + highscores.get(id)).send(args.getSender());
			}
			c++;
		}
	}
	
	@Command(name = "elytrarace.score", permission = "elytrarace.user", aliases = "er.score", usage = "elytrarace score <race>", inGameOnly = true)
	public void score(CommandArgs args) throws CloneNotSupportedException {
		if (args.length() != 1) {
			ElytraRaceMain.PREFIX.clone().then("Wrong Arguments entered: " + args.getCommand().getUsage()).color(ChatColor.RED).send(args.getSender());
			return;
		}
		
		String name = args.getArgs()[0];
		ElytraRace race = ElytraRaceMain.getInstance().getRace(name);
		if (race == null) {
			ElytraRaceMain.PREFIX.clone().then("Unknown Race: " + name).color(ChatColor.RED).send(args.getSender());
			return;
		}
		
		double score = race.getScore(args.getPlayer());
		
		ElytraRaceMain.PREFIX.clone().then("Your score for race " + name + ": " + score).send(args.getSender());
	}
	
	@Command(name = "elytrarace.create", permission = "elytrarace.admin", aliases = "er.create", usage = "elytrarace create <race>", inGameOnly = true)
	public void create(CommandArgs args) throws CloneNotSupportedException {
		if (args.length() != 1) {
			ElytraRaceMain.PREFIX.clone().then("Wrong Arguments entered: " + args.getCommand().getUsage()).color(ChatColor.RED).send(args.getSender());
			return;
		}
		String name = args.getArgs()[0];
		
		if (ElytraRaceMain.getInstance().getRace(name) != null) {
			ElytraRaceMain.PREFIX.clone().then("A race with that name already exists!").color(ChatColor.RED).send(args.getSender());
			return;
		}
		
		ElytraRace race = new ElytraRace(name, args.getPlayer().getLocation());
		ElytraRaceMain.getInstance().addRace(race);
		
		ElytraRaceMain.PREFIX.clone().then("Race created!").color(ChatColor.GREEN).send(args.getSender());
	}
	
	@Command(name = "elytrarace.spawn", permission = "elytrarace.admin", aliases = "er.spawn", usage = "elytrarace spawn <race>", inGameOnly = true)
	public void spawn(CommandArgs args) throws CloneNotSupportedException {
		if (args.length() != 1) {
			ElytraRaceMain.PREFIX.clone().then("Wrong Arguments entered: " + args.getCommand().getUsage()).color(ChatColor.RED).send(args.getSender());
			return;
		}
		
		String name = args.getArgs()[0];
		ElytraRace race = ElytraRaceMain.getInstance().getRace(name);
		if (race == null) {
			ElytraRaceMain.PREFIX.clone().then("Unknown Race: " + name).color(ChatColor.RED).send(args.getSender());
			return;
		}
		
		race.setSpawn(args.getPlayer().getLocation());
		
		ElytraRaceMain.PREFIX.clone().then("Spawn set!").color(ChatColor.GREEN).send(args.getSender());
	}
	
	@Command(name = "elytrarace.portal", permission = "elytrarace.admin", aliases = "er.portal", usage = "elytrarace portal <no> <type> <race>", inGameOnly = true)
	public void portal(CommandArgs args) throws CloneNotSupportedException {
		if (args.length() != 3) {
			ElytraRaceMain.PREFIX.clone().then("Wrong Arguments entered: " + args.getCommand().getUsage()).color(ChatColor.RED).send(args.getSender());
			return;
		}
		
		String race;
		ElytraRacePortalType type;
		int no;
		
		race = args.getArgs()[2];
		if (ElytraRaceMain.getInstance().getRace(race) == null) {
			ElytraRaceMain.PREFIX.clone().then("Unknown Race: " + race).color(ChatColor.RED).send(args.getSender());
			return;
		}
		
		try {
			type = ElytraRacePortalType.valueOf(args.getArgs()[1]);
		} catch (Exception e) {
			ElytraRaceMain.PREFIX.clone().then("Unknown Portal Type: " + args.getArgs()[1]).color(ChatColor.RED).send(args.getSender());
			return;
		}
		
		no = Integer.parseInt(args.getArgs()[0]);
		ElytraRacePortal p = new ElytraRacePortal(race, type);
		
		Location loc = args.getPlayer().getTargetBlock((Set<Material>) null, 10).getLocation();
		p.createPortal(loc);
		p.applyCreationEffect(args.getPlayer());
		ElytraRaceMain.getInstance().getRace(race).addPortal(p, no);
		ElytraRaceMain.getInstance().saveRaces();
		
		ElytraRaceMain.PREFIX.clone().then("Portal Created!").color(ChatColor.GREEN).send(args.getSender());
	}
	
	@Command(name = "elytrarace.delete", permission = "elytrarace.admin", aliases = "er.delete", usage = "elytrarace delete <race>", inGameOnly = true)
	public void delete(CommandArgs args) throws CloneNotSupportedException {
		if (args.length() != 1) {
			ElytraRaceMain.PREFIX.clone().then("Wrong Arguments entered: " + args.getCommand().getUsage()).color(ChatColor.RED).send(args.getSender());
			return;
		}
		
		String name = args.getArgs()[0];
		ElytraRace race = ElytraRaceMain.getInstance().getRace(name);
		if (race == null) {
			ElytraRaceMain.PREFIX.clone().then("Unknown Race: " + name).color(ChatColor.RED).send(args.getSender());
			return;
		}
		
		ElytraRaceMain.getInstance().deleteRace(race);
	}
}
