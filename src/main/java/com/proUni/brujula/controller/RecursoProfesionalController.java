package com.proUni.brujula.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.proUni.brujula.models.Noticias;
import com.proUni.brujula.service.RecursoProfesionalService;
import com.proUni.brujula.service.NoticiasService;

@RestController
@RequestMapping("/api/herramientaProfesional")
@CrossOrigin(origins = {"http://localhost:3000", "https://soporte2.intelectiasac.com"})
public class RecursoProfesionalController {

    @Autowired
    private RecursoProfesionalService service;

    
    @GetMapping
    public ResponseEntity<Map<String, Object>> listarDesarrolloProfesional(@RequestParam Long tipo) {
        return service.listarHerramientasProfesionalPorTipo(tipo);
    }
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, Object>> crearDesarrolloProfesional(
    		@RequestParam(value ="titulo", required = false) String titulo,
    		@RequestParam(value ="descripcion", required = false) String descripcion,
    		@RequestParam(value ="link", required = false) String link,
    		@RequestParam(value ="video", required = false) String video,
    		@RequestParam(value ="img", required = false) MultipartFile img,
    		@RequestParam(value ="dpId", required = false) Long dpId,
    		@RequestParam(value = "archivo", required = false) MultipartFile archivo
    		) {
        return service.crearHerramientasProfesional(titulo,descripcion,link,video,img,dpId, archivo);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> actualizarDesarrolloProfesional(
    		@PathVariable Long id,
    		@RequestParam(value ="titulo", required = false) String titulo,
    		@RequestParam(value ="descripcion", required = false) String descripcion,
    		@RequestParam(value ="link", required = false) String link,
    		@RequestParam(value ="video", required = false) String video,
    		@RequestParam(value ="img", required = false) MultipartFile img,
    		@RequestParam(value ="dpId", required = false) Long dpId,
    		@RequestParam(value = "archivo", required = false) MultipartFile archivo) throws Exception {
        return service.actualizarHerramientasProfesional(id,titulo,descripcion,link,video,img,dpId,archivo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> eliminar(@PathVariable Long id) {
        return service.eliminarHerramientasProfesional(id);
    }
   
    
}

