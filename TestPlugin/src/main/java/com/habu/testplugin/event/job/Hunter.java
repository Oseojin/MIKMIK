package com.habu.testplugin.event.job;

import com.habu.testplugin.manager.JobNameManager;
import com.habu.testplugin.manager.PlayerManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;
import org.bukkit.util.Vector;

import java.util.*;

public class Hunter implements Listener
{
    Random random = new Random();
    List<PotionType> arrowList = new ArrayList<PotionType>(Arrays.asList(
            PotionType.SLOWNESS,
            PotionType.INSTANT_DAMAGE,
            PotionType.POISON,
            PotionType.STRENGTH,
            PotionType.WEAKNESS,
            PotionType.INSTANT_HEAL
    ));

    HashMap<EntityType, Integer> entityGold = new HashMap<EntityType, Integer>()
    {{
        put(EntityType.WOLF, 8);
        put(EntityType.LLAMA, 11);
        put(EntityType.TRADER_LLAMA, 12);
        put(EntityType.PANDA, 16);
        put(EntityType.BEE, 8);
        put(EntityType.GOAT, 8);
        put(EntityType.SPIDER, 9);
        put(EntityType.CAVE_SPIDER, 9);
        put(EntityType.POLAR_BEAR, 19);
        put(EntityType.ZOMBIE, 11);
        put(EntityType.ZOMBIE_VILLAGER, 11);
        put(EntityType.HUSK, 12);
        put(EntityType.DROWNED, 15);
        put(EntityType.SKELETON, 12);
        put(EntityType.WITHER_SKELETON, 67);
        put(EntityType.STRAY, 13);
        put(EntityType.SLIME, 6);
        put(EntityType.MAGMA_CUBE, 8);
        put(EntityType.GUARDIAN, 22);
        put(EntityType.ELDER_GUARDIAN, 2549);
        put(EntityType.VINDICATOR, 19);
        put(EntityType.EVOKER, 17);
        put(EntityType.VEX, 15);
        put(EntityType.PILLAGER, 14);
        put(EntityType.RAVAGER, 2559);
        put(EntityType.CREEPER, 42);
        put(EntityType.GHAST, 518);
        put(EntityType.PHANTOM, 12);
        put(EntityType.PIGLIN, 13);
        put(EntityType.PIGLIN_BRUTE, 535);
        put(EntityType.WARDEN, 50317);
        put(EntityType.SILVERFISH, 7);
        put(EntityType.BLAZE, 68);
        put(EntityType.WITCH, 69);
        put(EntityType.ENDERMITE, 8);
        put(EntityType.SHULKER, 23);
        put(EntityType.HOGLIN, 24);
        put(EntityType.ZOGLIN, 24);
        put(EntityType.ZOMBIFIED_PIGLIN, 15);
        put(EntityType.ENDERMAN, 77);
        put(EntityType.IRON_GOLEM, 115);
        put(EntityType.WITHER, 75270);
        put(EntityType.ENDER_DRAGON, 200186);
    }};

    @EventHandler
    public void killEntity(EntityDeathEvent event)
    {
        if(event.getEntity().getKiller() instanceof Player)
        {
            Player player = event.getEntity().getKiller();
            Entity entity = event.getEntity();
            Location deathLoc = entity.getLocation();

            String playerJob = PlayerManager.GetJob(player);
            if(playerJob.equals(JobNameManager.HunterName))
            {
                if(entityGold.containsKey(entity.getType()))
                {
                    return;
                }
                PlayerManager.AddGold(player, entityGold.get(entity.getType()));
                int randNum = random.nextInt(10) + 1;
                if(randNum > 8)
                {
                    int randArrow = random.nextInt(arrowList.size());
                    ItemStack arrow = new ItemStack(Material.TIPPED_ARROW, 1);
                    PotionMeta arrowEffect = (PotionMeta) arrow.getItemMeta();
                    int randomUpgrade = random.nextInt(10);
                    boolean upgraded = false;
                    if(randomUpgrade >= 0 && arrowList.get(randArrow).isUpgradeable())
                    {
                        upgraded = true;
                    }
                    PotionData effectData = new PotionData(arrowList.get(randArrow), false, upgraded);
                    arrowEffect.setBasePotionData(effectData);
                    arrow.setItemMeta(arrowEffect);
                    player.getWorld().dropItem(deathLoc, arrow);
                    player.sendMessage(ChatColor.GOLD + "특수 화살이 드랍되었습니다!");
                }
                else
                {
                    ItemStack arrow = new ItemStack(Material.ARROW, 1);
                    player.getInventory().addItem(arrow);
                }
            }
        }
    }

    @EventHandler
    public void ShootArrow(EntityShootBowEvent event)
    {
        if(event.getEntity() instanceof Player && event.getProjectile() instanceof Arrow)
        {
            Player player = (Player) event.getEntity();
            Arrow arrow = (Arrow) event.getProjectile();
            if(PlayerManager.GetJob(player).equals(JobNameManager.HunterName))
            {
                arrow.setCritical(true);
                Vector arrowVec = arrow.getVelocity().multiply(3D);
                arrow.setVelocity(arrowVec);
            }
        }
    }

    @EventHandler
    public void HitArrowToEntity(ProjectileHitEvent event)
    {
        if(event.getEntity().getShooter() instanceof Player && event.getEntity() instanceof Arrow)
        {
            Player player = (Player) event.getEntity().getShooter();
            Arrow arrow = (Arrow) event.getEntity();
            Entity hitEntity = event.getHitEntity();

            if(PlayerManager.GetJob(player).equals(JobNameManager.HunterName))
            {
                if (hitEntity instanceof LivingEntity)
                {
                    LivingEntity AssumedLivingEntity = (LivingEntity) hitEntity;
                    if (!AssumedLivingEntity.equals(player) && AssumedLivingEntity instanceof Enderman)
                    {
                        double damage = 10;
                        AssumedLivingEntity.damage(damage, player);
                        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0, 0);
                    }
                }
            }
        }
    }
}