package com.gestor.gestorcursos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.List;

import org.apache.catalina.connector.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.gestor.gestorcursos.model.Curso;
import com.gestor.gestorcursos.model.dto.CursoDto;
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

    //Test para crear un curso nuevo
    @Test
    public void testCrearCursoNuevo(){
        when(cursoRepository.existsById(curso.getIdCurso())).thenReturn(false);
        when(cursoRepository.save(Mockito.any(CursoEntity.class))).thenReturn(cursoEntity);

        ResponseEntity<String> result = cursoService.crearCurso(curso);
        assertEquals("Curso creado correctamente", result.getBody());
    }
    //Test para verificar el no crear cursos existentes
    @Test
    public void testCrearCursoExistente(){
        when(cursoRepository.existsById(curso.getIdCurso())).thenReturn(true);

        ResponseEntity<String> result = cursoService.crearCurso(curso);
        assertEquals("El curso con el ID " + curso.getIdCurso() + " ya existe", result.getBody());
    }
    //Test para actualizar un curso existente
    @Test
    public void testActualizarCursoExistente() {
        when(cursoRepository.findById(curso.getIdCurso())).thenReturn(java.util.Optional.of(cursoEntity));
        when(cursoRepository.save(Mockito.any(CursoEntity.class))).thenReturn(cursoEntity);
        
        ResponseEntity<String> result = cursoService.actualizarCurso(curso);
        assertEquals("Curso actualizado correctamente", result.getBody());
    }
    //Test para verificar el no actualizar un curso que no existe
    @Test
    public void testActualizarCursoNoExistente() {
        when(cursoRepository.findById("A2")).thenReturn(java.util.Optional.empty());
        Curso cursoNoExistente = new Curso("A2", "Otro curso", "Descripción", "Instructor");
        ResponseEntity<String> result = cursoService.actualizarCurso(cursoNoExistente);

        assertEquals(404, result.getStatusCode().value());
    }
    //Test para eliminar un curso existente
    @Test 
    public void testEliminarCursoExistente() {
        when(cursoRepository.existsById(curso.getIdCurso())).thenReturn(true);
        ResponseEntity<String> result = cursoService.eliminarCurso(curso.getIdCurso());
        
        assertEquals("Curso eliminado correctamente", result.getBody());
    }
    //Test para verificar el no eliminar un curso que no existe
    @Test
    public void testEliminarCursoNoExistente() {
        when(cursoRepository.existsById("A2")).thenReturn(false);
        ResponseEntity<String> result = cursoService.eliminarCurso("A2");   
        assertEquals(404, result.getStatusCode().value());
    }

    //Test para obtener todos los cursos
    @SuppressWarnings("null")
    @Test
    public void testObtenerCursos() {
        when(cursoRepository.findAll()).thenReturn(List.of(cursoEntity));
        ResponseEntity<List<CursoEntity>> result = cursoService.obtenerCursos();
        assertNotNull(result);
        assertNotNull(result.getBody());
        assertEquals(1, result.getBody().size());
        assertEquals("A1", result.getBody().get(0).getIdCurso());
        assertEquals("Curso de prueba", result.getBody().get(0).getNombreCurso());
    }

    //Test para obtener un curso por su ID
    @Test
    public void testObtenerCursoporId() {
        when(cursoRepository.findByIdCurso("A1")).thenReturn(cursoEntity);
        when(cursoRepository.existsById("A1")).thenReturn(true);
        ResponseEntity<String> result = cursoService.obtenerCursoId("A1");
        assertNotNull(result);
        assertEquals(200, result.getStatusCode().value());
    }

    //Test para verificar el no obtener un curso que no existe
    @Test
    public void testObtenerCursoNoExistente() {
        when(cursoRepository.findByIdCurso("A2")).thenReturn(null);
        when(cursoRepository.existsById("A2")).thenReturn(false);
        ResponseEntity<String> result = cursoService.obtenerCursoId("A2");
        assertNotNull(result);
        assertEquals(404, result.getStatusCode().value());
    }
        
    @Test
    public void testObtenerCursoDto() {
        when(cursoRepository.findByIdCurso("A1")).thenReturn(cursoEntity);
        when(cursoRepository.existsById("A1")).thenReturn(true);
        CursoDto result = cursoService.obtenerCursoDto("A1");
        assertNotNull(result);
        assertEquals("Curso de prueba para testing", result.getNombreCurso());
        
    }
}
