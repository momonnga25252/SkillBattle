package jp.momonnga.skillbattle.skill;

import jp.momonnga.skillbattle.SkillBattle;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
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

    public static Skill getInstance(Class<? extends Skill> clazz) {
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

    public abstract Listener getSkillProcessor();

    public static boolean checkGameMode(Player player) {
        return !Arrays.asList(GameMode.CREATIVE,GameMode.SPECTATOR)
                .contains(player.getGameMode());
    }
}
