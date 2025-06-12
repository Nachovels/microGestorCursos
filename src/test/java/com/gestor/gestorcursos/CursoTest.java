package com.gestor.gestorcursos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.gestor.gestorcursos.model.Curso;
import com.gestor.gestorcursos.model.entity.CursoEntity;
import com.gestor.gestorcursos.repository.CursoRepository;
import com.gestor.gestorcursos.service.CursoService;

public class CursoTest {
    
    @Mock
    private CursoRepository cursoRepository;

    @InjectMocks
    private CursoService cursoService;
    
    private Curso curso;
    private CursoEntity cursoEntity;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
        curso = new Curso("A1", "Curso de prueba", "Curso de prueba para testing", "Instructor Test");
        cursoEntity = new CursoEntity();
        cursoEntity.setIdCurso("A1");
        cursoEntity.setNombreCurso("Curso de prueba");
        cursoEntity.setDescripcion("Curso de prueba para testing");
        cursoEntity.setInstructorAsignado("Instructor Test");
    }

    @Test
    public void testCrearCursoNuevo(){
        when(cursoRepository.existsById(curso.getIdCurso())).thenReturn(false);
        when(cursoRepository.save(Mockito.any(CursoEntity.class))).thenReturn(cursoEntity);

        ResponseEntity<String> result = cursoService.crearCurso(curso);
        assertEquals("Curso creado correctamente", result.getBody());
    }

    @Test
    public void testCrearCursoExistente(){
        when(cursoRepository.existsById(curso.getIdCurso())).thenReturn(true);

        ResponseEntity<String> result = cursoService.crearCurso(curso);
        assertEquals("El curso con el ID " + curso.getIdCurso() + " ya existe", result.getBody());
    }

    @Test
    public void testActualizarCursoExistente() {
        when(cursoRepository.findById(curso.getIdCurso())).thenReturn(java.util.Optional.of(cursoEntity));
        when(cursoRepository.save(Mockito.any(CursoEntity.class))).thenReturn(cursoEntity);
        
        ResponseEntity<String> result = cursoService.actualizarCurso(curso);
        assertEquals("Curso actualizado correctamente", result.getBody());
    }

    @Test
    public void testActualizarCursoNoExistente() {
        when(cursoRepository.findById("A2")).thenReturn(java.util.Optional.empty());
        Curso cursoNoExistente = new Curso("A2", "Otro curso", "Descripción", "Instructor");
        ResponseEntity<String> result = cursoService.actualizarCurso(cursoNoExistente);

        assertEquals(404, result.getStatusCode().value());
    }

    
}
