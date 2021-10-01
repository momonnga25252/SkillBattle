package jp.momonnga.skillbattle.skill;

import jp.momonnga.skillbattle.skill.Skill;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SkillManager {

    private final List<Skill> skillList = new ArrayList<>();
    private final Plugin plugin;

    public SkillManager(Plugin plugin) {
        this.plugin = plugin;
    }

    public static Skill searchSkill(Class<? extends Skill> clazz) {
        return Skill.getInstance(clazz);
    }

    public Skill searchSkill(String skillName) {
        return skillList.stream()
                .filter(skill -> skill.getSkillName().equals(skillName))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public void registerSkill(Skill skill) {
        skillList.add(skill);
        plugin.getServer().getPluginManager().registerEvents(skill.getSkillProcessor(), plugin);
    }

    public void unregisterSkill(Skill skill) {
        skillList.remove(skill);
        skill.getSkillProcessor().unregisterEvents();
    }

    @SuppressWarnings("rawtypes")
    public void registerDefaultSkills() {
        Class[] skills = new Class[]{WallKick.class, TestSkill.class,QuickTeleport.class};
        Arrays.stream(skills).map(Skill::getInstance).forEach(this::registerSkill);
    }


}
