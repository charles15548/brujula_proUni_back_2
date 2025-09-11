package com.proUni.brujula.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;


public interface NoticiasService {
	public ResponseEntity<Map<String, Object>> listarBaseNoticias();
	
	public ResponseEntity<Map<String, Object>> crearNoticia(String titulo,String gancho, String contenido,String fuente, MultipartFile imagen);
    public ResponseEntity<Map<String, Object>> actualizarNoticia(Long id,String gancho, String titulo, String contenido,String fuente,MultipartFile imagen);
    public ResponseEntity<Map<String, Object>> eliminarNoticia(Long id);
    
    /* NOTICIA */
    ResponseEntity<Map<String, Object>> listarNoticias(Long userId);
    ResponseEntity<Map<String, Object>> toggleLike(Long noticiaId, Long estudianteId);
}


