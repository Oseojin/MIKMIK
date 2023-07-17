package com.habu.testplugin.event.shop.randomshop;

import com.habu.testplugin.TestPlugin;
import com.habu.testplugin.manager.ItemManager;
import com.habu.testplugin.manager.PlayerManager;
import com.habu.testplugin.shop.GeneralShop;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class RandomSpawnEggShopClickEvent implements Listener
{
    Random random = new Random();
    static HashMap<UUID, Integer> countMap = new HashMap<UUID, Integer>();
    private static List<Material> spawneggs = new ArrayList<Material>(Arrays.asList(
            Material.ALLAY_SPAWN_EGG
            , Material.AXOLOTL_SPAWN_EGG
            , Material.BAT_SPAWN_EGG
            , Material.BEE_SPAWN_EGG
            , Material.BLAZE_SPAWN_EGG
            , Material.CAT_SPAWN_EGG
            , Material.CAVE_SPIDER_SPAWN_EGG
            , Material.CHICKEN_SPAWN_EGG
            , Material.COD_SPAWN_EGG
            , Material.COW_SPAWN_EGG
            , Material.CREEPER_SPAWN_EGG
            , Material.DOLPHIN_SPAWN_EGG
            , Material.DONKEY_SPAWN_EGG
            , Material.DROWNED_SPAWN_EGG
            , Material.ELDER_GUARDIAN_SPAWN_EGG
            , Material.ENDERMAN_SPAWN_EGG
            , Material.ENDERMITE_SPAWN_EGG
            , Material.EVOKER_SPAWN_EGG
            , Material.FOX_SPAWN_EGG
            , Material.FROG_SPAWN_EGG
            , Material.GHAST_SPAWN_EGG
            , Material.GLOW_SQUID_SPAWN_EGG
            , Material.GOAT_SPAWN_EGG
            , Material.GUARDIAN_SPAWN_EGG
            , Material.HOGLIN_SPAWN_EGG
            , Material.HORSE_SPAWN_EGG
            , Material.HUSK_SPAWN_EGG
            , Material.IRON_GOLEM_SPAWN_EGG
            , Material.LLAMA_SPAWN_EGG
            , Material.MAGMA_CUBE_SPAWN_EGG
            , Material.MOOSHROOM_SPAWN_EGG
            , Material.MULE_SPAWN_EGG
            , Material.OCELOT_SPAWN_EGG
            , Material.PANDA_SPAWN_EGG
            , Material.PARROT_SPAWN_EGG
            , Material.PHANTOM_SPAWN_EGG
            , Material.PIG_SPAWN_EGG
            , Material.PIGLIN_SPAWN_EGG
            , Material.PIGLIN_BRUTE_SPAWN_EGG
            , Material.PILLAGER_SPAWN_EGG
            , Material.POLAR_BEAR_SPAWN_EGG
            , Material.PUFFERFISH_SPAWN_EGG
            , Material.RABBIT_SPAWN_EGG
            , Material.RAVAGER_SPAWN_EGG
            , Material.SALMON_SPAWN_EGG
            , Material.SHEEP_SPAWN_EGG
            , Material.SHULKER_SPAWN_EGG
            , Material.SILVERFISH_SPAWN_EGG
            , Material.SKELETON_SPAWN_EGG
            , Material.SKELETON_HORSE_SPAWN_EGG
            , Material.SLIME_SPAWN_EGG
            , Material.SNOW_GOLEM_SPAWN_EGG
            , Material.SPIDER_SPAWN_EGG
            , Material.SQUID_SPAWN_EGG
            , Material.STRAY_SPAWN_EGG
            , Material.STRIDER_SPAWN_EGG
            , Material.TADPOLE_SPAWN_EGG
            , Material.TRADER_LLAMA_SPAWN_EGG
            , Material.TROPICAL_FISH_SPAWN_EGG
            , Material.TURTLE_SPAWN_EGG
            , Material.VEX_SPAWN_EGG
            , Material.VILLAGER_SPAWN_EGG
            , Material.VINDICATOR_SPAWN_EGG
            , Material.WANDERING_TRADER_SPAWN_EGG
            , Material.WARDEN_SPAWN_EGG
            , Material.WITCH_SPAWN_EGG
            , Material.WITHER_SKELETON_SPAWN_EGG
            , Material.WOLF_SPAWN_EGG
            , Material.ZOGLIN_SPAWN_EGG
            , Material.ZOMBIE_SPAWN_EGG
            , Material.ZOMBIE_HORSE_SPAWN_EGG
            , Material.ZOMBIE_VILLAGER_SPAWN_EGG
            , Material.ZOMBIFIED_PIGLIN_SPAWN_EGG));
    @EventHandler
    public void onClickShop(InventoryClickEvent event)
    {
        Player player = (Player) event.getWhoClicked();
        UUID uuid = player.getUniqueId();
        Inventory inv = event.getClickedInventory();
        if(inv == null || inv.equals(player.getInventory()))
            return;

        String title = ChatColor.stripColor(event.getView().getTitle());

        if(title.equalsIgnoreCase("RandomSpawnEggShop"))
        {
            event.setCancelled(true);
            Inventory playerInv = player.getInventory();
            ItemStack clickedItem = event.getCurrentItem();
            if(clickedItem == null)
                return;
            if (clickedItem.isSimilar(ItemManager.gui_SpawnEgg))
            {
                if(countMap.containsKey(uuid))
                {
                    return;
                }
                else
                {
                    countMap.put(uuid, 60);
                }
                Material material = clickedItem.getType();
                int price = GeneralShop.GetPrice(material);
                if(playerInv.firstEmpty() == -1)
                {
                    player.sendMessage("인벤토리 공간을 최소 1칸 비워주세요.");
                }
                else
                {
                    if(PlayerManager.GetGold(player) < price)
                    {
                        player.sendMessage("돈이 부족합니다.");
                        return;
                    }

                    int select = random.nextInt(spawneggs.size());

                    new BukkitRunnable()
                    {
                        @Override
                        public void run()
                        {
                            if(countMap.get(uuid) != -1)
                            {
                                if(countMap.get(uuid) != 0)
                                {
                                    int randomInt = random.nextInt(spawneggs.size());
                                    ItemStack randomSpawnEgg = new ItemStack(spawneggs.get(randomInt), 1);
                                    inv.setItem(13, randomSpawnEgg);
                                    countMap.put(uuid, countMap.get(uuid)-1);
                                }
                                else
                                {
                                    player.sendMessage(select+ "");

                                    ItemStack randomSpawnEgg = new ItemStack(spawneggs.get(select), 1);
                                    inv.setItem(13, randomSpawnEgg);
                                    PurchaseItem(player, spawneggs.get(select), uuid, price);
                                    countMap.remove(uuid);
                                    this.cancel();
                                }
                            }
                            else
                            {
                                countMap.remove(uuid);
                                this.cancel();
                            }
                        }
                    }.runTaskTimer(TestPlugin.getPlugin(), 0, 1L);

                    new BukkitRunnable()
                    {
                        @Override
                        public void run()
                        {
                            inv.setItem(13, ItemManager.gui_SpawnEgg);
                        }
                    }.runTaskLater(TestPlugin.getPlugin(), 100L);
                }
            }
        }
    }

    private void PurchaseItem(Player player, Material material, UUID uuid, Integer price)
    {
        ItemStack itemStack = new ItemStack(material, 1);
        player.getInventory().addItem(itemStack);
        PlayerManager.UseGold(player, price);
        player.sendMessage(itemStack.getItemMeta().getDisplayName() + ChatColor.WHITE + "를 " + price + " 골드에 구매하였습니다.");
    }
}
