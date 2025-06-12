package com.gestor.gestorcursos.controller;

import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.gestor.gestorcursos.model.Curso;
import com.gestor.gestorcursos.model.dto.CursoDto;
import com.gestor.gestorcursos.model.entity.CursoEntity;
import com.gestor.gestorcursos.repository.CursoRepository;
import com.gestor.gestorcursos.service.CursoService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
public class CursoController {

    private final CursoRepository cursoRepository;
    @Autowired
    private CursoService cursoService;

    CursoController(CursoRepository cursoRepository){
        this.cursoRepository = cursoRepository;
    }

    @Operation(summary = "Crear un nuevo curso")
    @PostMapping("/crearCurso")
    public ResponseEntity<String> crearCurso(@RequestBody Curso curso){
        return cursoService.crearCurso(curso);
    }

    @Operation(summary = "Actualizar un curso existente")
    @PutMapping("/actualizarCurso")
    public ResponseEntity<String> actualizarCurso(@RequestBody Curso curso){
        return cursoService.actualizarCurso(curso);
    }

    @Operation(summary = "Eliminar un curso existente")
    @DeleteMapping("/eliminarCurso/{idCurso}")
    public ResponseEntity<String> eliminarCurso(@PathVariable String idCurso){
        return cursoService.eliminarCurso(idCurso);
    }

    @Operation(summary = "Obtener todos los cursos existentes")
    @GetMapping("/cursos")
    public ResponseEntity<List<CursoEntity>> obtenerCursos(){
        return cursoService.obtenerCursos();
    }

    @Operation(summary = "Obtener un curso por su ID")
    @GetMapping("/cursos/{idCurso}")
        public ResponseEntity<String> traerCurso(@PathVariable String idCurso){
            return cursoService.obtenerCurso(idCurso);
        }
    
    @Operation(summary = "Obtener un curso DTO por su ID")
    @GetMapping("/cursos/dto/{idCurso}")
        public ResponseEntity<CursoDto> traerCursoDto(@PathVariable String idCurso){
            if(cursoService.obtenerCursoDto(idCurso) != null){
                return ResponseEntity.ok(cursoService.obtenerCursoDto(idCurso));
            }
            return ResponseEntity.notFound().build();
        }

    @GetMapping("/cursos/cantidad")
        public long contarCursos(){
            return cursoService.contarCursos();
        }
}