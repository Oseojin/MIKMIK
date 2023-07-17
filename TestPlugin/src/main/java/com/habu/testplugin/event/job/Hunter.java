package com.habu.testplugin.event.job;

import com.habu.testplugin.manager.JobNameManager;
import com.habu.testplugin.manager.PlayerManager;
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
        put(EntityType.CAVE_SPIDER, 5);
        put(EntityType.ENDERMAN, 5);
        put(EntityType.SPIDER, 5);
        put(EntityType.ZOMBIFIED_PIGLIN, 5);
        put(EntityType.PIGLIN, 5);
        put(EntityType.BLAZE, 5);
        put(EntityType.CREEPER, 5);
        put(EntityType.DROWNED, 5);
        put(EntityType.ELDER_GUARDIAN, 5);
        put(EntityType.ENDERMITE, 5);
        put(EntityType.EVOKER, 5);
        put(EntityType.GHAST, 5);
        put(EntityType.GUARDIAN, 5);
        put(EntityType.HUSK, 5);
        put(EntityType.MAGMA_CUBE, 5);
        put(EntityType.IRON_GOLEM, 5);
        put(EntityType.WOLF, 5);
        put(EntityType.BEE, 5);
        put(EntityType.LLAMA, 5);
        put(EntityType.TRADER_LLAMA, 5);
        put(EntityType.PANDA, 5);
        put(EntityType.GOAT, 5);
        put(EntityType.POLAR_BEAR, 5);
        put(EntityType.PHANTOM, 5);
        put(EntityType.PILLAGER, 5);
        put(EntityType.RAVAGER, 5);
        put(EntityType.SHULKER, 5);
        put(EntityType.SILVERFISH, 5);
        put(EntityType.SKELETON, 5);
        put(EntityType.SKELETON_HORSE, 5);
        put(EntityType.SLIME, 5);
        put(EntityType.STRAY, 5);
        put(EntityType.VEX, 5);
        put(EntityType.VINDICATOR, 5);
        put(EntityType.WITCH, 5);
        put(EntityType.WITHER_SKELETON, 5);
        put(EntityType.ZOMBIE, 5);
        put(EntityType.ZOMBIE_VILLAGER, 5);
        put(EntityType.HOGLIN, 5);
        put(EntityType.ZOGLIN, 5);
        put(EntityType.WARDEN, 5);
        put(EntityType.ENDER_DRAGON, 5);
        put(EntityType.WITHER, 5);
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
                int randNum = random.nextInt(arrowList.size());
                PlayerManager.AddGold(player, entityGold.get(entity.getType()));
                ItemStack arrow = new ItemStack(Material.TIPPED_ARROW, 1);
                PotionMeta arrowEffect = (PotionMeta) arrow.getItemMeta();
                int randomUpgrade = random.nextInt(10);
                boolean upgraded = false;
                if(randomUpgrade >= 0 && arrowList.get(randNum).isUpgradeable())
                {
                    upgraded = true;
                }
                player.sendMessage(arrowList.get(randNum) + "");
                PotionData effectData = new PotionData(arrowList.get(randNum), false, upgraded);
                arrowEffect.setBasePotionData(effectData);
                arrow.setItemMeta(arrowEffect);
                player.getWorld().dropItem(deathLoc, arrow);
            }
        }
    }

    @EventHandler
    public void ShootArrow(EntityShootBowEvent event)
    {
        if(event.getEntity() instanceof Player)
        {
            Player player = (Player) event.getEntity();
            Arrow arrow = (Arrow) event.getProjectile();
            if(PlayerManager.GetJob(player).equals(JobNameManager.HunterName))
            {
                arrow.setCritical(true);
                Vector arrowVec = arrow.getVelocity().multiply(3D);
                arrow.setVelocity(arrowVec);
                arrow.setDamage(10);
            }
        }
    }

    @EventHandler
    public void HitArrowToEntity(ProjectileHitEvent event)
    {
        if(event.getEntity().getShooter() instanceof Player)
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
                        double damage = arrow.getDamage();
                        AssumedLivingEntity.damage(damage, player);
                        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0, 0);
                    }
                }
            }
        }
    }
}