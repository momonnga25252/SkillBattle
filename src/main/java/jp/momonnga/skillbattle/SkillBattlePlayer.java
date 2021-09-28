package jp.momonnga.skillbattle;

import jp.momonnga.skillbattle.skill.Skill;
import org.bukkit.entity.Player;

public interface SkillBattlePlayer extends SkillPointHolder {
    static SkillBattlePlayer of(Player player) {
        SkillBattle sb = (SkillBattle) SkillBattle.getPlugin();
        SkillBattlePlayer converted;
        if (sb.hasSkillBattlePlayer(player)) converted = sb.getSkillBattlePlayer(player);
        else converted = sb.registerSkillBattlePlayer(player);
        return converted;
    }

    Player getPlayer();

    void addSkill(Skill skill);

    void removeSkill(Skill skill);

    boolean hasSkill(Skill skill);

}
