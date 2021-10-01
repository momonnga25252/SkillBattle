package jp.momonnga.skillbattle.skill.processor;

import jp.momonnga.skillbattle.SkillBattlePlayer;
import jp.momonnga.skillbattle.event.SkillEvent;
import jp.momonnga.skillbattle.skill.Skill;
import jp.momonnga.skillbattle.skill.WallKick;
import org.apache.commons.lang.BooleanUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

public class WallKickProcessor extends SkillProcessor {

    public WallKickProcessor(Skill skill) {
        super(skill);
    }

    @Override
    public void unregisterEvents() {
        PlayerMoveEvent.getHandlerList().unregister(this);
        PlayerGameModeChangeEvent.getHandlerList().unregister(this);
        PlayerToggleFlightEvent.getHandlerList().unregister(this);
    }

    @SuppressWarnings("deprecation")
    @EventHandler
    public void listenPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        SkillBattlePlayer sp = SkillBattlePlayer.of(player);

        if (!sp.hasSkill(getSkill())) return;
        if (!isAcceptedGameMode(player)) return;

        Location loc = player.getLocation();
        World world = player.getWorld();
        BlockFace playerFace = player.getFacing();
        Block playerLocBlock = world.getBlockAt(loc);
        Block faceBlock = playerLocBlock.getRelative(playerFace);
        Block upBlock = faceBlock.getRelative(BlockFace.UP);

        double wallDistance = calcWallDistance(world, loc, playerFace.getDirection(),WallKick.JUDGE_DISTANCE);
        player.setAllowFlight(containsJudgeDistance(wallDistance,WallKick.JUDGE_DISTANCE) && !upBlock.isEmpty() && !faceBlock.isEmpty() && !player.isOnGround());
    }

    @EventHandler
    public void changeGameMode(PlayerGameModeChangeEvent event) {
        event.getPlayer().setAllowFlight(isAcceptedGameMode(event.getPlayer()));
    }

    @SuppressWarnings("deprecation")
    @EventHandler
    public void listenToggleFlightEvent(PlayerToggleFlightEvent event) {
        Player player = event.getPlayer();
        SkillBattlePlayer sp = SkillBattlePlayer.of(player);

        if (!sp.hasSkill(getSkill())) return;
        if (!isAcceptedGameMode(player)) return;

        event.setCancelled(true);

        Location playerLoc = player.getLocation();
        World world = player.getWorld();
        BlockFace playerFace = player.getFacing();
        Block playerLocBlock = world.getBlockAt(playerLoc);
        Block playerFaceBlock1 = playerLocBlock.getRelative(playerFace);
        Block playerFaceBlock2 = playerFaceBlock1.getRelative(BlockFace.UP);

        double wallDistance = calcWallDistance(world, playerLoc, playerFace.getDirection(),WallKick.JUDGE_DISTANCE);
        if (!containsJudgeDistance(wallDistance,WallKick.JUDGE_DISTANCE) || playerFaceBlock1.isEmpty() || playerFaceBlock2.isEmpty() || player.isOnGround()) return;

        SkillEvent skillEvent = new SkillEvent(SkillBattlePlayer.of(player), getSkill());
        Bukkit.getServer().getPluginManager().callEvent(skillEvent);
        if (skillEvent.isCancelled()) return;

        Vector afterJumpVelocity = calcJumpVelocity(player.getVelocity(), playerFace);
        player.setVelocity(afterJumpVelocity);
    }

    public static double calcWallDistance(World world, Location location, Vector direction,double distance) {
        RayTraceResult result = world.rayTraceBlocks(location, direction, distance);
        if (result == null) return -1;
        return result.getHitPosition().distance(location.toVector());
    }

    public static boolean containsJudgeDistance(double wallDistance,double judgeDistance) {
        return BooleanUtils.isFalse(wallDistance == -1 || wallDistance > judgeDistance);
    }

    private Vector calcJumpVelocity(Vector playerVector, BlockFace playerFace) {
        Vector resultVector = playerVector.clone();
        resultVector.multiply(WallKick.MULTIPLE_MOVEMENT);
        resultVector.setY(WallKick.JUMP_VELOCITY);
        if (playerVector.getX() == 0 && playerVector.getZ() == 0) return resultVector;

        Vector oppositeFaceVelocity = playerFace.getOppositeFace().getDirection();
        if (oppositeFaceVelocity.getX() == 0) resultVector.setZ(oppositeFaceVelocity.getZ() * WallKick.REFLECT_VALUE);
        if (oppositeFaceVelocity.getZ() == 0) resultVector.setX(oppositeFaceVelocity.getX() * WallKick.REFLECT_VALUE);
        return resultVector;
    }
}
