package io.github.biielkts.scoreboard;

import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Team;

public class Scoreboard {

	private static final String LINE_TEAM_PREFIX = "sb0=";
	private Player player;
	private Objective objective;
	private org.bukkit.scoreboard.Scoreboard scoreboard;
	private HashMap<Integer, String> lines;
	private static HashMap<Player, Scoreboard> scoreboardManager = new HashMap<>();
	
	public Scoreboard(Player player) {
		this.player = player;
		this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
		
		this.objective = this.scoreboard.registerNewObjective("score", "dummy");
		
		this.lines = new HashMap<>();
	}
	
	public void setTitle(String text) {
		this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		this.objective.setDisplayName(text);
	}
	
	public void addLine(String text) {
		int index = 0;
		if (!this.lines.isEmpty()) {
			index = this.lines.size();
		}
		this.lines.put(index, text);
	}
	
	public void addLine(int line, String text) {
		this.lines.put(line, text);
	}
	
	public void updateLine(int line, String text) {
		this.lines.put(line, text);
		Team team = this.scoreboard.getTeam(LINE_TEAM_PREFIX + objective.getName() + line);
		putTextScoreboard(team, text);
	}
	
	public void putLines() {
		for (Entry<Integer, String> x : this.lines.entrySet()) {
			Team team = newRegisterTeam(x.getKey());
			
			putTextScoreboard(team, x.getValue());
			
			String entry = "§" + this.convertIntToColor(x.getKey());
			team.addEntry(entry);
			this.objective.getScore(entry).setScore(x.getKey());
		}
	}

	private Team newRegisterTeam(int line) {
		return this.scoreboard.registerNewTeam(LINE_TEAM_PREFIX + objective.getName() + line);
	}
	
	private void putTextScoreboard(Team team, String text) {
		String prefix = "";
		String suffix = text;

		if (text.length() > 16) {
			int colorIndex = Math.min(16, text.length() - 1);


			while (text.charAt(colorIndex) == '§' || text.charAt(colorIndex - 1) == '§') {
				colorIndex--;
			}

			prefix = text.substring(0, colorIndex);
			suffix = ChatColor.getLastColors(prefix) + text.substring(colorIndex);
			suffix = suffix.substring(0, Math.min(16, suffix.length()));
		}

		team.setPrefix(prefix);
		team.setSuffix(suffix);
	}
	
	public Player getPlayer() {
		return this.player;
	}
	
	public org.bukkit.scoreboard.Scoreboard getScoreboard() {
		return this.scoreboard;
	}
	
	public void apply() {
		if (this.player != null) {
			putLines();
			this.player.setScoreboard(this.scoreboard);
			scoreboardManager.put(this.player, this);
		}
	}

	public static Scoreboard get(Player player) {
		return (scoreboardManager.get(player) != null ? scoreboardManager.get(player) : null);
	}
	
    private String convertIntToColor(int priority) {
    	StringBuilder sb = new StringBuilder();
    	char[] alphabet = "abcdef".toCharArray();
    	if (priority > 9) {
    		sb.append(alphabet[priority - 9]);
    	} else {
    		sb.append(priority);
    	}
    	return sb.toString();
    }
	
}
