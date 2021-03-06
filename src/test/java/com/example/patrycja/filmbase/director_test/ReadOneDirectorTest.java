package com.example.patrycja.filmbase.director_test;

import com.example.patrycja.filmbase.DTO.DirectorDTO;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class ReadOneDirectorTest extends FillBaseTemplate {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void init() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
        setupParser();
        initFilms();
    }

    @Test
    public void checkIfResponseContainsDirectorWithGivenId() throws Exception {
        for (int i = 1; i <= requestGenerator.getCount(); i++) {
            MvcResult mvcResult = this.mockMvc.perform(get("/directors/{id}", i)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn();

            DirectorDTO responseDirector = gsonSerialize.fromJson(mvcResult
                            .getResponse()
                            .getContentAsString(),
                    DirectorDTO.class);
            assertTrue(responseDirector
                    .getFirstName()
                    .equals(requestGenerator
                            .getRequest(i - 1)
                            .getDirectorFirstName()) &&
                    responseDirector
                            .getLastName()
                            .equals(requestGenerator
                                    .getRequest(i - 1)
                                    .getDirectorLastName()
                            ));
        }
    }
}
