package oit.is.z1246.kaizi.janken.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class Lec02Controller {
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

  @GetMapping("/lec02")
  public String lec021(){
    return "lec02.html";
  }
}
