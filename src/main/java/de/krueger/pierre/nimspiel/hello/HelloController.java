package de.krueger.pierre.nimspiel.hello;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/")
public class HelloController {

  @GetMapping
  public String hello() {
    return "Willkommen beim Nim-Spiel.\n" +
      "Mit einem GET /play kannst du das Spiel initalisieren.\n" +
      "Mit einem POST /play kannst du deine Spielzüge absetzen.\n\nViel Spaß!\n\n" +
      "Mit diesem Request kannst du spielen:\n" +
      "{\n" +
      "\t\"countMatches\" : \"1\"\n" +
      "}";
  }
}
