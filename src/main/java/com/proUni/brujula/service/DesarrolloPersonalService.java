package com.proUni.brujula.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.proUni.brujula.models.DesarrolloPersonal;


public interface DesarrolloPersonalService {
	public ResponseEntity<Map<String, Object>> listarDesarrolloPersonal();
}


