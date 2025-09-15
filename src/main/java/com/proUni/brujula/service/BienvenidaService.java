package com.proUni.brujula.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.proUni.brujula.models.DesarrolloPersonal;

public interface BienvenidaService {

	public ResponseEntity<Map<String, Object>> listarBienvenida();
	public ResponseEntity<Map<String, Object>> crearBienvenida(String titulo, String descripcion);
	public ResponseEntity<Map<String, Object>> editarBienvenida(Long id,String titulo, String descripcion);
	public ResponseEntity<Map<String, Object>> eliminarBienvenida(Long id);
	

}


