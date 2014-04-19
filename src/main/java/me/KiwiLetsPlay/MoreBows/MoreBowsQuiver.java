package me.KiwiLetsPlay.MoreBows;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MoreBowsQuiver
		implements Listener {

	final private MoreBows plugin;

	public MoreBowsQuiver(MoreBows plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerDrag(PlayerInteractEvent event) {
		if ((event.getAction() != Action.RIGHT_CLICK_AIR) && (event.getAction() != Action.RIGHT_CLICK_BLOCK)) {
			return;
		}
		if (event.getPlayer().getItemInHand().getType() != Material.BOW) {
			return;
		}
		if (event.getPlayer().getInventory().contains(Material.ARROW)) {
			return;
		}
		Player player = event.getPlayer();
		if (player.getInventory().getChestplate() == null) {
			return;
		}
		if (!player.getInventory().getChestplate().hasItemMeta()) {
			return;
		}
		if (!player.getInventory().getChestplate().getItemMeta().hasLore()) {
			return;
		}
		List<String> lore = player.getInventory().getChestplate().getItemMeta().getLore();
		String[] loreI = ((String) lore.get(0)).split("/");
		if (!loreI[1].contains("320")) {
			return;
		}
		String arrowss = loreI[0];
		arrowss = ChatColor.stripColor(arrowss);

		int arrows = Integer.parseInt(arrowss);
		if (arrows > 0) {
			arrows--;
			String str = String.valueOf(arrows);

			List<String> newLore = new ArrayList();
			String loreString = ChatColor.GRAY + str + "/320";
			newLore.add(loreString);

			ItemMeta im = player.getInventory().getChestplate().getItemMeta();
			im.setLore(newLore);

			int count = 0;
			for (ItemStack i : player.getInventory()) {
				if (i == null) {
					count++;
				}
			}
			if (count > 0) {
				player.getInventory().addItem(new ItemStack[]{new ItemStack(Material.ARROW, 1)});
				player.getInventory().getChestplate().setItemMeta(im);
				player.updateInventory();
			} else {
				player.sendMessage(ChatColor.BLUE + "There's no space in your inventory to put an arrow to.");
			}
		} else if (!player.getInventory().contains(Material.ARROW)) {
			event.setCancelled(true);
			player.sendMessage(ChatColor.BLUE + "No more arrows in quiver or inventory");
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerInteract(PlayerInteractEvent event) {
		if ((event.getAction() != Action.RIGHT_CLICK_AIR) && (event.getAction() != Action.RIGHT_CLICK_BLOCK)) {
			return;
		}
		if (event.getPlayer().getItemInHand().getType() != Material.ARROW) {
			return;
		}
		Player player = event.getPlayer();
		if (player.getInventory().getChestplate() == null) {
			return;
		}
		if (!player.getInventory().getChestplate().hasItemMeta()) {
			return;
		}
		if (!player.getInventory().getChestplate().getItemMeta().hasLore()) {
			return;
		}
		ItemStack chest = player.getInventory().getChestplate();
		int arrowsInHand = player.getItemInHand().getAmount();
		List<String> lore = player.getInventory().getChestplate().getItemMeta().getLore();
		String[] loreI = ((String) lore.get(0)).split("/");
		String arrowss = loreI[0];
		arrowss = ChatColor.stripColor(arrowss);
		int arrowsInChest = Integer.parseInt(arrowss);
		if (arrowsInChest < 320) {
			if (arrowsInChest + arrowsInHand < 320) {
				int newArrows = arrowsInChest + arrowsInHand;

				String str = String.valueOf(newArrows);

				List<String> newLore = new ArrayList();
				String loreString = ChatColor.GRAY + str + "/320";
				newLore.add(loreString);

				ItemMeta im = chest.getItemMeta();
				im.setLore(newLore);
				chest.setItemMeta(im);
				player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.ARROW, player.getInventory().getItemInHand().getAmount())});
				player.playSound(player.getLocation(), Sound.ITEM_PICKUP, 100.0F, 1.0F);
				player.updateInventory();
			} else {
				List<String> newLore = new ArrayList();
				String loreString = ChatColor.GRAY + "320/320";
				newLore.add(loreString);

				ItemMeta im = chest.getItemMeta();
				im.setLore(newLore);
				chest.setItemMeta(im);

				int itemsRe = arrowsInHand - (320 - arrowsInChest);
				player.getInventory().getItemInHand().setAmount(itemsRe);
				if (itemsRe == 0) {
					player.getInventory().setItemInHand(new ItemStack(0));
				}
				player.playSound(player.getLocation(), Sound.ITEM_PICKUP, 100.0F, 1.0F);
				player.updateInventory();
			}
		} else {
			player.sendMessage(ChatColor.BLUE + "Your Quiver is full");
		}
	}
}
