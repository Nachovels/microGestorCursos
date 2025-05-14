package com.gestor.gestorcursos.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestor.gestorcursos.model.Curso;
import com.gestor.gestorcursos.model.dto.CursoDto;
import com.gestor.gestorcursos.model.entity.CursoEntity;
import com.gestor.gestorcursos.repository.CursoRepository;

@Service
public class CursoService {
    @Autowired
    private CursoRepository cursoRepository;
    
    private final List<CursoEntity> cursos = new ArrayList<>();
        
    public String crearCurso(Curso curso){
        try {
            Boolean existe = cursoRepository.existsById(curso.getIdCurso());
            if(!existe){
                CursoEntity cursoNuevo = new CursoEntity();
                cursoNuevo.setIdCurso(curso.getIdCurso());
                cursoNuevo.setNombreCurso(curso.getNombreCurso());
                cursoNuevo.setDescripcion(curso.getDescripcion());
                cursoNuevo.setInstructorAsignado(curso.getInstructorAsignado());
                
                cursoRepository.save(cursoNuevo);
                
                return "Curso creado correctamente";
            }
            return "El curso ya existe";
        } catch (Exception e) {
            return "Error al crear el curso: " + e.getMessage();
        }
    }

    public String actualizarCurso(Curso curso){
        try {
            Optional<CursoEntity> cursoExistente = cursoRepository.findById(curso.getIdCurso());
            if (cursoExistente.isPresent()){
                CursoEntity cursoActualizado = cursoExistente.get();
                cursoActualizado.setNombreCurso(curso.getNombreCurso());
                cursoActualizado.setDescripcion(curso.getDescripcion());
                cursoActualizado.setInstructorAsignado(curso.getInstructorAsignado());

                cursoRepository.save(cursoActualizado);
                return "Curso actualizado correctamente";
            }
            return "";
        } catch (Exception e) {
            return "Error al actualizar el curso: " + e.getMessage();
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

    public Curso obtenerCurso(String idCurso){
        try {
            CursoEntity curso = cursoRepository.findByIdCurso(idCurso);
            if (curso!=null){
                Curso cursoNuevo = new Curso(
                    curso.getIdCurso(),
                    curso.getNombreCurso(),
                    curso.getDescripcion(),
                    curso.getInstructorAsignado()
                );
                return cursoNuevo;
            }
            return null;
        } catch (Exception e) {
            return null;
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
}