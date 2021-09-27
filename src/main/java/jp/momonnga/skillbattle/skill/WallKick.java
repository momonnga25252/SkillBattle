package jp.momonnga.skillbattle.skill;

import jp.momonnga.skillbattle.skill.processor.WallKickProcessor;
import org.bukkit.event.Listener;

public class WallKick extends Skill {

    public static final String PERMISSION = "skillbattle.skill.wallkick";
    private final Listener skillProcessor = new WallKickProcessor();
    public static final double JUDGE_LENGTH = 0.5;
    public static final double REFLECT_VALUE = 0.3;
    public static final double MULTIPLE_MOVEMENT = 3;
    public static final double JUMP_VELOCITY = 0.7;

    @Override
    public Listener getSkillProcessor() {
        return skillProcessor;
    }

    @Override
    public String getPermissionName() {
        return PERMISSION;
    }
}
