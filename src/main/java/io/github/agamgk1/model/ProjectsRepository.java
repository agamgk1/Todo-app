package io.github.agamgk1.model;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

//Repozytorium z publicznymi metodami. Pozwala na ukrycie springData
public interface ProjectsRepository {

    List<Project> findAll();

    Page<Project> findAll(Pageable pageable);

    Optional<Project> findById(Integer id);

    Project save(Project entity);


}
