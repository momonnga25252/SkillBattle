package jp.momonnga.skillbattle.listener;

import jp.momonnga.skillbattle.skill.QuickTeleport;
import jp.momonnga.skillbattle.skill.SkillManager;
import jp.momonnga.skillbattle.event.SkillBattlePlayerCreateEvent;
import jp.momonnga.skillbattle.skill.WallKick;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class SkillBattleEventListener implements Listener {

    @EventHandler
    public void listenCreateSkillBattlePlayer(SkillBattlePlayerCreateEvent event) {
        event.getSkillBattlePlayer().addSkill(SkillManager.searchSkill(WallKick.class));
        event.getSkillBattlePlayer().addSkill(SkillManager.searchSkill(QuickTeleport.class));
    }


}
