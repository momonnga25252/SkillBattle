package jp.momonnga.skillbattle.skill;

import jp.momonnga.skillbattle.SkillBattle;
import org.apache.commons.lang.Validate;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Objects;

public abstract class ItemTriggerSkill extends Skill {

    public static final NamespacedKey SKILLS_KEY = new NamespacedKey(SkillBattle.getPlugin(),"skill_name");

    private int coolTime = 0;

    public ItemStack createTriggerItem() {
        ItemStack trigger = new ItemStack(getMaterial());
        ItemMeta meta = getItemMeta(trigger.getItemMeta());
        meta.getPersistentDataContainer().set(SKILLS_KEY, PersistentDataType.STRING,getClass().getName());
        trigger.setItemMeta(meta);
        return trigger;
    }

    public static boolean isSameSkillTrigger(ItemStack trigger1,ItemStack trigger2) {
        Validate.isTrue(isSkillTrigger(trigger1));
        Validate.isTrue(isSkillTrigger(trigger2));
        ItemMeta meta1 = Objects.requireNonNull(trigger1.getItemMeta());
        ItemMeta meta2 = Objects.requireNonNull(trigger2.getItemMeta());
        String name1 = meta1.getPersistentDataContainer().get(SKILLS_KEY,PersistentDataType.STRING);
        String name2 = meta2.getPersistentDataContainer().get(SKILLS_KEY,PersistentDataType.STRING);
        return Objects.equals(name1, name2);
    }

    public static boolean isSkillTrigger(ItemStack itemStack) {
        ItemMeta meta = Objects.requireNonNull(itemStack.getItemMeta());
        return meta.getPersistentDataContainer().has(SKILLS_KEY,PersistentDataType.STRING);
    }

    public static ItemTriggerSkill getInstance(ItemStack trigger) {
        Objects.requireNonNull(trigger);
        Objects.requireNonNull(trigger.getItemMeta());
        ItemTriggerSkill skill = null;
        try {
            Class clazz = Class.forName(trigger.getItemMeta().getPersistentDataContainer().get(SKILLS_KEY,PersistentDataType.STRING));

            skill = (ItemTriggerSkill) getInstance(clazz.asSubclass(ItemTriggerSkill.class));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return skill;
    }

    public abstract Material getMaterial();

    public abstract ItemMeta getItemMeta(ItemMeta meta);

    public int getCoolTime() {
        return coolTime;
    }

    public void setCoolTime(int ticks) {
        coolTime = ticks;
    }


}
