package jp.momonnga.skillbattle.skill;

import jp.momonnga.skillbattle.SkillBattle;
import org.bukkit.event.Listener;

import java.util.HashMap;
import java.util.Map;

public abstract class Skill {

    private static final Map<String,Skill> _classnameToInstance = new HashMap<>();
    private static final Object _lock = new Object();

    protected Skill() {
        synchronized (_lock) {
            String classname = this.getClass().getName();
            if (_classnameToInstance.get(classname) != null) throw new RuntimeException("Already created: " + classname);
            _classnameToInstance.put(classname, this);
        }
    }

    public static <T> T getInstance(Class<T> clazz) {
        synchronized (_lock) {
            String classname = clazz.getName();
            Skill obj = _classnameToInstance.get(classname);
            if (obj == null) {
                try {
                    Class<?> cls = Class.forName(classname);
                    obj = (Skill) cls.newInstance();
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(classname + " is not found");
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(classname + " cannot be accessed.");
                } catch (InstantiationException e) {
                    throw new RuntimeException(classname + " cannot be instantiated.");
                }
            }
            return (T) obj;
        }
    }

    public abstract Listener getSkillProcessor();

    public abstract String getPermissionName();

}
