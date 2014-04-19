package me.KiwiLetsPlay.MoreBows;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Biome;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Blaze;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Slime;
import org.bukkit.entity.Spider;
import org.bukkit.entity.Squid;
import org.bukkit.entity.Wither;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class ExplodingArrowsListener
		implements Listener {

	private MoreBows plugin;

	public ExplodingArrowsListener(MoreBows plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onProjectileHit(ProjectileHitEvent event) {
		Entity entity = event.getEntity();
		if (entity.getType() != EntityType.ARROW) {
			return;
		}
		final Arrow tArrow = (Arrow) entity;
		if (tArrow.getShooter() == null) {
			return;
		}
		if (!(tArrow.getShooter() instanceof Player)) {
			return;
		}
		final Player tPlayer = (Player) tArrow.getShooter();
		if (tPlayer.getItemInHand() == null) {
			return;
		}
		boolean perm = this.plugin.getConfig().getBoolean("Permissions.activated");
		if (tArrow.hasMetadata("lightning")) {
			Boolean use = Boolean.valueOf(this.plugin.getConfig().getBoolean("Lightning-bow.using"));
			if (use.booleanValue()) {
				if (((perm) && (tPlayer.hasPermission("morebows.bows.lightning"))) || (!perm)) {
					tArrow.remove();
					tPlayer.getWorld().strikeLightning(tArrow.getLocation());
				} else {
					tPlayer.sendMessage(ChatColor.RED
							+ "You dont have the permissions to use this bow. [morebows.bows.lightning]");
				}
			} else {
				tPlayer.sendMessage(ChatColor.RED + "This bow is disabled");
			}
		}
		if (tArrow.hasMetadata("explosion")) {
			Boolean use = Boolean.valueOf(this.plugin.getConfig().getBoolean("Explosion-bow.using"));
			if (use.booleanValue()) {
				if (((perm) && (tPlayer.hasPermission("morebows.bows.explosion"))) || (!perm)) {
					int radius = this.plugin.getConfig().getInt("Explosion-bow.radius");
					tArrow.remove();
					tPlayer.getWorld().createExplosion(tArrow.getLocation(), radius);
				} else {
					tPlayer.sendMessage(ChatColor.RED
							+ "You dont have the permissions to use this bow. [morebows.bows.explosion]");
				}
			} else {
				tPlayer.sendMessage(ChatColor.RED + "This bow is disabled");
			}
		}
		if (tArrow.hasMetadata("teleport")) {
			Boolean use = Boolean.valueOf(this.plugin.getConfig().getBoolean("Ender-bow.using"));
			if (use.booleanValue()) {
				Player enderplayer = tPlayer;
				if (((perm) && (enderplayer.hasPermission("morebows.bows.ender"))) || (!perm)) {
					float pitch = enderplayer.getEyeLocation().getPitch();
					float yaw = enderplayer.getEyeLocation().getYaw();
					Location newLoc = new Location(tArrow.getWorld(), tArrow.getLocation().getX(), tArrow.getLocation().getY(), tArrow.getLocation().getZ(), yaw, pitch);
					enderplayer.teleport(newLoc);

					tArrow.remove();
					enderplayer.playSound(enderplayer.getLocation(), Sound.ENDERMAN_TELEPORT, 10.0F, 1.0F);
					enderplayer.getWorld().playEffect(enderplayer.getLocation(), Effect.ENDER_SIGNAL, 10);
					enderplayer.getWorld().playEffect(enderplayer.getLocation(), Effect.ENDER_SIGNAL, 8);
					enderplayer.getWorld().playEffect(enderplayer.getLocation(), Effect.ENDER_SIGNAL, 6);
					enderplayer.getWorld().playEffect(enderplayer.getLocation(), Effect.ENDER_SIGNAL, 4);
				} else {
					tPlayer.sendMessage(ChatColor.RED
							+ "You dont have the permissions to use this bow. [morebows.bows.ender]");
				}
			} else {
				tPlayer.sendMessage(ChatColor.RED + "This bow is disabled");
			}
		}
		if (tArrow.hasMetadata("storm")) {
			Boolean use = Boolean.valueOf(this.plugin.getConfig().getBoolean("Storm-bow.using"));
			if (use.booleanValue()) {
				if (((perm) && (tPlayer.hasPermission("morebows.bows.storm"))) || (!perm)) {
					final List<Entity> ents = tArrow.getNearbyEntities(4.0D, 3.0D, 4.0D);
					tArrow.remove();

					new BukkitRunnable() {
						double count = 1.0D;

						public void run() {
							double x = this.count * Math.cos(4.0D * this.count);
							double z = this.count * Math.sin(4.0D * this.count);
							double y = this.count;
							Location loc = new Location(tArrow.getWorld(), x + tArrow.getLocation().getX(), y
									+ tArrow.getLocation().getY(), z + tArrow.getLocation().getZ());
							tArrow.getWorld().playEffect(loc, Effect.STEP_SOUND, 12);
							tArrow.getWorld().playEffect(tArrow.getLocation().add(0.0D, this.count, 0.0D), Effect.SMOKE, 4);
							x = this.count * Math.cos(3.0D * this.count);
							z = this.count * Math.sin(3.0D * this.count);
							y = this.count;
							loc = new Location(tArrow.getWorld(), x + tArrow.getLocation().getX(), y
									+ tArrow.getLocation().getY(), z + tArrow.getLocation().getZ());
							tArrow.getWorld().playEffect(loc, Effect.STEP_SOUND, 12);
							x = this.count * Math.cos(2.0D * this.count);
							z = this.count * Math.sin(2.0D * this.count);
							y = this.count;
							loc = new Location(tArrow.getWorld(), x + tArrow.getLocation().getX(), y
									+ tArrow.getLocation().getY(), z + tArrow.getLocation().getZ());
							tArrow.getWorld().playEffect(loc, Effect.STEP_SOUND, 12);
							x = this.count * Math.cos(1.0D * this.count);
							z = this.count * Math.sin(1.0D * this.count);
							y = this.count;
							loc = new Location(tArrow.getWorld(), x + tArrow.getLocation().getX(), y
									+ tArrow.getLocation().getY(), z + tArrow.getLocation().getZ());
							tArrow.getWorld().playEffect(loc, Effect.STEP_SOUND, 12);

							this.count += 0.1D;
							if (this.count > 6.0D) {
								cancel();
							}
							if ((this.count > 2.9D) && (this.count < 3.0D)) {
								for (int i = 0; i < ents.size(); i++) {
									final Entity ent = (Entity) ents.get(i);
									if (((ent instanceof LivingEntity)) && (ent != tPlayer)) {
										ent.getWorld().playSound(ent.getLocation(), Sound.ENDERDRAGON_WINGS, 1.0F, 0.5F);
										ent.setVelocity(new Vector(Math.random() * 0.5D, 1.5D, Math.random() * 0.5D));

										new BukkitRunnable() {
											int cnt = 15;

											public void run() {
												if ((!ent.isOnGround()) && (!ent.isDead())) {
													ent.setFallDistance(30.0F);
													ent.getWorld().playEffect(ent.getLocation(), Effect.SMOKE, 0);
												}
												this.cnt -= 1;
												if (this.cnt == 0) {
													cancel();
												}
											}
										}.runTaskTimer(ExplodingArrowsListener.this.plugin, 0L, 2L);
									}
								}
							}
						}
					}.runTaskTimer(this.plugin, 0L, 2L);
				} else {
					tPlayer.sendMessage(ChatColor.RED
							+ "You dont have the permissions to use this bow. [morebows.bows.storm]");
				}
			} else {
				tPlayer.sendMessage(ChatColor.RED + "This bow is disabled");
			}
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onMobKill(EntityDeathEvent event) {
		if ((event.getEntity() instanceof Skeleton)) {
			Biome b = event.getEntity().getLocation().getBlock().getBiome();
			if ((b.equals(Biome.DESERT)) || (b.equals(Biome.DESERT_HILLS)) || (b.equals(Biome.DESERT_MOUNTAINS))) {
				List<String> ls = new ArrayList();
				ls.add(ChatColor.GOLD + "Storm Soul");
				ItemStack lsoul = setName(new ItemStack(Material.BLAZE_POWDER), "Artefact", ls);
				Enchantment myEnchantment = new EnchantmentWrapper(19);
				lsoul.addUnsafeEnchantment(myEnchantment, 2);
				if (event.getEntity().getKiller() == null) {
					return;
				}
				if (event.getEntity().getKiller().getItemInHand() == null) {
					return;
				}
				boolean perm = this.plugin.getConfig().getBoolean("Permissions.activated");
				if (((!perm) || (!event.getEntity().getKiller().hasPermission("morebows.artefacts.storm.drop"))) && (perm)) {
					return;
				}
				int looting = 0;
				if ((event.getEntity().getKiller().getItemInHand().getEnchantments() != null)
						&& (event.getEntity().getKiller().getItemInHand().getEnchantments().containsKey(Enchantment.LOOT_BONUS_MOBS))) {
					looting = ((Integer) event.getEntity().getKiller().getItemInHand().getEnchantments().get(Enchantment.LOOT_BONUS_MOBS)).intValue();
				}
				int ran = randInt(0, 100);
				if ((ran > 43 - looting * 2) && (ran < 47 + looting * 2)) {
					if (event.getDrops().isEmpty()) {
						return;
					}
					event.getDrops().add(lsoul);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerInteract(PlayerInteractEvent event) {
		if ((event.getAction() != Action.RIGHT_CLICK_AIR) && (event.getAction() != Action.RIGHT_CLICK_BLOCK)) {
			return;
		}
		Player player = event.getPlayer();
		if (player.getItemInHand() == null) {
			return;
		}
		if (!player.getItemInHand().hasItemMeta()) {
			return;
		}
		if (!player.getItemInHand().getItemMeta().hasLore()) {
			return;
		}
		List<String> heldL = player.getItemInHand().getItemMeta().getLore();
		boolean perm = this.plugin.getConfig().getBoolean("Permissions.activated");
		if (heldL == null) {
			return;
		}
		Boolean use = Boolean.valueOf(this.plugin.getConfig().getBoolean("StormSoul.using"));
		if ((!use.booleanValue())
				&& (heldL.contains(ChatColor.GOLD + "Storm Soul")) && (((perm) && (player.hasPermission("morebows.artefacts.storm"))) || (!perm))) {
			List<String> ls = new ArrayList();
			ls.add(ChatColor.GOLD + "Storm Soul");
			ItemStack lsoul = setName(new ItemStack(Material.BLAZE_POWDER), "Artefact", ls);
			Enchantment myEnchantment = new EnchantmentWrapper(19);
			lsoul.addUnsafeEnchantment(myEnchantment, 2);

			List<String> lu = new ArrayList();
			lu.add(ChatColor.GRAY + "Power of the storms I");
			lu.add(ChatColor.RED + "uncharged");
			ItemStack lBowu = setName(new ItemStack(Material.BOW), "Storm Bow", lu);

			List<String> lb = new ArrayList();
			lb.add(ChatColor.GRAY + "Power of the storms I");
			ItemStack lBow = setName(new ItemStack(Material.BOW), "Storm Bow", lb);
			if (player.getInventory().contains(lBowu)) {
				player.playSound(player.getLocation(), Sound.AMBIENCE_THUNDER, 100.0F, 1.0F);
				player.playSound(player.getLocation(), Sound.FIRE, 100.0F, 1.0F);

				player.getWorld().playEffect(player.getLocation(), Effect.MOBSPAWNER_FLAMES, 10);
				player.getWorld().playEffect(player.getLocation(), Effect.MOBSPAWNER_FLAMES, 10);
				player.getWorld().playEffect(player.getLocation(), Effect.MOBSPAWNER_FLAMES, 10);

				player.getInventory().removeItem(new ItemStack[]{lsoul});
				player.getInventory().addItem(new ItemStack[]{lBow});
				player.getInventory().removeItem(new ItemStack[]{lBowu});
				player.updateInventory();
			}
		}
		use = Boolean.valueOf(this.plugin.getConfig().getBoolean("LightningSoul.using"));
		if ((use.booleanValue())
				&& (heldL.contains(ChatColor.GOLD + "Lightning Soul")) && (((perm) && (player.hasPermission("morebows.artefacts.lightning"))) || (!perm))) {
			List<String> ls = new ArrayList();
			ls.add(ChatColor.GOLD + "Lightning Soul");
			ItemStack lsoul = setName(new ItemStack(Material.MAGMA_CREAM), "Artefact", ls);
			Enchantment myEnchantment18 = new EnchantmentWrapper(20);
			lsoul.addUnsafeEnchantment(myEnchantment18, 1);

			List<String> lu = new ArrayList();
			lu.add(ChatColor.GRAY + "Lightning I");
			lu.add(ChatColor.RED + "uncharged");
			ItemStack lBowu = setName(new ItemStack(Material.BOW), "Lightning Bow", lu);
			Enchantment myEnchantment16 = new EnchantmentWrapper(48);
			lBowu.addEnchantment(myEnchantment16, 1);

			List<String> lb = new ArrayList();
			lb.add(ChatColor.GRAY + "Lightning I");
			ItemStack lBow = setName(new ItemStack(Material.BOW), "Lightning Bow", lb);
			Enchantment myEnchantment17 = new EnchantmentWrapper(48);
			lBow.addEnchantment(myEnchantment17, 1);
			if (player.getInventory().contains(lBowu)) {
				player.playSound(player.getLocation(), Sound.AMBIENCE_THUNDER, 100.0F, 1.0F);
				player.playSound(player.getLocation(), Sound.FIRE, 100.0F, 1.0F);

				player.getWorld().playEffect(player.getLocation(), Effect.MOBSPAWNER_FLAMES, 10);
				player.getWorld().playEffect(player.getLocation(), Effect.MOBSPAWNER_FLAMES, 10);
				player.getWorld().playEffect(player.getLocation(), Effect.MOBSPAWNER_FLAMES, 10);

				player.getInventory().removeItem(new ItemStack[]{lsoul});
				player.getInventory().addItem(new ItemStack[]{lBow});
				player.getInventory().removeItem(new ItemStack[]{lBowu});
				player.updateInventory();
			}
		}
		use = Boolean.valueOf(this.plugin.getConfig().getBoolean("Storm-bow.using"));
		if ((use.booleanValue())
				&& (heldL.contains(ChatColor.GRAY + "Power of the storms I"))
				&& (!heldL.contains(ChatColor.RED + "uncharged"))) {
			for (int i = 0; i < 5; i++) {
				int randomNum = randInt(0, 1);
				int randX = randInt(-1, 1);
				int randZ = randInt(-1, 1);
				if (randomNum == 1) {
					player.getWorld().playEffect(player.getLocation().add(randX, 0.0D, randZ), Effect.MOBSPAWNER_FLAMES, 4);
				}
			}
		}
	}

	private ItemStack setName(ItemStack is, String name, List<String> lore) {
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(name);
		im.setLore(lore);
		is.setItemMeta(im);
		return is;
	}

	public static int randInt(int min, int max) {
		Random rand = new Random();
		int randomNum = rand.nextInt(max - min + 1) + min;
		return randomNum;
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void damageListener(EntityDamageEvent e) {
		if (e.isCancelled()) {
			return;
		}
		if (!(e instanceof EntityDamageByEntityEvent)) {
			return;
		}
		EntityDamageByEntityEvent evt = (EntityDamageByEntityEvent) e;
		if (!(evt.getEntity() instanceof LivingEntity)) {
			return;
		}
		final LivingEntity ent = (LivingEntity) evt.getEntity();
		Entity dam = evt.getDamager();
		if (dam.getType() == EntityType.ARROW) {
			Projectile proj = (Arrow) dam;
			ProjectileSource le = proj.getShooter();
			if (!(le instanceof Player)) {
				return;
			}
			Player player = (Player) le;
			if (dam.hasMetadata("poison")) {
				Boolean use = Boolean.valueOf(this.plugin.getConfig().getBoolean("Poison-bow.using"));
				if (use.booleanValue()) {
					if (player.hasPermission("morebows.bows.poison")) {
						if (((ent instanceof Spider)) || ((ent instanceof PigZombie))) {
							ent.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 100, 1));
						}
						if (((ent instanceof Skeleton)) || ((ent instanceof Zombie))) {
							ent.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 100, 1));
						}
						ent.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 100, 1));
					} else {
						player.sendMessage(ChatColor.RED
								+ "You dont have the permissions to use this bow. [morebows.bows.poison]");
					}
				} else {
					player.sendMessage(ChatColor.RED + "This bow is disabled");
				}
			}
			if (dam.hasMetadata("disappearance")) {
				Boolean use = Boolean.valueOf(this.plugin.getConfig().getBoolean("Disappearance-bow.using"));
				if (use.booleanValue()) {
					if (player.hasPermission("morebows.bows.disappearance")) {
						if ((ent instanceof Player)) {
							player.getWorld().playEffect(ent.getLocation(), Effect.ENDER_SIGNAL, 10);
							player.getWorld().playEffect(ent.getLocation(), Effect.ZOMBIE_DESTROY_DOOR, 10);
						} else {
							ent.remove();
							player.getWorld().playEffect(ent.getLocation(), Effect.ENDER_SIGNAL, 10);
							player.getWorld().playEffect(ent.getLocation(), Effect.ENDER_SIGNAL, 8);
							player.getWorld().playEffect(ent.getLocation(), Effect.ENDER_SIGNAL, 6);
							player.getWorld().playEffect(ent.getLocation(), Effect.ENDER_SIGNAL, 4);
							player.getWorld().playEffect(ent.getLocation(), Effect.GHAST_SHOOT, 10);
						}
					} else {
						player.sendMessage(ChatColor.RED
								+ "You dont have the permissions to use this bow. [morebows.bows.disappearance]");
					}
				} else {
					player.sendMessage(ChatColor.RED + "This bow is disabled");
				}
			}
			if (dam.hasMetadata("freeze")) {
				Boolean use = Boolean.valueOf(this.plugin.getConfig().getBoolean("Freeze-bow.using"));
				if (use.booleanValue()) {
					if (player.hasPermission("morebows.bows.freeze")) {
						if ((!(ent instanceof Squid)) && (!(ent instanceof Ghast)) && (!(ent instanceof Slime))
								&& (!(ent instanceof IronGolem)) && (!(ent instanceof EnderDragon))
								&& (!(ent instanceof Wither)) && (!(ent instanceof Blaze))) {
							if (ent.isOnGround()) {
								ent.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 200, 6));
								final Location iceL = ent.getLocation();
								final Location eIceL = ent.getEyeLocation();
								final Location tpLoc = ent.getLocation();
								ent.getWorld().getBlockAt(iceL).setType(Material.ICE);
								ent.getWorld().getBlockAt(eIceL).setType(Material.ICE);

								this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable() {
									public void run() {
										ent.teleport(tpLoc);
										if (ent.isDead()) {
											ent.getWorld().getBlockAt(iceL).setType(Material.AIR);
											ent.getWorld().getBlockAt(eIceL).setType(Material.AIR);
										}
									}
								}, 7L);
								this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable() {
									public void run() {
										ent.getWorld().getBlockAt(iceL).setType(Material.AIR);
										ent.getWorld().getBlockAt(eIceL).setType(Material.AIR);
									}
								}, 200L);
							}
						}
					} else {
						player.sendMessage(ChatColor.RED
								+ "You dont have the permissions to use this bow. [morebows.bows.freeze]");
					}
				} else {
					player.sendMessage(ChatColor.RED + "This bow is disabled");
				}
			}
			if (dam.hasMetadata("blind")) {
				Boolean use = Boolean.valueOf(this.plugin.getConfig().getBoolean("Squid-bow.using"));
				if (use.booleanValue()) {
					if (player.hasPermission("morebows.bows.squid")) {
						int dur = this.plugin.getConfig().getInt("Squid-bow.effectDurability");
						if ((ent instanceof Player)) {
							ent.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, dur, 1));
						}
						ent.getWorld().playEffect(ent.getLocation(), Effect.SMOKE, 4);
						ent.getWorld().playSound(ent.getLocation(), Sound.SLIME_WALK, 100.0F, 2.0F);
					} else {
						player.sendMessage(ChatColor.RED
								+ "You dont have the permissions to use this bow. [morebows.bows.squid]");
					}
				} else {
					player.sendMessage(ChatColor.RED + "This bow is disabled");
				}
			}
			if (dam.getType() == EntityType.WITHER_SKULL) {
				if (dam.hasMetadata("wither")) {
					Boolean use = Boolean.valueOf(this.plugin.getConfig().getBoolean("Wither-bow.using"));
					if (use.booleanValue()) {
						if (player.hasPermission("morebows.bows.wither")) {
							int dur = this.plugin.getConfig().getInt("Wither-bow.effectDurability");
							ent.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, dur, 1));
						} else {
							player.sendMessage(ChatColor.RED
									+ "You dont have the permissions to use this bow. [morebows.bows.wither]");
						}
					} else {
						player.sendMessage(ChatColor.RED + "This bow is disabled");
					}
				}
			}
		}
	}
}
