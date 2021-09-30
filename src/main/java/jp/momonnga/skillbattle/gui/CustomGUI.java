package jp.momonnga.skillbattle.gui;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class CustomGUI {

    private static final Map<String, CustomGUI> _classnameToInstance = new HashMap<>();
    private static final Object _lock = new Object();

    private final List<Inventory> menu = new ArrayList<>();

    protected CustomGUI() {
        synchronized (_lock) {
            String classname = this.getClass().getName();
            if (_classnameToInstance.get(classname) != null)
                throw new RuntimeException("Already created: " + classname);
            _classnameToInstance.put(classname, this);
        }
    }

    static CustomGUI getInstance(Class<? extends CustomGUI> clazz) {
        synchronized (_lock) {
            String classname = clazz.getName();
            CustomGUI obj = _classnameToInstance.get(classname);
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

    static boolean isMenu(Inventory inventory) {
        return _classnameToInstance.values().stream().anyMatch(ins -> ins.hasMenu(inventory));
    }

    static CustomGUI getInstance(Inventory inventory) {
        Validate.isTrue(isMenu(inventory),"メニューじゃないものを");
        return _classnameToInstance.values().stream()
                .filter(ins -> ins.hasMenu(inventory))
                .findFirst()
                .orElseThrow(NullPointerException::new);
    }

    static CustomGUI getInstance(String url) {
        for (CustomGUI ins : _classnameToInstance.values()) {
            if(ins.getUrl().equals(url)) return ins;
        }
        throw new IllegalArgumentException();
    }

    public abstract String getUrl();

    public abstract int getSize();

    public abstract String getTitle();

    public Inventory toInventory() {
        Inventory inventory = Bukkit.createInventory(null,getSize(),getTitle());
        //TODO いろいろ
        menu.add(inventory);
        return null;
    }

    public boolean hasMenu(Inventory inventory) {
        return menu.contains(inventory);
    }

}
