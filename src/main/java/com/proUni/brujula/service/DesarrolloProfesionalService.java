package com.proUni.brujula.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.proUni.brujula.models.DesarrolloPersonal;

public interface DesarrolloProfesionalService {

	public ResponseEntity<Map<String, Object>> listarDesarrolloProfesional();

}


