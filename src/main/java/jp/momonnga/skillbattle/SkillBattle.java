package jp.momonnga.skillbattle;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class SkillBattle extends JavaPlugin {

    public static Plugin getPlugin() {
        return Bukkit.getPluginManager().getPlugin("SkillBattle");
    }

    private final List<SkillBattlePlayer> skillBattlePlayerList = new ArrayList<>();

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

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
            skillBattlePlayerList.add(registered);
        }
        return registered;
    }
}
