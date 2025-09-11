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
import com.proUni.brujula.models.PerfilesEstudiantes;
import com.proUni.brujula.service.NoticiasService;
import com.proUni.brujula.service.PerfilService;

@RestController
@RequestMapping("/api/perfil")
@CrossOrigin(origins = {"http://localhost:3000", "https://soporte2.intelectiasac.com"})
public class PerfilController {

    @Autowired
    private PerfilService service;

    
    @GetMapping("/auth/{authId}")
    public PerfilesEstudiantes obtenerPerfilPorAuthId(@PathVariable Long authId) {
        return service.obtenerPerfilPorAuthId(authId);
        
    }


}

