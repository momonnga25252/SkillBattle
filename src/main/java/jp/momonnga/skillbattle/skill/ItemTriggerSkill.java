package jp.momonnga.skillbattle.skill;

import jp.momonnga.skillbattle.SkillBattle;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public abstract class ItemTriggerSkill extends Skill {

    public static final NamespacedKey SKILLS_KEY = new NamespacedKey(SkillBattle.getPlugin(),"skill_name");

    public ItemStack createTriggerItem() {
        ItemStack trigger = new ItemStack(getMaterial());
        ItemMeta meta = getItemMeta(trigger.getItemMeta());
        meta.getPersistentDataContainer().set(SKILLS_KEY, PersistentDataType.STRING,getSkillName());
        trigger.setItemMeta(meta);
        return trigger;
    }

    public abstract Material getMaterial();

    public abstract ItemMeta getItemMeta(ItemMeta meta);

}
