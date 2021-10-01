package jp.momonnga.skillbattle.skill.processor;

import jp.momonnga.skillbattle.SkillBattlePlayer;
import jp.momonnga.skillbattle.skill.ItemTriggerSkill;
import jp.momonnga.skillbattle.skill.QuickTeleport;
import jp.momonnga.skillbattle.skill.Skill;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import java.util.List;

public class QuickTeleportProcessor extends SkillProcessor {

    public QuickTeleportProcessor(Skill skill) {
        super(skill);
    }

    @Override
    public void unregisterEvents() {
        PlayerInteractEvent.getHandlerList().unregister(this);
    }

    @EventHandler
    public void listenPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        SkillBattlePlayer sp = SkillBattlePlayer.of(player);

        if (!sp.hasSkill(getSkill())) return;
        if (!isAcceptedGameMode(player)) return;

        ItemStack trigger = event.getPlayer().getInventory().getItemInMainHand();
        if(trigger.getItemMeta() == null) return;
        if (!ItemTriggerSkill.isSkillTrigger(trigger)) return;
        if (player.hasCooldown(trigger.getType())) return;
        ItemTriggerSkill skill = ItemTriggerSkill.getInstance(trigger);

        World world = player.getWorld();
        Location playerLoc = player.getLocation();
        BlockFace playerFace = player.getFacing();
        Action action = event.getAction();

        if (!List.of(Action.RIGHT_CLICK_AIR, Action.RIGHT_CLICK_BLOCK).contains(action)) return;
        if (trigger.getItemMeta() == null) return;
        if (!(ItemTriggerSkill.getInstance(trigger) instanceof QuickTeleport)) return;

        Location afterTPLocation = playerLoc.clone();
        Vector afterTPVelocity = player.getVelocity();
        if (playerLoc.getPitch() < -50 && !player.isOnGround()) {
            RayTraceResult result = world.rayTraceBlocks(player.getEyeLocation(), player.getEyeLocation().getDirection(), QuickTeleport.TELEPORT_REACH);
            Vector hitPosition;
            Block hitBlock;
            if (result != null) {
                hitPosition = result.getHitPosition();
                hitBlock = result.getHitBlock();
            } else {
                return;
            }

            Location hitLocation = hitPosition.toLocation(world);
            if (hitBlock == null) return;
            if (hitBlock.isEmpty()) return;
            if (!hitBlock.getRelative(BlockFace.UP).isEmpty()) return;
            int tpy = hitBlock.getY() + 1;

            afterTPLocation = hitLocation;
            afterTPLocation.setY(tpy);
            afterTPLocation.setDirection(playerLoc.getDirection());
            afterTPVelocity.setY(0.3);
        } else {
            Block playerFaceBlock1 = playerLoc.getBlock().getRelative(playerFace);
            Block playerFaceBlock2 = playerFaceBlock1.getRelative(BlockFace.UP);
            Block playerFaceBlock3 = playerFaceBlock2.getRelative(playerFace);
            Block playerFaceBlock4 = playerFaceBlock3.getRelative(playerFace);

            double wallDistance = WallKickProcessor.calcWallDistance(world, playerLoc, playerFace.getDirection(),QuickTeleport.JUDGE_DISTANCE);
            if (WallKickProcessor.containsJudgeDistance(wallDistance,QuickTeleport.JUDGE_DISTANCE) || playerFaceBlock1.isEmpty()
                    || playerFaceBlock2.isEmpty()
                    || !playerFaceBlock3.isEmpty()
                    || !playerFaceBlock4.isEmpty()) return;

            afterTPLocation.add(playerFace.getDirection().multiply(2));
        }

        player.teleport(afterTPLocation);
        player.setVelocity(afterTPVelocity);
        player.setCooldown(trigger.getType(), skill.getCoolTime());
    }


}
