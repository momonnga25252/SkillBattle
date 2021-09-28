package jp.momonnga.skillbattle.event;

import jp.momonnga.skillbattle.SkillBattlePlayer;
import jp.momonnga.skillbattle.skill.Skill;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

public class SkillEvent extends SkillBattlePlayerEvent implements Cancellable {
    private static final HandlerList handlerList = new HandlerList();
    private final Skill skill;
    private boolean cancelled = false;

    public SkillEvent(SkillBattlePlayer who, Skill skill) {
        super(who);
        this.skill = skill;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    public Skill getSkill() {
        return skill;
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
