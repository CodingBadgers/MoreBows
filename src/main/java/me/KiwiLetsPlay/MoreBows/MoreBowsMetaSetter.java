package me.KiwiLetsPlay.MoreBows;

import java.util.HashMap;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.WitherSkull;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.scheduler.BukkitRunnable;

public class MoreBowsMetaSetter
		implements Listener {

	private MoreBows plugin;
	static HashMap<Player, Boolean> blocked = new HashMap();
	int loop = 0;

	public MoreBowsMetaSetter(MoreBows plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void arrowListener(EntityShootBowEvent evt) {
		if (evt.isCancelled()) {
			return;
		}
		if ((evt instanceof EntityShootBowEvent)) {
			if ((evt.getEntity() instanceof LivingEntity)) {
				Entity dam = evt.getProjectile();
				if ((dam instanceof Arrow)) {
					final Projectile proj = (Arrow) dam;
					ProjectileSource le = proj.getShooter();
					if ((le instanceof Player)) {
						final Player player = (Player) le;
						if (player.getItemInHand().getType().getId() != 0) {
							List<String> heldL = player.getItemInHand().getItemMeta().getLore();
							if (heldL != null) {
								boolean perm = this.plugin.getConfig().getBoolean("Permissions.activated");
								if (((heldL.contains(ChatColor.GRAY + "Lightning I")) || (heldL.contains("Lightning I")))
										&& (!heldL.contains(ChatColor.RED + "uncharged"))) {
									Boolean use = Boolean.valueOf(this.plugin.getConfig().getBoolean("Lightning-bow.using"));
									if (use.booleanValue()) {
										if (((perm) && (player.hasPermission("morebows.bows.lightning"))) || (!perm)) {
											proj.setMetadata("lightning", new FixedMetadataValue(this.plugin, Boolean.valueOf(true)));
										} else {
											player.sendMessage(ChatColor.RED + "You dont have the permissions to use this bow. [morebows.bows.lightning]");
										}
									} else {
										player.sendMessage(ChatColor.RED + "This bow is disabled");
									}
								}
								if ((heldL.contains(ChatColor.GRAY + "Explosion I")) || (heldL.contains("Explosion I"))) {
									Boolean use = Boolean.valueOf(this.plugin.getConfig().getBoolean("Explosion-bow.using"));
									if (use.booleanValue()) {
										if (((perm) && (player.hasPermission("morebows.bows.explosion"))) || (!perm)) {
											proj.setMetadata("explosion", new FixedMetadataValue(this.plugin, Boolean.valueOf(true)));
										} else {
											player.sendMessage(ChatColor.RED + "You dont have the permissions to use this bow. [morebows.bows.explosion]");
										}
									} else {
										player.sendMessage(ChatColor.RED + "This bow is disabled");
									}
								}
								if ((heldL.contains(ChatColor.GRAY + "Poison I")) || (heldL.contains("Poison I"))) {
									Boolean use = Boolean.valueOf(this.plugin.getConfig().getBoolean("Poison-bow.using"));
									if (use.booleanValue()) {
										if (((perm) && (player.hasPermission("morebows.bows.poison"))) || (!perm)) {
											proj.setMetadata("poison", new FixedMetadataValue(this.plugin, Boolean.valueOf(true)));
										} else {
											player.sendMessage(ChatColor.RED + "You dont have the permissions to use this bow. [morebows.bows.poison]");
										}
									} else {
										player.sendMessage(ChatColor.RED + "This bow is disabled");
									}
								}
								if ((heldL.contains(ChatColor.GRAY + "Disappearance I"))
										|| (heldL.contains("Disappearance I"))) {
									Boolean use = Boolean.valueOf(this.plugin.getConfig().getBoolean("Disappearance-bow.using"));
									if (use.booleanValue()) {
										if (((perm) && (player.hasPermission("morebows.bows.disappearance"))) || (!perm)) {
											proj.setMetadata("disappearance", new FixedMetadataValue(this.plugin, Boolean.valueOf(true)));
										} else {
											player.sendMessage(ChatColor.RED + "You dont have the permissions to use this bow. [morebows.bows.disappearance]");
										}
									} else {
										player.sendMessage(ChatColor.RED + "This bow is disabled");
									}
								}
								if ((heldL.contains(ChatColor.GRAY + "Freeze I")) || (heldL.contains("Freeze I"))) {
									Boolean use = Boolean.valueOf(this.plugin.getConfig().getBoolean("Freeze-bow.using"));
									if (use.booleanValue()) {
										if (((perm) && (player.hasPermission("morebows.bows.freeze"))) || (!perm)) {
											proj.setMetadata("freeze", new FixedMetadataValue(this.plugin, Boolean.valueOf(true)));

											this.loop = proj.getServer().getScheduler().scheduleSyncRepeatingTask(this.plugin, new Runnable() {
												public void run() {
													if (!proj.isOnGround()) {
														proj.getWorld().playEffect(proj.getLocation(), Effect.SMOKE, 4);
													} else {
														MoreBowsMetaSetter.this.plugin.getServer().getScheduler().cancelTask(MoreBowsMetaSetter.this.loop);
													}
												}
											}, 0L, 1L);
										} else {
											player.sendMessage(ChatColor.RED + "You dont have the permissions to use this bow. [morebows.bows.freeze]");
										}
									} else {
										player.sendMessage(ChatColor.RED + "This bow is disabled");
									}
								}
								if ((heldL.contains(ChatColor.GRAY + "Fireball I")) || (heldL.contains("Fireball I"))) {
									Boolean j = Boolean.valueOf(this.plugin.getConfig().getBoolean("Ghast-bow.onlynether"));
									Boolean use = Boolean.valueOf(this.plugin.getConfig().getBoolean("Ghast-bow.using"));
									Boolean consume = Boolean.valueOf(this.plugin.getConfig().getBoolean("Ghast-bow.consume"));
									if (use.booleanValue()) {
										if (((perm) && (player.hasPermission("morebows.bows.fireball"))) || (!perm)) {
											if (((j.booleanValue()) && (player.getWorld().getEnvironment().equals(World.Environment.NETHER))) || (!j.booleanValue())) {
												ItemStack m = new ItemStack(Material.FIREBALL, 1);
												if (((consume.booleanValue()) && (player.getInventory().contains(Material.FIREBALL))) || (!consume.booleanValue())) {
													evt.setCancelled(true);
													if (consume.booleanValue()) {
														player.getInventory().removeItem(new ItemStack[]{m});
													}
													((Fireball) player.launchProjectile(Fireball.class)).setVelocity(proj.getVelocity());
													player.getWorld().playEffect(player.getLocation(), Effect.BLAZE_SHOOT, 10);
												} else {
													evt.setCancelled(true);
													player.playSound(player.getLocation(), Sound.LAVA_POP, 10.0F, -200.0F);
													player.updateInventory();
												}
											} else {
												player.sendMessage(ChatColor.RED + "This bow is for the nether dimension only.");
											}
										} else {
											player.sendMessage(ChatColor.RED + "You dont have the permissions to use this bow. [morebows.bows.fireball]");
										}
									} else {
										player.sendMessage(ChatColor.RED + "This bow is disabled");
									}
								}
								if (heldL.contains(ChatColor.GRAY + "Blindness I")) {
									Boolean use = Boolean.valueOf(this.plugin.getConfig().getBoolean("Squid-bow.using"));
									Boolean consume = Boolean.valueOf(this.plugin.getConfig().getBoolean("Squid-bow.consume"));
									if (use.booleanValue()) {
										if (((perm) && (player.hasPermission("morebows.bows.squid"))) || (!perm)) {
											ItemStack m = new ItemStack(Material.INK_SACK, 1);
											if (((consume.booleanValue()) && (player.getInventory().contains(Material.INK_SACK))) || (!consume.booleanValue())) {
												player.playSound(player.getLocation(), Sound.ENDERDRAGON_WINGS, 10.0F, 200.0F);
												proj.setMetadata("blind", new FixedMetadataValue(this.plugin, Boolean.valueOf(true)));
												if (consume.booleanValue()) {
													player.getInventory().removeItem(new ItemStack[]{m});
												}
											} else {
												evt.setCancelled(true);
												player.playSound(player.getLocation(), Sound.LAVA_POP, 10.0F, -200.0F);
												player.updateInventory();
											}
										} else {
											player.sendMessage(ChatColor.RED
													+ "You dont have the permissions to use this bow. [morebows.bows.squid]");
										}
									} else {
										player.sendMessage(ChatColor.RED + "This bow is disabled");
									}
								}
								if (heldL.contains(ChatColor.GRAY + "Teleport I")) {
									Boolean use = Boolean.valueOf(this.plugin.getConfig().getBoolean("Ender-bow.using"));
									Boolean consume = Boolean.valueOf(this.plugin.getConfig().getBoolean("Ender-bow.consume"));
									if (use.booleanValue()) {
										if (((perm) && (player.hasPermission("morebows.bows.ender"))) || (!perm)) {
											ItemStack m = new ItemStack(Material.ENDER_PEARL, 1);
											if (((consume.booleanValue()) && (player.getInventory().contains(Material.ENDER_PEARL))) || (!consume.booleanValue())) {
												player.playSound(player.getLocation(), Sound.ENDERMAN_IDLE, 10.0F, 1.0F);
												proj.setMetadata("teleport", new FixedMetadataValue(this.plugin, Boolean.valueOf(true)));
												if (consume.booleanValue()) {
													player.getInventory().removeItem(new ItemStack[]{m});
												}
											} else {
												evt.setCancelled(true);
												player.playSound(player.getLocation(), Sound.LAVA_POP, 10.0F, -200.0F);
												player.updateInventory();
											}
										} else {
											player.sendMessage(ChatColor.RED + "You dont have the permissions to use this bow. [morebows.bows.ender]");
										}
									} else {
										player.sendMessage(ChatColor.RED + "This bow is disabled");
									}
								}
								if ((heldL.contains(ChatColor.GRAY + "Wither I")) || (heldL.contains("Wither I"))) {
									Boolean use = Boolean.valueOf(this.plugin.getConfig().getBoolean("Wither-bow.using"));
									if (use.booleanValue()) {
										if (((perm) && (player.hasPermission("morebows.bows.wither"))) || (!perm)) {
											evt.setCancelled(true);
											Projectile skull = player.launchProjectile(WitherSkull.class);
											skull.setVelocity(proj.getVelocity());
											skull.setMetadata("wither", new FixedMetadataValue(this.plugin, Boolean.valueOf(true)));
											player.playSound(player.getLocation(), Sound.WITHER_SHOOT, 10.0F, 1.0F);
										} else {
											player.sendMessage(ChatColor.RED + "You dont have the permissions to use this bow. [morebows.bows.wither]");
										}
									} else {
										player.sendMessage(ChatColor.RED + "This bow is disabled");
									}
								}
								if ((heldL.contains(ChatColor.GRAY + "Power of the storms I")) && (!heldL.contains(ChatColor.RED + "uncharged"))) {
									Boolean use = Boolean.valueOf(this.plugin.getConfig().getBoolean("Storm-bow.using"));
									if (use.booleanValue()) {
										if (((perm) && (player.hasPermission("morebows.bows.storm"))) || (!perm)) {
											if (!blocked.containsKey(player)) {
												proj.setMetadata("storm", new FixedMetadataValue(this.plugin, Boolean.valueOf(true)));
												blocked.put(player, Boolean.valueOf(true));
												new BukkitRunnable() {
													public void run() {
														if (MoreBowsMetaSetter.blocked.containsKey(player)) {
															MoreBowsMetaSetter.blocked.remove(player);
														}
													}
												}.runTaskLater(this.plugin, 120L);
											} else {
												player.playSound(player.getLocation(), Sound.LAVA_POP, 1.0F, 0.1F);
											}
										} else {
											player.sendMessage(ChatColor.RED + "You dont have the permissions to use this bow. [morebows.bows.storm]");
										}
									} else {
										player.sendMessage(ChatColor.RED + "This bow is disabled");
									}
								}
							}
						}
					}
				}
			}
		}
	}
}
