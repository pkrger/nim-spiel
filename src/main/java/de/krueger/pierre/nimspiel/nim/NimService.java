package de.krueger.pierre.nimspiel.nim;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Random;

@Slf4j
@Service
public class NimService {

  private int matchesLeft;

  /**
   * Initialisiert das Spiel
   *
   * @return Antwort an den Spieler mit der initialen Befüllung des Streichholzstapels
   */
  public NimResponse init() {
    matchesLeft = 13;
    NimResponse nimResponse = new NimResponse();
    nimResponse.setMatchesLeft(matchesLeft);
    nimResponse.setResult("Das Spiel hat begonnen, du bist dran");
    return nimResponse;
  }

  /**
   * Verarbeitende Methode des Spiels
   *
   * @param nimRequest Anfrage des Spielers mit Anzahl der gezogenen Streichhölzern
   * @return Antwort des Spiels mit noch verfügbaren Streichhölzern und Spielanweisung
   */
  public NimResponse play(NimRequest nimRequest) {
    // Plausibilisieren wir kurz vorab die Spieler-Eingaben
    if (nimRequest.getCountMatches() == 0) {
      return new NimResponse(matchesLeft, "Du musst mindestens 1 Streichholz ziehen!");
    } else if (nimRequest.getCountMatches() > matchesLeft) {
      return new NimResponse(matchesLeft, "Du darfst nicht mehr Hölzer ziehen, als vorhanden sind!");
    } else if (nimRequest.getCountMatches() > 3) {
      return new NimResponse(matchesLeft, "Du darfst nur 1, 2 oder 3 Hölzer auf einmal ziehen!");
    }

    matchesLeft -= nimRequest.getCountMatches();
    log.debug("matchesLeft (nach Spieler) = " + matchesLeft);

    if (matchesLeft <= 0) {
      NimResponse nimResponse = new NimResponse();
      nimResponse.setMatchesLeft(matchesLeft);
      nimResponse.setResult("Du hast verloren.");
      return nimResponse;
    }

    /*
     * Die Logik für einen gewinnorientierten Computer-Spielzug ist folgende:
     * Der Computer muss mit seinem Zug den Spieler dazu zwingen, entweder noch 1 oder 5 Streichhölzer zur Auswahl zu haben.
     * Hat der Computer mehr als 8 Hölzer zur Verfügung kann er zufällig aus 1, 2 oder 3 wählen
     * Hat der Computer zwischen 6 und 8 Hölzer übrig, zwingt er den Spieler auf 5 Hölzer herunter
     * Hat der Computer zwischen 2 und 4 Hölzer übrig, zwingt der den Spieler auf 1 Holz herunter
     * 5 ist dabei eine echt doofe Zahl, denn der Spieler ist anschließend immer noch 1x mehr dran als der Computer und verliert damit.
     */

    int[] computerSelection;
    if (matchesLeft > 8) {
      computerSelection = new int[]{1, 2, 3};
      log.debug("Init Array 1, 2, 3");
    } else if (matchesLeft >= 6) {
      computerSelection = new int[]{matchesLeft - 5};
      log.debug("Init Array, matchesLeft - 5 = " + (matchesLeft - 5));
    } else if (matchesLeft >= 2 && matchesLeft <= 4) {
      computerSelection = new int[]{matchesLeft - 1};
      log.debug("Init Array, matchesLeft - 1 = " + (matchesLeft - 1));
    } else {
      computerSelection = new int[]{1};
    }

    int computerPick = computerSelection[new Random().nextInt(computerSelection.length)];
    log.debug("computerPick = " + computerPick);
    matchesLeft -= computerPick;
    log.debug("matchesLeft (nach Computer) = " + matchesLeft);

    if (matchesLeft <= 0) {
      NimResponse nimResponse = new NimResponse();
      nimResponse.setMatchesLeft(matchesLeft);
      nimResponse.setResult("Der Computer hat verloren.");
      return nimResponse;
    }

    NimResponse nimResponse = new NimResponse();
    nimResponse.setResult("Der Computer hat " + computerPick + " Streichhölzer gezogen. Du bist dran.");
    nimResponse.setMatchesLeft(matchesLeft);
    return nimResponse;
  }

}
