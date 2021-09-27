package jp.momonnga.skillbattle;

import jp.momonnga.skillbattle.skill.Skill;
import jp.momonnga.skillbattle.skill.TestSkill;
import jp.momonnga.skillbattle.skill.WallKick;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public final class SkillBattle extends JavaPlugin {

    public static Plugin getPlugin() {
        return Bukkit.getPluginManager().getPlugin("SkillBattle");
    }

    private final List<SkillBattlePlayer> skillBattlePlayerList = new ArrayList<>();
    private final Set<Skill> skillSet = new HashSet<>();

    @Override
    public void onEnable() {
        registerDefaultSkills();
    }

    @Override
    public void onDisable() {

    }

    private void registerDefaultSkills() {
        Class<? extends Skill>[] skills = new Class[]{WallKick.class, TestSkill.class};
        Arrays.stream(skills).map(Skill::getInstance).forEach(this::registerSkill);
    }

    public void registerSkill(Skill skill) {
        skillSet.add(skill);
        getServer().getPluginManager().registerEvents(skill.getSkillProcessor(),this);
    }


    public SkillBattlePlayer getSkillBattlePlayer(Player player) {
        return skillBattlePlayerList.stream()
                .filter(skillBattlePlayer -> skillBattlePlayer.getPlayer().equals(player))
                .findFirst().orElseThrow(IllegalArgumentException::new);
    }

    public boolean hasSkillBattlePlayer(Player player) {
        return skillBattlePlayerList.stream()
                .anyMatch(skillBattlePlayer -> skillBattlePlayer.getPlayer().equals(player));
    }

    public SkillBattlePlayer registerSkillBattlePlayer(Player player) {
        SkillBattlePlayer registered;
        if(hasSkillBattlePlayer(player)) {
            registered = getSkillBattlePlayer(player);
        } else {
            registered = new PlayerRapper(player);
            getLogger().info("プレイヤーデータが生成されました");
            skillBattlePlayerList.add(registered);
        }
        return registered;
    }
}
