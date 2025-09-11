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

import com.proUni.brujula.models.Noticias;
import com.proUni.brujula.models.NoticiasLike;
import com.proUni.brujula.repository.NoticiaLikeRepository;
import com.proUni.brujula.repository.NoticiasRepository;
import com.proUni.brujula.service.NoticiasService;

import DTO.NoticiasProjection;

@Service
public class NoticiasSI implements NoticiasService{

	@Autowired
	private NoticiasRepository dao;
	@Autowired
	private NoticiaLikeRepository likeRepo;
	
	private final String SUPABASE_URL = "https://ykayyxqcplawwwyjqbrq.supabase.co";
    private final String SUPABASE_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InlrYXl5eHFjcGxhd3d3eWpxYnJxIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NTU2MjE2MDYsImV4cCI6MjA3MTE5NzYwNn0.hrKC7HNWdM-AcFBAx1_PAOVE6gzhwrkHXwhLfLqyJ9k";
    private final String BUCKET = "img/noticias"; 

	@Override
	public ResponseEntity<Map<String, Object>> listarBaseNoticias() {
		Map<String,Object> respuesta = new HashMap<>();	
		List<Noticias> noticias = dao.findAll();
		
		if(!noticias.isEmpty()) {
			respuesta.put("mensaje", "Lista de noticias");
			respuesta.put("noticias", noticias);
			respuesta.put("status", HttpStatus.OK);
			respuesta.put("fecha", new Date());	
			return ResponseEntity.status(HttpStatus.OK).body(respuesta);
		}else {
			respuesta.put("mensaje", "No existen registros");
			respuesta.put("status", HttpStatus.NOT_FOUND);
			respuesta.put("fecha", new Date());	
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
		}
	}
	@Override
    public ResponseEntity<Map<String, Object>> listarNoticias(Long userId) {
        Map<String, Object> respuesta = new HashMap<>();
        List<NoticiasProjection> noticias = dao.listarNoticiasConLikes(userId);

        if (noticias.isEmpty()) {
            respuesta.put("mensaje", "No existen registros");
            respuesta.put("status", HttpStatus.NOT_FOUND);
            respuesta.put("fecha", new Date());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
        }

        respuesta.put("mensaje", "Lista de noticias");
        respuesta.put("noticias", noticias);
        respuesta.put("status", HttpStatus.OK);
        respuesta.put("fecha", new Date());
        return ResponseEntity.ok(respuesta);
    }
	
	
	@Override
    public ResponseEntity<Map<String, Object>> crearNoticia(String titulo,String gancho, String contenido,String fuente, MultipartFile imagen) {
        Map<String, Object> respuesta = new HashMap<>();
        try {
            // Subir imagen
            String filename = UUID.randomUUID() + "_" + imagen.getOriginalFilename();
            String imageUrl = subirImagen(imagen, filename);

            // Guardar noticia
            Noticias noticia = new Noticias(titulo,gancho, contenido,fuente, imageUrl);
            Noticias nueva = dao.save(noticia);
            
            respuesta.put("mensaje", "Noticia creada con éxito");
            respuesta.put("noticia", nueva);
            return ResponseEntity.ok(respuesta);
            
        } catch (Exception e) {
            respuesta.put("mensaje", "Error: " + e.getMessage());
            return ResponseEntity.badRequest().body(respuesta);
        }
    }
	
	@Override
	public ResponseEntity<Map<String,Object>>actualizarNoticia(Long id,String titulo,String gancho, String contenido,String fuente ,MultipartFile imagen) {
		Map<String, Object> respuesta = new HashMap<>();
		Optional<Noticias> existe = dao.findById(id);
		
		if (existe.isPresent()) {
			Noticias n = existe.get();
			
			
            if(imagen != null && !imagen.isEmpty()) {
	            try {
	            	String filename = UUID.randomUUID() + "_" + imagen.getOriginalFilename();
	            	String imageUrl = subirImagen(imagen, filename);
	            	n.setImagenUrl(imageUrl);
				} catch (Exception e) {
					respuesta.put("mensaje", "Error al subir la imagen: " + e.getMessage());
	                respuesta.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
	                respuesta.put("fecha", new Date());
	                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
				}	
            }
			
			
            
            n.setTitulo(titulo);
            n.setGancho(gancho);
            n.setContenido(contenido);
            n.setFuente(fuente);
            n.setFechaPublicacion(LocalDateTime.now());
            dao.save(n);

            respuesta.put("mensaje", "Noticia actualizada con éxito");
            respuesta.put("noticia", n);
            respuesta.put("status", HttpStatus.OK.value());
            respuesta.put("fecha", new Date());
            return ResponseEntity.ok(respuesta);
        } else {
            respuesta.put("mensaje", "No se encontró la noticia para actualizar");
            respuesta.put("status", HttpStatus.NOT_FOUND.value());
            respuesta.put("fecha", new Date());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
        }
	}
	
	
	 @Override
	    public ResponseEntity<Map<String, Object>> eliminarNoticia(Long id) {
	        Map<String, Object> respuesta = new HashMap<>();
	        Optional<Noticias> noticiaOpt = dao.findById(id);

	        if (noticiaOpt.isPresent()) {
	        	Noticias noticia = noticiaOpt.get();
	        	if(noticia.getImagenUrl() != null && !noticia.getImagenUrl().isEmpty()) {
	        		try {
	        			String[] parts = noticia.getImagenUrl().split("/");
	        			String filename = parts[parts.length -1];
	        			eliminarImagenSupabase(filename);
	        		}catch (Exception e) {
	        			respuesta.put("mensaje", "Error al eliminar la imagen de Supabase: " + e.getMessage());
	                    respuesta.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
	                    respuesta.put("fecha", new Date());
	                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
	        		}
	        	}
	            dao.delete(noticia);
	            respuesta.put("mensaje", "Noticia eliminada con éxito");
	            respuesta.put("status", HttpStatus.OK.value());
	            respuesta.put("fecha", new Date());
	            return ResponseEntity.ok(respuesta);
	        } else {
	            respuesta.put("mensaje", "No se encontró la noticia para eliminar");
	            respuesta.put("status", HttpStatus.NOT_FOUND.value());
	            respuesta.put("fecha", new Date());
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
	        }
	    }
	 
	 
	 
	 
	 private String subirImagen(MultipartFile imagen, String filename) throws Exception {
	        String uploadUrl = SUPABASE_URL + "/storage/v1/object/" + BUCKET + "/" + filename;
	        
	        System.out.println("URL de subida: " + uploadUrl);
	        System.out.println("Tamaño archivo: " + imagen.getSize() + " bytes");
	        System.out.println("Tipo de archivo: " + imagen.getContentType());
	        
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

	 private void eliminarImagenSupabase(String filename) throws Exception {
		    String deleteUrl = SUPABASE_URL + "/storage/v1/object/" + BUCKET + "/" + filename;

		    HttpRequest request = HttpRequest.newBuilder()
		            .uri(URI.create(deleteUrl))
		            .header("Authorization", "Bearer " + SUPABASE_KEY)
		            .DELETE()
		            .build();

		    HttpResponse<String> response = HttpClient.newHttpClient()
		            .send(request, HttpResponse.BodyHandlers.ofString());

		    if (response.statusCode() != 200 && response.statusCode() != 204) {
		        throw new RuntimeException("Error al eliminar imagen en Supabase. Status: " 
		                                   + response.statusCode() + " - " + response.body());
		    }
		}





	

	 @Override
	    public ResponseEntity<Map<String, Object>> toggleLike(Long noticiaId, Long estudianteId) {
	        Map<String, Object> respuesta = new HashMap<>();

	        Optional<NoticiasLike> likeOpt = likeRepo.findByNoticiaIdAndEstudianteId(noticiaId, estudianteId);

	        if (likeOpt.isPresent()) {
	            // ya existe → quitar like
	            likeRepo.delete(likeOpt.get());
	            respuesta.put("mensaje", "Like eliminado");
	            respuesta.put("meGusta", false);
	        } else {
	            // no existe → dar like
	            
	            Noticias noticia = dao.findById(noticiaId)
                        .orElseThrow(() -> new RuntimeException("Noticia no encontrada"));
	            NoticiasLike like = new NoticiasLike();
	            like.setNoticia(noticia);
	            like.setEstudianteId(estudianteId);
	            like.setFechaLike(LocalDateTime.now());
	            likeRepo.save(like);
	            respuesta.put("mensaje", "Like registrado");
	            respuesta.put("meGusta", true);
	        }

	        // total de likes actualizado
	        long totalLikes = likeRepo.countByNoticiaId(noticiaId);
	        respuesta.put("totalLikes", totalLikes);

	        return ResponseEntity.ok(respuesta);
	    }


	

	
}
