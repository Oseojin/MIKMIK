package com.habu.testplugin.event.job;

import com.habu.testplugin.TestPlugin;
import com.habu.testplugin.db.player_db_connect;
import com.habu.testplugin.manager.JobNameManager;
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
        put(EntityType.WOLF, 53);
        put(EntityType.LLAMA, 111);
        put(EntityType.TRADER_LLAMA, 112);
        put(EntityType.PANDA, 119);
        put(EntityType.BEE, 57);
        put(EntityType.GOAT, 57);
        put(EntityType.SPIDER, 85);
        put(EntityType.CAVE_SPIDER, 67);
        put(EntityType.POLAR_BEAR, 167);
        put(EntityType.ZOMBIE, 108);
        put(EntityType.ZOMBIE_VILLAGER, 108);
        put(EntityType.HUSK, 109);
        put(EntityType.DROWNED, 121);
        put(EntityType.SKELETON, 108);
        put(EntityType.WITHER_SKELETON, 175);
        put(EntityType.STRAY, 109);
        put(EntityType.SLIME, 44);
        put(EntityType.MAGMA_CUBE, 49);
        put(EntityType.GUARDIAN, 170);
        put(EntityType.ELDER_GUARDIAN, 2927);
        put(EntityType.VINDICATOR, 147);
        put(EntityType.EVOKER, 134);
        put(EntityType.VEX, 98);
        put(EntityType.PILLAGER, 129);
        put(EntityType.RAVAGER, 3036);
        put(EntityType.CREEPER, 229);
        put(EntityType.GHAST, 600);
        put(EntityType.PHANTOM, 106);
        put(EntityType.PIGLIN, 101);
        put(EntityType.PIGLIN_BRUTE, 782);
        put(EntityType.WARDEN, 52612);
        put(EntityType.SILVERFISH, 44);
        put(EntityType.BLAZE, 168);
        put(EntityType.WITCH, 195);
        put(EntityType.ENDERMITE, 48);
        put(EntityType.SHULKER, 164);
        put(EntityType.HOGLIN, 217);
        put(EntityType.ZOGLIN, 217);
        put(EntityType.ZOMBIFIED_PIGLIN, 121);
        put(EntityType.IRON_GOLEM, 613);
        put(EntityType.WITHER, 76826);
        put(EntityType.ENDER_DRAGON, 201186);
    }};

    @EventHandler
    public void killEntity(EntityDeathEvent event)
    {
        if(event.getEntity().getKiller() instanceof Player)
        {
            Player player = event.getEntity().getKiller();
            Entity entity = event.getEntity();
            Location deathLoc = entity.getLocation();

            String playerJob = TestPlugin.db_conn.GetJob(player);
            if(playerJob.equals(JobNameManager.HunterName))
            {
                if(!entityGold.containsKey(entity.getType()))
                {
                    return;
                }
                TestPlugin.db_conn.AddGold(player, entityGold.get(entity.getType()));
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
            if(TestPlugin.db_conn.GetJob(player).equals(JobNameManager.HunterName))
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

            if(TestPlugin.db_conn.GetJob(player).equals(JobNameManager.HunterName))
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