package com.gestor.gestorcursos.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gestor.gestorcursos.model.entity.CursoEntity;


@Repository
public interface CursoRepository extends JpaRepository<CursoEntity, String>{
    long count();
    CursoEntity findByIdCurso(String idCurso);
    Boolean existsByIdCurso(String idCurso);
    void deleteByIdCurso(String idCurso);
}