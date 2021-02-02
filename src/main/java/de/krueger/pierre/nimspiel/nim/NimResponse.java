package de.krueger.pierre.nimspiel.nim;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NimResponse {
  private int matchesLeft;
  private String result;
}
