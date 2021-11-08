package oit.is.z1246.kaizi.janken.model;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface MatchMapper {
  /**
   * DBのカラム名とjavaクラスのフィールド名が同じ場合はそのまま代入してくれる（大文字小文字の違いは無視される）
   * カラム名とフィールド名が異なる場合の対応も可能だが，いきなり複雑になるので，selectで指定するテーブル中のカラム名とクラスのフィールド名は同一になるよう設計することが望ましい
   *
   * @return
   */
  @Select("SELECT * FROM MATCHES;")
  ArrayList<Match> selectAllMatches();

  @Select("SELECT * FROM MATCHES where isActive='TRUE';")
  ArrayList<Match> selectAllActiveMatches();

  @Insert("INSERT INTO matches (user1,user2,user1Hand,user2Hand,isActive) VALUES (#{user1},#{user2},#{user1Hand},#{user2Hand},#{isActive});")
  @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
  void insertMatch(Match match);

  @Select("SELECT * FROM MATCHES WHERE user1=#{user1} and user2=#{user2} and isActive='TRUE';")
  ArrayList<Match> selectThisMatch(int user1, int user2);

  @Update("UPDATE MATCHES SET user2Hand=#{user2Hand}, WHERE isActive='TRUE'")
  void updateById(String user2Hand);

  @Update("UPDATE MATCHES SET isActive='FALSE' WHERE isActive='TRUE'")
  void updateisActive();
}
