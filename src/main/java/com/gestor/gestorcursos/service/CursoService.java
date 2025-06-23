package com.gestor.gestorcursos.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    public ResponseEntity<String> eliminarCurso(String idCurso){
        try {
            if(cursoRepository.existsById(idCurso)){
                cursoRepository.deleteById(idCurso);
                return ResponseEntity.ok("Curso eliminado correctamente");
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al eliminar el curso: " + e.getMessage());
        }
    }

    public ResponseEntity<List<CursoEntity>> obtenerCursos(){
        try {
            List<CursoEntity> cursos = cursoRepository.findAll();
            if (cursos.isEmpty()) {
                return ResponseEntity.noContent().header("Mensaje", "No se encontró data").build();
            }
            return ResponseEntity.ok(cursos);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    public ResponseEntity<String> obtenerCursoId(String idCurso){
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
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Curso no encontrado con el ID: " + idCurso);
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

    public List<Curso> obtenerCursosPorIds(List<String> ids) {
        List<CursoEntity> entidades = cursoRepository.findAllById(ids);

        return entidades.stream().map(entidad -> {
            Curso curso = new Curso();
            curso.setIdCurso(entidad.getIdCurso());
            curso.setNombreCurso(entidad.getNombreCurso());
            curso.setDescripcion(entidad.getDescripcion());
            return curso;
        }).collect(Collectors.toList());
    }
    
}