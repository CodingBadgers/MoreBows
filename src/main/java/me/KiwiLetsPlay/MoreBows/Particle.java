package me.KiwiLetsPlay.MoreBows;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public enum Particle {

	HUGE_EXPLOSION("hugeexplosion", 0), LARGE_EXPLODE("largeexplode", 1), FIREWORKS_SPARK("fireworksSpark", 2), BUBBLE("bubble", 3), SUSPEND("suspend", 4), DEPTH_SUSPEND("depthSuspend", 5), TOWN_AURA("townaura", 6), CRIT("crit", 7), MAGIC_CRIT("magicCrit", 8), MOB_SPELL("mobSpell", 9), MOB_SPELL_AMBIENT("mobSpellAmbient", 10), SPELL("spell", 11), INSTANT_SPELL("instantSpell", 12), WITCH_MAGIC("witchMagic", 13), NOTE("note", 14), PORTAL("portal", 15), ENCHANTMENT_TABLE("enchantmenttable", 16), EXPLODE("explode", 17), FLAME("flame", 18), LAVA("lava", 19), FOOTSTEP("footstep", 20), SPLASH("splash", 21), LARGE_SMOKE("largesmoke", 22), CLOUD("cloud", 23), RED_DUST("reddust", 24), SNOWBALL_POOF("snowballpoof", 25), DRIP_WATER("dripWater", 26), DRIP_LAVA("dripLava", 27), SNOW_SHOVEL("snowshovel", 28), SLIME("slime", 29), HEART("heart", 30), ANGRY_VILLAGER("angryVillager", 31), HAPPY_VILLAGER("happyVillager", 32);

	private static final Map<String, Particle> NAME_MAP;
	private static final Map<Integer, Particle> ID_MAP;
	private static final double MAX_RANGE = 20.0D;
	private static final float DEFAULT_CRACK_SPEED = 0.1F;
	private static Constructor<?> PARTICLE_PACKET_CONSTRUCTOR;
	private String name;
	private int id;

	static {
		NAME_MAP = new HashMap();
		ID_MAP = new HashMap();
		for (Particle effect : values()) {
			NAME_MAP.put(effect.name, effect);
			ID_MAP.put(Integer.valueOf(effect.id), effect);
		}
		try {
			PARTICLE_PACKET_CONSTRUCTOR = ReflectionUtil.getConstructor(ReflectionUtil.getClass("PacketPlayOutWorldParticles", ReflectionUtil.DynamicPackage.MINECRAFT_SERVER), new Class[0]);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Particle(String name, int id) {
		this.name = name;
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public int getId() {
		return this.id;
	}

	public static Particle fromName(String name) {
		if (name != null) {
			for (Map.Entry<String, Particle> e : NAME_MAP.entrySet()) {
				if (((String) e.getKey()).equalsIgnoreCase(name)) {
					return (Particle) e.getValue();
				}
			}
		}
		return null;
	}

	public static Particle fromId(int id) {
		return (Particle) ID_MAP.get(Integer.valueOf(id));
	}

	private static List<Player> getPlayersInRange(Location loc, double range) {
		List<Player> players = new ArrayList();
		double sqr = range * range;
		for (Player p : loc.getWorld().getPlayers()) {
			if (p.getLocation().distanceSquared(loc) <= sqr) {
				players.add(p);
			}
		}
		return players;
	}

	public void display(Location loc, float offsetX, float offsetY, float offsetZ, float speed, int amount, Player... players) {
		sendPacket(Arrays.asList(players), createPacket(loc, offsetX, offsetY, offsetZ, speed, amount));
	}

	public void display(Location loc, float offsetX, float offsetY, float offsetZ, float speed, int amount) {
		display(loc, 20.0D, offsetX, offsetY, offsetZ, speed, amount);
	}

	public void display(Location loc, double range, float offsetX, float offsetY, float offsetZ, float speed, int amount) {
		if (range > 20.0D) {
			throw new IllegalArgumentException("Range has to be lower/equal the maximum of 20");
		}
		sendPacket(getPlayersInRange(loc, range), createPacket(loc, offsetX, offsetY, offsetZ, speed, amount));
	}

	public static void displayTileCrack(Location loc, int id, byte data, float offsetX, float offsetY, float offsetZ, int amount, Player... players) {
		sendPacket(Arrays.asList(players), createTileCrackPacket(id, data, loc, offsetX, offsetY, offsetZ, amount));
	}

	public static void displayTileCrack(Location loc, int id, byte data, float offsetX, float offsetY, float offsetZ, int amount) {
		displayTileCrack(loc, 20.0D, id, data, offsetX, offsetY, offsetZ, amount);
	}

	public static void displayTileCrack(Location loc, double range, int id, byte data, float offsetX, float offsetY, float offsetZ, int amount) {
		if (range > 20.0D) {
			throw new IllegalArgumentException("Range has to be lower/equal the maximum of 20");
		}
		sendPacket(getPlayersInRange(loc, range), createTileCrackPacket(id, data, loc, offsetX, offsetY, offsetZ, amount));
	}

	public static void displayIconCrack(Location loc, int id, float offsetX, float offsetY, float offsetZ, int amount, Player... players) {
		sendPacket(Arrays.asList(players), createIconCrackPacket(id, loc, offsetX, offsetY, offsetZ, amount));
	}

	public static void displayIconCrack(Location loc, int id, float offsetX, float offsetY, float offsetZ, int amount) {
		displayIconCrack(loc, 20.0D, id, offsetX, offsetY, offsetZ, amount);
	}

	public static void displayIconCrack(Location loc, double range, int id, float offsetX, float offsetY, float offsetZ, int amount) {
		if (range > 20.0D) {
			throw new IllegalArgumentException("Range has to be lower/equal the maximum of 20");
		}
		sendPacket(getPlayersInRange(loc, range), createIconCrackPacket(id, loc, offsetX, offsetY, offsetZ, amount));
	}

	private Object createPacket(Location loc, float offsetX, float offsetY, float offsetZ, float speed, int amount) {
		return createPacket(getName(), loc, offsetX, offsetY, offsetZ, speed, amount);
	}

	private static Object createTileCrackPacket(int id, byte data, Location loc, float offsetX, float offsetY, float offsetZ, int amount) {
		return createPacket("tilecrack_" + id + "_" + data, loc, offsetX, offsetY, offsetZ, 0.1F, amount);
	}

	private static Object createIconCrackPacket(int id, Location loc, float offsetX, float offsetY, float offsetZ, int amount) {
		return createPacket("iconcrack_" + id, loc, offsetX, offsetY, offsetZ, 0.1F, amount);
	}

	private static Object createPacket(String name, Location loc, float offsetX, float offsetY, float offsetZ, float speed, int amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException("Amount of particles has to be greater than 0");
		}
		try {
			Object p = PARTICLE_PACKET_CONSTRUCTOR.newInstance(new Object[0]);
			ReflectionUtil.setValues(p, new ReflectionUtil.FieldEntry[]{new ReflectionUtil.FieldEntry("a", name), new ReflectionUtil.FieldEntry("b", Float.valueOf((float) loc.getX())), new ReflectionUtil.FieldEntry("c", Float.valueOf((float) loc.getY())), new ReflectionUtil.FieldEntry("d", Float.valueOf((float) loc.getZ())), new ReflectionUtil.FieldEntry("e",
				Float.valueOf(offsetX)), new ReflectionUtil.FieldEntry("f", Float.valueOf(offsetY)), new ReflectionUtil.FieldEntry("g", Float.valueOf(offsetZ)), new ReflectionUtil.FieldEntry("h", Float.valueOf(speed)), new ReflectionUtil.FieldEntry("i", Integer.valueOf(amount))});
			return p;
		} catch (Exception e) {
			Bukkit.getLogger().warning("[ParticleEffect] Failed to create a particle packet!");
		}
		return null;
	}

	private static void sendPacket(Player p, Object packet) {
		if (packet != null) {
			try {
				Object entityPlayer = ReflectionUtil.invokeMethod("getHandle", p.getClass(), p, new Object[0]);
				Object playerConnection = ReflectionUtil.getValue("playerConnection", entityPlayer);
				ReflectionUtil.invokeMethod("sendPacket", playerConnection.getClass(), playerConnection, new Object[]{packet});
			} catch (Exception e) {
				Bukkit.getLogger().warning("[ParticleEffect] Failed to send a particle packet to " + p.getName() + "!");
			}
		}
	}

	private static void sendPacket(Iterable<Player> players, Object packet) {
		for (Player p : players) {
			sendPacket(p, packet);
		}
	}
}
