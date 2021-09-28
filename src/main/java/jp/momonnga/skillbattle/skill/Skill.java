package jp.momonnga.skillbattle.skill;

import jp.momonnga.skillbattle.skill.processor.SkillProcessor;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public abstract class Skill {

    private static final Map<String, Skill> _classnameToInstance = new HashMap<>();
    private static final Object _lock = new Object();

    private String displayName = getSkillName();

    protected Skill() {
        synchronized (_lock) {
            String classname = this.getClass().getName();
            if (_classnameToInstance.get(classname) != null)
                throw new RuntimeException("Already created: " + classname);
            _classnameToInstance.put(classname, this);
        }
    }

    static Skill getInstance(Class<? extends Skill> clazz) {
        synchronized (_lock) {
            String classname = clazz.getName();
            Skill obj = _classnameToInstance.get(classname);
            if (obj == null) {
                try {
                    obj = clazz.getDeclaredConstructor().newInstance();
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(classname + " cannot be accessed.");
                } catch (InstantiationException e) {
                    throw new RuntimeException(classname + " cannot be instantiated.");
                } catch (InvocationTargetException | NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
            return obj;
        }
    }

    public abstract SkillProcessor getSkillProcessor();

    public final String getSkillName() {
        return getClass().getSimpleName();
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Skill skill)) return false;
        return Objects.equals(getDisplayName(), skill.getDisplayName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDisplayName());
    }
}
