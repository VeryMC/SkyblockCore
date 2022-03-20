package main.java.fr.verymc.minions;

import main.java.fr.verymc.core.Main;
import main.java.fr.verymc.utils.PreGenItems;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.EulerAngle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

public class MinionManager {

    public static MinionManager instance;
    public static Integer level1 = 50000;
    public static Integer level2 = 75000;
    public static Integer level3 = 100000;
    public static Integer level4 = 150000;
    public static Integer level5 = 250000;
    public static Integer level6 = 500000;
    public ArrayList<Minion> minions = new ArrayList<>();
    public ArrayList<BlockFace> faceBloc = new ArrayList<>();
    public HashMap<Player, Minion> openedMinion = new HashMap<>();


    public MinionManager() {
        instance = this;
        readForMinions();
        faceBloc.addAll(Arrays.asList(BlockFace.EAST, BlockFace.NORTH, BlockFace.SOUTH, BlockFace.WEST));
    }

    public void readForMinions() {
        if (Main.instance.getDataMinion().getConfigurationSection("Minions.mineur") == null) {
            return;
        }
        for (String id : Main.instance.getDataMinion().getConfigurationSection("Minions.mineur").getKeys(false)) {
            Long idMinion = Long.parseLong(id);
            String ownerS = Main.instance.getDataMinion().getString("Minions.mineur." + id + ".ownerS");
            UUID ownerUUID = UUID.fromString(Main.instance.getDataMinion().getString("Minions.mineur." + id + ".ownerUUID"));
            Integer levelInt = Main.instance.getDataMinion().getInt("Minions.mineur." + id + ".levelint");
            Location blocLoc = Main.instance.getDataMinion().getLocation("Minions.mineur." + id + ".blocLoc");
            MinionType minionType = MinionType.valueOf(Main.instance.getDataMinion().getString("Minions.mineur." + id + ".minionType"));
            BlockFace blockFace = BlockFace.valueOf(Main.instance.getDataMinion().getString("Minions.mineur." + id + ".blocFace"));
            Block blocChest = null;
            Boolean isChestLinked = Main.instance.getDataMinion().getBoolean("Minions.mineur." + id + ".isChestLinked");
            if (isChestLinked) {
                blocChest = Main.instance.getDataMinion().getLocation("Minions.mineur." + id + ".blocChest").getBlock();
            }

            minions.add(new Minion(idMinion, ownerS, ownerUUID, levelInt, blocLoc, minionType
                    , blockFace, isChestLinked, blocChest));
        }
    }

    public void giveMinionItem(Player player, String type) {
        ItemStack custom1 = new ItemStack(Material.DRAGON_BREATH, 1);
        custom1.setUnbreakable(true);
        ItemMeta meta1 = custom1.getItemMeta();
        meta1.setDisplayName("§6Minion " + type);
        meta1.setLore(Arrays.asList("§6Niveau §e0"));
        custom1.setItemMeta(meta1);

        player.getInventory().addItem(custom1);
    }

    public void giveMinionItemForExistingMinion(Player player, Minion minion) {
        ItemStack custom1 = new ItemStack(Material.DRAGON_BREATH, 1);
        custom1.setUnbreakable(true);
        ItemMeta meta1 = custom1.getItemMeta();
        meta1.setDisplayName("§6Minion " + minion.getMinionType().getName(minion.getMinionType()));
        meta1.setLore(Arrays.asList("§6Niveau §e" + minion.getLevelInt()));
        custom1.setItemMeta(meta1);

        player.getInventory().addItem(custom1);
    }

    public Boolean getBeforeBooleanUpgrade(Integer level, Minion minion) {
        if (level > minion.getLevelInt()) {
            return false;
        } else {
            return true;
        }
    }

    public Integer getMinerDelay(Integer level) {
        Integer toReturn = 12;
        if (level == 0) toReturn = 10;
        if (level == 1) toReturn = 9;
        if (level == 2) toReturn = 8;
        if (level == 3) toReturn = 7;
        if (level == 4) toReturn = 6;
        if (level == 5) toReturn = 5;
        if (level == 6) toReturn = 4;
        return toReturn;
    }

    public Integer getNextUpgradeCost(Integer level, Integer currentLevel) {
        Integer toReturn = 0;
        if (currentLevel + 1 == level) {
            if (level == 1) toReturn = level1;
            if (level == 2) toReturn = level2;
            if (level == 3) toReturn = level3;
            if (level == 4) toReturn = level4;
            if (level == 5) toReturn = level5;
            if (level == 6) toReturn = level6;
        } else {
            for (int i = 1; i <= level; i++) {
                currentLevel += 1;
                if (currentLevel == 1) toReturn += level1;
                if (currentLevel == 2) toReturn += level2;
                if (currentLevel == 3) toReturn += level3;
                if (currentLevel == 4) toReturn += level4;
                if (currentLevel == 5) toReturn += level5;
                if (currentLevel == 6) toReturn += level6;
            }
        }
        return toReturn;
    }

    public void addMinion(Player player, Location blocLoc, MinionType minionType, BlockFace blockFace, Integer levelInt) {
        Long id = System.currentTimeMillis();
        blocLoc.add(0.5, 1, 0.5);
        blocLoc.setDirection(player.getLocation().getDirection());
        Main.instance.getDataMinion().set("Minions.mineur." + id + ".ownerS", player.getName());
        Main.instance.getDataMinion().set("Minions.mineur." + id + ".ownerUUID", player.getUniqueId().toString());
        Main.instance.getDataMinion().set("Minions.mineur." + id + ".levelint", levelInt);
        Main.instance.getDataMinion().set("Minions.mineur." + id + ".blocLoc", blocLoc);
        Main.instance.getDataMinion().set("Minions.mineur." + id + ".minionType", minionType.getName(minionType));
        Main.instance.getDataMinion().set("Minions.mineur." + id + ".blocFace", blockFace.toString());
        Main.instance.getDataMinion().set("Minions.mineur." + id + ".isChestLinked", false);
        Main.instance.getDataMinion().set("Minions.mineur." + id + ".blocChest", null);
        Main.instance.saveDataMinions();

        Minion minion = new Minion(id, player.getName(), player.getUniqueId(), levelInt, blocLoc, minionType,
                blockFace, false, null);

        final ArmorStand stand = (ArmorStand) minion.getBlocLocation().getWorld().spawnEntity(minion.getBlocLocation(), EntityType.ARMOR_STAND);
        final EntityEquipment equipment = stand.getEquipment();
        stand.setMetadata("minion", new FixedMetadataValue(Main.instance, true));
        stand.setVisible(true);
        stand.setCustomName("§eMinion " + minionType.getName(minionType));
        stand.setCustomNameVisible(true);
        stand.setGravity(false);
        stand.setArms(true);
        stand.setSmall(true);
        stand.setBasePlate(false);
        stand.setInvulnerable(true);
        final ItemStack chestPlate = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
        final LeatherArmorMeta lam3 = (LeatherArmorMeta) chestPlate.getItemMeta();
        lam3.setColor(Color.fromRGB(249, 128, 29));
        chestPlate.setItemMeta((ItemMeta) lam3);
        equipment.setChestplate(chestPlate);
        final ItemStack pants = new ItemStack(Material.LEATHER_LEGGINGS, 1);
        final LeatherArmorMeta lam4 = (LeatherArmorMeta) pants.getItemMeta();
        lam4.setColor(Color.fromRGB(249, 128, 29));
        pants.setItemMeta((ItemMeta) lam4);
        equipment.setLeggings(pants);
        final ItemStack boots = new ItemStack(Material.LEATHER_BOOTS, 1);
        final LeatherArmorMeta lam5 = (LeatherArmorMeta) boots.getItemMeta();
        lam5.setColor(Color.fromRGB(249, 128, 29));
        boots.setItemMeta((ItemMeta) lam5);
        equipment.setBoots(boots);
        equipment.setItemInMainHand(new ItemStack(Material.DIAMOND_PICKAXE));

        equipment.setHelmet(PreGenItems.getHead(player));

        stand.setRightLegPose(new EulerAngle(0.0, 0.0, -50.0));
        stand.setLeftLegPose(new EulerAngle(0.0, 0.0, 50.0));
        stand.setRightArmPose(new EulerAngle(206.0, 0.0, 0.0));

        minions.add(minion);

        player.sendMessage("§6§lMinions §8» §fMinion §aplacé§f, pour qu'il fonctionne, il faut lier un coffre au minion. " +
                "(Aller dans l'inventaire du minion puis cliquer le coffre)");
    }

    public void removeMinion(Minion minion) {

        for (Entity entity : Bukkit.getWorld(minion.getBlocLocation().getWorld().getName()).getEntities()) {
            if (!(entity instanceof ArmorStand)) continue;
            if (entity.getLocation().getBlock().getLocation().equals(minion.getBlocLocation().getBlock().getLocation())
                    && !entity.hasGravity()) {
                entity.remove();
            }
        }

        Main.instance.getDataMinion().set("Minions.mineur." + minion.getID(), null);
        Main.instance.saveDataMinions();

        minions.remove(minion);
    }

}
