package jp.momonnga.skillbattle.skill;

import jp.momonnga.skillbattle.skill.processor.TestSkillProcessor;
import org.bukkit.event.Listener;

public class TestSkill extends Skill{

    private final Listener skillProcessor = new TestSkillProcessor();

    @Override
    public Listener getSkillProcessor() {
        return skillProcessor;
    }

}
