package com.proUni.brujula.serviceImplement;


import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.proUni.brujula.models.DesarrolloPersonal;
import com.proUni.brujula.repository.DesarrolloPersonalRepository;
import com.proUni.brujula.service.DesarrolloPersonalService;

@Service
public class DesarrolloPersonalSI implements DesarrolloPersonalService{

	@Autowired
	private DesarrolloPersonalRepository dao;
	
	
	

	@Override
	public ResponseEntity<Map<String, Object>> listarDesarrolloPersonal() {
		Map<String,Object> respuesta = new HashMap<>();	
		List<DesarrolloPersonal> desarrollo_Personal = dao.findAll();
		
		if(!desarrollo_Personal.isEmpty()) {
			respuesta.put("mensaje", "Lista de DP");
			respuesta.put("desarrollo_Personal", desarrollo_Personal);
			respuesta.put("status", HttpStatus.OK);
			respuesta.put("fecha", new Date());	
			return ResponseEntity.status(HttpStatus.OK).body(respuesta);
		}else {
			respuesta.put("mensaje", "No existen registros DP");
			respuesta.put("status", HttpStatus.NOT_FOUND);
			respuesta.put("fecha", new Date());	
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
		}
	}

	 

}