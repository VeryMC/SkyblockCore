package fr.farmeurimmo.verymc.shopgui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import fr.farmeurimmo.verymc.core.Main;
import fr.farmeurimmo.verymc.eco.EcoAccountsManager;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;

public class GenShopPage {
	
	public static HashMap <ItemStack, Integer> blocspage1 = new HashMap < > ();
	public static HashMap <ItemStack, Integer> blocspage2 = new HashMap < > ();
	public static HashMap <String, Integer> maxpage = new HashMap < > ();
	
	
	public static HashMap <String, Integer > numpages = new HashMap < > ();
	public static ArrayList<Integer> slotstofill = new ArrayList<Integer>();
	public static HashMap <ItemStack, Integer> toshowtemp = new HashMap < > ();
	
	public static void GenenerateShopPageStartup(String page) {
		slotstofill.clear();
		toshowtemp.clear();
        slotstofill.addAll(Arrays.asList(10,11,12,13,14,15,16,19,20,21,22,23,24,25,28,29,30,31,32,33,34,37,38,39,40,41,42,43));
        
        int currentpage = 1;
			int numberofitems = 0;
			for(String aa : Main.instance1.getConfig().getConfigurationSection("Shops."+page).getKeys(false)) {
				ItemStack custom1 = new ItemStack(Material.valueOf(Main.instance1.getConfig().getString("Shops."+page+"."+aa+".material")), 1);
				int slot = GetNextSlot();
				slotstofill.removeAll(Arrays.asList(slot));
				toshowtemp.put(custom1,slot);
				if(slot == 0) {
				break;
				}
				if(numberofitems >= 29) {
					if(currentpage == 1) {
						for(Entry<ItemStack, Integer> cc : toshowtemp.entrySet()) {
				        	blocspage1.put(cc.getKey(), cc.getValue());
				        }
						}
					else if(currentpage == 2) {
						for(Entry<ItemStack, Integer> cc : toshowtemp.entrySet()) {
				        	blocspage2.put(cc.getKey(), cc.getValue());
				        }
					}
					currentpage+=1;
					numberofitems = 0;
					slotstofill.clear();
					slotstofill.addAll(Arrays.asList(10,11,12,13,14,15,16,  19,20,21,22,23,24,25,  28,29,30,31,32,33,34,  37,38,39,40,41,42,43));
					toshowtemp.clear();
				}
				numberofitems+=1;
			}   
		slotstofill.clear();
		toshowtemp.clear();
	}
	@SuppressWarnings("deprecation")
	public static void OpenPreGenPage(Player player, String page, int pagenum) {
		User user = LuckPermsProvider.get().getUserManager().getUser(player.getName());
		String Grade = user.getCachedData().getMetaData().getPrefix().replace("&", "§");
		
		int numberofpage = maxpage.get(page);
        
        Inventory inv = Bukkit.createInventory(null, 54, "§6" + page + " "+pagenum+"/"+numberofpage);
        
        if(pagenum == 1) {
        for(Entry<ItemStack, Integer> cc : blocspage1.entrySet()) {
        	inv.setItem(cc.getValue(), cc.getKey());
        }
        } else if(pagenum == 2) {
        	for(Entry<ItemStack, Integer> cc : blocspage2.entrySet()) {
            	inv.setItem(cc.getValue(), cc.getKey());
            }
        }
        
        if(numberofpage >= 2 && pagenum < numberofpage) {
            ItemStack custom7 = new ItemStack(Material.ARROW, 1);
    		ItemMeta customg = custom7.getItemMeta();
    		customg.setDisplayName("§6Page suivante");
    		custom7.setItemMeta(customg);
    		inv.setItem(53, custom7);
            }
    		
    		if(pagenum >= 2) {
    		 ItemStack custom6 = new ItemStack(Material.ARROW, 1);
    			ItemMeta customh = custom6.getItemMeta();
    			customh.setDisplayName("§6Page précédente");
    			custom6.setItemMeta(customh);
    			inv.setItem(45, custom6);
    		}
    		
    		ItemStack custom2 = new ItemStack(Material.PLAYER_HEAD, 1);
    		SkullMeta customb = (SkullMeta) custom2.getItemMeta();
    		customb.setOwner(player.getName());
    		customb.setDisplayName("§7" + player.getName());
    		customb.setLore(Arrays.asList("§7Grade: " + Grade, "§7Argent: " + EcoAccountsManager.Moneys.get(player.getName())));
    		custom2.setItemMeta(customb);
    		inv.setItem(49, custom2);
            
    		player.openInventory(inv);
    		numpages.put(player.getName(), pagenum);
	}
	public static int GetNextSlot() {
		int slottoreturn = 0;
		if(slotstofill.size() == 0) {
			return slottoreturn;
		}
		while (slottoreturn != slotstofill.get(0)){
			slottoreturn+=1;
		}
		if(slotstofill.contains(slottoreturn)) {
		slotstofill.removeAll(Arrays.asList(slottoreturn));
		}
		return slottoreturn;
	}

	public static void GetNumberOfPage() {
		int items = 0;
        for(@SuppressWarnings("unused") String aa : Main.instance1.getConfig().getConfigurationSection("Shops.Blocs").getKeys(false)) {
        	items +=1;
        }
        int max = items/28;
        max+=1;
        maxpage.put("Blocs", max);
	}
}
