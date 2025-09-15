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

import com.proUni.brujula.models.DesarrolloPersonal;
import com.proUni.brujula.service.BienvenidaService;
import com.proUni.brujula.service.DesarrolloPersonalService;

@RestController
@RequestMapping("/api/bienvenida")
@CrossOrigin(origins = {"http://localhost:3000", "https://soporte2.intelectiasac.com"})
public class BienvenidaController {

    @Autowired
    private BienvenidaService service;

    @GetMapping
    public ResponseEntity<Map<String, Object>> listarBienvenida() {
        return service.listarBienvenida();
    }
    @PostMapping
    public ResponseEntity<Map<String, Object>> crearBienvenida(
    		@RequestParam("titulo") String titulo,
    		@RequestParam("descripcion") String descripcion) {
        return service.crearBienvenida(titulo,descripcion);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> editarBienvenida( @PathVariable Long id,
    		@RequestParam("titulo") String titulo,
    		@RequestParam("descripcion") String descripcion) {
        return service.editarBienvenida(id,titulo,descripcion);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> eliminarBienvenida( @PathVariable Long id
    		) {
        return service.eliminarBienvenida(id);
    }


}

