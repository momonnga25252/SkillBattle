package jp.momonnga.skillbattle.skill.processor;

import jp.momonnga.skillbattle.skill.Skill;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.List;

public abstract class SkillProcessor implements Listener {

    private final List<GameMode> ignored;
    private final Skill skill;

    public SkillProcessor(Skill skill) {
        this(skill, List.of(GameMode.CREATIVE, GameMode.SPECTATOR));
    }

    public SkillProcessor(Skill skill, List<GameMode> ignored) {
        this.ignored = ignored;
        this.skill = skill;
    }

    protected boolean checkGameMode(Player player) {
        return !ignored.contains(player.getGameMode());
    }

    protected Skill getSkill() {
        return skill;
    }

    public abstract void unregisterEvents();

}
