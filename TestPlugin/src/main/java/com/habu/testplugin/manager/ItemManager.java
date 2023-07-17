package com.habu.testplugin.manager;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class ItemManager
{
    // 아이템 메타데이터 조작(인첸트 미포함)
    public static ItemStack buildItem(Material type, int amount, String displayName, String... lore)
    {
        ItemStack stack = new ItemStack(type, amount);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(displayName);
        meta.setLore(Arrays.asList(lore));
        stack.setItemMeta(meta);
        return stack;
    }

    public static ItemStack buildGeneralItem(Material type, String displayName, String... lore)
    {
        ItemStack itemStack = new ItemStack(type);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayName));
        itemMeta.setLore(Arrays.asList(lore));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack buildGeneralItem(Material type, String displayName, Enchantment enchantment, int enchantLevel, String... lore)
    {
        ItemStack itemStack = new ItemStack(type);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayName));
        itemMeta.addEnchant(enchantment, enchantLevel, true);
        itemMeta.setLore(Arrays.asList(ChatColor.translateAlternateColorCodes('&', lore[0])));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static final ItemStack gui_GrayGlassPane = buildItem(Material.GRAY_STAINED_GLASS_PANE, 1, ChatColor.GRAY + "", "");

    // 스폰알 뽑기
    public static final ItemStack gui_SpawnEgg = buildGeneralItem(Material.PIG_SPAWN_EGG, ChatColor.RED + "몹 스폰 알 랜덤 뽑기", Enchantment.DURABILITY, 10, ChatColor.WHITE + "클릭하면 랜덤 뽑기를 시작합니다.");

    // 잡화상점
    public static final ItemStack item_AreaProtect = buildGeneralItem(Material.BRICK, "&b&l보호 지역 &7&l생성 아이템", Enchantment.DURABILITY, 10, "&9&l[ 우클릭 블럭 ] &f&l해당 블럭을 기준으로 보호 지역이 생성됩니다.");
    public static final ItemStack gui_AreaProtect = buildGeneralItem(Material.BRICK, "&b&l보호 지역 &7&l생성 아이템", Enchantment.DURABILITY, 10, "&9&l[ 우클릭 블럭 ] &f&l해당 블럭을 기준으로 보호 지역이 생성됩니다.");
    public static final ItemStack item_DoorLock = buildGeneralItem(Material.TRIPWIRE_HOOK,"&b&l문 &7&l잠금장치", "§b§l[ 문에 우클릭 ] §f§l우클릭 한 문이 잠가집니다.");
    public static final ItemStack gui_DoorLock = buildGeneralItem(Material.TRIPWIRE_HOOK,"&b&l문 &7&l잠금장치", "§b§l[ 문에 우클릭 ] §f§l우클릭 한 문이 잠가집니다.");
    public static final ItemStack item_InventorySave = buildGeneralItem(Material.NETHER_STAR, ChatColor.AQUA + "인벤토리 보호 쿠폰", Enchantment.DURABILITY, 10,  ChatColor.WHITE + "인벤토리에 소지 하고 있으면 죽어도 아이템을 잃지 않는다.");
    public static final ItemStack gui_InventorySave = buildItem(Material.NETHER_STAR, 1, ChatColor.AQUA + "인벤토리 보호 쿠폰",  ChatColor.WHITE + "인벤토리에 소지 하고 있으면 죽어도 아이템을 잃지 않는다.");
    public static final ItemStack item_JobInitializer = buildGeneralItem(Material.PAPER, ChatColor.AQUA + "직업 초기화", Enchantment.DURABILITY, 10, ChatColor.WHITE + "손에 들고 우클릭하면 백수가 될 수 있다.");
    public static final ItemStack gui_JobInitializer = buildItem(Material.PAPER, 1, ChatColor.AQUA + "직업 초기화", ChatColor.WHITE + "손에 들고 우클릭하면 백수가 될 수 있다.");
    public static final ItemStack item_ReturnVillage = buildGeneralItem(Material.BOOK, ChatColor.AQUA + "마을 귀환서", Enchantment.DURABILITY, 10, ChatColor.WHITE + "사용하면 5초뒤 마을로 이동된다.");
    public static final ItemStack gui_ReturnVillage = buildItem(Material.BOOK, 1, ChatColor.AQUA + "마을 귀환서", ChatColor.WHITE + "사용하면 5초뒤 마을로 이동된다.");

    public static final ItemStack item_WhiteListAdder = buildGeneralItem(Material.ENCHANTED_BOOK, ChatColor.AQUA + "서버 초대 쿠폰", Enchantment.DURABILITY, 10, ChatColor.WHITE + "모루를 통해 쿠폰의 이름을 초대하고자 하는 사람의 닉네임으로 바꾼 후 우클릭하면 초대할 수 있다.");
    public static final ItemStack gui_WhiteListAdder = buildItem(Material.ENCHANTED_BOOK, 1, ChatColor.AQUA + "서버 초대 쿠폰", ChatColor.WHITE + "모루를 통해 쿠폰의 이름을 초대하고자 하는 사람의 닉네임으로 바꾼 후 우클릭하면 초대할 수 있다.");

    // 낚시꾼
    public static final ItemStack gui_FisherSelecter = buildItem(Material.PAPER, 1, ChatColor.AQUA + "낚시 자격증", ChatColor.WHITE + "손에 들고 우클릭하면 낚시꾼의 자격을 얻을 수 있다.", ChatColor.AQUA + "[낚시꾼]", ChatColor.WHITE + "크기 별 물고기 획득 가능", ChatColor.WHITE + "일정 확률로 낚시 주머니 획득 가능", ChatColor.WHITE + "바다 상점 이용 가능", ChatColor.WHITE + "판매가: ");
    public static final ItemStack item_FisherSelecter = buildItem(Material.PAPER, 1, ChatColor.AQUA + "낚시 자격증", ChatColor.WHITE + "손에 들고 우클릭하면 낚시꾼의 자격을 얻을 수 있다.");

    public static final ItemStack gui_FisherCod = buildItem(Material.COD, 1, ChatColor.WHITE + "대구", ChatColor.WHITE + "판매가: ");
    public static final ItemStack gui_FisherSalmon = buildItem(Material.SALMON, 1, ChatColor.WHITE + "연어", ChatColor.WHITE + "판매가: ");
    public static final ItemStack gui_FisherTropical_Fish = buildItem(Material.TROPICAL_FISH, 1, ChatColor.WHITE + "열대어", ChatColor.WHITE + "판매가: ");
    public static final ItemStack gui_FisherPufferfish = buildItem(Material.PUFFERFISH, 1, ChatColor.WHITE + "복어", ChatColor.WHITE + "판매가: ");

    // 광부
    public static final ItemStack gui_MinerSelecter = buildItem(Material.PAPER, 1, ChatColor.YELLOW + "채광 자격증", ChatColor.WHITE + "손에 들고 우클릭하면 광부의 자격을 얻을 수 있다.", ChatColor.YELLOW + "[광부]", ChatColor.WHITE + "등급 별 광물 획득 가능", ChatColor.WHITE + "광물 상점 이용 가능", ChatColor.WHITE + "판매가: ");
    public static final ItemStack item_MinerSelecter = buildItem(Material.PAPER, 1, ChatColor.YELLOW + "채광 자격증", ChatColor.WHITE + "손에 들고 우클릭하면 광부의 자격을 얻을 수 있다.");

    public static final ItemStack gui_MinerCoal = buildItem(Material.COAL, 1, ChatColor.WHITE + "석탄", ChatColor.WHITE + "판매가: ");
    public static final ItemStack gui_MinerCopper = buildItem(Material.COPPER_INGOT, 1, ChatColor.WHITE + "구리", ChatColor.WHITE + "판매가: ");
    public static final ItemStack gui_MinerIron = buildItem(Material.IRON_INGOT, 1, ChatColor.WHITE + "철", ChatColor.WHITE + "판매가: ");
    public static final ItemStack gui_MinerGold = buildItem(Material.GOLD_INGOT, 1, ChatColor.WHITE + "금", ChatColor.WHITE + "판매가: ");
    public static final ItemStack gui_MinerLapis_Lazuli = buildItem(Material.LAPIS_LAZULI, 1, ChatColor.WHITE + "청금석", ChatColor.WHITE + "판매가: ");
    public static final ItemStack gui_MinerRedstone = buildItem(Material.REDSTONE, 1, ChatColor.WHITE + "레드스톤", ChatColor.WHITE + "판매가: ");
    public static final ItemStack gui_MinerDiamond = buildItem(Material.DIAMOND, 1, ChatColor.WHITE + "다이아몬드", ChatColor.WHITE + "판매가: ");
    public static final ItemStack gui_MinerEmerald = buildItem(Material.EMERALD, 1, ChatColor.WHITE + "에메랄드", ChatColor.WHITE + "판매가: ");
    public static final ItemStack gui_MinerQuartz = buildItem(Material.QUARTZ, 1, ChatColor.WHITE + "석영", ChatColor.WHITE + "판매가: ");
    public static final ItemStack gui_MinerAmethyst_Shard = buildItem(Material.AMETHYST_SHARD, 1, ChatColor.WHITE + "자수정", ChatColor.WHITE + "판매가: ");
    public static final ItemStack gui_MinerNetherite = buildItem(Material.NETHERITE_INGOT, 1, ChatColor.WHITE + "네더라이트", ChatColor.WHITE + "판매가: ");

    // 농부
    public static final ItemStack gui_FarmerSelecter = buildItem(Material.PAPER, 1, ChatColor.GREEN + "농사 자격증", ChatColor.WHITE + "손에 들고 우클릭하면 농부의 자격을 얻을 수 있다.", ChatColor.GREEN + "[농부]", ChatColor.WHITE + "작물 수확시 1 ~ 7개 추가 획득", ChatColor.WHITE + "작물 상점 이용 가능", ChatColor.WHITE + "판매가: ");
    public static final ItemStack item_FarmerSelecter = buildItem(Material.PAPER, 1, ChatColor.GREEN + "농사 자격증", ChatColor.WHITE + "손에 들고 우클릭하면 농부의 자격을 얻을 수 있다.");
    
    public static final ItemStack gui_FarmerWheat = buildItem(Material.WHEAT, 1, ChatColor.WHITE + "밀", ChatColor.WHITE + "판매가: ");
    public static final ItemStack gui_FarmerCarrot = buildItem(Material.CARROT, 1, ChatColor.WHITE + "당근", ChatColor.WHITE + "판매가: ");
    public static final ItemStack gui_FarmerPotato = buildItem(Material.POTATO, 1, ChatColor.WHITE + "감자", ChatColor.WHITE + "판매가: ");
    public static final ItemStack gui_FarmerBeetroot = buildItem(Material.BEETROOT, 1, ChatColor.WHITE + "비트", ChatColor.WHITE + "판매가: ");
    public static final ItemStack gui_FarmerMelon = buildItem(Material.MELON_SLICE, 1, ChatColor.WHITE + "수박", ChatColor.WHITE + "판매가: ");
    public static final ItemStack gui_FarmerPumpkin = buildItem(Material.PUMPKIN, 1, ChatColor.WHITE + "호박", ChatColor.WHITE + "판매가: ");
    public static final ItemStack gui_FarmerSweet_Berries = buildItem(Material.SWEET_BERRIES, 1, ChatColor.WHITE + "달콤한 열매", ChatColor.WHITE + "판매가: ");
    public static final ItemStack gui_FarmerCoCoa_Beans = buildItem(Material.COCOA_BEANS, 1, ChatColor.WHITE + "코코아 콩", ChatColor.WHITE + "판매가: ");
    public static final ItemStack gui_FarmerNether_Wart = buildItem(Material.NETHER_WART, 1, ChatColor.WHITE + "네더 사마귀", ChatColor.WHITE + "판매가: ");


    // 나무꾼
    public static final ItemStack gui_WoodCutterSelecter = buildItem(Material.PAPER, 1, ChatColor.GOLD + "벌목 자격증", ChatColor.WHITE + "손에 들고 우클릭하면 나무꾼의 자격을 얻을 수 있다.", ChatColor.GOLD + "[나무꾼]", ChatColor.WHITE + "나무를 캘 시 확률적으로 열매 주머니 획득 가능", ChatColor.WHITE + "나무 상점 이용 가능", ChatColor.WHITE + "판매가: ");
    public static final ItemStack item_WoodCutterSelecter = buildItem(Material.PAPER, 1, ChatColor.GOLD + "벌목 자격증", ChatColor.WHITE + "손에 들고 우클릭하면 나무꾼의 자격을 얻을 수 있다.");
    
    public static final ItemStack gui_WoodCutterOak = buildItem(Material.OAK_LOG, 1, ChatColor.WHITE + "참나무 원목", ChatColor.WHITE + "판매가: ");
    public static final ItemStack gui_WoodCutterSpruce = buildItem(Material.SPRUCE_LOG, 1, ChatColor.WHITE + "가문비나무 원목", ChatColor.WHITE + "판매가: ");
    public static final ItemStack gui_WoodCutterBirch = buildItem(Material.BIRCH_LOG, 1, ChatColor.WHITE + "자작나무 원목", ChatColor.WHITE + "판매가: ");
    public static final ItemStack gui_WoodCutterJungle = buildItem(Material.JUNGLE_LOG, 1, ChatColor.WHITE + "정글나무 원목", ChatColor.WHITE + "판매가: ");
    public static final ItemStack gui_WoodCutterAcacia = buildItem(Material.ACACIA_LOG, 1, ChatColor.WHITE + "아카시아나무 원목", ChatColor.WHITE + "판매가: ");
    public static final ItemStack gui_WoodCutterDark_Oak = buildItem(Material.DARK_OAK_LOG, 1, ChatColor.WHITE + "짙은 참나무 원목", ChatColor.WHITE + "판매가: ");
    public static final ItemStack gui_WoodCutterMangrove = buildItem(Material.MANGROVE_LOG, 1, ChatColor.WHITE + "맹그로브나무 원목", ChatColor.WHITE + "판매가: ");
    public static final ItemStack gui_WoodCutterCrimson_Stem = buildItem(Material.CRIMSON_STEM, 1, ChatColor.WHITE + "진홍빛 자루", ChatColor.WHITE + "판매가: ");
    public static final ItemStack gui_WoodCutterMushroom_Stem = buildItem(Material.MUSHROOM_STEM, 1, ChatColor.WHITE + "버섯 자루", ChatColor.WHITE + "판매가: ");
    public static final ItemStack gui_WoodCutterWarped_Stem = buildItem(Material.WARPED_STEM, 1, ChatColor.WHITE + "뒤틀린 자루", ChatColor.WHITE + "판매가: ");
    public static final ItemStack gui_WoodCutterApple = buildItem(Material.APPLE, 1, ChatColor.WHITE + "사과", ChatColor.WHITE + "판매가: ");
    public static final ItemStack gui_WoodCutterGolden_Apple = buildItem(Material.GOLDEN_APPLE, 1, ChatColor.WHITE + "황금 사과", ChatColor.WHITE + "판매가: ");
    public static final ItemStack gui_WoodCutterEnchanted_Golden_Apple = buildItem(Material.ENCHANTED_GOLDEN_APPLE, 1, ChatColor.WHITE + "마법이 부여된 황금 사과", ChatColor.WHITE + "판매가: ");

    // 사냥꾼
    public static final ItemStack gui_HunterSelecter = buildItem(Material.PAPER, 1, ChatColor.GOLD + "사냥 자격증", ChatColor.WHITE + "손에 들고 우클릭하면 사냥꾼의 자격을 얻을 수 있다.", ChatColor.DARK_GREEN + "[사냥꾼]", ChatColor.WHITE + "몬스터를 죽이면 종류에 따라 골드 획득", ChatColor.WHITE + "몬스터 처치시 화살 1개 획득", ChatColor.WHITE + "몬스터 처치시 확률적으로 효과 부여 화살 드랍", ChatColor.WHITE + "활 데미지 강화", ChatColor.LIGHT_PURPLE + "사냥꾼의 화살은 엔더맨도 피할 수 없습니다...", ChatColor.WHITE + "판매가: ");
    public static final ItemStack item_HunterSelecter = buildItem(Material.PAPER, 1, ChatColor.GOLD + "사냥 자격증", ChatColor.WHITE + "손에 들고 우클릭하면 사냥꾼의 자격을 얻을 수 있다.");
}