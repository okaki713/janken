package oit.is.z1246.kaizi.janken.controller;

import java.security.Principal;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import oit.is.z1246.kaizi.janken.model.Entry;
import oit.is.z1246.kaizi.janken.model.User;
import oit.is.z1246.kaizi.janken.model.UserMapper;
import oit.is.z1246.kaizi.janken.model.Match;
import oit.is.z1246.kaizi.janken.model.MatchMapper;
import oit.is.z1246.kaizi.janken.model.Matchinfo;
import oit.is.z1246.kaizi.janken.model.MatchinfoMapper;

@Controller
@RequestMapping("/")
public class Lec02Controller {

  @Autowired
  private Entry entry;
  @Autowired
  UserMapper userMapper;

  @Autowired
  MatchMapper matchMapper;

  @Autowired
  MatchinfoMapper matchinfoMapper;

  /**
   *
   * @param name
   * @param model
   * @return
   */
  @PostMapping("/lec02")
  public String lec02(@RequestParam String name, ModelMap model) {
    model.addAttribute("username", name);
    return "lec02.html";
  }

  /*
   * @GetMapping("/lec02") public String lec021(){ return "lec02.html"; }
   */

  /**
   * パスパラメータ2つをGETで受け付ける 1つ目の変数をparam1という名前で，2つ目の変数をparam2という名前で受け取る
   * GETで受け取った2つの変数とsample22()の引数の名前が同じなため， 引数の前に @PathVariable と付けるだけで，パスパラメータの値を
   * javaで処理できるようになる ModelMapはthymeleafに渡すためのMapと呼ばれるデータ構造を持つ変数
   * Mapはkeyとvalueの組み合わせで値を保持する
   *
   * @param param1
   * @param model
   * @return
   */
  @GetMapping("/match/{param1}/{param2}")
  @Transactional
  public String lec023(@PathVariable String param1, @PathVariable String param2, ModelMap model, Principal prin) {
    String myhand;
    String yourhand = "gu";
    String judge;
    if (param1.equals("gu")) {
      myhand = "gu";
    } else if (param1.equals("choki")) {
      myhand = "choki";
    } else if (param1.equals("pa")) {
      myhand = "pa";
    } else {
      myhand = "";
    }
    Matchinfo addmatchinfo = new Matchinfo();
    addmatchinfo.setUser1(userMapper.selectByName(prin.getName()).getId());
    addmatchinfo.setUser2(Integer.parseInt(param2));
    addmatchinfo.setUser1Hand(myhand);
    addmatchinfo.setActive(true);
    matchinfoMapper.insertMatchinfo(addmatchinfo);
    model.addAttribute("myhand", myhand);
    model.addAttribute("login_user", prin.getName());
    return "wait.html";

  }

  @GetMapping("lec02")
  public String lec025(Principal prin, ModelMap model) {
    String loginUser = prin.getName();
    this.entry.addUser(loginUser);
    ArrayList<User> chambers5 = userMapper.selectAllUsers();
    ArrayList<Match> matches = matchMapper.selectAllMatches();
    ArrayList<Matchinfo> matchinfo = matchinfoMapper.selectAllActiveMatches();
    model.addAttribute("entry", this.entry);
    model.addAttribute("users", chambers5);
    model.addAttribute("matches", matches);
    model.addAttribute("info", matchinfo);
    return "lec02.html";
  }

  @GetMapping("/match")
  public String match(@RequestParam Integer id, ModelMap model, Principal prin) {
    String user1 = prin.getName();
    User user2 = userMapper.selectById(id);
    System.out.println("user2name = " + user2.getName());
    model.addAttribute("user1", user1);
    model.addAttribute("user2", user2.getName());
    model.addAttribute("id", id);
    return "match.html";

  }
}
