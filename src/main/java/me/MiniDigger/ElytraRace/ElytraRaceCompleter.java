package me.MiniDigger.ElytraRace;

import java.util.*;

import me.MiniDigger.ElytraRace.lib.com.minnymin.command.*;

public class ElytraRaceCompleter {
	
	@Completer(name = "elytrarace", aliases = "er")
	public List<String> elytrarace(CommandArgs args) {
		final List<String> result = new ArrayList<>();
		
		if (args.getArgs().length == 1) {
			if (args.getSender().hasPermission("elytrarace.user")) {
				result.add("start");
				result.add("restart");
				result.add("end");
				result.add("highscore");
				result.add("score");
			}
			if (args.getSender().hasPermission("elyrarace.admin")) {
				result.add("create");
				result.add("spawn");
				result.add("portal");
				result.add("delete");
			}
			
			return completer(result, args.getArgs()[0]);
		} else {
			return new ArrayList<String>();
		}
	}
	
	// TODO add more tab completer
	
	private List<String> completer(final List<String> list, final String prefix) {
		final List<String> result = new ArrayList<>();
		
		for (final String s : list) {
			if (s.toLowerCase().startsWith(prefix.toLowerCase())) {
				result.add(s);
			}
		}
		
		return result;
	}
}
