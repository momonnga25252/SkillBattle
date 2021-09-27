package jp.momonnga.skillbattle.listener;

import jp.momonnga.skillbattle.SkillBattlePlayer;
import jp.momonnga.skillbattle.skill.event.SkillEvent;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Arrays;

public class SkillEventListener implements Listener {

    @EventHandler
    public void listenSkill(SkillEvent event) {
        SkillBattlePlayer player = event.getSkillBattlePlayer();
        if(Arrays.asList(GameMode.CREATIVE,GameMode.SPECTATOR).contains(player.getPlayer().getGameMode())) event.setCancelled(true);
    }

}
