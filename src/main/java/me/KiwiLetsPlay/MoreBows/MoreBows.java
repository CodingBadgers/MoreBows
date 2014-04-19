package me.KiwiLetsPlay.MoreBows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class MoreBows
		extends JavaPlugin {

	protected ArrayList<String> enabledPlayers;
	List<String> list = Arrays.asList(new String[]{"Explosion", "Lightning", "Lightning_Soul", "Disappearance", "Poison", "Freeze", "Ghast", "Wither", "Squid", "Ender", "Storm", "Storm_Soul", "Leather_quiver", "Iron_quiver", "Gold_quiver", "Diamond_quiver", "Chain_quiver"});

	@Override
	public void onDisable() {
		System.out.println("[MoreBows] Plugin deaktiviert");
	}

	@Override
	public void onEnable() {
		loadConfig();

		this.enabledPlayers = new ArrayList();

		PluginManager manager = getServer().getPluginManager();
		manager.registerEvents(new MoreBowsMetaSetter(this), this);
		manager.registerEvents(new ExplodingArrowsListener(this), this);
		manager.registerEvents(new MoreBowsQuiver(this), this);
		manager.registerEvents(new MoreBowsCraftListener(this), this);

		Server server = getServer();

		PluginDescriptionFile descFile = getDescription();
		System.out.println("[MoreBows] Plugin aktiviert");
		System.out.println("[MoreBows] Version " + descFile.getVersion());

		ItemStack fBow = new ItemStack(261);
		Enchantment myEnchantment1 = new EnchantmentWrapper(50);
		fBow.addEnchantment(myEnchantment1, 1);

		ShapedRecipe fireBow = new ShapedRecipe(new ItemStack(fBow));
		fireBow.shape(new String[]{" ST", "SMT", " ST"});
		fireBow.setIngredient('S', Material.STICK);
		fireBow.setIngredient('T', Material.STRING);
		fireBow.setIngredient('M', Material.FLINT_AND_STEEL);
		Boolean craft = getConfig().getBoolean("Flame-bow.crafting");
		if (craft) {
			server.addRecipe(fireBow);
		}
		ItemStack pBow = new ItemStack(261);
		Enchantment myEnchantment2 = new EnchantmentWrapper(49);
		pBow.addEnchantment(myEnchantment2, 1);

		ShapedRecipe punchBow = new ShapedRecipe(new ItemStack(pBow));
		punchBow.shape(new String[]{" ST", "SGT", " ST"});
		punchBow.setIngredient('S', Material.STICK);
		punchBow.setIngredient('T', Material.STRING);
		punchBow.setIngredient('G', Material.GOLD_INGOT);
		craft = getConfig().getBoolean("Punch-bow.crafting");
		if (craft) {
			server.addRecipe(punchBow);
		}
		ItemStack p2Bow = new ItemStack(261);
		Enchantment myEnchantment3 = new EnchantmentWrapper(49);
		p2Bow.addEnchantment(myEnchantment3, 2);

		ShapedRecipe punch2Bow = new ShapedRecipe(new ItemStack(p2Bow));
		punch2Bow.shape(new String[]{"GST", "S T", "GST"});
		punch2Bow.setIngredient('S', Material.STICK);
		punch2Bow.setIngredient('T', Material.STRING);
		punch2Bow.setIngredient('G', Material.GOLD_INGOT);
		craft = getConfig().getBoolean("Punch2-bow.crafting");
		if (craft) {
			server.addRecipe(punch2Bow);
		}
		ItemStack powBow = new ItemStack(261);
		Enchantment myEnchantment4 = new EnchantmentWrapper(48);
		powBow.addEnchantment(myEnchantment4, 1);

		ShapedRecipe powerBow = new ShapedRecipe(new ItemStack(powBow));
		powerBow.shape(new String[]{" ST", "SDT", " ST"});
		powerBow.setIngredient('S', Material.STICK);
		powerBow.setIngredient('T', Material.STRING);
		powerBow.setIngredient('D', Material.DIAMOND);
		craft = getConfig().getBoolean("Power-bow.crafting");
		if (craft) {
			server.addRecipe(powerBow);
		}
		ItemStack pow2Bow = new ItemStack(261);
		Enchantment myEnchantment5 = new EnchantmentWrapper(48);
		pow2Bow.addEnchantment(myEnchantment5, 2);

		ShapedRecipe power2Bow = new ShapedRecipe(new ItemStack(pow2Bow));
		power2Bow.shape(new String[]{"DST", "S T", "DST"});
		power2Bow.setIngredient('S', Material.STICK);
		power2Bow.setIngredient('T', Material.STRING);
		power2Bow.setIngredient('D', Material.DIAMOND);
		craft = getConfig().getBoolean("Power2-bow.crafting");
		if (craft) {
			server.addRecipe(power2Bow);
		}
		ItemStack pow3Bow = new ItemStack(261);
		Enchantment myEnchantment6 = new EnchantmentWrapper(48);
		pow3Bow.addEnchantment(myEnchantment6, 3);

		ShapedRecipe power3Bow = new ShapedRecipe(new ItemStack(pow3Bow));
		power3Bow.shape(new String[]{"DST", "SDT", "DST"});
		power3Bow.setIngredient('S', Material.STICK);
		power3Bow.setIngredient('T', Material.STRING);
		power3Bow.setIngredient('D', Material.DIAMOND);
		craft = getConfig().getBoolean("Power3-bow.crafting");
		if (craft) {
			server.addRecipe(power3Bow);
		}
		ItemStack pow4Bow = new ItemStack(261);
		Enchantment myEnchantment7 = new EnchantmentWrapper(48);
		pow4Bow.addEnchantment(myEnchantment7, 4);

		ShapedRecipe power4Bow = new ShapedRecipe(new ItemStack(pow4Bow));
		power4Bow.shape(new String[]{"DST", "SGT", "DST"});
		power4Bow.setIngredient('S', Material.STICK);
		power4Bow.setIngredient('T', Material.STRING);
		power4Bow.setIngredient('D', Material.DIAMOND);
		power4Bow.setIngredient('G', Material.GOLDEN_APPLE);
		craft = getConfig().getBoolean("Power4-bow.crafting");
		if (craft) {
			server.addRecipe(power4Bow);
		}
		ItemStack pow5Bow = new ItemStack(261);
		Enchantment myEnchantment8 = new EnchantmentWrapper(48);
		pow5Bow.addEnchantment(myEnchantment8, 5);

		ShapedRecipe power5Bow = new ShapedRecipe(new ItemStack(pow5Bow));
		power5Bow.shape(new String[]{"GST", "SDT", "GST"});
		power5Bow.setIngredient('S', Material.STICK);
		power5Bow.setIngredient('T', Material.STRING);
		power5Bow.setIngredient('D', Material.DIAMOND);
		power5Bow.setIngredient('G', Material.GOLDEN_APPLE);
		craft = getConfig().getBoolean("Power5-bow.crafting");
		if (craft) {
			server.addRecipe(power5Bow);
		}
		ItemStack unbBow = new ItemStack(261);
		Enchantment myEnchantment9 = new EnchantmentWrapper(34);
		unbBow.addEnchantment(myEnchantment9, 1);

		ShapedRecipe unbr1Bow = new ShapedRecipe(new ItemStack(unbBow));
		unbr1Bow.shape(new String[]{" ST", "SBT", " ST"});
		unbr1Bow.setIngredient('S', Material.STICK);
		unbr1Bow.setIngredient('T', Material.STRING);
		unbr1Bow.setIngredient('B', Material.BOW);
		craft = getConfig().getBoolean("Unbreaking-bow.crafting");
		if (craft) {
			server.addRecipe(unbr1Bow);
		}
		ItemStack unb2Bow = new ItemStack(261);
		Enchantment myEnchantment10 = new EnchantmentWrapper(34);
		unb2Bow.addEnchantment(myEnchantment10, 2);

		ShapedRecipe unbr2Bow = new ShapedRecipe(new ItemStack(unb2Bow));
		unbr2Bow.shape(new String[]{"BST", "S T", "BST"});
		unbr2Bow.setIngredient('S', Material.STICK);
		unbr2Bow.setIngredient('T', Material.STRING);
		unbr2Bow.setIngredient('B', Material.BOW);
		craft = getConfig().getBoolean("Unbreaking2-bow.crafting");
		if (craft) {
			server.addRecipe(unbr2Bow);
		}
		ItemStack unb3Bow = new ItemStack(261);
		Enchantment myEnchantment11 = new EnchantmentWrapper(34);
		unb3Bow.addEnchantment(myEnchantment11, 3);

		ShapedRecipe unbr3Bow = new ShapedRecipe(new ItemStack(unb3Bow));
		unbr3Bow.shape(new String[]{"BST", "SBT", "BST"});
		unbr3Bow.setIngredient('S', Material.STICK);
		unbr3Bow.setIngredient('T', Material.STRING);
		unbr3Bow.setIngredient('B', Material.BOW);
		craft = getConfig().getBoolean("Unbreaking3-bow.crafting");
		if (craft) {
			server.addRecipe(unbr3Bow);
		}
		ShapedRecipe arrows = new ShapedRecipe(new ItemStack(Material.ARROW, 15));
		arrows.shape(new String[]{"FFF", "SSS", "CCC"});
		arrows.setIngredient('F', Material.FLINT);
		arrows.setIngredient('S', Material.STICK);
		arrows.setIngredient('C', Material.FEATHER);

		craft = getConfig().getBoolean("Arrows.crafting");
		if (craft) {
			server.addRecipe(arrows);
		}
		ItemStack lHelmet = new ItemStack(298);
		lHelmet.addEnchantment(new EnchantmentWrapper(4), 1);

		ShapedRecipe leHelmet = new ShapedRecipe(new ItemStack(lHelmet));
		leHelmet.shape(new String[]{"LLL", "LAL", "   "});
		leHelmet.setIngredient('L', Material.LEATHER);
		leHelmet.setIngredient('A', Material.ARROW);
		craft = getConfig().getBoolean("Helmet.crafting");
		if (craft) {
			server.addRecipe(leHelmet);
		}
		ItemStack lHelmet2 = new ItemStack(298);
		Enchantment myEnchantment13 = new EnchantmentWrapper(4);
		lHelmet2.addEnchantment(myEnchantment13, 2);

		ShapedRecipe leHelmet2 = new ShapedRecipe(new ItemStack(lHelmet2));
		leHelmet2.shape(new String[]{"LLL", "LAL", " A "});
		leHelmet2.setIngredient('L', Material.LEATHER);
		leHelmet2.setIngredient('A', Material.ARROW);
		craft = getConfig().getBoolean("Helmet2.crafting");
		if (craft) {
			server.addRecipe(leHelmet2);
		}
		ItemStack lHelmet3 = new ItemStack(298);
		Enchantment myEnchantment14 = new EnchantmentWrapper(4);
		lHelmet3.addEnchantment(myEnchantment14, 3);

		ShapedRecipe leHelmet3 = new ShapedRecipe(new ItemStack(lHelmet3));
		leHelmet3.shape(new String[]{"LLL", "LAL", "A A"});
		leHelmet3.setIngredient('L', Material.LEATHER);
		leHelmet3.setIngredient('A', Material.ARROW);
		craft = getConfig().getBoolean("Helmet3.crafting");
		if (craft) {
			server.addRecipe(leHelmet3);
		}
		ItemStack lHelmet4 = new ItemStack(298);
		Enchantment myEnchantment15 = new EnchantmentWrapper(4);
		lHelmet4.addEnchantment(myEnchantment15, 4);

		ShapedRecipe leHelmet4 = new ShapedRecipe(new ItemStack(lHelmet4));
		leHelmet4.shape(new String[]{"LLL", "LAL", "AAA"});
		leHelmet4.setIngredient('L', Material.LEATHER);
		leHelmet4.setIngredient('A', Material.ARROW);
		craft = getConfig().getBoolean("Helmet4.crafting");
		if (craft) {
			server.addRecipe(leHelmet4);
		}
		List<String> ls = new ArrayList();
		ls.add(ChatColor.GOLD + "Lightning Soul");
		ItemStack lsoul = setName(new ItemStack(Material.MAGMA_CREAM), "Artefact", ls);
		Enchantment myEnchantment18 = new EnchantmentWrapper(20);
		lsoul.addUnsafeEnchantment(myEnchantment18, 1);

		ShapedRecipe magmaSoul = new ShapedRecipe(new ItemStack(lsoul));
		magmaSoul.shape(new String[]{" BR", "BSB", "RB "});
		magmaSoul.setIngredient('S', Material.SLIME_BALL);
		magmaSoul.setIngredient('B', Material.BLAZE_ROD);
		magmaSoul.setIngredient('R', Material.REDSTONE);
		craft = getConfig().getBoolean("LightningSoul.crafting");
		if (craft) {
			server.addRecipe(magmaSoul);
		}
		List<String> lbs = new ArrayList();
		lbs.add(ChatColor.GRAY + "Lightning I");
		lbs.add(ChatColor.RED + "uncharged");
		ItemStack lBow = setName(new ItemStack(Material.BOW), "Lightning Bow", lbs);
		Enchantment myEnchantment16 = new EnchantmentWrapper(48);
		lBow.addEnchantment(myEnchantment16, 1);

		ShapedRecipe lightBow = new ShapedRecipe(new ItemStack(lBow));
		lightBow.shape(new String[]{"BST", "SGT", "BST"});
		lightBow.setIngredient('S', Material.STICK);
		lightBow.setIngredient('T', Material.STRING);
		lightBow.setIngredient('B', Material.BLAZE_ROD);
		lightBow.setIngredient('G', Material.GLOWSTONE_DUST);
		craft = getConfig().getBoolean("Lightning-bow.crafting");
		if (craft) {
			server.addRecipe(lightBow);
		}
		List<String> es = new ArrayList();
		es.add(ChatColor.GRAY + "Explosion I");
		ItemStack eBow = setName(new ItemStack(Material.BOW), "Explosion Bow", es);

		Enchantment myEnchantment17 = new EnchantmentWrapper(48);
		eBow.addEnchantment(myEnchantment17, 1);

		ShapedRecipe explBow = new ShapedRecipe(new ItemStack(eBow));
		explBow.shape(new String[]{"BST", "SBT", "BST"});
		explBow.setIngredient('S', Material.STICK);
		explBow.setIngredient('T', Material.STRING);
		explBow.setIngredient('B', Material.TNT);
		craft = getConfig().getBoolean("Explosion-bow.crafting");
		if (craft) {
			server.addRecipe(explBow);
		}
		List<String> ps = new ArrayList();
		ps.add(ChatColor.GRAY + "Poison I");
		ItemStack poiBow = setName(new ItemStack(Material.BOW), "Poison Bow", ps);

		ShapedRecipe poisonBow = new ShapedRecipe(new ItemStack(poiBow));
		poisonBow.shape(new String[]{"FST", "SET", "FST"});

		poisonBow.setIngredient('S', Material.STICK);
		poisonBow.setIngredient('T', Material.STRING);
		poisonBow.setIngredient('E', Material.SPIDER_EYE);
		poisonBow.setIngredient('F', Material.FERMENTED_SPIDER_EYE);
		craft = getConfig().getBoolean("Poison-bow.crafting");
		if (craft) {
			server.addRecipe(poisonBow);
		}
		List<String> db = new ArrayList();
		db.add("Disappearance I");
		ItemStack disBow = setName(new ItemStack(Material.BOW), "Disappearance Bow", db);

		ShapedRecipe disapBow = new ShapedRecipe(new ItemStack(disBow));
		disapBow.shape(new String[]{"EST", "SET", "EST"});
		disapBow.setIngredient('S', Material.STICK);
		disapBow.setIngredient('T', Material.STRING);
		disapBow.setIngredient('E', Material.EYE_OF_ENDER);
		craft = getConfig().getBoolean("Disappearance-bow.crafting");
		if (craft) {
			server.addRecipe(disapBow);
		}
		List<String> fb = new ArrayList();
		fb.add(ChatColor.GRAY + "Freeze I");
		ItemStack freBow = setName(new ItemStack(Material.BOW), "Freeze Bow", fb);

		ShapedRecipe freezeBow = new ShapedRecipe(new ItemStack(freBow));
		freezeBow.shape(new String[]{"IST", "SIT", "IST"});
		freezeBow.setIngredient('S', Material.STICK);
		freezeBow.setIngredient('T', Material.STRING);
		freezeBow.setIngredient('I', Material.ICE);
		craft = getConfig().getBoolean("Freeze-bow.crafting");
		if (craft) {
			server.addRecipe(freezeBow);
		}
		List<String> gb = new ArrayList();
		gb.add(ChatColor.GRAY + "Fireball I");
		ItemStack gstBow = setName(new ItemStack(Material.BOW), "Ghast Bow", gb);

		ShapedRecipe ghastBow = new ShapedRecipe(new ItemStack(gstBow));
		ghastBow.shape(new String[]{"CST", "SGT", "CST"});
		ghastBow.setIngredient('S', Material.STICK);
		ghastBow.setIngredient('T', Material.STRING);
		ghastBow.setIngredient('C', Material.FIREBALL);
		ghastBow.setIngredient('G', Material.GHAST_TEAR);
		craft = getConfig().getBoolean("Ghast-bow.crafting");
		if (craft) {
			server.addRecipe(ghastBow);
		}
		List<String> wb = new ArrayList();
		wb.add(ChatColor.GRAY + "Wither I");
		ItemStack witBow = setName(new ItemStack(Material.BOW), "Wither Bow", wb);

		ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 1);

		ShapedRecipe witherBow = new ShapedRecipe(new ItemStack(witBow));
		witherBow.shape(new String[]{"WST", "SOT", "WST"});
		witherBow.setIngredient('S', Material.STICK);
		witherBow.setIngredient('T', Material.STRING);
		witherBow.setIngredient('W', skull.getData());
		witherBow.setIngredient('O', Material.SOUL_SAND);
		craft = getConfig().getBoolean("Wither-bow.crafting");
		if (craft) {
			server.addRecipe(witherBow);
		}
		List<String> sq = new ArrayList();
		sq.add(ChatColor.GRAY + "Blindness I");
		ItemStack sqBow = setName(new ItemStack(Material.BOW), "Squid Bow", sq);
		ShapedRecipe squidBow = new ShapedRecipe(new ItemStack(sqBow));
		squidBow.shape(new String[]{"IST", "SIT", "IST"});
		squidBow.setIngredient('S', Material.STICK);
		squidBow.setIngredient('T', Material.STRING);
		squidBow.setIngredient('I', Material.INK_SACK);
		craft = getConfig().getBoolean("Squid-bow.crafting");
		if (craft) {
			server.addRecipe(squidBow);
		}
		List<String> eb = new ArrayList();
		eb.add(ChatColor.GRAY + "Teleport I");
		ItemStack ebBow = setName(new ItemStack(Material.BOW), "Ender Bow", eb);
		ShapedRecipe enderBow = new ShapedRecipe(new ItemStack(ebBow));
		enderBow.shape(new String[]{"EST", "SET", "EST"});
		enderBow.setIngredient('S', Material.STICK);
		enderBow.setIngredient('T', Material.STRING);
		enderBow.setIngredient('E', Material.ENDER_PEARL);
		craft = getConfig().getBoolean("Ender-bow.crafting");
		if (craft) {
			server.addRecipe(enderBow);
		}
		List<String> stb = new ArrayList();
		stb.add(ChatColor.GRAY + "Power of the storms I");
		stb.add(ChatColor.RED + "uncharged");
		ItemStack stBow = setName(new ItemStack(Material.BOW), "Storm Bow", stb);
		ShapedRecipe stormBow = new ShapedRecipe(new ItemStack(stBow));
		stormBow.shape(new String[]{"AST", "SQT", "AST"});
		stormBow.setIngredient('S', Material.STICK);
		stormBow.setIngredient('T', Material.STRING);
		stormBow.setIngredient('A', Material.SAND);
		stormBow.setIngredient('Q', Material.QUARTZ);
		craft = getConfig().getBoolean("Storm-bow.crafting");
		if (craft) {
			server.addRecipe(stormBow);
		}
		List<String> quiver = new ArrayList();
		quiver.add(ChatColor.GRAY + "0/320");
		ItemStack quiverC = setName(new ItemStack(Material.LEATHER_CHESTPLATE), "Leather Tunic with Quiver", quiver);
		ShapedRecipe quiverChest1 = new ShapedRecipe(new ItemStack(quiverC));
		quiverChest1.shape(new String[]{" SA", "SCL", "RL "});
		quiverChest1.setIngredient('L', Material.LEATHER);
		quiverChest1.setIngredient('S', Material.STICK);
		quiverChest1.setIngredient('R', Material.STRING);
		quiverChest1.setIngredient('A', Material.ARROW);
		quiverChest1.setIngredient('C', Material.LEATHER_CHESTPLATE);
		craft = getConfig().getBoolean("quiver.leather.crafting");
		if (craft) {
			server.addRecipe(quiverChest1);
		}
		List<String> quiver2 = new ArrayList();
		quiver2.add(ChatColor.GRAY + "0/320");
		ItemStack quiverC2 = setName(new ItemStack(Material.IRON_CHESTPLATE), "Iron Chestplate with Quiver", quiver2);
		ShapedRecipe quiverChest2 = new ShapedRecipe(new ItemStack(quiverC2));
		quiverChest2.shape(new String[]{" SA", "SCL", "RL "});
		quiverChest2.setIngredient('L', Material.LEATHER);
		quiverChest2.setIngredient('S', Material.STICK);
		quiverChest2.setIngredient('R', Material.STRING);
		quiverChest2.setIngredient('A', Material.ARROW);
		quiverChest2.setIngredient('C', Material.IRON_CHESTPLATE);
		craft = getConfig().getBoolean("quiver.iron.crafting");
		if (craft) {
			server.addRecipe(quiverChest2);
		}
		List<String> quiver3 = new ArrayList();
		quiver3.add(ChatColor.GRAY + "0/320");
		ItemStack quiverC3 = setName(new ItemStack(Material.GOLD_CHESTPLATE), "Golden Chestplate with Quiver", quiver3);
		ShapedRecipe quiverChest3 = new ShapedRecipe(new ItemStack(quiverC3));
		quiverChest3.shape(new String[]{" SA", "SCL", "RL "});
		quiverChest3.setIngredient('L', Material.LEATHER);
		quiverChest3.setIngredient('S', Material.STICK);
		quiverChest3.setIngredient('R', Material.STRING);
		quiverChest3.setIngredient('A', Material.ARROW);
		quiverChest3.setIngredient('C', Material.GOLD_CHESTPLATE);
		craft = getConfig().getBoolean("quiver.gold.crafting");
		if (craft) {
			server.addRecipe(quiverChest3);
		}
		List<String> quiver4 = new ArrayList();
		quiver4.add(ChatColor.GRAY + "0/320");
		ItemStack quiverC4 = setName(new ItemStack(Material.DIAMOND_CHESTPLATE), "Diamond Chestplate with Quiver", quiver4);
		ShapedRecipe quiverChest4 = new ShapedRecipe(new ItemStack(quiverC4));
		quiverChest4.shape(new String[]{" SA", "SCL", "RL "});
		quiverChest4.setIngredient('L', Material.LEATHER);
		quiverChest4.setIngredient('S', Material.STICK);
		quiverChest4.setIngredient('R', Material.STRING);
		quiverChest4.setIngredient('A', Material.ARROW);
		quiverChest4.setIngredient('C', Material.DIAMOND_CHESTPLATE);
		craft = getConfig().getBoolean("quiver.diamond.crafting");
		if (craft) {
			server.addRecipe(quiverChest4);
		}
		List<String> quiver5 = new ArrayList();
		quiver5.add(ChatColor.GRAY + "0/320");
		ItemStack quiverC5 = setName(new ItemStack(Material.CHAINMAIL_CHESTPLATE), "Chain Chestplate with Quiver", quiver5);
		ShapedRecipe quiverChest5 = new ShapedRecipe(new ItemStack(quiverC5));
		quiverChest5.shape(new String[]{" SA", "SCL", "RL "});
		quiverChest5.setIngredient('L', Material.LEATHER);
		quiverChest5.setIngredient('S', Material.STICK);
		quiverChest5.setIngredient('R', Material.STRING);
		quiverChest5.setIngredient('A', Material.ARROW);
		quiverChest5.setIngredient('C', Material.CHAINMAIL_CHESTPLATE);
		craft = getConfig().getBoolean("quiver.chain.crafting");
		if (craft) {
			server.addRecipe(quiverChest5);
		}
	}

	private ItemStack setName(ItemStack is, String name, List<String> lore) {
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(name);
		im.setLore(lore);
		is.setItemMeta(im);
		return is;
	}

	private void loadConfig() {
		FileConfiguration cfg = getConfig();
		cfg.options().copyDefaults(true);
		saveConfig();
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		if (((sender instanceof Player))
				&& (command.getName().equalsIgnoreCase("mbgive"))) {
			Player[] players = sender.getServer().getOnlinePlayers();
			List<String> names = new ArrayList(players.length);
			for (Player player : players) {
				names.add(player.getName());
			}
			switch (args.length) {
				case 1:
					return names;
				case 2:
					return this.list;
			}
			return null;
		}
		return null;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		boolean erfolg = false;
		boolean perm = getConfig().getBoolean("Permissions.activated");
		if (cmd.getName().equalsIgnoreCase("morebows")) {
			if (args.length == 0) {
				PluginDescriptionFile descFile = getDescription();
				sender.sendMessage(ChatColor.GREEN + "[MoreBows] Version " + descFile.getVersion() + " is activated");
			}
			erfolg = true;
		}
		if (cmd.getName().equalsIgnoreCase("mblist")) {
			if (((perm) && (sender.hasPermission("morebows.command.list"))) || (!perm)) {
				if (args.length == 0) {
					sender.sendMessage(this.list.toString());
				}
				erfolg = true;
			} else {
				sender.sendMessage(ChatColor.RED + "You dont have the permissions to use this command [morebows.command]");
			}
		}
		if (cmd.getName().equalsIgnoreCase("mbreload")) {
			if (((perm) && (sender.hasPermission("morebows.command"))) || (!perm)) {
				if (args.length == 0) {
					sender.sendMessage(ChatColor.GREEN + "[Morebows] Config.yml reloaded");
					reloadConfig();
				}
				erfolg = true;
			} else {
				sender.sendMessage(ChatColor.RED + "You dont have the permissions to use this command [morebows.command]");
			}
		}
		if (cmd.getName().equalsIgnoreCase("mbconfig")) {
			if (((perm) && (sender.hasPermission("morebows.command"))) || (!perm)) {
				if (args.length == 2) {
					if (getConfig().get(args[0]) != null) {
						String path = args[0];
						String value = args[1];
						Object conp = getConfig().get(path);
						boolean suc = false;
						if ((conp instanceof Boolean)) {
							if (args[1].equalsIgnoreCase("true")) {
								getConfig().set(path, true);
								suc = true;
							} else if (args[1].equalsIgnoreCase("false")) {
								getConfig().set(path, false);
								suc = true;
							} else {
								sender.sendMessage(ChatColor.RED + "Invalid Value Type. Value of path '" + ChatColor.GRAY + path + ChatColor.RED + "' is type boolean.");
							}
						}
						if ((conp instanceof String)) {
							getConfig().set(path, value);
							suc = true;
						} else if ((conp instanceof Double)) {
							try {
								getConfig().set(path, Double.parseDouble(value));
								suc = true;
							} catch (NumberFormatException e) {
								sender.sendMessage(ChatColor.RED + "Invalid Value Type. Value of path '" + ChatColor.GRAY + path + ChatColor.RED + "' is type double.");
							}
						}
						if (suc) {
							sender.sendMessage(ChatColor.GREEN + "Successfully changed '" + path + "' to '" + value + "' !");
						}
						saveConfig();
					} else {
						sender.sendMessage(ChatColor.RED + "The path '" + ChatColor.GRAY + args[0] + ChatColor.RED + "' does not exist.");
					}
				} else {
					sender.sendMessage(ChatColor.RED + "Usage: /mbconfig <path> <value>");
				}
				erfolg = true;
			} else {
				sender.sendMessage(ChatColor.RED + "You dont have the permissions to use this command [morebows.command]");
			}
		}
		if (cmd.getName().equalsIgnoreCase("mbgive")) {
			if (((perm) && (sender.hasPermission("morebows.command"))) || (!perm)) {
				if (args.length == 2) {
					if (sender.getServer().getPlayer(args[0]) != null) {
						Player player = sender.getServer().getPlayer(args[0]);
						boolean corBow = false;

						List<String> ls = new ArrayList();
						ls.add(ChatColor.GRAY + "Lightning I");
						ItemStack lBow = setName(new ItemStack(Material.BOW), "Lightning Bow", ls);
						Enchantment myEnchantment16 = new EnchantmentWrapper(48);
						lBow.addEnchantment(myEnchantment16, 1);
						if (args[1].equals("Lightning")) {
							player.getInventory().addItem(new ItemStack[]{lBow});
							corBow = true;
						}
						List<String> ls1 = new ArrayList();
						ls1.add(ChatColor.GOLD + "Lightning Soul");
						ItemStack lsoul = setName(new ItemStack(Material.MAGMA_CREAM), "Artefact", ls1);
						Enchantment myEnchantment18 = new EnchantmentWrapper(20);
						lsoul.addUnsafeEnchantment(myEnchantment18, 1);
						if (args[1].equals("Lightning_Soul")) {
							player.getInventory().addItem(new ItemStack[]{lsoul});
							corBow = true;
						}
						List<String> es = new ArrayList();
						es.add(ChatColor.GRAY + "Explosion I");
						ItemStack eBow = setName(new ItemStack(Material.BOW), "Explosion Bow", es);
						Enchantment myEnchantment17 = new EnchantmentWrapper(48);
						eBow.addEnchantment(myEnchantment17, 1);
						if (args[1].equals("Explosion")) {
							player.getInventory().addItem(new ItemStack[]{eBow});
							corBow = true;
						}
						List<String> ps = new ArrayList();
						ps.add(ChatColor.GRAY + "Poison I");
						ItemStack poiBow = setName(new ItemStack(Material.BOW), "Poison Bow", ps);
						if (args[1].equals("Poison")) {
							player.getInventory().addItem(new ItemStack[]{poiBow});
							corBow = true;
						}
						List<String> db = new ArrayList();
						db.add("Disappearance I");
						ItemStack disBow = setName(new ItemStack(Material.BOW), "Disappearance Bow", db);
						if (args[1].equals("Disappearance")) {
							player.getInventory().addItem(new ItemStack[]{disBow});
							corBow = true;
						}
						List<String> fb = new ArrayList();
						fb.add(ChatColor.GRAY + "Freeze I");
						ItemStack freBow = setName(new ItemStack(Material.BOW), "Freeze Bow", fb);
						if (args[1].equals("Freeze")) {
							player.getInventory().addItem(new ItemStack[]{freBow});
							corBow = true;
						}
						List<String> gb = new ArrayList();
						gb.add(ChatColor.GRAY + "Fireball I");
						ItemStack gstBow = setName(new ItemStack(Material.BOW), "Ghast Bow", gb);
						if (args[1].equals("Ghast")) {
							player.getInventory().addItem(new ItemStack[]{gstBow});
							corBow = true;
						}
						List<String> wb = new ArrayList();
						wb.add(ChatColor.GRAY + "Wither I");
						ItemStack witBow = setName(new ItemStack(Material.BOW), "Wither Bow", wb);
						if (args[1].equals("Wither")) {
							player.getInventory().addItem(new ItemStack[]{witBow});
							corBow = true;
						}
						List<String> sb = new ArrayList();
						sb.add(ChatColor.GRAY + "Blindness I");
						ItemStack squidBow = setName(new ItemStack(Material.BOW), "Squid Bow", sb);
						if (args[1].equals("Squid")) {
							player.getInventory().addItem(new ItemStack[]{squidBow});
							corBow = true;
						}
						List<String> eb = new ArrayList();
						eb.add(ChatColor.GRAY + "Teleport I");
						ItemStack enderBow = setName(new ItemStack(Material.BOW), "Ender Bow", eb);
						if (args[1].equals("Ender")) {
							player.getInventory().addItem(new ItemStack[]{enderBow});
							corBow = true;
						}
						List<String> stb = new ArrayList();
						stb.add(ChatColor.GRAY + "Power of the storms I");
						ItemStack stormBow = setName(new ItemStack(Material.BOW), "Storm Bow", stb);
						if (args[1].equals("Storm")) {
							player.getInventory().addItem(new ItemStack[]{stormBow});
							corBow = true;
						}
						List<String> ls11 = new ArrayList();
						ls11.add(ChatColor.GOLD + "Storm Soul");
						ItemStack lsoul1 = setName(new ItemStack(Material.BLAZE_POWDER), "Artefact", ls11);
						Enchantment myEnchantment = new EnchantmentWrapper(19);
						lsoul1.addUnsafeEnchantment(myEnchantment, 2);
						if (args[1].equals("Storm_Soul")) {
							player.getInventory().addItem(new ItemStack[]{lsoul1});
							corBow = true;
						}
						List<String> quiver1 = new ArrayList();
						quiver1.add(ChatColor.GRAY + "0/320");
						ItemStack quiverC1 = setName(new ItemStack(Material.LEATHER_CHESTPLATE), "Leather Tunic with Quiver", quiver1);
						if (args[1].equals("Leather_quiver")) {
							player.getInventory().addItem(new ItemStack[]{quiverC1});
							corBow = true;
						}
						List<String> quiver2 = new ArrayList();
						quiver2.add(ChatColor.GRAY + "0/320");
						ItemStack quiverC2 = setName(new ItemStack(Material.IRON_CHESTPLATE), "Iron Chestplate with Quiver", quiver2);
						if (args[1].equals("Iron_quiver")) {
							player.getInventory().addItem(new ItemStack[]{quiverC2});
							corBow = true;
						}
						List<String> quiver3 = new ArrayList();
						quiver3.add(ChatColor.GRAY + "0/320");
						ItemStack quiverC3 = setName(new ItemStack(Material.GOLD_CHESTPLATE), "Golden Chestplate with Quiver", quiver3);
						if (args[1].equals("Gold_quiver")) {
							player.getInventory().addItem(new ItemStack[]{quiverC3});
							corBow = true;
						}
						List<String> quiver4 = new ArrayList();
						quiver4.add(ChatColor.GRAY + "0/320");
						ItemStack quiverC4 = setName(new ItemStack(Material.DIAMOND_CHESTPLATE), "Diamond Chestplate with Quiver", quiver4);
						if (args[1].equals("Diamond_quiver")) {
							player.getInventory().addItem(new ItemStack[]{quiverC4});
							corBow = true;
						}
						List<String> quiver5 = new ArrayList();
						quiver5.add(ChatColor.GRAY + "0/320");
						ItemStack quiverC5 = setName(new ItemStack(Material.CHAINMAIL_CHESTPLATE), "Chain Chestplate with Quiver", quiver5);
						if (args[1].equals("Chain_quiver")) {
							player.getInventory().addItem(new ItemStack[]{quiverC5});
							corBow = true;
						}
						if (!corBow) {
							sender.sendMessage(ChatColor.RED + "The bow " + args[1] + " does not exist, type /mblist");
						} else {
							sender.sendMessage("Gave " + args[0] + " [" + args[1] + "]");
						}
					} else {
						sender.sendMessage(ChatColor.RED + "The player " + args[0] + " is not online");
					}
				} else {
					sender.sendMessage(ChatColor.RED + "Usage: /mbgive <player> <bow>");
				}
				erfolg = true;
			} else {
				sender.sendMessage(ChatColor.RED + "You dont have the permissions to use this command [morebows.command]");
			}
		}
		return erfolg;
	}
}
