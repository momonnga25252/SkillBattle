package jp.momonnga.skillbattle;

public interface SkillPointHolder {

    int getLimitSkillPoint();

    void setLimitSkillPoint(int value);

    int getAutoRegenerationSkillPoint();

    void setAutoRegenerationSkillPoint(int value);

    int getSkillPoint();

    void setSkillPoint(int value);

    void addSkillPoint(int value);

    void removeSkillPoint(int value);

    boolean canUse(SkillPointConsumer skill);

}
