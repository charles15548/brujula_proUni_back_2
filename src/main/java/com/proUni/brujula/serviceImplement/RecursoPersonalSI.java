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
import com.proUni.brujula.models.Noticias;
import com.proUni.brujula.models.NoticiasLike;
import com.proUni.brujula.models.RecursosPersonal;
import com.proUni.brujula.repository.RecursoProfesionalRepository;
import com.proUni.brujula.repository.NoticiaLikeRepository;
import com.proUni.brujula.repository.NoticiasRepository;
import com.proUni.brujula.repository.RecursoPersonalRepository;
import com.proUni.brujula.service.RecursoProfesionalService;
import com.proUni.brujula.service.NoticiasService;
import com.proUni.brujula.service.RecursoPersonalService;

import DTO.NoticiasProjection;

@Service
public class RecursoPersonalSI implements RecursoPersonalService{

	@Autowired
	private RecursoPersonalRepository dao;

    private final String BUCKET = "img/desarrolloPersonal"; 

	Utilitarios util = new Utilitarios();
	
	 
	 
	@Override
	public ResponseEntity<Map<String, Object>> listarRecursosPersonales() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ResponseEntity<Map<String, Object>> listarRecursosPersonalesPorTipo(Long tipoId) {
		 Map<String, Object> respuesta = new HashMap<>();
	        List<RecursosPersonal> recurso = dao.findByDpId(tipoId);

	        if (recurso.isEmpty()) {
	            respuesta.put("mensaje", "No existen registros");
	            respuesta.put("status", HttpStatus.NOT_FOUND);
	            respuesta.put("fecha", new Date());
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
	        }

	        respuesta.put("mensaje", "Listado");
	        respuesta.put("recurso_personal", recurso);
	        respuesta.put("status", HttpStatus.OK);
	        respuesta.put("fecha", new Date());
	        return ResponseEntity.ok(respuesta);
	}
	@Override
	public ResponseEntity<Map<String, Object>> crearRecursosPersonales(String titulo, String descripcion, String link,
			MultipartFile img, Long dpId) {
		Map<String, Object> respuesta = new HashMap<>();
		try {
			String imageUrl = util.subirImagen(img, util.filename(img), BUCKET);
			
			RecursosPersonal personal = new RecursosPersonal(titulo, descripcion,link,imageUrl,dpId);
			RecursosPersonal nueva = dao.save(personal);
			respuesta.put("mensaje", "Creado con existo");
            respuesta.put("recurso_personal", nueva);
			return ResponseEntity.ok(respuesta);
		}catch (Exception e) {
			respuesta.put("mensaje", "Error:" + e.getMessage());
			return ResponseEntity.badRequest().body(respuesta);
		}
	}
	@Override
	public ResponseEntity<Map<String, Object>> actualizarRecursosPersonales(Long id, String titulo, String descripcion,
			String link, MultipartFile img, Long dpId) throws Exception {
		Map<String, Object> respuesta = new HashMap<>();
		Optional<RecursosPersonal> existe = dao.findById(id);
		
		if(existe.isPresent()) {
			RecursosPersonal r = existe.get();
			
			if(img != null && !img.isEmpty()) {
				String imageUrl = util.actualizarArchivo(img, BUCKET, r.getImg());
				r.setImg(imageUrl);
			}
			r.setTitulo(titulo);
			r.setDescripcion(descripcion);
			r.setLink(link);
			r.setFechaRegistro(LocalDateTime.now());
			dao.save(r);
			
			
			
			respuesta.put("mensaje", "Actualizado con éxito");
	        respuesta.put("recurso_personal", r);
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
	public ResponseEntity<Map<String, Object>> eliminarRecursosPersonales(Long id) throws Exception {
		Map<String, Object> respuesta = new HashMap<>();
		Optional<RecursosPersonal> existe = dao.findById(id);
		
		if(existe.isPresent()) {
			RecursosPersonal p = existe.get();
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
