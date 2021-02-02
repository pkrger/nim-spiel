package de.krueger.pierre.nimspiel.nim;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController("/nim")
public class NimController {

  private final NimService nimService;

  public NimController(NimService nimService) {
    this.nimService = nimService;
  }

  /**
   * Initialisiert das Spiel
   *
   * @return Antwort an den Spieler mit der initialen Befüllung des Streichholzstapels
   */
  @GetMapping("/play")
  public NimResponse init() {
    log.debug("Init-Methode");
    return nimService.init();
  }

  /**
   * Zentrale Methode, um das Spiel zu spielen
   *
   * @param nimRequest Anfrage des Spielers mit Anzahl der gezogenen Streichhölzern
   * @return Antwort des Spiels mit noch verfügbaren Streichhölzern und Spielanweisung
   */
  @PostMapping("/play")
  public NimResponse play(@RequestBody NimRequest nimRequest) {
    log.debug("NimRequest = {}", nimRequest);
    return nimService.play(nimRequest);
  }
}
