package jp.momonnga.skillbattle.skill;

import jp.momonnga.skillbattle.skill.processor.WallKickProcessor;
import org.bukkit.event.Listener;

import java.util.Objects;

public class WallKick extends Skill {

    public static final double JUDGE_LENGTH = 0.5;
    public static final double REFLECT_VALUE = 0.3;
    public static final double MULTIPLE_MOVEMENT = 3;
    public static final double JUMP_VELOCITY = 0.7;

    private final Listener skillProcessor = new WallKickProcessor();

    @Override
    public Listener getSkillProcessor() {
        return skillProcessor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WallKick wallKick = (WallKick) o;
        return Objects.equals(skillProcessor, wallKick.skillProcessor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(skillProcessor);
    }
}
