package com.proUni.brujula.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.proUni.brujula.models.Noticias;
import com.proUni.brujula.models.PerfilesEstudiantes;

public interface PerfilService {
	public ResponseEntity<Map<String, Object>> listarPerfil();
	
	public ResponseEntity<Map<String, Object>> crearPerfil(String nombre,String apellido, String carrera,String biografia,String proyectoVida,String urlCv,  MultipartFile foto);
    public ResponseEntity<Map<String, Object>> actualizarPerfil(Long id,String nombre,String apellido, String carrera,String biografia,String proyectoVida,String urlCv,  MultipartFile foto);
    public ResponseEntity<Map<String, Object>> eliminarPerfil(Long id);
    
    
    PerfilesEstudiantes obtenerPerfilPorAuthId(Long authId);
    
    ResponseEntity<Map<String, Object>> toggleLike(Long noticiaId, Long estudianteId);
}


