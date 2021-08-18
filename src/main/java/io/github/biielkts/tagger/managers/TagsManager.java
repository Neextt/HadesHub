package io.github.biielkts.tagger.managers;

import io.github.biielkts.tagger.data.FakeTeam;
import io.github.biielkts.tagger.packets.PacketWrapper;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.*;

public class TagsManager {
    private HashMap<String, FakeTeam> TEAMS;
    private HashMap<String, FakeTeam> CACHED_FAKE_TEAMS;

    public TagsManager() {
        TEAMS = new HashMap<String, FakeTeam>();
        CACHED_FAKE_TEAMS = new HashMap<String, FakeTeam>();
    }

    private FakeTeam getFakeTeam(String prefix, String suffix) {
        for (FakeTeam fakeTeam : this.TEAMS.values()) {
            if (fakeTeam.isSimilar(prefix, suffix)) {
                return fakeTeam;
            }
        }
        return null;
    }

    private void addPlayerToTeam(String player, String prefix, String suffix, int sortPriority, boolean playerTag) {
        FakeTeam previous = this.getFakeTeam(player);
        if (previous != null && previous.isSimilar(prefix, suffix)) {
            return;
        }
        this.reset(player);
        FakeTeam joining = this.getFakeTeam(prefix, suffix);
        if (joining != null) {
            joining.addMember(player);
        } else {
            joining = new FakeTeam(prefix, suffix, sortPriority, playerTag);
            joining.addMember(player);
            this.TEAMS.put(joining.getName(), joining);
            this.addTeamPackets(joining);
        }
        Player adding = Bukkit.getPlayerExact(player);
        if (adding != null) {
            this.addPlayerToTeamPackets(joining, adding.getName());
            this.cache(adding.getName(), joining);
        } else {
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(player);
            this.addPlayerToTeamPackets(joining, offlinePlayer.getName());
            this.cache(offlinePlayer.getName(), joining);
        }
    }

    public FakeTeam reset(String player) {
        return this.reset(player, this.decache(player));
    }

    private FakeTeam reset(String player, FakeTeam fakeTeam) {
        if (fakeTeam != null && fakeTeam.getMembers().remove(player)) {
            Player removing = Bukkit.getPlayerExact(player);
            boolean delete;
            if (removing != null) {
                delete = this.removePlayerFromTeamPackets(fakeTeam, removing.getName());
            } else {
                OfflinePlayer toRemoveOffline = Bukkit.getOfflinePlayer(player);
                delete = this.removePlayerFromTeamPackets(fakeTeam, toRemoveOffline.getName());
            }
            if (delete) {
                this.removeTeamPackets(fakeTeam);
                this.TEAMS.remove(fakeTeam.getName());
            }
        }
        return fakeTeam;
    }

    private FakeTeam decache(String player) {
        return this.CACHED_FAKE_TEAMS.remove(player);
    }

    public FakeTeam getFakeTeam(String player) {
        return this.CACHED_FAKE_TEAMS.get(player);
    }

    private void cache(String player, FakeTeam fakeTeam) {
        this.CACHED_FAKE_TEAMS.put(player, fakeTeam);
    }

    public void setNametag(String player, String prefix, String suffix) {
        this.setNametag(player, prefix, suffix, -1);
    }

    public void setNametag(String player, String prefix, String suffix, int sortPriority) {
        this.setNametag(player, prefix, suffix, sortPriority, false);
    }

    public void setNametag(String player, String prefix, String suffix, int sortPriority, boolean playerTag) {
        this.addPlayerToTeam(player, (prefix != null) ? prefix : "§7", (suffix != null) ? suffix : "", sortPriority, playerTag);
    }

    public void sendTeams(Player player) {
        for (FakeTeam fakeTeam : this.TEAMS.values()) {
            new PacketWrapper(fakeTeam.getName(), fakeTeam.getPrefix(), fakeTeam.getSuffix(), 0, fakeTeam.getMembers()).send(player);
        }
    }

    public void reset() {
        for (FakeTeam fakeTeam : this.TEAMS.values()) {
            this.removePlayerFromTeamPackets(fakeTeam, fakeTeam.getMembers());
            this.removeTeamPackets(fakeTeam);
        }
        this.CACHED_FAKE_TEAMS.clear();
        this.TEAMS.clear();
    }

    private void removeTeamPackets(FakeTeam fakeTeam) {
        new PacketWrapper(fakeTeam.getName(), fakeTeam.getPrefix(), fakeTeam.getSuffix(), 1, new ArrayList<Object>()).send();
    }

    private boolean removePlayerFromTeamPackets(FakeTeam fakeTeam, String... players) {
        return this.removePlayerFromTeamPackets(fakeTeam, Arrays.asList(players));
    }

    private boolean removePlayerFromTeamPackets(FakeTeam fakeTeam, List<String> players) {
        new PacketWrapper(fakeTeam.getName(), 4, players).send();
        fakeTeam.getMembers().removeAll(players);
        return fakeTeam.getMembers().isEmpty();
    }

    private void addTeamPackets(FakeTeam fakeTeam) {
        new PacketWrapper(fakeTeam.getName(), fakeTeam.getPrefix(), fakeTeam.getSuffix(), 0, fakeTeam.getMembers()).send();
    }

    private void addPlayerToTeamPackets(FakeTeam fakeTeam, String player) {
        new PacketWrapper(fakeTeam.getName(), 3, Collections.singletonList(player)).send();
    }
}
