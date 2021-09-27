package jp.momonnga.skillbattle.skill.event;

import jp.momonnga.skillbattle.SkillBattlePlayer;
import jp.momonnga.skillbattle.skill.Skill;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class SkillEvent extends Event implements Cancellable {
    private static final HandlerList handlerList = new HandlerList();
    private final SkillBattlePlayer player;
    private final Skill skill;
    private boolean cancelled = false;

    public SkillEvent(SkillBattlePlayer who,Skill skill) {
        this.player = who;
        this.skill = skill;
    }

    public SkillBattlePlayer getSkillBattlePlayer() {
        return player;
    }

    public Skill getSkill() {
        return skill;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        cancelled = cancel;
    }
}
