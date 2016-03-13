package me.MiniDigger.ElytraRace;

import me.MiniDigger.ElytraRace.lib.com.minnymin.command.Command;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class ElytraRaceCommands {
	
	@Command(name = "elytrarace", permission = "", aliases = "er")
	public void elytrarace(CommandSender sender) throws CloneNotSupportedException {
		ElytraRaceMain.PREFIX.clone().then("===== ElytraRace =====").send(sender);
		ElytraRaceMain.PREFIX.clone().then("You are running v" + ElytraRaceMain.getInstance().getDescription().getVersion() + " by MiniDigger").send(sender);
		if (sender.hasPermission("elytrarace.user")) {
			ElytraRaceMain.PREFIX.clone().then("User Commands: ").style(ChatColor.BOLD).send(sender);
			ElytraRaceMain.PREFIX.clone().then("Use ").then("'/er start <race>'").color(ChatColor.YELLOW).then(" to start a race!").send(sender);
			ElytraRaceMain.PREFIX.clone().then("Use ").then("'/er restart <race>'").color(ChatColor.YELLOW).then(" to restart a race!").send(sender);
			ElytraRaceMain.PREFIX.clone().then("Use ").then("'/er end <race>'").color(ChatColor.YELLOW).then(" to leave a race!").send(sender);
			ElytraRaceMain.PREFIX.clone().then("Use ").then("'/er highscore <race>'").color(ChatColor.YELLOW).then(" to show the highscores for this race!")
					.send(sender);
			ElytraRaceMain.PREFIX.clone().then("Use ").then("'/er score <race>'").color(ChatColor.YELLOW).then(" to show your for this race!").send(sender);
		}
		
		if (sender.hasPermission("elytrarace.admin")) {
			ElytraRaceMain.PREFIX.clone().then("Admin Commands: ").style(ChatColor.BOLD).send(sender);
			ElytraRaceMain.PREFIX.clone().then("Use ").then("'/er create <race>'").color(ChatColor.YELLOW).then(" to create a new race!").send(sender);
			ElytraRaceMain.PREFIX.clone().then("Use ").then("'/er spawn <race>'").color(ChatColor.YELLOW).then(" to set the spawn of a race!").send(sender);
			ElytraRaceMain.PREFIX.clone().then("Use ").then("'/er portal <no> <type> <race>'").color(ChatColor.YELLOW).then(" to set a portal!").send(sender);
			ElytraRaceMain.PREFIX.clone().then("Use ").then("'/er delete <race>'").color(ChatColor.YELLOW).then(" to delete a race!").send(sender);
		}
	}
	
	@Command(name = "elytrarace.start", permission = "elytrarace.user", aliases = "er.start", usage = "elytrarace start <race>")
	public void start(CommandSender sender) {
		// TODO implement missing command
	}
	
	@Command(name = "elytrarace.restart", permission = "elytrarace.user", aliases = "er.restart", usage = "elytrarace restart <race>")
	public void restart(CommandSender sender) {
		// TODO implement missing command
	}
	
	@Command(name = "elytrarace.end", permission = "elytrarace.user", aliases = "er.end", usage = "elytrarace end <race>")
	public void end(CommandSender sender) {
		// TODO implement missing command
	}
	
	@Command(name = "elytrarace.highscore", permission = "elytrarace.user", aliases = "er.highscore", usage = "elytrarace highscore <race>")
	public void highscore(CommandSender sender) {
		// TODO implement missing command
	}
	
	@Command(name = "elytrarace.score", permission = "elytrarace.user", aliases = "er.score", usage = "elytrarace score <race>")
	public void score(CommandSender sender) {
		// TODO implement missing command
	}
	
	@Command(name = "elytrarace.create", permission = "elytrarace.admin", aliases = "er.create", usage = "elytrarace create <race>")
	public void create(CommandSender sender) {
		// TODO implement missing command
	}
	
	@Command(name = "elytrarace.spawn", permission = "elytrarace.admin", aliases = "er.spawn", usage = "elytrarace spawn <race>")
	public void spawn(CommandSender sender) {
		// TODO implement missing command
	}
	
	@Command(name = "elytrarace.portal", permission = "elytrarace.admin", aliases = "er.portal", usage = "elytrarace portal <no> <type> <race>")
	public void portal(CommandSender sender) {
		// TODO implement missing command
	}
	
	@Command(name = "elytrarace.delete", permission = "elytrarace.admin", aliases = "er.delete", usage = "elytrarace delete <race>")
	public void delete(CommandSender sender) {
		// TODO implement missing command
	}
}
