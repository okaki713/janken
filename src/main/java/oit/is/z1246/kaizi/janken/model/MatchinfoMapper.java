package oit.is.z1246.kaizi.janken.model;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface MatchinfoMapper {

  @Select("SELECT * FROM Matchinfo where isActive = 'TRUE';")
  ArrayList<Matchinfo> selectAllActiveMatches();

  @Update("UPDATE MATCHINFO SET isActive='false' WHERE isActive='TRUE'")
  void updateisActives();

  @Select("SELECT * FROM Matchinfo where isActive = 'TRUE' and user2 = #{id};")
  ArrayList<Matchinfo> selectAllMyMatches(int id);

  @Insert("INSERT INTO matchinfo (user1,user2,user1Hand,isActive) VALUES (#{user1},#{user2},#{user1Hand},#{isActive});")
  @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
  void insertMatchinfo(Matchinfo matchinfo);

}
