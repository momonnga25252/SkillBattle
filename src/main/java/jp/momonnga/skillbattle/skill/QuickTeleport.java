package jp.momonnga.skillbattle.skill;

import jp.momonnga.skillbattle.skill.processor.QuickTeleportProcessor;
import jp.momonnga.skillbattle.skill.processor.SkillProcessor;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class QuickTeleport extends ItemTriggerSkill {

    public static final double TELEPORT_REACH = 6;

    private final SkillProcessor processor = new QuickTeleportProcessor(this);

    public QuickTeleport() {
        setDisplayName(ChatColor.RED +"クイックテレポート");
    }

    @Override
    public SkillProcessor getSkillProcessor() {
        return processor;
    }

    @Override
    public Material getMaterial() {
        return Material.GOLD_INGOT;
    }

    @Override
    public ItemMeta getItemMeta(ItemMeta meta) {
        meta.setDisplayName(getDisplayName());
        meta.setLore(List.of("スキル名: "+getDisplayName()+ChatColor.RESET,"使用方法: 壁や天井に向けて右クリック","説明: 壁や天井を通り抜けることができる。"));
        return meta;
    }
}
