package com.proUni.brujula.serviceImplement;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
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


import com.proUni.brujula.models.PerfilesEstudiantes;
import com.proUni.brujula.repository.PerfilEstudianteRespository;
import com.proUni.brujula.service.PerfilService;


@Service
public class PerfilEstudianteSI implements PerfilService{
	
	
	
	private final String BUCKET = "img/perfil"; 
	
	@Autowired
    private PerfilEstudianteRespository dao;

	Utilitarios util = new Utilitarios();
	@Override
	public ResponseEntity<Map<String, Object>> listarPerfil() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Map<String, Object>> crearPerfil(String nombre, String apellido, String carrera,
			String biografia, String proyectoVida, String urlCv, MultipartFile foto) {
		 Map<String, Object> respuesta = new HashMap<>();
	        try {
	            // Subir imagen
	            String imageUrl = util.subirImagen(foto, util.filename(foto),BUCKET);

	            // Guardar noticia
	            PerfilesEstudiantes pe = new PerfilesEstudiantes(nombre,apellido,carrera,biografia,proyectoVida,urlCv,imageUrl);
	            PerfilesEstudiantes nueva = dao.save(pe);
	            
	            respuesta.put("mensaje", "Perfil creada con éxito");
	            respuesta.put("noticia", nueva);
	            return ResponseEntity.ok(respuesta);
	            
	        } catch (Exception e) {
	            respuesta.put("mensaje", "Error: " + e.getMessage());
	            return ResponseEntity.badRequest().body(respuesta);
	        }
	}

	@Override
	public ResponseEntity<Map<String, Object>> actualizarPerfil(Long id, String nombre, String apellido, String carrera,
			String biografia, String proyectoVida, String urlCv, MultipartFile foto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Map<String, Object>> eliminarPerfil(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PerfilesEstudiantes obtenerPerfilPorAuthId(Long authId) {
	        return dao.findByEstudianteId(authId)
	                .orElseThrow(() -> new RuntimeException("Perfil no encontrado para authId: " + authId));
	}

	@Override
	public ResponseEntity<Map<String, Object>> toggleLike(Long noticiaId, Long estudianteId) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	
	

}

