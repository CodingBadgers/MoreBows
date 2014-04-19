package me.KiwiLetsPlay.MoreBows;

import java.util.List;
import java.util.Random;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;

public class MoreBowsCraftListener
		implements Listener {

	final private MoreBows plugin;

	public MoreBowsCraftListener(MoreBows plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerCraft(CraftItemEvent event) {
		boolean perm = this.plugin.getConfig().getBoolean("Permissions.activated");
		Player player = (Player) event.getWhoClicked();
		if (!perm) {
			return;
		}
		if (event.getRecipe().getResult() == null) {
			return;
		}
		ItemStack item = event.getRecipe().getResult();
		if (!item.hasItemMeta()) {
			return;
		}
		if (!item.getItemMeta().hasLore()) {
			return;
		}
		List<String> heldL = item.getItemMeta().getLore();
		if ((heldL.contains(ChatColor.GRAY + "Lightning I")) && (!player.hasPermission("morebows.craft.lightning"))) {
			event.setCancelled(true);
		}
		if ((heldL.contains(ChatColor.GOLD + "Lightning Soul")) && (!player.hasPermission("morebows.artefacts.lightning.craft"))) {
			event.setCancelled(true);
		}
		if ((heldL.contains(ChatColor.GRAY + "Explosion I")) && (!player.hasPermission("morebows.craft.explosion"))) {
			event.setCancelled(true);
		}
		if ((heldL.contains(ChatColor.GRAY + "Poison I")) && (!player.hasPermission("morebows.craft.poison"))) {
			event.setCancelled(true);
		}
		if ((heldL.contains(ChatColor.GRAY + "Disappearance I")) && (!player.hasPermission("morebows.craft.disappearance"))) {
			event.setCancelled(true);
		}
		if ((heldL.contains(ChatColor.GRAY + "Freeze I")) && (!player.hasPermission("morebows.craft.freeze"))) {
			event.setCancelled(true);
		}
		if ((heldL.contains(ChatColor.GRAY + "Fireball I")) && (!player.hasPermission("morebows.craft.fireball"))) {
			event.setCancelled(true);
		}
		if ((heldL.contains(ChatColor.GRAY + "Wither I")) && (!player.hasPermission("morebows.craft.wither"))) {
			event.setCancelled(true);
		}
		if ((heldL.contains(ChatColor.GRAY + "Blindness I")) && (!player.hasPermission("morebows.craft.squid"))) {
			event.setCancelled(true);
		}
		if ((heldL.contains(ChatColor.GRAY + "Teleport I")) && (!player.hasPermission("morebows.craft.ender"))) {
			event.setCancelled(true);
		}
		if ((heldL.contains(ChatColor.GRAY + "Power of the storms I")) && (!player.hasPermission("morebows.craft.storm"))) {
			event.setCancelled(true);
		}
		if ((heldL.contains(ChatColor.GOLD + "Storm Soul")) && (!player.hasPermission("morebows.artefacts.storm.craft"))) {
			event.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerEnch(EnchantItemEvent event) {
		if (event.getItem().getType() != Material.BOW) {
			return;
		}
		int lvl = 1;
		if (event.getExpLevelCost() < 11) {
			lvl = 1;
		}
		if ((event.getExpLevelCost() > 10) && (event.getExpLevelCost() < 29)) {
			lvl = 2;
		}
		if (event.getExpLevelCost() > 28) {
			lvl = 3;
		}
		int ran = randInt(0, 100);
		if ((ran > 44) && (ran < 50)) {
			event.getItem().addUnsafeEnchantment(Enchantment.LOOT_BONUS_MOBS, lvl);
		}
	}

	public static int randInt(int min, int max) {
		Random rand = new Random();
		int randomNum = rand.nextInt(max - min + 1) + min;
		return randomNum;
	}
}
