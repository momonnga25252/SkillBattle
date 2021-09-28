package jp.momonnga.skillbattle.event;

import jp.momonnga.skillbattle.SkillBattlePlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class SkillBattlePlayerEvent extends Event {

    private static final HandlerList handlerList = new HandlerList();
    private final SkillBattlePlayer player;

    public SkillBattlePlayerEvent(SkillBattlePlayer player) {
        this.player = player;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    public SkillBattlePlayer getSkillBattlePlayer() {
        return player;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}
