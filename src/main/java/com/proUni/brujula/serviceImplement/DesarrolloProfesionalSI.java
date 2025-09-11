package com.proUni.brujula.serviceImplement;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.proUni.brujula.models.DesarrolloProfesional;
import com.proUni.brujula.repository.DesarrolloProfesionalRepository;
import com.proUni.brujula.service.DesarrolloProfesionalService;

@Service
public class DesarrolloProfesionalSI implements DesarrolloProfesionalService{
	
	@Autowired 
	private DesarrolloProfesionalRepository dao;

	@Override
	public ResponseEntity<Map<String, Object>> listarDesarrolloProfesional() {
		Map<String, Object> respuesta = new HashMap<>();
		List<DesarrolloProfesional> desarrolloProfesional = dao.findAll();
		if(!desarrolloProfesional.isEmpty()) {
			respuesta.put("mensaje", "lista D Profesional");
			respuesta.put("profesional", desarrolloProfesional);
			respuesta.put("status", HttpStatus.OK);
			respuesta.put("fecha", new Date());	
			return ResponseEntity.status(HttpStatus.OK).body(respuesta);
		}else {
			respuesta.put("mensaje", "No existen registros DProfesional");
			respuesta.put("status", HttpStatus.NOT_FOUND);
			respuesta.put("fecha", new Date());	
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
		}
	}
	
}
