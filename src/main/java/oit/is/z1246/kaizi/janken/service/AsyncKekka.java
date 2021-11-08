package oit.is.z1246.kaizi.janken.service;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import oit.is.z1246.kaizi.janken.model.Match;
import oit.is.z1246.kaizi.janken.model.MatchMapper;
import oit.is.z1246.kaizi.janken.model.Matchinfo;

@Service
public class AsyncKekka {
  boolean dbUpdated = false;

  private final Logger logger = LoggerFactory.getLogger(AsyncKekka.class);

  @Autowired
  MatchMapper mMapper;

  public ArrayList<Match> syncShowMatchs() {
    return mMapper.selectAllActiveMatches();
  }

  /**
   * 購入対象の果物IDの果物をDBから削除し，購入対象の果物オブジェクトを返す
   *
   * @param id 購入対象の果物のID
   * @return 購入対象の果物のオブジェクトを返す
   */
  @Transactional
  public void syncMatchHand(String hand) {
    // 削除対象のフルーツを取得
    mMapper.updateById(hand);

    // 非同期でDB更新したことを共有する際に利用する
    this.dbUpdated = true;

  }

  /**
   * dbUpdatedがtrueのときのみブラウザにDBからフルーツリストを取得して送付する
   *
   * @param emitter
   */
  @Async
  public void asyncJudgeMatch(SseEmitter emitter) {
    dbUpdated = true;
    try {
      while (true) {// 無限ループ
        // DBが更新されていなければ0.5s休み
        if (false == dbUpdated) {
          TimeUnit.MILLISECONDS.sleep(500);
          continue;
        }
        // DBが更新されていれば更新後のフルーツリストを取得してsendし，1s休み，dbUpdatedをfalseにする
        ArrayList<Match> match = this.syncShowMatchs();
        emitter.send(match);
        TimeUnit.MILLISECONDS.sleep(5000);
        dbUpdated = false;
        if (match.get(0).getUser2Hand() != null) {
          mMapper.updateisActive();
        }

      }
    } catch (Exception e) {
      // 例外の名前とメッセージだけ表示する
      logger.warn("Exception:" + e.getClass().getName() + ":" + e.getMessage());
    } finally {
      emitter.complete();
    }
    System.out.println("asyncShowFruitsList complete");
  }
}
