package com.mbach231.ritualcraft.MagicItems;

import com.mbach231.ritualcraft.Ritual.ValidRituals;
import com.mbach231.ritualcraft.Ritual.ValidRituals.RitualTypeEn;
import net.minecraft.util.org.apache.commons.lang3.text.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

public class MagicItems {

    public static ItemStack getRitualBook() {
        ItemStack book = new ItemStack(Material.WRITTEN_BOOK, 1);

        BookMeta bookMeta = (BookMeta) book.getItemMeta();

        bookMeta.setTitle("Ritual Magic Compendium");
        bookMeta.setAuthor(ChatColor.MAGIC + "mbach231");

        for (RitualTypeEn type : ValidRituals.RitualTypeEn.values()) {

            if (ValidRituals.getRitual(type) != null) {
                bookMeta.addPage(ChatColor.DARK_PURPLE + adjStr(type.toString()) + "\n\n" + ValidRituals.getRitual(type).getRitualString());
            }
        }

        book.setItemMeta(bookMeta);

        return book;
    }

    private static String adjStr(String str) {
        return WordUtils.capitalizeFully(str.replace("_", " "));
    }
}
