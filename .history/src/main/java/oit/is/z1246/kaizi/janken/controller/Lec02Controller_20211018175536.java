package oit.is.z1246.kaizi.janken.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class Lec02Controller {
  @Autowired
  private Entry enrty;
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

/*  @GetMapping("/lec02")
  public String lec021(){
    return "lec02.html";
  }
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
  @GetMapping("/lec02/{param1}")
  public String lec023(@PathVariable String param1, ModelMap model) {
    String myhand;
    String yourhand = "gu";
    String judge;
    if(param1.equals("gu")){
      myhand = "gu";
      judge = "Draw...";
    }else if(param1.equals("choki")){
      myhand = "choki";
      judge = "You Lose...";
    }else if(param1.equals("pa")){
      myhand = "pa";
      judge = "You Win!!";
    }else{
      myhand = "";
      judge = "";
    }
    model.addAttribute("myhand", myhand);
    model.addAttribute("yourhand", yourhand);
    model.addAttribute("judge", judge);
    return "lec02.html";

  }

  @GetMapping("lec02")
  public String sample38(Principal prin, ModelMap model) {
    String loginUser = prin.getName();
    this.entry.addUser(loginUser);
    model.addAttribute("room", this.room);

    return "lec02.html";
  }
}
