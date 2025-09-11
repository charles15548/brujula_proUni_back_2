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
	
	
	private final String SUPABASE_URL = "https://ykayyxqcplawwwyjqbrq.supabase.co";
    private final String SUPABASE_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InlrYXl5eHFjcGxhd3d3eWpxYnJxIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NTU2MjE2MDYsImV4cCI6MjA3MTE5NzYwNn0.hrKC7HNWdM-AcFBAx1_PAOVE6gzhwrkHXwhLfLqyJ9k";
    private final String BUCKET = "img/perfil"; 
	
	@Autowired
    private PerfilEstudianteRespository dao;

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
	            String filename = UUID.randomUUID() + "_" + foto.getOriginalFilename();
	            String imageUrl = subirImagen(foto, filename);

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

	
	
	 private String subirImagen(MultipartFile imagen, String filename) throws Exception {
	        String uploadUrl = SUPABASE_URL + "/storage/v1/object/" + BUCKET + "/" + filename;
	        
	        System.out.println("URL de subida: " + uploadUrl);
	        
	        HttpRequest request = HttpRequest.newBuilder()
	                .uri(URI.create(uploadUrl))
	                .header("Authorization", "Bearer " + SUPABASE_KEY)
	                .header("Content-Type", imagen.getContentType())
	                .header("x-upsert", "true")
	                .POST(HttpRequest.BodyPublishers.ofByteArray(imagen.getBytes()))
	                .build();

	        HttpResponse<String> response = HttpClient.newHttpClient()
	                .send(request, HttpResponse.BodyHandlers.ofString());
	        
	        System.out.println("Status Code: " + response.statusCode());
	        System.out.println("Respuesta: " + response.body());
	        
	        if (response.statusCode() != 200 && response.statusCode() != 201) {
	            throw new RuntimeException("Error al subir imagen. Status: " + response.statusCode() + " - " + response.body());
	        }
	        
	        return SUPABASE_URL + "/storage/v1/object/public/" + BUCKET + "/" + filename;
	    }
	

}

