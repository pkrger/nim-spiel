package de.krueger.pierre.nimspiel.hello;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(HelloController.class)
public class HelloControllerTest {

  @Autowired
  private MockMvc mockMvc;

  /**
   * Testet die Hello-Methode und erwartet einen 200 OK.
   */
  @Test
  public void helloTest() throws Exception {
    mockMvc.perform(get("/")).andExpect(status().isOk());
  }
}
