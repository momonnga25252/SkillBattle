package jp.momonnga.skillbattle;

import jp.momonnga.skillbattle.command.SkillBattleCommand;
import jp.momonnga.skillbattle.event.SkillBattlePlayerCreateEvent;
import jp.momonnga.skillbattle.listener.SkillBattleEventListener;
import jp.momonnga.skillbattle.skill.Skill;
import jp.momonnga.skillbattle.skill.SkillManager;
import jp.momonnga.skillbattle.skill.TestSkill;
import jp.momonnga.skillbattle.skill.WallKick;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class SkillBattle extends JavaPlugin {

    private final List<SkillBattlePlayer> skillBattlePlayerList = new ArrayList<>();
    private final SkillManager skillManager = new SkillManager(this);

    public static Plugin getPlugin() {
        return Bukkit.getPluginManager().getPlugin("SkillBattle");
    }

    @Override
    public void onEnable() {
        skillManager.registerDefaultSkills();
        getCommand(SkillBattleCommand.LABEL).setExecutor(new SkillBattleCommand());
        getServer().getPluginManager().registerEvents(new SkillBattleEventListener(),this);
    }

    @Override
    public void onDisable() {

    }

    public SkillManager getSkillManager() {
        return skillManager;
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
        if (hasSkillBattlePlayer(player)) {
            registered = getSkillBattlePlayer(player);
        } else {
            registered = new PlayerRapper(player);
            getLogger().info("????????????????????????????????????????????????");
            getServer().getPluginManager().callEvent(new SkillBattlePlayerCreateEvent(registered));
            skillBattlePlayerList.add(registered);
        }
        return registered;
    }
}
