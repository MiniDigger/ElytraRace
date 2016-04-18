package me.MiniDigger.ElytraRace;

import java.util.*;

import org.inventivetalent.particle.ParticleEffect;

import org.bukkit.*;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_9_R1.*;
import net.minecraft.server.v1_9_R1.PacketPlayOutTitle.EnumTitleAction;

public class ElytraRacePortal implements ConfigurationSerializable {

    private String race;
    private ElytraRacePortalType type;
    private int no;
    private List<Location> locs = new ArrayList<>();

    public ElytraRacePortal(String race, ElytraRacePortalType type, List<Location> locs, int no) {
        this(race, type, no);
        this.locs = locs;
    }

    public ElytraRacePortal(String race, ElytraRacePortalType type, int no) {
        this.race = race;
        this.type = type;
        this.no = no;
        this.locs = new ArrayList<>();
    }

    public int getNo() {
        return no;
    }

    public List<Location> getLocations() {
        return locs;
    }

    public String getRaceName() {
        return race;
    }

    public ElytraRacePortalType getType() {
        return type;
    }

    public ElytraRace getRace() {
        return ElytraRaceMain.getInstance().getRace(race);
    }

    public int createPortal(Location loc) {
        checkLoc(loc.getBlock());
        return locs.size();
    }

    private void checkLoc(Block b) {
        if (locs.contains(b.getLocation())) {
            return;
        }

        if (b.getType() != Material.STAINED_GLASS) {
            return;
        }

        b.setType(Material.AIR);
        locs.add(b.getLocation());

        checkLoc(b.getRelative(BlockFace.UP));
        checkLoc(b.getRelative(BlockFace.DOWN));
        checkLoc(b.getRelative(BlockFace.NORTH));
        checkLoc(b.getRelative(BlockFace.NORTH_EAST));
        checkLoc(b.getRelative(BlockFace.EAST));
        checkLoc(b.getRelative(BlockFace.SOUTH_EAST));
        checkLoc(b.getRelative(BlockFace.SOUTH));
        checkLoc(b.getRelative(BlockFace.SOUTH_WEST));
        checkLoc(b.getRelative(BlockFace.WEST));
        checkLoc(b.getRelative(BlockFace.NORTH_WEST));
    }

    public void applyEffect(Player p) {
        Color color = Color.AQUA;
        switch (type) {
            case BOOST:
                p.getPlayer().setVelocity(p.getLocation().getDirection().multiply(ElytraRaceMain.BOOST_SPEED));
                actionbar(p, ChatColor.GOLD + "Boost!");
                break;
            case FINISH:
                // TODO Handle finish
                actionbar(p, ChatColor.GOLD + "Finish!");
                break;
            case LOW:
                p.getPlayer().setVelocity(p.getLocation().getDirection().multiply(ElytraRaceMain.SLOW_SPEED));
                actionbar(p, ChatColor.GOLD + "Slow!");
                break;
            case NORMAL:
                break;
            default:
                break;
        }

        sound(p.getLocation());
        effect(color);
        ElytraRace race = getRace();
        if (type == ElytraRacePortalType.FINISH) {
            //TODO show score
            title(p, ChatColor.GREEN + "FINISH", ChatColor.GREEN + "Your score: ", 20, 2 * 20,
                    20);
        } else {
            title(p, ChatColor.GOLD + "Portal passed", ChatColor.GOLD + "Only " + (race.getPortals().size() - race.getPortalNo(p)) + " Portals to go", 20, 2 * 20,
                    20);
        }
    }

    public void applyNegativEffect(Player player, int oldNo, int newNo) {
        effect(Color.BLACK);
        actionbar(player, ChatColor.RED + "That was the wrong portal!");
        if (oldNo == newNo) {
            title(player, ChatColor.BLACK + "Wrong Portal", ChatColor.BLACK + "You already passed that one!", 20, 2 * 20, 20);
        } else {
            title(player, ChatColor.BLACK + "Wrong Portal", ChatColor.BLACK + "You need to pass " + (oldNo + 1) + " First", 20, 2 * 20, 20);
        }
    }

    public void applyCreationEffect(Player player) {
        sound(player.getLocation());
        effect(Color.GREEN, player);
        actionbar(player, ChatColor.GREEN + "Portal Created!");
        title(player, ChatColor.GREEN + "Portal Created!", ChatColor.GREEN + "Portal was created with " + locs.size() + " Locations", 20, 2 * 20, 20);
    }

    private void effect(Color color, Player... ppp) {
        List<Player> players = new ArrayList<>();
        for (UUID id : getRace().getPlayers()) {
            Player pp = Bukkit.getPlayer(id);
            if (pp != null) {
                players.add(pp);
            }
        }

        Collections.addAll(players, ppp);

        for (Location loc : locs) {
            ParticleEffect.REDSTONE.sendColor(players, (double) loc.getBlockX(), (double) loc.getBlockY(), (double) loc.getBlockZ(), color);
        }
    }

    private void actionbar(Player p, String msg) {
        ChatComponentText comp = new ChatComponentText(msg);
        PacketPlayOutChat packet = new PacketPlayOutChat(comp, (byte) 2);
        EntityPlayer nmsp = ((CraftPlayer) p).getHandle();
        nmsp.playerConnection.sendPacket(packet);
    }

    private void title(Player p, String msg, String msg2, int fadeIn, int stay, int fadeOut) {
        ChatComponentText title = new ChatComponentText(msg);
        ChatComponentText subtitle = new ChatComponentText(msg2);
        PacketPlayOutTitle p1 = new PacketPlayOutTitle(EnumTitleAction.TITLE, title);
        PacketPlayOutTitle p2 = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, subtitle);
        PacketPlayOutTitle p3 = new PacketPlayOutTitle(fadeIn, stay, fadeOut);
        EntityPlayer nmsp = ((CraftPlayer) p).getHandle();
        if (!"".equals(msg)) {
            nmsp.playerConnection.sendPacket(p1);
        }
        if (!"".equals(msg2)) {
            nmsp.playerConnection.sendPacket(p2);
        }
        nmsp.playerConnection.sendPacket(p3);
    }

    private void sound(Location loc) {
        loc.getWorld().playSound(loc, Sound.ENTITY_FIREWORK_BLAST, 1, 1);
    }

    @SuppressWarnings("unchecked")
    public static ElytraRacePortal deserialize(Map<String, Object> map) {
        return new ElytraRacePortal((String) map.get("race"), ElytraRacePortalType.valueOf((String) map.get("type")), (List<Location>) map.get("locs"), (int) map.get("no"));
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("race", race);
        map.put("type", type.name());
        map.put("locs", locs);
        map.put("no", no);
        return map;
    }
}
