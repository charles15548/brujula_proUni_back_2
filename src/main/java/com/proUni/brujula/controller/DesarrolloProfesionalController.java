package com.proUni.brujula.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proUni.brujula.service.DesarrolloProfesionalService;

@RestController
@RequestMapping("/api/desarrollo_profesional")
@CrossOrigin(origins = {"http://localhost:3000", "https://soporte2.intelectiasac.com"})
public class DesarrolloProfesionalController {

	@Autowired
	private DesarrolloProfesionalService serivice;
	
	@GetMapping
	public ResponseEntity<Map<String, Object>> listarDesarrolloProfesional(){
		return serivice.listarDesarrolloProfesional();
	}
}
