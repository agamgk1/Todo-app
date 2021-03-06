package io.github.agamgk1.adapter;

import io.github.agamgk1.model.TaskGroup;
import io.github.agamgk1.model.TaskGroupRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

//repozytorium służy do komunikacji z bazą danych.
//Adnotacja repozytorium
// rozszerzenie taskRepository z metodami publicznymi
@Repository
interface SqlTaskGroupRepository extends JpaRepository<TaskGroup, Integer>, TaskGroupRepository {

    // native query =false(domyślne) - powoduje ze zapytanie jest na encjach a nie na tabelach bazodanowych
    // join fetch - rozwiazanie n+1 selectow. W jednym zapytaniu prosimy zeby wsztystkie taski zwiazane z grupami byly pobrane
    @Override
    @Query("select distinct g from TaskGroup g join fetch g.tasks")
    List<TaskGroup> findAll();


}
