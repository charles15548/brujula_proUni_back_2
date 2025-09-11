package com.proUni.brujula.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proUni.brujula.models.AuthEstudiantes;
import com.proUni.brujula.serviceImplement.AuthEstudianteSI;

@RestController
@RequestMapping("/api/authEstudiante")
@CrossOrigin(origins = {"http://localhost:3000", "https://soporte2.intelectiasac.com"})
public class AuthEstudiantesController {
	
	@Autowired
	AuthEstudianteSI authService;
	
	 
	 @PostMapping("/login")
	 public ResponseEntity<Map<String, Object>> login(@RequestBody AuthEstudiantes loginRequest) {
		    Map<String, Object> response = authService.login(
		        loginRequest.getCorreo(),
		        loginRequest.getPassword()
		    );         return ResponseEntity.ok(response);
		}

}
