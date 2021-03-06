package com.example.patrycja.filmbase.template;

import com.example.patrycja.filmbase.request.AddFilmRequest;
import com.example.patrycja.filmbase.request.SignUpRequest;
import com.example.patrycja.filmbase.service.RequestGenerator;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FillBaseTemplate {

    private final String[] USERNAMES = {"alicja", "login_idea", "UpperAndLower", "user1234", "0xd0"};
    private final String[] PASSWORDS = {"easy-one", "hardPassword", "pass1234", "anotherone", "hasl099"};
    private final String[] EMAILS = {"mojemail@op.pl", "randomemail@gmail.com", "taki_email@domena.xd",
            "123niemampomyslu@wp.pl", "jeszczejeden@gmail.com"};

    protected Gson gsonSerialize;
    protected Gson gsonDeserialize;

    protected List<AddFilmRequest> createdRequests;
    protected RequestGenerator requestGenerator;
    protected List<SignUpRequest> userRequests;

    protected void setupParser() {
        gsonSerialize = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateSerializer())
                .create();
        gsonDeserialize = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateDeserializer())
                .create();
    }

    protected void initUsers() {
        userRequests = new ArrayList<>();
        for(int i=0; i<USERNAMES.length; i++) {
            userRequests.add(new SignUpRequest(USERNAMES[i], PASSWORDS[i], EMAILS[i]));
        }
    }

    protected void initFilms() {
        createdRequests = new ArrayList<>();
        requestGenerator = new RequestGenerator();
        for (int i = 0; i < requestGenerator.getCount(); i++) {
            createdRequests.add(requestGenerator.getRequest(i));
        }
    }

    //May replace the @PostConstruct part from Film Controller - will need some alteration before though
    /*@WithMockUser(username = "test", password = "test", roles = {"USER"})
    protected void postFilms() {
        List<String> jsons = new ArrayList<>();
        createdRequests.forEach(filmRequest -> jsons.add(gsonSerialize.toJson(filmRequest)));
        jsons.forEach(json -> {
            try {
                this.mockMvc.perform(post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }*/
}
