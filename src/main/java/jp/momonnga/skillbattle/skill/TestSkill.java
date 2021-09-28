package jp.momonnga.skillbattle.skill;

import jp.momonnga.skillbattle.skill.processor.SkillProcessor;
import jp.momonnga.skillbattle.skill.processor.TestSkillProcessor;

public class TestSkill extends Skill {

    private final SkillProcessor skillProcessor = new TestSkillProcessor(this);

    @Override
    public SkillProcessor getSkillProcessor() {
        return skillProcessor;
    }

}
