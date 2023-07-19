package com.habu.testplugin.event.Battle;

import com.habu.testplugin.TestPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.boss.BarColor;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EnderDragonBattle implements Listener
{
    double maxHealth = 10000;
    boolean BattleStart = false;
    Boolean Phase1 = false;
    Boolean Phase2 = false;
    Boolean Phase3 = false;
    Boolean LastPhase = false;
    int battlePlayer = 0;

    @EventHandler
    public void EnderDragonHit(EntityDamageEvent event)
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
            entityEnderDragon.setPhase(EnderDragon.Phase.LAND_ON_PORTAL);

            battlePlayer = 0;
            List<Player> playerList = (List<Player>) Bukkit.getOnlinePlayers();
            for(int i = 0; i < playerList.size(); i++)
            {
                Player player = playerList.get(i);
                if(player.getWorld().equals(Bukkit.getWorld("world_the_end")))
                {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 1200, 1));
                    battlePlayer+=1;
                }
            }
        }
        BattleStart = true;
        Phase1 = true;

        double dragonHealth = entityEnderDragon.getHealth();
        if(dragonHealth <= 0)
        {
            if(!LastPhase)
            {

                LastPhase = true;
            }
            BattleStart = false;
            Phase1 = false;
            Phase2 = false;
            Phase3 = false;
            LastPhase = false;
        }
        else if(dragonHealth < maxHealth * (25f/100f))
        {
            if (!Phase3)
            {

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
                        Location dragonLoc = entityEnderDragon.getLocation();
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
                int count = battlePlayer * 10;
                Location fromLoc = new Location(Bukkit.getWorld("world_the_end"), 0, 100, 0);
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


    @EventHandler
    public void EnderDragonDeath(EntityDeathEvent event)
    {
        if(!event.getEntity().getType().equals(EntityType.ENDER_DRAGON))
        {
            return;
        }
        Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + "엔더드래곤을 처치했습니다!");
    }
}
