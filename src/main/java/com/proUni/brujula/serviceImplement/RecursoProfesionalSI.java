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

import com.proUni.brujula.models.RecursosProfesional;
import com.proUni.brujula.models.DesarrolloProfesional;
import com.proUni.brujula.models.Noticias;
import com.proUni.brujula.models.NoticiasLike;
import com.proUni.brujula.repository.RecursoProfesionalRepository;
import com.proUni.brujula.repository.NoticiaLikeRepository;
import com.proUni.brujula.repository.NoticiasRepository;
import com.proUni.brujula.service.RecursoProfesionalService;
import com.proUni.brujula.service.NoticiasService;

import DTO.NoticiasProjection;
import com.proUni.brujula.serviceImplement.Utilitarios;
@Service
public class RecursoProfesionalSI implements RecursoProfesionalService{

	@Autowired
	private RecursoProfesionalRepository dao;
	
	public final String BUCKET = "img/desarrolloProfesional"; 

    Utilitarios util = new Utilitarios();
	
	
	 
	 
	 
	@Override
	public ResponseEntity<Map<String, Object>> listarHerramientasProfesional() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public ResponseEntity<Map<String, Object>> listarHerramientasProfesionalPorTipo(Long userId) {
		 Map<String, Object> respuesta = new HashMap<>();
	        List<RecursosProfesional> herramienta = dao.findByDpId(userId);

	        if (herramienta.isEmpty()) {
	            respuesta.put("mensaje", "No existen registros");
	            respuesta.put("status", HttpStatus.NOT_FOUND);
	            respuesta.put("fecha", new Date());
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
	        }

	        respuesta.put("mensaje", "Listado");
	        respuesta.put("herramienta", herramienta);
	        respuesta.put("status", HttpStatus.OK);
	        respuesta.put("fecha", new Date());
	        return ResponseEntity.ok(respuesta);
	}
	@Override
	public ResponseEntity<Map<String, Object>> crearHerramientasProfesional(String titulo, String descripcion,
			String link, String video, MultipartFile imagen, Long dpId, MultipartFile archivo) {
		
		Map<String, Object> respuesta = new HashMap<>();
		try {
			String imageUrl = null;
	        String archivoUrl = null;
	        if(imagen != null && !imagen.isEmpty()) {
	        	imageUrl = util.subirImagen(imagen, util.filename(imagen), BUCKET);
	        }
			if(archivo != null && !archivo.isEmpty()) {
				archivoUrl = util.subirImagen(archivo, util.filename(archivo), BUCKET);
			}
			RecursosProfesional profesional = new RecursosProfesional(titulo, descripcion,link,video,imageUrl,dpId, archivoUrl);
			RecursosProfesional nueva = dao.save(profesional);
			respuesta.put("mensaje", "Creado con existo");
            respuesta.put("profesional", nueva);
			return ResponseEntity.ok(respuesta);
		}catch (Exception e) {
			respuesta.put("mensaje", "Error:" + e.getMessage());
			return ResponseEntity.badRequest().body(respuesta);
		}
	}
	
	@Override
	public ResponseEntity<Map<String, Object>> actualizarHerramientasProfesional(Long id, String titulo, String descripcion, String link, String video, MultipartFile imagen,Long dpId, MultipartFile archivo) throws Exception {
		Map<String, Object> respuesta = new HashMap<>();
		Optional<RecursosProfesional> existe = dao.findById(id);
		
		if(existe.isPresent()) {
			RecursosProfesional r = existe.get();
			
			if (imagen != null && !imagen.isEmpty()) {
			    String imgURL = util.actualizarArchivo(imagen, BUCKET, r.getImg());
			    r.setImg(imgURL);
			}
			if (archivo != null && !archivo.isEmpty()) {
			    String archivoURL = util.actualizarArchivo(archivo, BUCKET, r.getArchivo());
			    r.setArchivo(archivoURL);
			}
			
			
			r.setTitulo(titulo);
			r.setDescripcion(descripcion);
			r.setLink(link);
			r.setVideo(video);
			r.setFechaRegistro(LocalDateTime.now());
			
			dao.save(r);
			

            respuesta.put("mensaje", "Actualizada con éxito");
            respuesta.put("noticia", r);
            respuesta.put("status", HttpStatus.OK.value());
            respuesta.put("fecha", new Date());
            return ResponseEntity.ok(respuesta);
		}
		try {
			
			return ResponseEntity.ok(respuesta);
		}catch (Exception e) {
			respuesta.put("mensaje", "Error:" + e.getMessage());
			return ResponseEntity.badRequest().body(respuesta);
		}
	}



	@Override
	public ResponseEntity<Map<String, Object>> eliminarHerramientasProfesional(Long id) {
		
		Map<String, Object> respuesta = new HashMap<>();
		Optional<RecursosProfesional> existe = dao.findById(id);
		
		if(existe.isPresent()) {
			RecursosProfesional p = existe.get();
			if(p.getImg() != null && !p.getImg().isEmpty()) {
				try {
        			util.eliminarImagenSupabase(util.splitArchivo(p.getImg()), BUCKET);
        		}catch (Exception e) {
        			respuesta.put("mensaje", "Error al eliminar la imagen de Supabase: " + e.getMessage());
                    respuesta.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
                    respuesta.put("fecha", new Date());
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
        		}
			}
			if(p.getArchivo() != null && !p.getArchivo().isEmpty()) {
				try {
        			util.eliminarImagenSupabase(util.splitArchivo(p.getArchivo()), BUCKET);
        		}catch (Exception e) {
        			respuesta.put("mensaje", "Error al eliminar la archivo de Supabase: " + e.getMessage());
                    respuesta.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
                    respuesta.put("fecha", new Date());
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
        		}
			}
			dao.delete(p);
			respuesta.put("mensaje", "eliminada con éxito");
            respuesta.put("status", HttpStatus.OK.value());
            respuesta.put("fecha", new Date());
            return ResponseEntity.ok(respuesta);
		}else {
			respuesta.put("mensaje", "No se encontró para eliminar");
            respuesta.put("status", HttpStatus.NOT_FOUND.value());
            respuesta.put("fecha", new Date());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
		}
	}

	
}
