package com.proUni.brujula.serviceImplement;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proUni.brujula.models.AuthEstudiantes;
import com.proUni.brujula.repository.AuthEstudianteRespository;

@Service
public class AuthEstudianteSI {
	
	@Autowired
    private AuthEstudianteRespository repository;

	
	public Map<String, Object> login(String correo, String password) {
	    Map<String, Object> response = new HashMap<>();
	    
	    Optional<AuthEstudiantes> opt = repository.findByCorreo(correo);

	    if (opt.isEmpty()) {
	        response.put("success", false);
	        response.put("message", "❌ Correo no registrado");
	        return response;
	    }

	    AuthEstudiantes estudiante = opt.get();

	    if (estudiante.getPassword().equals(password)) {
	        response.put("success", true);
	        response.put("message", "✅ Login exitoso");
	        response.put("id", estudiante.getId());
	    } else {
	        response.put("success", false);
	        response.put("message", "❌ Contraseña incorrecta");
	    }

	    return response;
	}
}

