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
import com.proUni.brujula.models.servicios;
import com.proUni.brujula.models.DesarrolloProfesional;
import com.proUni.brujula.models.Noticias;
import com.proUni.brujula.models.NoticiasLike;
import com.proUni.brujula.repository.RecursoProfesionalRepository;
import com.proUni.brujula.repository.ServiciosRepository;
import com.proUni.brujula.repository.NoticiaLikeRepository;
import com.proUni.brujula.repository.NoticiasRepository;
import com.proUni.brujula.service.RecursoProfesionalService;
import com.proUni.brujula.service.ServicioService;
import com.proUni.brujula.service.NoticiasService;

import DTO.NoticiasProjection;
import com.proUni.brujula.serviceImplement.Utilitarios;
@Service
public class ServiciosSI implements ServicioService{

	@Autowired
	private ServiciosRepository dao;
	public final String BUCKET = "img/servicios"; 
	Utilitarios util  = new Utilitarios();
	

	@Override
	public ResponseEntity<Map<String, Object>> listarServiciosPorTipo(String tipo) {
		Map<String, Object> respuesta = new HashMap<>();
		List<servicios> s = dao.findByTipo(tipo);
		
		if(s.isEmpty()) {
			respuesta.put("mensaje", "No existen registros");
            respuesta.put("status", HttpStatus.NOT_FOUND);
            respuesta.put("fecha", new Date());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
		}
		respuesta.put("mensaje", "Listado");
        respuesta.put("servicio", s);
        respuesta.put("status", HttpStatus.OK);
        respuesta.put("fecha", new Date());
        return ResponseEntity.ok(respuesta);
	}

	@Override
	public ResponseEntity<Map<String, Object>> listarServiciosPorFacultad(String facultad) {
		Map<String, Object> respuesta = new HashMap<>();
		List<servicios> s = dao.findByFacultad(facultad);
		
		if(s.isEmpty()) {
			respuesta.put("mensaje", "No existen registros");
            respuesta.put("status", HttpStatus.NOT_FOUND);
            respuesta.put("fecha", new Date());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
		}
		respuesta.put("mensaje", "Listado");
        respuesta.put("servicio", s);
        respuesta.put("status", HttpStatus.OK);
        respuesta.put("fecha", new Date());
        return ResponseEntity.ok(respuesta);
	}

	@Override
	public ResponseEntity<Map<String, Object>> listarServiciosPorTipoYFacultad(String tipo, String facultad) {
		Map<String, Object> respuesta = new HashMap<>();
		List<servicios> s = dao.findByTipoAndFacultad(tipo,facultad);
		
		if(s.isEmpty()) {
			respuesta.put("mensaje", "No existen registros");
            respuesta.put("status", HttpStatus.NOT_FOUND);
            respuesta.put("fecha", new Date());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
		}
		respuesta.put("mensaje", "Listado");
        respuesta.put("servicio", s);
        respuesta.put("status", HttpStatus.OK);
        respuesta.put("fecha", new Date());
        return ResponseEntity.ok(respuesta);
	}

	

	@Override
	public ResponseEntity<Map<String, Object>> crearServicios(String tipo, String facultad, String nombre,
			String ubicacion, String link, MultipartFile imagen) {
		Map<String, Object> respuesta = new HashMap<>();
		
		String imagenUrl = null;
		if(imagen != null && !imagen.isEmpty()) {
			try {
				imagenUrl =util.subirImagen(imagen, util.filename(imagen), BUCKET);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		servicios s = new servicios(tipo, facultad, nombre, ubicacion, link,imagenUrl );
		servicios nueva = dao.save(s);
		respuesta.put("mensaje", "Creado con exito");
		respuesta.put("servicio", nueva);
		return ResponseEntity.ok(respuesta);
	}

	@Override
	public ResponseEntity<Map<String, Object>> actualizarServicios(Long id, String tipo, String facultad, String nombre,
			String ubicacion, String link,MultipartFile imagen) throws Exception {
		Map<String, Object> respuesta = new HashMap<>();
		Optional<servicios> existe = dao.findById(id);
		if(existe.isPresent()) {
			
			servicios s = existe.get();
			if(imagen!= null && !imagen.isEmpty()) {
				String imgUrl = util.actualizarArchivo(imagen, BUCKET, s.getImagen());
				s.setImagen(imgUrl);
			}
			s.setNombre(nombre);
			s.setUbicacion(ubicacion);
			s.setLink(link);
			s.setFechaPublicacion(LocalDateTime.now());
			dao.save(s);

			respuesta.put("mensaje", "actualizado con exito");
			respuesta.put("servicio", s);
			respuesta.put("status", HttpStatus.OK.value());
            respuesta.put("fecha", new Date());
            return ResponseEntity.ok(respuesta);
			
		}else {
            respuesta.put("mensaje", "No se encontró servicio para actualizar");
            respuesta.put("status", HttpStatus.NOT_FOUND.value());
            respuesta.put("fecha", new Date());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
        }
		
	}

	@Override
	public ResponseEntity<Map<String, Object>> eliminarServicios(Long id) {
		Map<String, Object> respuesta = new HashMap<>();
		Optional<servicios> existe = dao.findById(id);
		if(existe.isPresent()) {
			servicios s = existe.get();
			
			if(s.getImagen() != null && !s.getImagen().isEmpty()) {
				try {
					util.eliminarImagenSupabase(util.splitArchivo(s.getImagen()), BUCKET);
				}catch (Exception e) {
        			respuesta.put("mensaje", "Error al eliminar la imagen de Supabase: " + e.getMessage());
                    respuesta.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
                    respuesta.put("fecha", new Date());
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
        		}
			}
			
			dao.delete(s);
			  respuesta.put("mensaje", "Noticia eliminada con éxito");
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
