package com.gestor.gestorcursos.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.gestor.gestorcursos.model.Curso;
import com.gestor.gestorcursos.model.dto.CursoDto;
import com.gestor.gestorcursos.model.entity.CursoEntity;
import com.gestor.gestorcursos.repository.CursoRepository;

@Service
public class CursoService {
    @Autowired
    private CursoRepository cursoRepository;
    
        
    @SuppressWarnings("null")
    public ResponseEntity<String> crearCurso(Curso curso){
        try {
            Boolean existe = cursoRepository.existsById(curso.getIdCurso());
            if(curso.getIdCurso().isEmpty() || curso.getNombreCurso().isEmpty() || curso.getDescripcion().isEmpty() || curso.getInstructorAsignado().isEmpty()){
                
                return ResponseEntity.badRequest().body("Debe ingresar todos los campos requeridos");
            }
            if(!existe){
                CursoEntity cursoNuevo = new CursoEntity();
                cursoNuevo.setIdCurso(curso.getIdCurso());
                cursoNuevo.setNombreCurso(curso.getNombreCurso());
                cursoNuevo.setDescripcion(curso.getDescripcion());
                cursoNuevo.setInstructorAsignado(curso.getInstructorAsignado());
                
                cursoRepository.save(cursoNuevo);
            }
            else {
                return ResponseEntity.status(org.springframework.http.HttpStatus.CONFLICT).body("El curso con el ID " + curso.getIdCurso() + " ya existe");
            }
            return ResponseEntity.created(null).body("Curso creado correctamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al crear el curso: " + e.getMessage());
        }
    }

    @SuppressWarnings("null")
    public ResponseEntity<String> actualizarCurso(Curso curso){
        try {
            Optional<CursoEntity> cursoExistente = cursoRepository.findById(curso.getIdCurso());
            if (cursoExistente.isPresent()){
                CursoEntity cursoActualizado = cursoExistente.get();
                cursoActualizado.setNombreCurso(curso.getNombreCurso());
                cursoActualizado.setDescripcion(curso.getDescripcion());
                cursoActualizado.setInstructorAsignado(curso.getInstructorAsignado());

                cursoRepository.save(cursoActualizado);
                return ResponseEntity.created(null).body("Curso actualizado correctamente");
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al actualizar el curso: " + e.getMessage());
        }
    }

    public String eliminarCurso(String idCurso){
        try {
            if(cursoRepository.existsById(idCurso)){
                cursoRepository.deleteById(idCurso);
                return "Curso eliminado correctamente";
            }
            return "El curso no existe";
        } catch (Exception e) {
            return "Error al eliminar el curso: "+ e.getMessage();
        }
    }

    public List<CursoEntity> obtenerCursos(){
        return cursoRepository.findAll();
    }

    public ResponseEntity<String> obtenerCurso(String idCurso){
        try {
            CursoEntity curso = cursoRepository.findByIdCurso(idCurso);
            if (curso!=null){
                Curso cursoNuevo = new Curso(
                    curso.getIdCurso(),
                    curso.getNombreCurso(),
                    curso.getDescripcion(),
                    curso.getInstructorAsignado()
                );
                return ResponseEntity.ok(cursoNuevo.toString());
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al obtener el curso: " + e.getMessage());
        }
    }

    public CursoDto obtenerCursoDto(String idCurso){
        try {
            CursoEntity curso = cursoRepository.findByIdCurso(idCurso);
            CursoDto cursoNuevo = new CursoDto(
                curso.getNombreCurso(),
                curso.getDescripcion(),
                curso.getInstructorAsignado()
            );
            return cursoNuevo;
        } catch (Exception e) {
            return null;
        }
    }

    public long contarCursos(){
        return cursoRepository.count();
    }
    
}