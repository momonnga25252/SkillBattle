package jp.momonnga.skillbattle;

import org.bukkit.entity.Player;

public interface SkillBattlePlayer extends SkillPointHolder {
    static SkillBattlePlayer of(Player player) {
        SkillBattle sb = (SkillBattle) SkillBattle.getPlugin();
        SkillBattlePlayer converted;
        if(sb.hasSkillBattlePlayer(player)) converted = sb.getSkillBattlePlayer(player);
        else converted = sb.registerSkillBattlePlayer(player);
        return converted;
    }

    Player getPlayer();


}
