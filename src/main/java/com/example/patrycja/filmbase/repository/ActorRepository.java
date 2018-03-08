package com.example.patrycja.filmbase.repository;

import com.example.patrycja.filmbase.model.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Long> {
    Actor findByFirstNameAndLastName(String firstName, String lastName);

    Actor findById(long id);

    @Transactional
    @Modifying
    @Query("update Actor a set a.dateOfBirth = ?1 where a.id = ?2")
    void setDateOfBirthById(LocalDate dateOfBirth, long id);
}
