package io.github.agamgk1.adapter;

import io.github.agamgk1.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

//repozytorium służy do komunikacji z bazą danych.
//Adnotacja repozytorium
// rozszerzenie taskRepository z metodami publicznymi
@Repository
interface SqlProjectsRepository extends JpaRepository<Project, Integer>, ProjectsRepository {

    // native query =false(domyślne) - powoduje ze zapytanie jest na encjach a nie na tabelach bazodanowych
    // join fetch - rozwiazanie n+1 selectow. W jednym zapytaniu prosimy zeby wsztystkie taski zwiazane z grupami byly pobrane
    @Override
    @Query("select distinct p from Project p join fetch p.projectSteps")
    List<Project> findAll();
}
