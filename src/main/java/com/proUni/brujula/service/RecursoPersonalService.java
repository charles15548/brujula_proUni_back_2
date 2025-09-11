package com.proUni.brujula.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;


public interface RecursoPersonalService {
	public ResponseEntity<Map<String, Object>> listarRecursosPersonales();
	ResponseEntity<Map<String, Object>> listarRecursosPersonalesPorTipo(Long tipoId);

	public ResponseEntity<Map<String, Object>> crearRecursosPersonales(String titulo,String descripcion, String link, MultipartFile img, Long dpId);
    public ResponseEntity<Map<String, Object>> actualizarRecursosPersonales(Long id, String titulo, String descripcion, String link, MultipartFile img,Long dpId) throws Exception;
    public ResponseEntity<Map<String, Object>> eliminarRecursosPersonales(Long id) throws Exception;
    
}


