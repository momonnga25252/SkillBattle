package jp.momonnga.skillbattle.skill;

import jp.momonnga.skillbattle.SkillBattle;
import jp.momonnga.skillbattle.skill.processor.QuickTeleportProcessor;
import jp.momonnga.skillbattle.skill.processor.SkillProcessor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Objects;

public class QuickTeleport extends ItemTriggerSkill {

    public static final double TELEPORT_REACH = 6;

    private final SkillProcessor processor = new QuickTeleportProcessor(this);


    @Override
    public SkillProcessor getSkillProcessor() {
        return processor;
    }

    @Override
    public Material getMaterial() {
        return null;
    }

    @Override
    public ItemMeta getItemMeta(ItemMeta meta) {
        return null;
    }
}
