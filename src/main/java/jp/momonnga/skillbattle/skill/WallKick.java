package jp.momonnga.skillbattle.skill;

import jp.momonnga.skillbattle.skill.processor.SkillProcessor;
import jp.momonnga.skillbattle.skill.processor.WallKickProcessor;

public class WallKick extends Skill {

    public static final double JUDGE_DISTANCE = 0.5;
    public static final double REFLECT_VALUE = 0.3;
    public static final double MULTIPLE_MOVEMENT = 3;
    public static final double JUMP_VELOCITY = 0.7;

    private final SkillProcessor skillProcessor = new WallKickProcessor(this);

    @Override
    public SkillProcessor getSkillProcessor() {
        return skillProcessor;
    }

}
