package de.krueger.pierre.nimspiel.nim;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class NimServiceTest {

  @InjectMocks
  private NimService nimService;


  private NimResponse nimResponse = new NimResponse();
  private NimRequest nimRequest = new NimRequest();

  /**
   * Testet die Init-Methode
   */
  @Test
  public void initTest() {
    nimResponse.setMatchesLeft(13);
    nimResponse.setResult("Das Spiel hat begonnen, du bist dran");

    NimResponse nimResponseActual = nimService.init();

    assertEquals(nimResponseActual.getResult(), nimResponse.getResult());
    assertEquals(nimResponseActual.getMatchesLeft(), nimResponse.getMatchesLeft());
  }

  /**
   * Testet den Fall, dass der Spieler kein Holz nehmen will.
   */
  @Test
  public void playTestPlayerPicksZero() {
    nimRequest.setCountMatches(0);

    ReflectionTestUtils.setField(nimService, "matchesLeft", 1);
    NimResponse nimResponseActual = nimService.play(nimRequest);

    assertEquals("Du musst mindestens 1 Streichholz ziehen!", nimResponseActual.getResult());
  }

  /**
   * Testet den Fall, dass der Spieler genau 1 Holz zur Verfügung hat, aber 2 nehmen will.
   */
  @Test
  public void playTestPlayerPicksMoreThanAvailable() {
    ReflectionTestUtils.setField(nimService, "matchesLeft", 1);
    nimRequest.setCountMatches(2);

    NimResponse nimResponseActual = nimService.play(nimRequest);

    assertEquals("Du darfst nicht mehr Hölzer ziehen, als vorhanden sind!", nimResponseActual.getResult());
  }

  /**
   * Testet den Fall, dass der Spieler mehr als 3 Hölzer nehmen will.
   */
  @Test
  public void playTestPlayerPicksMoreThanAllowed() {
    ReflectionTestUtils.setField(nimService, "matchesLeft", 13);
    nimRequest.setCountMatches(4);

    NimResponse nimResponseActual = nimService.play(nimRequest);

    assertEquals("Du darfst nur 1, 2 oder 3 Hölzer auf einmal ziehen!", nimResponseActual.getResult());
  }

  /**
   * Testet den Fall, dass der Spieler genau 1 Holz zur Verfügung hat, 1 nimmt und verliert.
   */
  @Test
  public void playTestPlayerPicksAndLoses() {
    ReflectionTestUtils.setField(nimService, "matchesLeft", 1);
    nimRequest.setCountMatches(1);

    NimResponse nimResponseActual = nimService.play(nimRequest);
    assertEquals("Du hast verloren.", nimResponseActual.getResult());
  }

  /**
   * Testet den Fall, dass der Computer genau 1 Holz zur Verfügung hat, 1 nimmt und verliert.
   */
  @Test
  public void playTestComputerPicksAndLoses() {
    ReflectionTestUtils.setField(nimService, "matchesLeft", 2);
    nimRequest.setCountMatches(1);

    NimResponse nimResponseActual = nimService.play(nimRequest);
    assertEquals("Der Computer hat verloren.", nimResponseActual.getResult());
  }

  /**
   * Testet den normalen Spielablauf
   */
  @Test
  public void playTestNormal() {
    ReflectionTestUtils.setField(nimService, "matchesLeft", 3);
    nimRequest.setCountMatches(1);

    NimResponse nimResponseActual = nimService.play(nimRequest);
    assertEquals("Der Computer hat 1 Streichhölzer gezogen. Du bist dran.", nimResponseActual.getResult());
  }

  /**
   * Testet den Fall, dass der Computer mehr als 8 Hölzer zur Verfügung hat. Er müsste 1, 2 oder 3 Hölzer nehmen.
   */
  @Test
  public void playTestComputerSelectionAboveEight() {
    ReflectionTestUtils.setField(nimService, "matchesLeft", 13);
    nimRequest.setCountMatches(1);

    NimResponse nimResponseActual = nimService.play(nimRequest);
    Assertions.assertNotNull(nimResponseActual.getResult());
  }

  /**
   * Testet den Fall, dass der Computer genau 8 Hölzer zur Verfügung hat. Er müsste 3 nehmen, um den Spieler auf 5 zu zwingen.
   */
  @Test
  public void playTestComputerSelectionAboveSix() {
    ReflectionTestUtils.setField(nimService, "matchesLeft", 9);
    nimRequest.setCountMatches(1);

    NimResponse nimResponseActual = nimService.play(nimRequest);
    assertEquals("Der Computer hat 3 Streichhölzer gezogen. Du bist dran.", nimResponseActual.getResult());
  }

  /**
   * Testet den Fall, dass der Computer zwischen 2 und 4 Hölzer zur Verfügung hat. Er müsste so viele nehmen, um den Spieler auf 1 zu zwingen.
   */
  @Test
  public void playTestComputerSelectionBetweenTwoAndFour() {
    ReflectionTestUtils.setField(nimService, "matchesLeft", 4);
    nimRequest.setCountMatches(1);

    NimResponse nimResponseActual = nimService.play(nimRequest);
    assertEquals("Der Computer hat 2 Streichhölzer gezogen. Du bist dran.", nimResponseActual.getResult());
  }
}
