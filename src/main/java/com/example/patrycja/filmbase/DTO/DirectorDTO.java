package com.example.patrycja.filmbase.DTO;

import com.example.patrycja.filmbase.model.Director;
import com.example.patrycja.filmbase.model.Film;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DirectorDTO {

    private long id;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private List<FilmBriefDTO> films;

    public DirectorDTO() {}

    public DirectorDTO(Director director) {
        this.id = director.getId();
        this.firstName = director.getFirstName();
        this.lastName = director.getLastName();
        this.dateOfBirth = director.getDateOfBirth();
        films = new ArrayList<>();
        for(Film film : director.getFilms()) {
            films.add(new FilmBriefDTO(film));
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public List<FilmBriefDTO> getFilms() {
        return films;
    }

    public void setFilms(List<FilmBriefDTO> films) {
        this.films = films;
    }

    public Boolean checkIfDataEquals(DirectorDTO directorDTO) {
        if(firstName.equals(directorDTO.getFirstName()) &&
                lastName.equals(directorDTO.getLastName()) &&
                dateOfBirth == directorDTO.getDateOfBirth() &&
                films.equals(directorDTO.getFilms())) {
            return true;
        }
        return false;
    }
}