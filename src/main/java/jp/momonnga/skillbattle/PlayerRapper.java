package jp.momonnga.skillbattle;

import jp.momonnga.skillbattle.skill.Skill;
import org.apache.commons.lang.Validate;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;


public class PlayerRapper implements SkillBattlePlayer {
    private final Player self;
    private final Set<Skill> skillSet = new HashSet<>();
    private int skillPoint;
    private int limitSkillPoint;
    private int regenerationSkillPoint;

    public PlayerRapper(Player player) {
        this.self = player;
    }

    @Override
    public Player getPlayer() {
        return self;
    }

    @Override
    public void addSkill(Skill skill) {
        skillSet.add(skill);
    }

    @Override
    public void removeSkill(Skill skill) {
        skillSet.remove(skill);
    }

    @Override
    public boolean hasSkill(Skill skill) {
        return skillSet.contains(skill);
    }

    @Override
    public int getLimitSkillPoint() {
        return limitSkillPoint;
    }

    @Override
    public void setLimitSkillPoint(int value) {
        Validate.isTrue(value >= 0, "負の数は指定できません");
        limitSkillPoint = value;
    }

    @Override
    public int getAutoRegenerationSkillPoint() {
        return regenerationSkillPoint;
    }

    @Override
    public void setAutoRegenerationSkillPoint(int value) {
        Validate.isTrue(value >= 0, "負の数は指定できません");
        regenerationSkillPoint = value;
    }

    @Override
    public int getSkillPoint() {
        return skillPoint;
    }

    @Override
    public void setSkillPoint(int value) {
        Validate.isTrue(value >= 0, "負の数は指定できません");
        Validate.isTrue(value <= limitSkillPoint, "上限を超えて指定することはできません");
        skillPoint = value;
    }

    @Override
    public void addSkillPoint(int value) {
        int expression = skillPoint + value;
        Validate.isTrue(value >= 0, "負の数は指定できません removeメソッドを利用してください");
        Validate.isTrue(expression <= limitSkillPoint, "上限を超えるような値を代入することはできません");
        setSkillPoint(expression);
    }

    @Override
    public void removeSkillPoint(int value) {
        int expression = skillPoint - value;
        Validate.isTrue(value >= 0, "負の数は指定できません removeメソッドを利用してください");
        Validate.isTrue(expression <= limitSkillPoint, "上限を超えるような値を代入することはできません");
        setSkillPoint(expression);
    }

    @Override
    public boolean canUse(SkillPointConsumer skill) {
        return skillPoint >= skill.getNeedSkillPoint();
    }

}
