package jp.momonnga.skillbattle.skill.processor;

import jp.momonnga.skillbattle.SkillBattlePlayer;
import jp.momonnga.skillbattle.skill.event.SkillEvent;
import jp.momonnga.skillbattle.skill.WallKick;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import java.util.Arrays;

public class WallKickProcessor implements Listener {

    /*
        ・プレイヤーが空中で壁ジャンプの条件を満たしながら移動しているときにAllowFlightをONにする
        ・AllowFlightがONでないとToggleFlightEventが発火しないので注意
        ・AllowFlightが常にONだとイベントキャンセルしても連打で低速落下できるので条件を満たしているときのみAllowFlightをONにする
        ・ゲーム中のプレイヤーもしくはコマンドで許可したプレイヤーのAllowFlightのみに干渉する
        ・正面の2ブロックが空気でなく地面に接していないのが壁ジャンプの条件
     */
    @EventHandler
    public void listenPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        SkillBattlePlayer sp = SkillBattlePlayer.of(player);

        //干渉条件
        if (!sp.hasSkill(WallKick.getInstance(WallKick.class))) return;
        if(WallKick.checkGameMode(player)) return;

        Location loc = player.getLocation();
        World world = player.getWorld();
        BlockFace playerFace = player.getFacing();
        Block playerLocBlock = world.getBlockAt(loc);
        Block faceBlock = playerLocBlock.getRelative(playerFace);
        Block upBlock = faceBlock.getRelative(BlockFace.UP);

        //壁ジャンプの条件
        player.setAllowFlight(!upBlock.isEmpty() && !faceBlock.isEmpty() && !player.isOnGround());
    }

    /*
        モード切替時AllowFlightを変更
     */
    @EventHandler
    public void changeGameMode(PlayerGameModeChangeEvent event) {
        event.getPlayer().setAllowFlight(WallKick.checkGameMode(event.getPlayer()));
    }

    /*
        実際の壁ジャンプの処理
     */
    @EventHandler
    public void listenToggleFlightEvent(PlayerToggleFlightEvent event) {
        Player player = event.getPlayer();
        SkillBattlePlayer sp = SkillBattlePlayer.of(player);

        //干渉条件
        if (!sp.hasSkill(WallKick.getInstance(WallKick.class))) return;
        if(WallKick.checkGameMode(player)) return;

        //切り替えをキャンセル
        event.setCancelled(true);

        Location playerLoc = player.getLocation();
        World world = player.getWorld();
        BlockFace playerFace = player.getFacing();
        Block playerLocBlock = world.getBlockAt(playerLoc);
        Block faceBlock = playerLocBlock.getRelative(playerFace);
        Block upBlock = faceBlock.getRelative(BlockFace.UP);

        /*
            ベクトルがxyに0の場合垂直にジャンプ
            y軸方向のベクトルは0.7で固定 大体4マスまでは乗り越えられる
            プレイヤーの向きと位置で壁ジャンプ可能か判定 向いてる壁から0.5マス内
            向いてる壁と逆向きに0.3のベクトル 反射
            進行方向に対しての成分は3倍 加速
         */
        //壁ジャンプ判定
        if (upBlock.isEmpty() || faceBlock.isEmpty() || player.isOnGround()) return;

        //壁との距離計測
        RayTraceResult result = world.rayTraceBlocks(playerLoc,playerFace.getDirection(),1);
        if(result == null) return;
        double wallDistance = result.getHitPosition().distance(playerLoc.toVector());

        //判定内か確認
        if(wallDistance > WallKick.JUDGE_LENGTH) return;

        //イベントを発火
        SkillEvent skillEvent = new SkillEvent(SkillBattlePlayer.of(player),WallKick.getInstance(WallKick.class));
        Bukkit.getServer().getPluginManager().callEvent(skillEvent);

        //イベント確認
        if(!skillEvent.isCancelled()) {
            double reflectValue = WallKick.REFLECT_VALUE;
            Vector afterJumpVelocity = player.getVelocity();

            //進行方向のベクトルを伸ばす 飛距離上げ
            afterJumpVelocity.multiply(WallKick.MULTIPLE_MOVEMENT);

            //向いてる方向でベクトル変更
            afterJumpVelocity.setY(WallKick.JUMP_VELOCITY);
            Vector oppositeFaceVector = playerFace.getOppositeFace().getDirection();
            if(afterJumpVelocity.getX() != 0 || afterJumpVelocity.getZ() != 0) {
                //if文を消すと視点変更でのベクトル操作ができなくなる 進行方向に対するベクトルがいったん消える wasdで再加速すればある程度は曲がる
                if(oppositeFaceVector.getX() == 0) afterJumpVelocity.setZ(oppositeFaceVector.getZ() * reflectValue);
                if(oppositeFaceVector.getZ() == 0) afterJumpVelocity.setX(oppositeFaceVector.getX() * reflectValue);
            }

            //ベクトル反映
            player.setVelocity(afterJumpVelocity);
        }

        //一応OFFにしとく PlayerMoveEventでも上書きされる
        player.setAllowFlight(false);
    }
}
