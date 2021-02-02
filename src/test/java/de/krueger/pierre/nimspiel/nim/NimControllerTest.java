package de.krueger.pierre.nimspiel.nim;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(NimController.class)
public class NimControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private NimService nimService;

  @Autowired
  private ObjectMapper objectMapper;

  private NimRequest nimRequest = new NimRequest();

  @Before
  public void setUp() {
    nimRequest.setCountMatches(1);
  }

  /**
   * Testet die Init-Methode und erwartet einen 200 OK.
   */
  @Test
  public void initTest() throws Exception {
    given(nimService.init()).willReturn(new NimResponse(0, "Test"));

    mockMvc.perform(get("/play"))
      .andExpect(status().isOk());
  }

  /**
   * Testet die Play-Methode und erwartet einen 200 OK.
   */
  @Test
  public void playTest() throws Exception {
    given(nimService.play(nimRequest)).willReturn(new NimResponse(0, "Test"));

    mockMvc.perform(post("/play").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsBytes(nimRequest)))
      .andExpect(status().isOk());
  }

}
