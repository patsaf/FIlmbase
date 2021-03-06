package com.example.patrycja.filmbase.controller;

import com.example.patrycja.filmbase.DTO.ActorDTO;
import com.example.patrycja.filmbase.exception.DuplicateException;
import com.example.patrycja.filmbase.exception.InvalidIdException;
import com.example.patrycja.filmbase.model.Actor;
import com.example.patrycja.filmbase.model.Film;
import com.example.patrycja.filmbase.repository.ActorRepository;
import com.example.patrycja.filmbase.repository.FilmRepository;
import com.example.patrycja.filmbase.request.AddActorRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
public class FilmCastController {

    @Autowired
    FilmRepository filmRepository;

    @Autowired
    ActorRepository actorRepository;

    @GetMapping("/films/{id}/cast")
    public List<ActorDTO> getFilmCast(@PathVariable("id") long id) {
        Film film = filmRepository.findById(id);

        if (film == null) {
            throw new InvalidIdException();
        }

        List<ActorDTO> actorDTOs = new ArrayList<>();
        film.getCast()
                .forEach(actor -> actorDTOs.add(new ActorDTO(actor)));
        return actorDTOs;
    }

    @PostMapping("/films/{id}/cast")
    public List<ActorDTO> addActorToCast(@Valid @RequestBody AddActorRequest actorRequest,
                                         @PathVariable("id") long id) {
        Film film = filmRepository.findById(id);
        Actor actor = actorRepository.findByFirstNameAndLastName(
                actorRequest.getFirstName(),
                actorRequest.getLastName());
        Actor finalActor = actor;
        if (actor == null) {
            List<Film> films = new ArrayList<>();
            films.add(film);
            actor = new Actor.ActorBuilder(
                    actorRequest.getFirstName(),
                    actorRequest.getLastName())
                    .dateOfBirth(actorRequest.getDateOfBirth())
                    .films(films)
                    .build();
        } else if (film.getCast()
                .stream()
                .anyMatch(oldActor -> oldActor
                        .getFirstName()
                        .equals(finalActor.getFirstName()) &&
                        oldActor
                                .getLastName()
                                .equals(finalActor.getLastName()))) {
            throw new DuplicateException("Actor already in cast!");
        }
        actorRepository.save(actor);
        film.addActorToCast(actor);
        filmRepository.save(film);
        List<ActorDTO> actorDTOs = new ArrayList<>();
        for (Actor actor1 : film.getCast()) {
            actorDTOs.add(new ActorDTO(actor1));
        }
        return actorDTOs;
    }
}
