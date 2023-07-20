package com.habu.testplugin.event.Battle;

import com.habu.testplugin.TestPlugin;
import com.habu.testplugin.manager.JobNameManager;
import com.habu.testplugin.manager.PlayerManager;
import com.habu.testplugin.manager.PlayerScoreboardManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.boss.BarColor;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.*;

public class EnderDragonBattle implements Listener
{
    double maxHealth = 100000; // 100000
    boolean BattleStart = false;
    Boolean Phase1 = false;
    Boolean Phase2 = false;
    Boolean Phase3 = false;
    Boolean LastPhase = false;

    List<Material> bedList = new ArrayList<>(Arrays.asList(
            Material.WHITE_BED,
            Material.LIGHT_GRAY_BED,
            Material.GRAY_BED,
            Material.BLACK_BED,
            Material.BROWN_BED,
            Material.RED_BED,
            Material.ORANGE_BED,
            Material.YELLOW_BED,
            Material.LIME_BED,
            Material.GREEN_BED,
            Material.CYAN_BED,
            Material.LIGHT_BLUE_BED,
            Material.BLUE_BED,
            Material.PURPLE_BED,
            Material.MAGENTA_BED,
            Material.PINK_BED
    ));

    private static HashMap<Player, Double> combatParticipants = new HashMap<Player, Double>();
    private static double totalDamage = 0;

    private static List<Location> AlterEgoLocs = new ArrayList<Location>(Arrays.asList(
            new Location(Bukkit.getWorld("world_the_end"), 30, 85, 30),
            new Location(Bukkit.getWorld("world_the_end"), -30, 85, 30),
            new Location(Bukkit.getWorld("world_the_end"), 30, 85, -30),
            new Location(Bukkit.getWorld("world_the_end"), -30, 85, -30)
    ));

    private static List<LivingEntity> AlterEgos = new ArrayList<LivingEntity>();

    private static int AlterEgoMaxHealth = 1000;

    @EventHandler
    public void EnderDragonHit(EntityDamageByEntityEvent event)
    {
        if(!event.getEntity().getType().equals(EntityType.ENDER_DRAGON))
        {
            return;
        }

        EnderDragon entityEnderDragon = Bukkit.getWorld("world_the_end").getEnderDragonBattle().getEnderDragon();

        if(!BattleStart)
        {
            SendAllEndPlayer(ChatColor.LIGHT_PURPLE + "엔더드래곤이 포효합니다", ChatColor.RED + "플레이어의 능력치가 감소합니다");
            entityEnderDragon.getBossBar().setColor(BarColor.RED);
            entityEnderDragon.getBossBar().setTitle(ChatColor.LIGHT_PURPLE + "경계의 주인 " + ChatColor.BLACK + ":" + ChatColor.RED + " 엔더 드래곤");
            entityEnderDragon.getBossBar().setVisible(true);
            entityEnderDragon.setCustomName(ChatColor.LIGHT_PURPLE + "경계의 주인 " + ChatColor.BLACK + ":" + ChatColor.RED + " 엔더 드래곤");
            entityEnderDragon.setCustomNameVisible(true);
            entityEnderDragon.setMaxHealth(maxHealth);
            entityEnderDragon.setHealth(entityEnderDragon.getMaxHealth());
            totalDamage = 0;

            List<Player> playerList = (List<Player>) Bukkit.getOnlinePlayers();
            for(int i = 0; i < playerList.size(); i++)
            {
                Player player = playerList.get(i);
                if(player.getWorld().equals(Bukkit.getWorld("world_the_end")))
                {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 1200, 1));
                }
            }
        }

        if(event.getDamager().getType().equals(EntityType.ARROW))
        {
            Arrow arrow = (Arrow) event.getDamager();
            Player player = (Player) arrow.getShooter();
            AddContribution(player, entityEnderDragon.getLastDamage());

            Bukkit.broadcastMessage(event.getDamager() + " " + combatParticipants.get(player) + " " + totalDamage);
        }
        else if(event.getDamager().getType().equals(EntityType.PLAYER))
        {
            Player player = (Player) event.getDamager();
            AddContribution(player, entityEnderDragon.getLastDamage());

            Bukkit.broadcastMessage(event.getDamager() + " " + combatParticipants.get(player) + " " + totalDamage);
        }

        BattleStart = true;
        Phase1 = true;

        double dragonHealth = entityEnderDragon.getHealth();
        if(dragonHealth <= 0)
        {
            if(!LastPhase)
            {
                SendAllEndPlayer(ChatColor.LIGHT_PURPLE + "엔더드래곤이 마지막 발악을 시작합니다", ChatColor.RED + "");

                /*for (int i = 0; i < AlterEgos.size(); i++)
                {
                    AlterEgos.get(i).damage(AlterEgoMaxHealth);
                    Bukkit.broadcastMessage(AlterEgos.get(i) + " " + AlterEgos.get(i).getHealth());
                }*/
                LastPhase = true;
            }
        }
        else if(dragonHealth < maxHealth * (25f/100f))
        {
            if (!Phase3)
            {
                SendAllEndPlayer(ChatColor.LIGHT_PURPLE + "엔더드래곤이 생명의 위협을 느낍니다", ChatColor.RED + "");

                /*for(int i = 0; i < AlterEgoLocs.size(); i++)
                {
                    LivingEntity AlterDragon = (LivingEntity) entityEnderDragon.getWorld().spawnEntity(AlterEgoLocs.get(i), EntityType.ENDER_DRAGON);

                    AlterDragon.setMaxHealth(maxHealth);
                    AlterDragon.damage(maxHealth);

                    Bukkit.broadcastMessage(AlterDragon.getHealth() + "");

                    AlterEgos.add(AlterDragon);
                }*/

                Phase3 = true;
            }
        }
        else if(dragonHealth < maxHealth * (50f/100f))
        {
            if(!Phase2)
            {
                SendAllEndPlayer(ChatColor.LIGHT_PURPLE + "엔더드래곤이 당신을 적수로 인정합니다", ChatColor.RED + "화염구를 주의하세요");

                entityEnderDragon.setPhase(EnderDragon.Phase.LAND_ON_PORTAL);

                new BukkitRunnable()
                {
                    @Override
                    public void run()
                    {
                        Location dragonLoc = new Location(entityEnderDragon.getWorld(), entityEnderDragon.getLocation().getX(), entityEnderDragon.getLocation().getY() + 2, entityEnderDragon.getLocation().getZ());
                        Location targetLoc = new Location(Bukkit.getWorld("world_the_end"), 0, 150, 0);

                        Vector from = new Vector(dragonLoc.getX(), dragonLoc.getY(), dragonLoc.getZ());
                        Vector to = new Vector(targetLoc.getX(), targetLoc.getY(), targetLoc.getZ());
                        Vector vector = to.subtract(from).normalize();
                        DragonFireball fireball = dragonLoc.getWorld().spawn(dragonLoc, DragonFireball.class);
                        fireball.setDirection(vector);

                        Thread firstFire = new Thread(
                            new Runnable()
                            {
                                @Override
                                public void run()
                                {
                                    new BukkitRunnable()
                                    {
                                        @Override
                                        public void run()
                                        {
                                            Location fireballLoc = fireball.getLocation();
                                            entityEnderDragon.getWorld().createExplosion(fireballLoc, 3);
                                            FireBallRain();
                                            fireball.remove();
                                        }
                                    }.runTaskLater(TestPlugin.getPlugin(), 40L);
                                }
                            }
                        );
                        firstFire.run();
                        this.cancel();
                    }
                }.runTaskLater(TestPlugin.getPlugin(), 200L);

                Phase2 = true;
            }
        }
    }

    private void SendAllEndPlayer(String title, String sub)
    {
        List<Player> playerList = (List<Player>) Bukkit.getOnlinePlayers();
        for(int i = 0; i < playerList.size(); i++)
        {
            Player player = playerList.get(i);
            if(player.getWorld().equals(Bukkit.getWorld("world_the_end")))
            {
                player.sendTitle(title, sub);
            }
        }
    }

    private void FireBallRain()
    {
        Thread fireBlindly = new Thread(
            new Runnable()
            {
                int count = combatParticipants.size() * 10;
                Location fromLoc = new Location(Bukkit.getWorld("world_the_end"), 0, 115, 0);
                @Override
                public void run()
                {
                    new BukkitRunnable()
                    {
                        @Override
                        public void run()
                        {
                            if(count <= 0)
                            {
                                this.cancel();
                            }
                            else
                            {
                                List<Player> playerList = (List<Player>) Bukkit.getOnlinePlayers();
                                for(int i = 0; i < playerList.size(); i++)
                                {
                                    Player player = playerList.get(i);
                                    if(player.getWorld().equals(Bukkit.getWorld("world_the_end")))
                                    {
                                        Location playerLoc = player.getLocation();
                                        Vector from = new Vector(fromLoc.getX(), fromLoc.getY(), fromLoc.getZ());
                                        Vector to = new Vector(playerLoc.getX(), playerLoc.getY(), playerLoc.getZ());
                                        Vector vector = to.subtract(from).normalize();
                                        if(count % 2 == 0)
                                        {
                                            DragonFireball fireball = fromLoc.getWorld().spawn(fromLoc, DragonFireball.class);
                                            fireball.setDirection(vector);
                                        }
                                        else
                                        {
                                            Fireball fireball = fromLoc.getWorld().spawn(fromLoc, Fireball.class);
                                            fireball.setDirection(vector);
                                        }
                                        count--;
                                    }
                                }
                            }
                        }
                    }.runTaskTimer(TestPlugin.getPlugin(), 0, 10L);
                }
            }
        );
        fireBlindly.run();
    }

    public void AddContribution(Player player, Double damage)
    {
        if(combatParticipants.containsKey(player))
        {
            combatParticipants.put(player, combatParticipants.get(player) + damage);
        }
        else
        {
            combatParticipants.put(player, damage);
        }

        totalDamage += damage;
    }


    @EventHandler
    public void EnderDragonDeath(EntityDeathEvent event)
    {
        if(!event.getEntity().getType().equals(EntityType.ENDER_DRAGON))
        {
            return;
        }


        combatParticipants.forEach((key, value) -> {
            Player player = key;

            double damagePer = value / totalDamage;

            int playerAddGold = (int) Math.round(1000000 * damagePer);

            PlayerManager.AddGold(player, playerAddGold);
            player.sendMessage(ChatColor.GOLD + "" + playerAddGold + " 골드 획득!");
        });

        SendAllEndPlayer(ChatColor.LIGHT_PURPLE + "엔더드래곤을 처치했습니다!", ChatColor.GOLD + "1,000,000 골드가 기여도에 따라 분배됩니다!");

        BattleStart = false;
        Phase1 = false;
        Phase2 = false;
        Phase3 = false;
        LastPhase = false;
        totalDamage = 0;

        combatParticipants.clear();
    }

    @EventHandler
    public void PlaceBed(BlockPlaceEvent event)
    {
        Player player = event.getPlayer();
        Block placedBlock = event.getBlockPlaced();
        if(player.getWorld().equals(Bukkit.getWorld("world_the_end")))
        {
            if(bedList.contains(placedBlock.getType()))
            {
                event.setCancelled(true);
                player.sendMessage(ChatColor.RED + "이곳에서는 설치할 수 없습니다.");
            }
        }
    }
}