package com.gestor.gestorcursos.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CursoDto {
    private String nombreCurso, descripcion, instructorAsignado;

    
}