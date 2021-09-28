package jp.momonnga.skillbattle.skill.processor;

import jp.momonnga.skillbattle.skill.Skill;
import org.bukkit.event.EventHandler;

public class TestSkillProcessor extends SkillProcessor {

    public TestSkillProcessor(Skill skill) {
        super(skill);
    }

    @Override
    public void unregisterEvents() {

    }

    //仮スキルの実装テスト用
    @EventHandler
    public void listenTestSkillTrigger() {

    }


}
