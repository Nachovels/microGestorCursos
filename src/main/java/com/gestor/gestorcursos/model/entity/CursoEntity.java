package com.gestor.gestorcursos.model.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class CursoEntity {
    @Id
    private String idCurso;
    @Column(name= "nombreCurso")
    private String nombreCurso;
    @Column(name= "descripcion")
    private String descripcion;
    @Column(name= "instructorAsignado")
    private String instructorAsignado;
}