package jp.momonnga.skillbattle;

public interface SkillPointConsumer {

    int getNeedSkillPoint();

    void consume(SkillPointHolder pointHolder);


}
