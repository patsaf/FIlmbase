package com.example.patrycja.filmbase.actor_test;

import com.example.patrycja.filmbase.DTO.FilmBriefDTO;
import com.example.patrycja.filmbase.request.AddActorRequest;
import com.example.patrycja.filmbase.template.FillBaseTemplate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class AddNewActorTest extends FillBaseTemplate {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;
    private AddActorRequest actorRequest;

    @Before
    public void init() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
        setupParser();
        initFilms();
        List<FilmBriefDTO> filmBriefDTOList = new ArrayList<>();
        FilmBriefDTO film1 = new FilmBriefDTO();
        film1.setTitle("Lady Bird");
        film1.setProductionYear(2017);
        filmBriefDTOList.add(film1);
        actorRequest = new AddActorRequest
                .AddActorRequestBuilder("Laurie", "Metcalf")
                .films(filmBriefDTOList)
                .dateOfBirth(LocalDate.of(1955, Month.MAY, 26))
                .build();
    }

    @Test
    @WithMockUser(username = "test", password = "test", roles = "USER")
    public void addValidActor() throws Exception {
        this.mockMvc.perform(post("/actors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gsonSerialize.toJson(actorRequest)))
                .andExpect(status().isCreated());
    }
}
