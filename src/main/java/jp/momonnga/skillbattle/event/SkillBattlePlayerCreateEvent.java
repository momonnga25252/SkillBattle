package jp.momonnga.skillbattle.event;

import jp.momonnga.skillbattle.SkillBattlePlayer;
import org.bukkit.event.HandlerList;

public class SkillBattlePlayerCreateEvent extends SkillBattlePlayerEvent {

    private static final HandlerList handlerList = new HandlerList();

    public SkillBattlePlayerCreateEvent(SkillBattlePlayer player) {
        super(player);
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

}
