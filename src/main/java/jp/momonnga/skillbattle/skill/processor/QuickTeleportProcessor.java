package jp.momonnga.skillbattle.skill.processor;

import jp.momonnga.skillbattle.SkillBattlePlayer;
import jp.momonnga.skillbattle.skill.Skill;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;

public class QuickTeleportProcessor extends SkillProcessor {

    public QuickTeleportProcessor(Skill skill) {
        super(skill);
    }

    @Override
    public void unregisterEvents() {

    }

    @EventHandler
    public void listenPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        SkillBattlePlayer sp = SkillBattlePlayer.of(player);

        if (!sp.hasSkill(getSkill())) return;
        if (checkGameMode(player)) return;



        //event.setCancelled(true);
    }
}
