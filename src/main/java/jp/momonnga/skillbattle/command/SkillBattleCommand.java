package jp.momonnga.skillbattle.command;

import jp.momonnga.skillbattle.SkillBattle;
import jp.momonnga.skillbattle.SkillBattlePlayer;
import jp.momonnga.skillbattle.skill.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.List;

public class SkillBattleCommand implements TabExecutor {

    public static final String LABEL = "skillbattle";

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if(args[0].equalsIgnoreCase("item")) {
                SkillManager manager = SkillBattle.getPlugin(SkillBattle.class).getSkillManager();
                ItemTriggerSkill triggerSkill = (ItemTriggerSkill) manager.searchSkill(QuickTeleport.class.getSimpleName());
                ((Player) sender).getInventory().addItem(triggerSkill.createTriggerItem());
            }
        } else {
            sender.sendMessage("プレイヤーのみ利用可能なコマンドです");

        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return null;
    }


}
