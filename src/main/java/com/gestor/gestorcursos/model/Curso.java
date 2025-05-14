package com.gestor.gestorcursos.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Curso {
    private String idCurso, nombreCurso, descripcion;
    private String instructorAsignado;
}